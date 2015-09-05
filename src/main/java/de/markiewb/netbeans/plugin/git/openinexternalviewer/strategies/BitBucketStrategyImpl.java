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
import static de.markiewb.netbeans.plugin.git.openinexternalviewer.RepoStrategy.Type.PULL_REQUEST;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openide.util.lookup.ServiceProvider;

/**
 * https://bitbucket.org/elbrecht/git-blog-examples.git
 * https://bitbucket.org/elbrecht/git-blog-examples/commits/branch/master
 *
 * https://www.bitbucket.org/elbrecht/git-blog-examples/pull-request/new?source=elbrecht/git-blog-examples::branch_name
 * https://gist.github.com/pitr/8242ac5253a72ddf3838
 *
 * @author markiewb
 */
@ServiceProvider(service = RepoStrategy.class)
public final class BitBucketStrategyImpl extends AbstractRepoStrategy {

    private final Pattern p = Pattern.compile("(?<protocol>https)://(?<username>.+?@)?bitbucket\\.org/(?<repo>.+)\\.git");
    private final Pattern pGit = Pattern.compile("(?<username>git)@bitbucket\\.org:(?<repo>.+)\\.git");

    public BitBucketStrategyImpl() {

        addPattern(new PatternConfig(OPEN, p, "<protocol>://bitbucket.org/<repo>/commits/branch/<branch>"));
        addPattern(new PatternConfig(OPEN, pGit, "https://bitbucket.org/<repo>/commits/branch/<branch>"));

        addPattern(new PatternConfig(PULL_REQUEST, p, "<protocol>://bitbucket.org/<repo>/pull-request/new?source=<repo>::<branch>"));
        addPattern(new PatternConfig(PULL_REQUEST, pGit, "https://bitbucket.org/<repo>/pull-request/new?source=<repo>::<branch>"));
    }

    @Override
    public String getLabel() {
        return "BitBucket";
    }

}
