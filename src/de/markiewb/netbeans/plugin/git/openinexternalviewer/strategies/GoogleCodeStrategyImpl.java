/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.RepoStrategy;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.netbeans.libs.git.GitBranch;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author markiewb
 */
@ServiceProvider(service = RepoStrategy.class)
public final class GoogleCodeStrategyImpl implements RepoStrategy {

    @Override
    public String getUrl(String remote, String branchName, String branchRevId) {
        //origin/bootstrap at https://code.google.com/p/gitblit/
        //https://code.google.com/p/gitblit/source/list?name=bootstrap
        if (supports(remote)) {

            if (this.supports(remote)) {
                final String url = remote + "source/list?name=" + branchName;
                System.out.println("url = " + url);
                return url;
            }
        }
        return null;
    }

    @Override
    public boolean supports(String remote) {
        return remote.startsWith("https://code.google.com/p/");
    }

    @Override
    public String getLabel() {
        return "Google Code";
    }

}
