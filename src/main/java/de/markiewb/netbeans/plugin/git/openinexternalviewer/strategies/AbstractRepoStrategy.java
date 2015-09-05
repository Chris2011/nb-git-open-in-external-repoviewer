/**
 * Copyright 2015 markiewb
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author markiewb
 */
public abstract class AbstractRepoStrategy implements RepoStrategy {

    private List<PatternConfig> patterns=new ArrayList<PatternConfig>();

    public void addPattern(PatternConfig pattern) {
        patterns.add(pattern);
    }

    @Override
    public String getUrl(RepoStrategy.Type type, String remote, String branchName, String branchRevId) {
        return new PatternTranslator().replacePatterns(type, remote, branchName, branchRevId, this.patterns);
    }

    @Override
    public boolean supports(RepoStrategy.Type type, String remote) {
        return new PatternTranslator().supports(type, remote, this.patterns);
    }

    public List<PatternConfig> getPatterns() {
        return patterns;
    }

}
