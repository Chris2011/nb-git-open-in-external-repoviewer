/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.BitBucketStrategyImpl;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.GitBlitStrategyImpl;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.GitHubStrategyImpl;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.GitWebStrategyImpl;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.GoogleCodeStrategyImpl;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.netbeans.libs.git.GitBranch;
import org.openide.awt.*;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Node;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.util.actions.Presenter;

@ActionID(category = "Git", id = "de.markiewb.netbeans.plugin.git.openinexternalviewer.OpenAction")
@ActionRegistration(lazy = false, displayName = "openinexternalviewer.OpenAction")
@ActionReferences({
    @ActionReference(path = "Projects/Actions")
})
public final class OpenAction extends AbstractAction implements ContextAwareAction {

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public Action createContextAwareInstance(Lookup lkp) {
        return new ContextAction(lkp);
    }

    static class ContextAction extends AbstractAction {
        //TODO use ServiceLoader to lookup strategies

        private final Collection<RepoStrategy> strategies = Arrays.asList(new GitBlitStrategyImpl(), new GoogleCodeStrategyImpl(), new GitHubStrategyImpl(), new BitBucketStrategyImpl(), new GitWebStrategyImpl());

        private final Lookup lkp;
        private String url = null;

        private ContextAction(Lookup lkp) {
            this.lkp = lkp;
            putValue(NAME, null);
            putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
            init(lkp);
        }

        private RepoStrategy getStrategy(String remote) {
            RepoStrategy usedStrategy = null;
            for (RepoStrategy strategy : strategies) {
                boolean supported = strategy.supports(remote);
                if (supported) {
                    usedStrategy = strategy;
                    break;
                }
            }
            return usedStrategy;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (null != url) {
                try {
                    HtmlBrowser.URLDisplayer.getDefault().showURLExternal(new URL(url));
                } catch (MalformedURLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        private void init(Lookup lkp) {
            setEnabled(false);
            //only support one project selected project
            Collection<? extends Project> lookupAll = lkp.lookupAll(Project.class);
            if (lookupAll != null && lookupAll.size() >= 2) {
                return;
            }
            
            Project project = lkp.lookup(Project.class);
            FileObject gitRepoDirectory = GitUtils.getGitRepoDirectory(project.getProjectDirectory());
            if (gitRepoDirectory == null) {
                return;
            }
            GitBranch activeBranch = GitUtils.getActiveBranch(gitRepoDirectory);
            if (activeBranch == null) {
                return;
            }
            if (activeBranch.getTrackedBranch() == null) {
                //TODO support detached heads
                //TODO support tags
                return;
            } else {
                final String remoteBranchName = activeBranch.getTrackedBranch().getName();
                final String origin = remoteBranchName.substring(0, remoteBranchName.indexOf("/"));
                final String remoteName = remoteBranchName.substring(remoteBranchName.indexOf("/") + 1);

                final String remote = GitUtils.getRemote(gitRepoDirectory, origin);
                final RepoStrategy strategy = getStrategy(remote);
                if (strategy != null) {
                    putValue(NAME, MessageFormat.format("<html>Open <b>{0}</b> at <b>{1}</b>", remoteBranchName, strategy.getLabel()));

                    url = strategy.getUrl(remote, remoteName, activeBranch.getId());
                    setEnabled(null != url);
                }

            }
        }

    }
}
