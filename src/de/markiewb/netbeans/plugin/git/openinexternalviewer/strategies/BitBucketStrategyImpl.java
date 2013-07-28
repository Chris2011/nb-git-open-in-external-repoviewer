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
 * https://bitbucket.org/elbrecht/git-blog-examples.git
 * https://bitbucket.org/elbrecht/git-blog-examples/commits/branch/master
 * <p>
 * @author markiewb
 */
@ServiceProvider(service = RepoStrategy.class)
public final class BitBucketStrategyImpl implements RepoStrategy {

    private final Pattern p = Pattern.compile("(https)://(bitbucket.org)/(.+)(.git)");

    @Override
    public String getUrl(String remote, String branchName, String branchRevId) {
        String url = null;
        if (this.supports(remote)) {
            Matcher matcher = p.matcher(remote);
            matcher.find();
            String protocol = matcher.group(1);
            String server = matcher.group(2);
            String repo = matcher.group(3);
            String username = null;
            url = MessageFormat.format("{0}://{1}/{2}/commits/branch/{3}", protocol, server, repo, branchName);
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
        return "BitBucket";
    }

}
