
package de.markiewb.netbeans.plugin.git.openinexternalviewer;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

@ActionID(category = "Git", id = "de.markiewb.netbeans.plugin.git.openinexternalviewer.PullRequestAction")
@ActionRegistration(lazy = false, displayName = "openinexternalviewer.PullRequestAction")
@ActionReferences({
    @ActionReference(path = "Projects/Actions", position = 505)
})
public final class PullRequestAction extends UrlAction {

    public PullRequestAction() {
        super(RepoStrategy.Type.PULL_REQUEST, "Create Pull Request at {1}");
    }

}
