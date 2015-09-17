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
package de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.PatternConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 *
 * @author markiewb
 */
public class URIPlaceHolderResolver implements PlaceHolderResolver {

    private final String URIFull;
    private final PatternConfig pattern;

    public URIPlaceHolderResolver(PatternConfig pattern, String URIFull) {
        this.pattern = pattern;
        this.URIFull = URIFull;
    }

    @Override
    public Map<String, String> resolve() {
        Matcher matcher = pattern.createMatcher(URIFull);

        Map<String, String> result = new HashMap<String, String>();
        result.put("<protocol>", extractFromURI(matcher, "protocol", ""));
        result.put("<server>", extractFromURI(matcher, "server", ""));
        final String repo = extractFromURI(matcher, "repo", "");
        result.put("<repo>", repo);
        result.put("<repo\\|escapeSlashWithBang>", escapeSlashWithBang(repo));

        return result;
    }

    private String extractFromURI(Matcher matcher, String name, String defaultValue) {
        try {
            return matcher.group(name);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    private String escapeSlashWithBang(String repo) {
        return repo.replaceAll("/", "!");
    }

}
