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
import java.util.List;
import java.util.regex.Matcher;

/**
 *
 * @author markiewb
 */
public class PatternTranslator {

    public String replacePatterns(RepoStrategy.Type type, String remote, String branchName, String branchRevId, List<PatternConfig> patterns) {
        for (PatternConfig pattern : patterns) {
            if (type.equals(pattern.getType()) && pattern.matches(remote)) {
                Matcher matcher = pattern.createMatcher(remote);
                String protocol = extractFromPattern(matcher, "protocol", "");
                String username = extractFromPattern(matcher, "username", "");
                String server = extractFromPattern(matcher, "server", "");
                String repo = extractFromPattern(matcher, "repo", "");
                return replacePatterns(pattern.getTarget(), protocol, server, repo, branchName, username, branchRevId);
            }
        }
        return null;
    }

    public static String extractFromPattern(Matcher matcher, String name, String defaultValue) {
        try {
            return matcher.group(name);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    boolean supports(RepoStrategy.Type type, String remote, List<PatternConfig> patterns) {
        for (PatternConfig pattern : patterns) {
            if (type.equals(pattern.getType()) && pattern.matches(remote)) {
                return true;
            }
        }
        return false;
    }

    private String escapeSlashWithBang(String repo) {
        return repo.replaceAll("/", "!");
    }

    private String replacePatterns(String target, String protocol, String server, String repo, String branchName, String username, String branchRevId) {
        String url = target;
        url = url.replaceAll("<protocol>", protocol);
        url = url.replaceAll("<server>", server);
        url = url.replaceAll("<repo>", repo);
        url = url.replaceAll("<repo\\|escapeSlashWithBang>", escapeSlashWithBang(repo));
        url = url.replaceAll("<branch>", branchName);
        url = url.replaceAll("<branch\\|escapeSlashWithBang>", escapeSlashWithBang(branchName));
        url = url.replaceAll("<user>", username);
        url = url.replaceAll("<revision>", branchRevId);
        return url;
    }

}
