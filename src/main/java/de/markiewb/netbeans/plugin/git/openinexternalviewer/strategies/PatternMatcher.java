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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author markiewb
 */
class PatternMatcher {

    public PatternConfig findByTypeAndPattern(String remoteURI, RepoStrategy.Type type, List<PatternConfig> patterns) {
        PatternConfig pattern = filterByPattern(filterByType(patterns, type), remoteURI);
        if (null == pattern) {
            return null;
        }
        return pattern;
    }

    private List<PatternConfig> filterByType(List<PatternConfig> patterns, RepoStrategy.Type type) {
        List<PatternConfig> result = new ArrayList<PatternConfig>();
        for (PatternConfig pattern : patterns) {
            if (null!=type && null!=pattern){
            if (type.equals(pattern.getType())) {
                result.add(pattern);
            }
            }
        }
        return result;
    }

    private PatternConfig filterByPattern(List<PatternConfig> patterns, String remote) {
        for (PatternConfig pattern : patterns) {
            if (pattern.matches(remote)) {
                return pattern;
            }
        }
        return null;
    }

}
