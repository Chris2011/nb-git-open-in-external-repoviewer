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
 * https://benno.markiewicz@code.google.com/p/nb-close-other-projects/
 * https://code.google.com/p/gitblit/
 *
 * @author markiewb
 */
@ServiceProvider(service = RepoStrategy.class)
public final class GoogleCodeStrategyImpl extends AbstractRepoStrategy {

    private final Pattern p = Pattern.compile("(?<protocol>http|https)://(?<username>.*?)@?code\\.google\\.com/p/(?<repo>.+?)/");

    public GoogleCodeStrategyImpl() {
        addPattern(new PatternConfig(OPEN, p, "<protocol>://code.google.com/p/<repo>/source/list?name=<branch>"));
    }

    @Override
    public String getLabel() {
        return "Google Code";
    }

}
