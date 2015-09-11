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

import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.PlaceHolderResolvers;

public interface RepoStrategy {

    public enum Type {

        SHOW_LOG("Show log"), PULL_REQUEST("Create Pull-Request"), SHOW_COMMIT("Show commit"), SHOW_COMMITDIFF("Show diff"), SHOW_FILE_HISTORY("Show file history"), SHOW_FILE("Show file"), SHOW_FILE_COMMITDIFF("Show file in diff");

        private final String label;

        private Type(String label) {
            this.label = label;

        }

        public String getLabel() {
            return label;
        }
    }

    String getUrl(RepoStrategy.Type type, String remoteURI, PlaceHolderResolvers resolvers);

    String getLabel();
}
