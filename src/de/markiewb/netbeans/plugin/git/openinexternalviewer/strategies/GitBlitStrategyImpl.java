/**
 * Copyright 2013 markiewb
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.RepoStrategy;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
