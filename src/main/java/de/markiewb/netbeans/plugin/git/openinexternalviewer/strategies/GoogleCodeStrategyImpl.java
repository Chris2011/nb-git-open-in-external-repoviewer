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
 * https://benno.markiewicz@code.google.com/p/nb-close-other-projects/
 * https://code.google.com/p/gitblit/
 *
 * @author markiewb
 */
@ServiceProvider(service = RepoStrategy.class)
public final class GoogleCodeStrategyImpl implements RepoStrategy {

    private final Pattern p = Pattern.compile("(?<protocol>http|https)://(?<username>.*?)@?(?<server>code\\.google\\.com)/p/(?<repo>.+?)/");

    @Override
    public String getUrl(RepoStrategy.Type type, String remote, String branchName, String branchRevId) {
        String url = null;
        if (this.supports(type, remote)) {
            Matcher matcher = p.matcher(remote);
            matcher.find();
            String protocol = matcher.group("protocol");
            String username = matcher.group("username");
            String server = matcher.group("server");
            String repo = matcher.group("repo");
            url = MessageFormat.format("{0}://{1}/p/{2}/source/list?name={3}", protocol, server, repo, branchName);
        }
        return url;
    }

    @Override
    public boolean supports(RepoStrategy.Type type, String remote) {
        if (RepoStrategy.Type.OPEN.equals(type)) {
            return p.matcher(remote).matches();
        }
        return false;
    }
    
    @Override
    public String getLabel() {
        return "Google Code";
    }

}
