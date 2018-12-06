/*
 * Copyright 2015 markiewb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer.actions;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.Options;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.git.GitUtils;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.EditorPlaceHolderResolver;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.FileObjectPlaceHolderResolver;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.PlaceHolderResolver;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.PlaceHolderResolvers;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.WCPlaceHolderResolver;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.AbstractRepoStrategy;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.RepoStrategy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.editor.BaseDocument;
import org.netbeans.libs.git.GitBranch;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;

/**
 * Popup menu for a {@link RepoStrategy}.
 *
 * @author markiewb
 */
public abstract class AbstractRepositoryPopupAction extends AbstractAction implements ActionListener, Presenter.Popup {
    private static final Logger LOG = Logger.getLogger(AbstractRepositoryPopupAction.class.getName());

    @Override
    public void actionPerformed(ActionEvent e) {
        //NOP
    }

    /**
     * Like
     * <pre>
     * Github
     *    Show Log
     *    Show XXX
     * </pre>
     *
     * @param lookup
     * @return
     */
    public JMenu createMenu(final Lookup lookup) {
        RepoStrategyConfig config = detectRepositoryStrategy(lookup);

        if (null == config) {
            return null;
        }

        JMenu main = new JMenu(config.strategy.getLabel());
        List<JMenuItem> items = createMenuItems(config);

        for (JMenuItem item : items) {
            main.add(item);
        }

        return main;
    }

    @Override
    public JMenuItem getPopupPresenter() {
        final Lookup lookup = Utilities.actionsGlobalContext();
        JMenu main = createMenu(lookup);

        if (null != main) {
            setEnabled(main.getMenuComponentCount() > 0);

            return main;
        }

        return null;
    }

    protected List<JMenuItem> createMenuItems(RepoStrategyConfig strategyC) {
        if (null == strategyC) {
            return Collections.emptyList();
        }

        List<JMenuItem> items = new ArrayList<>();

        for (final RepoStrategy.Type type : getSUPPORTEDTYPES()) {
            final String url = strategyC.strategy.getUrl(type, strategyC.remoteURI, strategyC.resolvers);

            if (null == url) {
                continue;
            }

            JMenuItem jMenuItem = new JMenuItem(new OpenURLAction(url, type.getLabel()));
            items.add(jMenuItem);
        }
        return items;
    }

    protected RepoStrategyConfig detectRepositoryStrategy(Lookup lkp) {
        RepoStrategyConfig result = null;
        setEnabled(false);
        //only support one selected project
        Collection<? extends FileObject> lookupAll = lkp.lookupAll(FileObject.class);
        if (lookupAll.size() != 1) {
            return result;
        }
        final FileObject fileObject = lookupAll.iterator().next();

        FileObject gitRepoDirectory = GitUtils.getGitRepoDirectory(fileObject);
        if (gitRepoDirectory == null) {
            return result;
        }
        GitBranch activeBranch = GitUtils.getActiveBranch(gitRepoDirectory);
        if (activeBranch == null) {
            return result;
        }
        if (activeBranch.getTrackedBranch() == null) {
            //TODO support detached heads
            //TODO support tags
            return result;
        } else {
            final String remoteBranchName = activeBranch.getTrackedBranch().getName();
            //split "origin/master" to "origin" "master"
            //split "orgin/feature/myfeature" to "origin" "feature/myfeature"
            int indexOf = remoteBranchName.indexOf('/');
            if (indexOf <= 0 || remoteBranchName.startsWith("/") || remoteBranchName.endsWith("/")) {
                // no slash found OR
                // slash is the first char? NOGO
                //slash at the end? NOGO
                return result;
            }
            {
                final String origin = remoteBranchName.substring(0, indexOf);
                final String remoteName = remoteBranchName.substring(indexOf + 1);
                PlaceHolderResolver fileOrEditorResolver = getFileOrEditorPlaceholder(fileObject, gitRepoDirectory);

                WCPlaceHolderResolver wcPlaceHolderResolver = new WCPlaceHolderResolver(remoteName, activeBranch.getId());
                PlaceHolderResolvers resolvers = new PlaceHolderResolvers(wcPlaceHolderResolver, fileOrEditorResolver);
                final String remoteURI = GitUtils.getRemote(gitRepoDirectory, origin);
                return getStrategy(remoteURI, resolvers);
            }
        }
    }

    public PlaceHolderResolver getFileOrEditorPlaceholder(final FileObject fileObject, FileObject gitRepoDirectory) {
        BaseDocument doc = null;
        JTextComponent ed = EditorRegistry.lastFocusedComponent();
        if (ed != null) {
            doc = org.netbeans.editor.Utilities.getDocument(ed);
        }
        PlaceHolderResolver fileOrEditorResolver;
        if (null != doc) {

            FileObject fileObjectFromDoc = org.netbeans.modules.editor.NbEditorUtilities.getFileObject(doc);
            //Is the current editor for the current fileobject?
            //use the editor
            if (fileObject.equals(fileObjectFromDoc)) {
                fileOrEditorResolver = new EditorPlaceHolderResolver(ed, gitRepoDirectory);

            } else {
                fileOrEditorResolver = new FileObjectPlaceHolderResolver(fileObject, gitRepoDirectory);
            }
        } else {
            fileOrEditorResolver = new FileObjectPlaceHolderResolver(fileObject, gitRepoDirectory);

        }
        return fileOrEditorResolver;
    }

    protected abstract EnumSet<RepoStrategy.Type> getSUPPORTEDTYPES();

    protected RepoStrategyConfig getStrategy(String remoteURI, PlaceHolderResolvers resolvers) {
        List<String> strategyIds = new Options().getStrategies();
        List<RepoStrategy> strategies = new ArrayList<>();

        for (String strategyId : strategyIds) {
            strategies.add(new AbstractRepoStrategy(strategyId));
        }

        for (RepoStrategy strategy : strategies) {
            boolean supported = false;

            try {
                for (final RepoStrategy.Type type : getSUPPORTEDTYPES()) {
                    supported = null != strategy.getUrl(type, remoteURI, resolvers);

                    if (supported) {
                        RepoStrategyConfig config = new RepoStrategyConfig();

                        config.remoteURI = remoteURI;
                        config.strategy = strategy;
                        config.resolvers = resolvers;

                        return config;
                    }
                }
            } catch (Exception e) {
                LOG.warning(String.format("caught exception while calling strategy %s with %s :\n%s", strategy, remoteURI, e));
            }
        }

        return null;
    }

    protected class RepoStrategyConfig {
        RepoStrategy strategy;
        String remoteURI;
        PlaceHolderResolvers resolvers;
    }
}