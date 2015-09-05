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
import static de.markiewb.netbeans.plugin.git.openinexternalviewer.RepoStrategy.Type.OPEN;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openide.util.lookup.ServiceProvider;

/**
 * https://github.com/markiewb/nb-git-branch-in-statusbar.git
 * https://github.com/markiewb/nb-git-branch-in-statusbar/commits/master
 * git@github.com:markiewb/nb-git-branch-in-statusbar.git
 *
 * @author markiewb
 */
@ServiceProvider(service = RepoStrategy.class)
public final class GitHubStrategyImpl extends AbstractRepoStrategy {

    private final Pattern pHttpP1 = Pattern.compile("(?<protocol>http|https)://(?<username>.+?@)?github\\.com/(?<repo>.+)\\.git");
    //HACK could not express as regex, that is why I split it into two separate ones
    private final Pattern pHttpP2 = Pattern.compile("(?<protocol>http|https)://(?<username>.+?@)?github\\.com/(?<repo>.+)");
    private final Pattern pGit = Pattern.compile("git@github\\.com:(?<repo>.+)\\.git");
    private final Pattern pGit2 = Pattern.compile("git://github\\.com/(?<repo>.+)\\.git");
    public GitHubStrategyImpl() {
        addPattern(new PatternConfig(OPEN, pHttpP1, "<protocol>://github.com/<repo>/commits/<branch>"));
        addPattern(new PatternConfig(OPEN, pHttpP2, "<protocol>://github.com/<repo>/commits/<branch>"));
        addPattern(new PatternConfig(OPEN, pGit, "https://github.com/<repo>/commits/<branch>"));
        addPattern(new PatternConfig(OPEN, pGit2, "https://github.com/<repo>/commits/<branch>"));
    }

    @Override
    public String getLabel() {
        return "GitHub";
    }

}
