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
