
package de.markiewb.netbeans.plugin.git.openinexternalviewer;

import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import org.netbeans.api.project.Project;
import org.netbeans.libs.git.GitBranch;
import org.openide.awt.DynamicMenuContent;
import org.openide.awt.HtmlBrowser;
import org.openide.filesystems.FileObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

public abstract class UrlAction extends AbstractAction implements ContextAwareAction {

    private static final Logger LOG = Logger.getLogger(ContextAction.class.getName());
    private final RepoStrategy.Type type;
    private final String nameFormat;

    protected UrlAction(RepoStrategy.Type type, String nameFormat) {
        this.type = type;
        this.nameFormat = nameFormat;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public Action createContextAwareInstance(Lookup lkp) {
        return new ContextAction(type, nameFormat, lkp);
    }

    static class ContextAction extends AbstractAction {
        private String url = null;
        private final RepoStrategy.Type type;
        private final String nameFormat;

        private ContextAction(RepoStrategy.Type type, String nameFormat, Lookup lkp) {
            putValue(NAME, null);
            this.type = type;
            this.nameFormat = nameFormat;
            putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
            init(lkp);
        }

        private RepoStrategy getStrategy(String remote) {
            Collection<? extends RepoStrategy> strategies = Lookup.getDefault().lookupAll(RepoStrategy.class);
            RepoStrategy usedStrategy = null;
            for (RepoStrategy strategy : strategies) {
                boolean supported;
                try {
                    supported = strategy.supports(type, remote);
                } catch (Exception e) {
                    LOG.warning(String.format("caught exception while calling strategy %s with %s :\n%s", strategy, remote, e));
                    supported = false;
                }
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
                //split "origin/master" to "origin" "master"
                //split "orgin/feature/myfeature" to "origin" "feature/myfeature"
                int indexOf = remoteBranchName.indexOf("/");
                if (indexOf<=0 || remoteBranchName.startsWith("/") || remoteBranchName.endsWith("/")){
                    // no slash found OR
                    // slash is the first char? NOGO
                    //slash at the end? NOGO
                    return;
                }
                {
                    final String origin = remoteBranchName.substring(0, indexOf);
                    final String remoteName = remoteBranchName.substring(indexOf + 1);

                    final String remote = GitUtils.getRemote(gitRepoDirectory, origin);
                    final RepoStrategy strategy = getStrategy(remote);
                    if (strategy != null) {
                        putValue(NAME, MessageFormat.format(nameFormat, remoteBranchName, strategy.getLabel()));

                        url = strategy.getUrl(type, remote, remoteName, activeBranch.getId());
                        setEnabled(null != url);
                    }
                }
            }
        }

    }
}
