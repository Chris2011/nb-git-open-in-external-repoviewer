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
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.WCPlaceHolderResolver;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author markiewb
 */
public class GitWebStrategyTest {

    @org.junit.Before
    public void setup() {
        new de.markiewb.netbeans.plugin.git.openinexternalviewer.Options().resetToDefault();
    }

    private PlaceHolderResolvers x(String branchName, String branchRevId) {
        WCPlaceHolderResolver wcPlaceHolderResolver = new WCPlaceHolderResolver(branchName, branchRevId);
        PlaceHolderResolvers resolvers = new PlaceHolderResolvers(wcPlaceHolderResolver);
        return resolvers;
    }

    @Test
    public void testGetOpenUrlHttp() {
        String remote = "http://devel.dolmen-project.org/dolmen.menu.git";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new GitWebStrategyImpl().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "http://gitweb.dolmen-project.org/dolmen.menu.git/shortlog/refs/heads/master";
        assertEquals(expResult, result);
    }
}
