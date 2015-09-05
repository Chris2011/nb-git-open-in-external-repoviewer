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
import java.util.regex.Pattern;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author markiewb
 */
@ServiceProvider(service = RepoStrategy.class)
public final class GitBlitStrategyImpl extends AbstractRepoStrategy {

    private final Pattern p0_source = Pattern.compile("(?<protocol>http|https)://(?<username>.+?@)?(?<server>.+?)/(git/r)/(?<repo>.+)\\.git");
    private final Pattern p1_source = Pattern.compile("(?<protocol>http|https)://(?<username>.+?@)?(?<server>.+?)/(git|r)/(?<repo>.+)\\.git");
    private final String p0_target = "<protocol>://<server>/git/log/<repo|escapeSlashWithBang>.git/refs!heads!<branch|escapeSlashWithBang>";
    private final String p1_target = "<protocol>://<server>/log/<repo|escapeSlashWithBang>.git/refs!heads!<branch|escapeSlashWithBang>";

    public GitBlitStrategyImpl() {
        addPattern(new PatternConfig(OPEN, p0_source, p0_target));
        addPattern(new PatternConfig(OPEN, p1_source, p1_target));
    }

    @Override
    public String getLabel() {
        return "GitBlit";
    }

}
