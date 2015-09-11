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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author markiewb
 */
public class WCPlaceHolderResolver implements PlaceHolderResolver{

    private final String branch;
    private final String revision;

    public WCPlaceHolderResolver(String branch, String revision) {
        this.branch = branch;
        this.revision = revision;
    }

    @Override
    public Map<String, String> resolve() {
        Map<String, String> result = new HashMap<String, String>();
        //url = url.replaceAll("<branch>", branchName);
        //url = url.replaceAll("<branch\\|escapeSlashWithBang>", escapeSlashWithBang(branchName));
        //url = url.replaceAll("<revision>", branchRevId);
        result.put("<branch>", branch);
        result.put("<branch\\|escapeSlashWithBang>", escapeSlashWithBang(branch));
        result.put("<revision>", revision);
        return result;
    }

    private String escapeSlashWithBang(String repo) {
        return repo.replaceAll("/", "!");
    }
}
