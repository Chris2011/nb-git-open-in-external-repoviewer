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

import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.EditorPlaceHolderResolver;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.MockedEditorPlaceHolderResolver;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.PlaceHolderResolvers;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders.WCPlaceHolderResolver;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author markiewb
 */
public class BitBucketStrategyTest {

    private PlaceHolderResolvers x(String branchName, String branchRevId) {
        WCPlaceHolderResolver wcPlaceHolderResolver = new WCPlaceHolderResolver(branchName, branchRevId);
        PlaceHolderResolvers resolvers = new PlaceHolderResolvers(wcPlaceHolderResolver);
        return resolvers;
    }

    private PlaceHolderResolvers x(String branchName, String branchRevId, String fullFilePath, String fileName, Integer linenumber0Based) {
        WCPlaceHolderResolver wcPlaceHolderResolver = new WCPlaceHolderResolver(branchName, branchRevId);
        EditorPlaceHolderResolver mockedEditorPlaceHolderResolver = new MockedEditorPlaceHolderResolver(fullFilePath, fileName, linenumber0Based);
        PlaceHolderResolvers resolvers = new PlaceHolderResolvers(wcPlaceHolderResolver, mockedEditorPlaceHolderResolver);
        return resolvers;
    }

    @org.junit.Before
    public void setup() {
        new de.markiewb.netbeans.plugin.git.openinexternalviewer.Options().resetToDefault();
    }

    @Test
    public void testGetOpenUrl() {

        String remote = "https://bitbucket.org/elbrecht/git-blog-examples.git";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://bitbucket.org/elbrecht/git-blog-examples/commits/branch/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrlSsh() {

        String remote = "git@bitbucket.org:elbrecht/git-blog-examples.git";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://bitbucket.org/elbrecht/git-blog-examples/commits/branch/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrl_Username() {

        String remote = "https://user@bitbucket.org/elbrecht/git-blog-examples.git";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://bitbucket.org/elbrecht/git-blog-examples/commits/branch/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPRUrlSsh() {

        String remote = "git@bitbucket.org:elbrecht/git-blog-examples.git";
        String branchName = "branch1";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.PULL_REQUEST, remote, x(branchName, branchRevId));
        String expResult = "https://bitbucket.org/elbrecht/git-blog-examples/pull-request/new?source=elbrecht/git-blog-examples::branch1";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFile() {
        String remote = "https://user@bitbucket.org/elbrecht/git-blog-examples.git";
        String branchName = "master";
        String branchRevId = "9eb37c6786f71f25afd80f93e8013c6bc12b0a34";
        String fullFilePath = "shortableEntity/pom.xml";
        String fileName = "pom.xml";
        Integer linenumber0Based = 14;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://bitbucket.org/elbrecht/git-blog-examples/src/9eb37c6786f71f25afd80f93e8013c6bc12b0a34/shortableEntity/pom.xml?at=master&fileviewer=file-view-default#pom.xml-15";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFile2() {
        String remote = "git@bitbucket.org:elbrecht/git-blog-examples.git";
        String branchName = "master";
        String branchRevId = "9eb37c6786f71f25afd80f93e8013c6bc12b0a34";
        String fullFilePath = "shortableEntity/pom.xml";
        String fileName = "pom.xml";
        Integer linenumber0Based = 14;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://bitbucket.org/elbrecht/git-blog-examples/src/9eb37c6786f71f25afd80f93e8013c6bc12b0a34/shortableEntity/pom.xml?at=master&fileviewer=file-view-default#pom.xml-15";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistory() {
        String remote = "https://user@bitbucket.org/elbrecht/git-blog-examples.git";
        String branchName = "master";
        String branchRevId = "9eb37c6786f71f25afd80f93e8013c6bc12b0a34";
        String fullFilePath = "shortableEntity/pom.xml";
        String fileName = "pom.xml";
        Integer linenumber0Based = 14;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://bitbucket.org/elbrecht/git-blog-examples/history-node/master/shortableEntity/pom.xml?at=master";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistory2() {
        String remote = "git@bitbucket.org:elbrecht/git-blog-examples.git";
        String branchName = "master";
        String branchRevId = "9eb37c6786f71f25afd80f93e8013c6bc12b0a34";
        String fullFilePath = "shortableEntity/pom.xml";
        String fileName = "pom.xml";
        Integer linenumber0Based = 14;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://bitbucket.org/elbrecht/git-blog-examples/history-node/master/shortableEntity/pom.xml?at=master";
        assertEquals(expResult, result);
    }

    private static class StrategyUnderTest extends AbstractRepoStrategy {

        public StrategyUnderTest() {
            super("bitbucket");
        }
    }

}
