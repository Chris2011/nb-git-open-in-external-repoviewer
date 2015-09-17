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
import org.junit.Ignore;

/**
 *
 * @author markiewb
 */
public class GitBlitStrategyImplTest {

    @org.junit.Before
    public void setup() {
        new de.markiewb.netbeans.plugin.git.openinexternalviewer.Options().resetToDefault();
    }

    @Test
    public void testGetOpenUrl3() {
        String remote = "http://user@my.company.com/gitblit/r/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "http://my.company.com/gitblit/log/category!my.complex.repo.git/refs!heads!feature!feature42D";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrl4() {
        String remote = "http://git.delwink.com/git/r/patts-qt.git";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "http://git.delwink.com/git/log/patts-qt.git/refs!heads!master";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrl_Category() {
        String remote = "http://my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "http://my.company.com/gitblit/log/category!my.complex.repo.git/refs!heads!feature!feature42D";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrl_CategoryAndUsername() {
        String remote = "http://user@my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "http://my.company.com/gitblit/log/category!my.complex.repo.git/refs!heads!feature!feature42D";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrl_Username() {
        String remote = "http://user@my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "http://my.company.com/gitblit/log/category!my.complex.repo.git/refs!heads!feature!feature42D";
        assertEquals(expResult, result);
    }

    @Test
    public void testOpenGetUrl2() {
        String remote = "https://demo-gitblit.rhcloud.com/git/gitblit.git";
        String branchName = "gh-pages";
        String branchRevId = "42D";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://demo-gitblit.rhcloud.com/log/gitblit.git/refs!heads!gh-pages";
        assertEquals(expResult, result);
    }

    @Ignore
    @Test
    public void testShowCommit() {
        String remote = "http://my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_COMMIT, remote, x(branchName, branchRevId));
        String expResult = "http://my.company.com/gitblit/commit/category!my.complex.repo.git/1234";
        assertEquals(expResult, result);
    }

    @Ignore
    @Test
    public void testShowCommit2() {
        String remote = "http://git.delwink.com/git/r/patts-qt.git";
        String branchName = "master";
        String branchRevId = "607a417df243451330d91b48515b4df9eb106580";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_COMMIT, remote, x(branchName, branchRevId));
        String expResult = "http://git.delwink.com/git/commit/patts-qt.git/607a417df243451330d91b48515b4df9eb106580";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowCommitDiff() {
        String remote = "http://my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_COMMITDIFF, remote, x(branchName, branchRevId));
        String expResult = "http://my.company.com/gitblit/commitdiff/category!my.complex.repo.git/1234";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowCommitDiff2() {
        String remote = "http://git.delwink.com/git/r/patts-qt.git";
        String branchName = "master";
        String branchRevId = "607a417df243451330d91b48515b4df9eb106580";
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_COMMITDIFF, remote, x(branchName, branchRevId));
        String expResult = "http://git.delwink.com/git/commitdiff/patts-qt.git/607a417df243451330d91b48515b4df9eb106580";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFile() {
        String remote = "http://my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String fullFilePath = "src/com/foo/FooTest.java";
        Integer linenumber0Based = 32;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, fullFilePath, linenumber0Based));
        String expResult = "http://my.company.com/gitblit/blob/category!my.complex.repo.git/1234/src!com!foo!FooTest.java#L33";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFile2() {
        String remote = "http://git.delwink.com/git/r/ljpapi.git";
        String branchName = "master";
        String branchRevId = "607a417df243451330d91b48515b4df9eb106580";
        String fullFilePath = "src/com/delwink/ljp/Alignment.java";
        Integer linenumber0Based = 32;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, fullFilePath, linenumber0Based));
        String expResult = "http://git.delwink.com/git/blob/ljpapi.git/607a417df243451330d91b48515b4df9eb106580/src!com!delwink!ljp!Alignment.java#L33";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistory() {
        String remote = "http://my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String fullFilePath = "src/com/foo/FooTest.java";
        Integer linenumber0Based = 32;

        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, fullFilePath, linenumber0Based));
        String expResult = "http://my.company.com/gitblit/history/category!my.complex.repo.git/feature!feature42D/src!com!foo!FooTest.java";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistory2() {
        String remote = "http://git.delwink.com/git/r/ljpapi.git";
        String branchName = "master";
        String branchRevId = "607a417df243451330d91b48515b4df9eb106580";
        String fullFilePath = "src/com/delwink/ljp/Alignment.java";
        Integer linenumber0Based = 32;
        String result = new StrategyUnderTest().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, fullFilePath, linenumber0Based));
        String expResult = "http://git.delwink.com/git/history/ljpapi.git/master/src!com!delwink!ljp!Alignment.java";
        assertEquals(expResult, result);
    }

    private PlaceHolderResolvers x(String branchName, String branchRevId) {
        WCPlaceHolderResolver wcPlaceHolderResolver = new WCPlaceHolderResolver(branchName, branchRevId);
        PlaceHolderResolvers resolvers = new PlaceHolderResolvers(wcPlaceHolderResolver);
        return resolvers;
    }

    private PlaceHolderResolvers x(String branchName, String branchRevId, String fullFilePath, Integer linenumber0Based) {
        WCPlaceHolderResolver wcPlaceHolderResolver = new WCPlaceHolderResolver(branchName, branchRevId);
        EditorPlaceHolderResolver mockedEditorPlaceHolderResolver = new MockedEditorPlaceHolderResolver(fullFilePath, linenumber0Based);
        PlaceHolderResolvers resolvers = new PlaceHolderResolvers(wcPlaceHolderResolver, mockedEditorPlaceHolderResolver);
        return resolvers;
    }

    private static class StrategyUnderTest extends AbstractRepoStrategy {

        public StrategyUnderTest() {
            super("gitblit");
        }
    }

}
