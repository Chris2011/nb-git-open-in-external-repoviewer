/* 
 * Copyright 2015 markiewb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author markiewb
 */
public class PatternConfig {

    private final RepoStrategy.Type type;
    private final Pattern source;
    private final String target;

    public PatternConfig(RepoStrategy.Type type, Pattern source, String target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public String getTargetURL() {
        return target;
    }

    public RepoStrategy.Type getType() {
        return type;
    }

    public boolean matches(String text) {
        return source.matcher(text).matches();
    }

    public Matcher createMatcher(String text) {
        Matcher matcher = source.matcher(text);
        matcher.find();
        return matcher;
    }

}
