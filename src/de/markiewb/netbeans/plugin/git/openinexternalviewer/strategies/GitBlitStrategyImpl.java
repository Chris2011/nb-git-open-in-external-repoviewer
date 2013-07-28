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
public final class GitBlitStrategyImpl implements RepoStrategy {

    private final Pattern p = Pattern.compile("(http|https)://(.*?@)?(.+?)/git/(.+.git)");

    @Override
    public String getUrl(String remote, String branchName, String branchRevId) {
        String url = null;
        if (this.supports(remote)) {
            Matcher matcher = p.matcher(remote);
            matcher.find();
            String protocol = matcher.group(1);
            String username = matcher.group(2);
            String server = matcher.group(3);
            String repo = matcher.group(4);
            url = MessageFormat.format("{0}://{1}/log/{2}/refs!heads!{3}", protocol, server, escape(repo), escape(branchName));
            System.out.println("url = " + url);
        }
        return url;
    }

    @Override
    public boolean supports(String remote) {
        return p.matcher(remote).matches();
    }

    private String escape(String repo) {
        return repo.replaceAll("/", "!");
    }

    @Override
    public String getLabel() {
        return "GitBlit";
    }

}
