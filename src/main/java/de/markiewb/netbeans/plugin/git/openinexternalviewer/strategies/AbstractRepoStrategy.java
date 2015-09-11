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

import de.markiewb.netbeans.plugin.git.openinexternalviewer.Options;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.PlaceHolderResolver;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.PlaceHolderResolvers;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.URIPlaceHolderResolver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import org.openide.util.NbPreferences;

/**
 *
 * @author markiewb
 */
public abstract class AbstractRepoStrategy implements RepoStrategy {

    private final String label;

    private final PatternConfigs patternConfigs = new PatternConfigs();

    public AbstractRepoStrategy(String strategy) {
        new Options().resetToDefaultIfNotExisting();

        Preferences pref = NbPreferences.forModule(Options.class);
        label = getLabelFromPreferences(pref, strategy);

        List<PatternConfig> configs = getPatternConfigs(pref, strategy);
        for (PatternConfig config : configs) {
            patternConfigs.addPattern(config);
        }

    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getUrl(Type type, String remoteURI, PlaceHolderResolvers resolvers) {

        final PatternConfig pattern = new PatternMatcher().findByTypeAndPattern(remoteURI, type, patternConfigs.getPatterns());
        if (null == pattern) {
            return null;
        }

        return createURL(pattern, remoteURI, resolvers.getResolvers());
    }

    private Map<String, String> createArgumentMapViaResolver(Collection<PlaceHolderResolver> resolvers) {
        Map<String, String> map = new HashMap<String, String>();
        for (PlaceHolderResolver resolver : resolvers) {

            map.putAll(resolver.resolve());
        }
        return map;
    }

    private String createURL(final PatternConfig pattern, String URIFull, Collection<PlaceHolderResolver> resolverss) {
        PlaceHolderResolver uriResolver = new URIPlaceHolderResolver(pattern, URIFull);

        List<PlaceHolderResolver> resolvers = new ArrayList<PlaceHolderResolver>();
        resolvers.addAll(resolverss);
        resolvers.add(uriResolver);

        Map<String, String> argMap = createArgumentMapViaResolver(resolvers);
//        System.out.println("argMap = " + argMap);

        return replacePlaceholdersInTargetURL(pattern.getTargetURL(), argMap);
    }

    private String getLabelFromPreferences(Preferences pref, String strategy) {
        return pref.get(strategy + ".label", "");
    }

    private List<PatternConfig> getPatternConfigs(Preferences pref, String strategy) {
        List<String> sourceURLs = getSourceURLs(pref, strategy);
        Type[] values = Type.values();
        List<PatternConfig> configs = new ArrayList<PatternConfig>();
        for (int i = 0; i < sourceURLs.size(); i++) {

            for (Type type : values) {
                //f.e.: gitblit.target.show_log.url.0
                String key = strategy + ".target." + type.name().toLowerCase() + ".url." + i;
                //f.e.: <protocol>://<server>/git/log/<repo|escapeSlashWithBang>.git/refs!heads!<branch|escapeSlashWithBang>
                String value = pref.get(key, null);
                if (null != value && !value.isEmpty()) {
                    final PatternConfig config = new PatternConfig(type, Pattern.compile(sourceURLs.get(i)), value);
                    configs.add(config);
                }
            }
        }
        return configs;
    }

    private List<String> getSourceURLs(Preferences pref, String strategy) {
        List<String> sourceURLs = new ArrayList<String>();
        int i = 0;
        while (true) {
            //like gitblit.source.url.0
            String sourceUrl = pref.get(strategy + ".source.url." + i, null);
            if (null == sourceUrl) {
                break;
            }
            i++;
            sourceURLs.add(sourceUrl);
        }
        return sourceURLs;
    }

    private String replacePlaceholdersInTargetURL(String target, Map<String, String> argMap) {
        String url = target;
        for (Map.Entry<String, String> entrySet : argMap.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();

            //<protocol> -> value
            url = url.replaceAll(key, value);
        }
        return url;
    }

}
