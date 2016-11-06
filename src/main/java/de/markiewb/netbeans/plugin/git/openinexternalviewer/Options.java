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
package de.markiewb.netbeans.plugin.git.openinexternalviewer;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.PatternConfig;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.RepoStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

/**
 *
 * @author markiewb
 */
public class Options {

    public List<String> getStrategies() {
        Preferences pref = NbPreferences.forModule(Options.class);
        String values = pref.get("$.global.strategies", "");
        return Arrays.asList(values.split(","));
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

    public List<PatternConfig> getPatternConfigs(String strategy) {
        Preferences pref = NbPreferences.forModule(Options.class);

        List<String> sourceURLs = getSourceURLs(pref, strategy);
        RepoStrategy.Type[] values = RepoStrategy.Type.values();
        List<PatternConfig> configs = new ArrayList<PatternConfig>();
        for (int i = 0; i < sourceURLs.size(); i++) {

            for (RepoStrategy.Type type : values) {
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

    public String getLabelFromPreferences(String strategy) {
        Preferences pref = NbPreferences.forModule(Options.class);

        return pref.get(strategy + ".label", "");
    }

    public void resetToDefaultIfNotExisting() {
        //only for debugging
//        new Options().resetToDefault();
        try {
            Preferences pref = NbPreferences.forModule(Options.class);
            if (null == pref.keys() | pref.keys().length == 0) {
                new Options().resetToDefault();
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void resetToDefault() {
        String d
                = getDefaultConfig();
        persist(d);
    }

    public void persist(String propertyFileContent) {
        Preferences pref = NbPreferences.forModule(Options.class);
        try {
            pref.clear();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
        String[] lines = propertyFileContent.split("\n");
        for (String line : lines) {
            //only the first match of '='
            String[] keyValue = line.split("=", 2);
            if (null != keyValue && keyValue.length == 2) {
                String key = keyValue[0];
                String val = keyValue[1];
                pref.put(key, val);
            }
        }
        try {
            pref.flush();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    public String load(Preferences pref) {
        StringBuilder sb = new StringBuilder();
        String[] keys;
        try {
            keys = pref.keys();
            for (String key : keys) {
                final String value = pref.get(key, "");
                sb.append(String.format("%s=%s\n", key, value));
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
        return sb.toString().trim();
    }

    public static String getDefaultConfig() {
        return "$.global.strategies=github,gitblit,bitbucket,gitlab,gitweb\n"
                + "bitbucket.label=BitBucket\n"
                + "bitbucket.source.url.0=(?<protocol>https)://(?<username>.+?@)?bitbucket\\.org/(?<repo>.+)\\.git\n"
                + "bitbucket.source.url.1=(?<username>git)@bitbucket\\.org:(?<repo>.+)\\.git\n"
                + "bitbucket.target.pull_request.url.0=<protocol>://bitbucket.org/<repo>/pull-request/new?source=<repo>::<branch>\n"
                + "bitbucket.target.pull_request.url.1=https://bitbucket.org/<repo>/pull-request/new?source=<repo>::<branch>\n"
                + "bitbucket.target.show_file.url.0=https://bitbucket.org/<repo>/src/<revision>/<fullfilepath>?at=<branch>&fileviewer=file-view-default#<filename>-<linenumber|1based>\n"
                + "bitbucket.target.show_file.url.1=https://bitbucket.org/<repo>/src/<revision>/<fullfilepath>?at=<branch>&fileviewer=file-view-default#<filename>-<linenumber|1based>\n"
                + "bitbucket.target.show_file_history.url.0=https://bitbucket.org/<repo>/history-node/<branch>/<fullfilepath>?at=<branch>\n"
                + "bitbucket.target.show_file_history.url.1=https://bitbucket.org/<repo>/history-node/<branch>/<fullfilepath>?at=<branch>\n"
                + "bitbucket.target.show_log.url.0=<protocol>://bitbucket.org/<repo>/commits/branch/<branch>\n"
                + "bitbucket.target.show_log.url.1=https://bitbucket.org/<repo>/commits/branch/<branch>\n"
                + "gitblit.label=GitBlit\n"
                + "gitblit.source.url.0=(?<protocol>http|https)://(?<username>.+?@)?(?<server>.+?)/(git/r)/(?<repo>.+)\\.git\n"
                + "gitblit.source.url.1=(?<protocol>http|https)://(?<username>.+?@)?(?<server>.+?)/(git|r)/(?<repo>.+)\\.git\n"
                + "gitblit.target.pull_request.url.0=\n"
                + "gitblit.target.pull_request.url.1=\n"
                + "gitblit.target.show_commit.url.0=\n"
                + "gitblit.target.show_commit.url.1=\n"
                + "gitblit.target.show_commitdiff.url.0=<protocol>://<server>/git/commitdiff/<repo|escapeSlashWithBang>.git/<revision>\n"
                + "gitblit.target.show_commitdiff.url.1=<protocol>://<server>/commitdiff/<repo|escapeSlashWithBang>.git/<revision>\n"
                + "gitblit.target.show_file.url.0=<protocol>://<server>/git/blob/<repo|escapeSlashWithBang>.git/<revision>/<fullfilepath|escapeSlashWithBang>#L<linenumber|1based>\n"
                + "gitblit.target.show_file.url.1=<protocol>://<server>/blob/<repo|escapeSlashWithBang>.git/<revision>/<fullfilepath|escapeSlashWithBang>#L<linenumber|1based>\n"
                + "gitblit.target.show_file_history.url.0=<protocol>://<server>/git/history/<repo|escapeSlashWithBang>.git/<branch|escapeSlashWithBang>/<fullfilepath|escapeSlashWithBang>\n"
                + "gitblit.target.show_file_history.url.1=<protocol>://<server>/history/<repo|escapeSlashWithBang>.git/<branch|escapeSlashWithBang>/<fullfilepath|escapeSlashWithBang>\n"
                + "gitblit.target.show_log.url.0=<protocol>://<server>/git/log/<repo|escapeSlashWithBang>.git/refs!heads!<branch|escapeSlashWithBang>\n"
                + "gitblit.target.show_log.url.1=<protocol>://<server>/log/<repo|escapeSlashWithBang>.git/refs!heads!<branch|escapeSlashWithBang>\n"
                + "github.label=Github\n"
                + "github.source.url.0=(?<protocol>http|https)://(?<username>.+?@)?github\\.com/(?<repo>.+)\\.git\n"
                + "github.source.url.1=(?<protocol>http|https)://(?<username>.+?@)?github\\.com/(?<repo>.+)\n"
                + "github.source.url.2=git@github\\.com:(?<repo>.+)\\.git\n"
                + "github.source.url.3=git://github\\.com/(?<repo>.+)\\.git\n"
                + "github.target.show_commitdiff.url.0=https://github.com/<repo>/commit/<revision>\n"
                + "github.target.show_commitdiff.url.1=https://github.com/<repo>/commit/<revision>\n"
                + "github.target.show_commitdiff.url.2=https://github.com/<repo>/commit/<revision>\n"
                + "github.target.show_commitdiff.url.3=https://github.com/<repo>/commit/<revision>\n"
                + "github.target.show_file.url.0=https://github.com/<repo>/blob/<revision>/<fullfilepath>#L<linenumber|1based>\n"
                + "github.target.show_file.url.1=https://github.com/<repo>/blob/<revision>/<fullfilepath>#L<linenumber|1based>\n"
                + "github.target.show_file.url.2=https://github.com/<repo>/blob/<revision>/<fullfilepath>#L<linenumber|1based>\n"
                + "github.target.show_file.url.3=https://github.com/<repo>/blob/<revision>/<fullfilepath>#L<linenumber|1based>\n"
                + "github.target.show_file_history.url.0=https://github.com/<repo>/commits/<branch>/<fullfilepath>\n"
                + "github.target.show_file_history.url.1=https://github.com/<repo>/commits/<branch>/<fullfilepath>\n"
                + "github.target.show_file_history.url.2=https://github.com/<repo>/commits/<branch>/<fullfilepath>\n"
                + "github.target.show_file_history.url.3=https://github.com/<repo>/commits/<branch>/<fullfilepath>\n"
                + "github.target.show_log.url.0=<protocol>://github.com/<repo>/commits/<branch>\n"
                + "github.target.show_log.url.1=<protocol>://github.com/<repo>/commits/<branch>\n"
                + "github.target.show_log.url.2=https://github.com/<repo>/commits/<branch>\n"
                + "github.target.show_log.url.3=https://github.com/<repo>/commits/<branch>\n"
                + "gitlab.label=Gitlab\n"
                + "gitlab.source.url.0=https://(?<username>.+?@)?gitlab\\.com/(?<repo>.+)\\.git\n"
                + "gitlab.source.url.1=git@gitlab.com:(?<repo>.+)\\.git\n"
                + "gitlab.target.pull_request.url.0=https://gitlab.com/<repo>/merge_requests/new?merge_request[source_branch]=<branch>\n"
                + "gitlab.target.pull_request.url.1=https://gitlab.com/<repo>/merge_requests/new?merge_request[source_branch]=<branch>\n"
                + "gitlab.target.show_commit.url.0=\n"
                + "gitlab.target.show_commit.url.1=\n"
                + "gitlab.target.show_commitdiff.url.0=https://gitlab.com/<repo>/commit/<revision>\n"
                + "gitlab.target.show_commitdiff.url.1=https://gitlab.com/<repo>/commit/<revision>\n"
                + "gitlab.target.show_file.url.0=https://gitlab.com/<repo>/blob/<revision>/<fullfilepath>#L<linenumber|1based>\n"
                + "gitlab.target.show_file.url.1=https://gitlab.com/<repo>/blob/<revision>/<fullfilepath>#L<linenumber|1based>\n"
                + "gitlab.target.show_file_history.url.0=https://gitlab.com/<repo>/commits/<revision>/<fullfilepath>\n"
                + "gitlab.target.show_file_history.url.1=https://gitlab.com/<repo>/commits/<revision>/<fullfilepath>\n"
                + "gitlab.target.show_log.url.0=https://gitlab.com/<repo>/commits/<branch>\n"
                + "gitlab.target.show_log.url.1=https://gitlab.com/<repo>/commits/<branch>\n"
                + "gitweb.label=GitWeb\n"
                + "gitweb.source.url.0=(?<protocol>http|https)://(.*?)\\.(?<server>.+?)/(?<repo>.+).git\n"
                + "gitweb.target.show_log.url.0=<protocol>://gitweb.<server>/<repo>.git/shortlog/refs/heads/<branch>\n".trim();
    }

}
