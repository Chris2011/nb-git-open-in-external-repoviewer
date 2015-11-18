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
public class GitHubStrategyImplTest {

    private static final String P0 = "https://github.com/markiewb/nb-git-close-unmodified-documents.git";
    private static final String P1 = "https://github.com/markiewb/nb-git-close-unmodified-documents";
    private static final String P2 = "git@github.com:markiewb/nb-git-close-unmodified-documents.git";
    private static final String P3 = "https://markiewb@github.com/markiewb/nb-git-close-unmodified-documents.git";
    private static final String P4 = "git://github.com/madflow/flow-netbeans-markdown.git";

    @org.junit.Before
    public void setup() {
        new de.markiewb.netbeans.plugin.git.openinexternalviewer.Options().resetToDefault();
    }

    @Test
    public void testGetOpenUrlGit() {

        String remote = "git@github.com:markiewb/nb-git-branch-in-statusbar.git";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://github.com/markiewb/nb-git-branch-in-statusbar/commits/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrlGit2() {

        String remote = "git://github.com/madflow/flow-netbeans-markdown.git";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://github.com/madflow/flow-netbeans-markdown/commits/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrlHttps() {

        String remote = "https://github.com/markiewb/nb-git-branch-in-statusbar.git";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://github.com/markiewb/nb-git-branch-in-statusbar/commits/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrlHttps_Username() {

        String remote = "https://markiewb@github.com/markiewb/nb-git-branch-in-statusbar.git";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://github.com/markiewb/nb-git-branch-in-statusbar/commits/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrlHttps_WithoutGitPostfix() {

        String remote = "https://github.com/markiewb/nb-git-branch-in-statusbar";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://github.com/markiewb/nb-git-branch-in-statusbar/commits/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowCommitDiff0() {
        String remote = P0;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_COMMITDIFF, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/commit/fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowCommitDiff1() {
        String remote = P1;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_COMMITDIFF, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/commit/fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowCommitDiff2() {
        String remote = P2;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_COMMITDIFF, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/commit/fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowCommitDiff3() {
        String remote = P3;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_COMMITDIFF, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/commit/fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowCommitDiff4() {
        String remote = P4;
        String branchName = "nb8";
        String branchRevId = "a80b67bf8f7e3982cd402c46c7cdcf9adbc6173b";
        String fullFilePath = "nbproject/genfiles.properties";
        String fileName = "genfiles.properties";
        Integer linenumber0Based = 5;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_COMMITDIFF, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/madflow/flow-netbeans-markdown/commit/a80b67bf8f7e3982cd402c46c7cdcf9adbc6173b";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFile0() {
        String remote = P0;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/blob/fd5f7aa6c993e974b904cd9f4b87caac1c22e98b/src/main/nbm/manifest.mf#L2";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFile1() {
        String remote = P1;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/blob/fd5f7aa6c993e974b904cd9f4b87caac1c22e98b/src/main/nbm/manifest.mf#L2";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFile2() {
        String remote = P2;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/blob/fd5f7aa6c993e974b904cd9f4b87caac1c22e98b/src/main/nbm/manifest.mf#L2";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFile3() {
        String remote = P3;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/blob/fd5f7aa6c993e974b904cd9f4b87caac1c22e98b/src/main/nbm/manifest.mf#L2";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFile4() {
        String remote = P4;
        String branchName = "nb8";
        String branchRevId = "a80b67bf8f7e3982cd402c46c7cdcf9adbc6173b";
        String fullFilePath = "nbproject/genfiles.properties";
        String fileName = "genfiles.properties";
        Integer linenumber0Based = 5;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/madflow/flow-netbeans-markdown/blob/a80b67bf8f7e3982cd402c46c7cdcf9adbc6173b/nbproject/genfiles.properties#L6";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistory0() {
        String remote = P0;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/commits/master/src/main/nbm/manifest.mf";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistory1() {
        String remote = P1;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/commits/master/src/main/nbm/manifest.mf";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistory2() {
        String remote = P2;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/commits/master/src/main/nbm/manifest.mf";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistory3() {
        String remote = P3;
        String branchName = "master";
        String branchRevId = "fd5f7aa6c993e974b904cd9f4b87caac1c22e98b";
        String fullFilePath = "src/main/nbm/manifest.mf";
        String fileName = "manifest.mf";
        Integer linenumber0Based = 1;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/markiewb/nb-git-close-unmodified-documents/commits/master/src/main/nbm/manifest.mf";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistory4() {
        String remote = P4;

        String branchName = "nb8";
        String branchRevId = "a80b67bf8f7e3982cd402c46c7cdcf9adbc6173b";
        String fullFilePath = "nbproject/genfiles.properties";
        String fileName = "genfiles.properties";
        Integer linenumber0Based = 5;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, fullFilePath, fileName, linenumber0Based));
        String expResult = "https://github.com/madflow/flow-netbeans-markdown/commits/nb8/nbproject/genfiles.properties";
        assertEquals(expResult, result);
    }

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

    private static class StrategyUnderTest extends AbstractRepoStrategy {

        public StrategyUnderTest() {
            super("github");
        }
    }

}
