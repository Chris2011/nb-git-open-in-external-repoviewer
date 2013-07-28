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
public final class GitWebStrategyImpl implements RepoStrategy {

    private final Pattern p = Pattern.compile("(http|https)://(.*?)\\.(.+?)/(.+?).git");

    @Override
    public String getUrl(String remote, String branchName, String branchRevId) {
        String url = null;
        if (this.supports(remote)) {
            Matcher matcher = p.matcher(remote);
            matcher.find();
            String protocol = matcher.group(1);
            String subdomain = matcher.group(2);
            String server = matcher.group(3);
            String repo = matcher.group(4);
            //https://devel.dolmen-project.org/dolmen.menu.git
            //http://gitweb.dolmen-project.org/dolmen.menu.git/shortlog/refs/heads/master
            url = MessageFormat.format("{0}://gitweb.{1}/{2}.git/shortlog/refs/heads/{3}", protocol, server, repo, branchName);
            System.out.println("url = " + url);
        }
        return url;
    }

    @Override
    public boolean supports(String remote) {
        return p.matcher(remote).matches();
    }

    @Override
    public String getLabel() {
        return "GitWeb";
    }

}
