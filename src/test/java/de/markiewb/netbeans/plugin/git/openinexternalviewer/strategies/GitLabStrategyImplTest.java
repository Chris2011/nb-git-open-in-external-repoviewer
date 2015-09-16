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
public class GitLabStrategyImplTest {

    @org.junit.Before
    public void setup() {
        new de.markiewb.netbeans.plugin.git.openinexternalviewer.Options().resetToDefault();
    }

    @Test
    public void testGetOpenUrlHTTPS() {
        String remote = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin.git";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new GitLabStrategyImpl().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin/commits/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOpenUrlSSH() {
        String remote = "git@gitlab.com:elimane-gueye/maven-git-commit-id-plugin.git";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new GitLabStrategyImpl().getUrl(RepoStrategy.Type.SHOW_LOG, remote, x(branchName, branchRevId));
        String expResult = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin/commits/master";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowCommitDiffHTTPS() {
        String remote = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin.git";
        String branchName = "master";
        String branchRevId = "70a17c8ec9e066cebb94714d13c39f28f36c4785";
        String result = new GitLabStrategyImpl().getUrl(RepoStrategy.Type.SHOW_COMMITDIFF, remote, x(branchName, branchRevId));
        String expResult = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin/commit/70a17c8ec9e066cebb94714d13c39f28f36c4785";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowCommitDiffSSH() {
        String remote = "git@gitlab.com:elimane-gueye/maven-git-commit-id-plugin.git";
        String branchName = "master";
        String branchRevId = "70a17c8ec9e066cebb94714d13c39f28f36c4785";
        String result = new GitLabStrategyImpl().getUrl(RepoStrategy.Type.SHOW_COMMITDIFF, remote, x(branchName, branchRevId));
        String expResult = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin/commit/70a17c8ec9e066cebb94714d13c39f28f36c4785";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHTTPS() {
        String remote = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin.git";
        String branchName = "master";
        String branchRevId = "70a17c8ec9e066cebb94714d13c39f28f36c4785";
        String result = new GitLabStrategyImpl().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, "src/main/java/pl/project13/maven/git/GitCommitIdMojo.java", 41));
        String expResult = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin/blob/70a17c8ec9e066cebb94714d13c39f28f36c4785/src/main/java/pl/project13/maven/git/GitCommitIdMojo.java#L42";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistoryHTTPS() {
        String remote = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin.git";
        String branchName = "master";
        String branchRevId = "70a17c8ec9e066cebb94714d13c39f28f36c4785";
        String result = new GitLabStrategyImpl().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, "src/main/java/pl/project13/maven/git/GitCommitIdMojo.java", -1));
        String expResult = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin/commits/70a17c8ec9e066cebb94714d13c39f28f36c4785/src/main/java/pl/project13/maven/git/GitCommitIdMojo.java";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileHistorySSH() {
        String remote = "git@gitlab.com:elimane-gueye/maven-git-commit-id-plugin.git";
        String branchName = "master";
        String branchRevId = "70a17c8ec9e066cebb94714d13c39f28f36c4785";
        String result = new GitLabStrategyImpl().getUrl(RepoStrategy.Type.SHOW_FILE_HISTORY, remote, x(branchName, branchRevId, "src/main/java/pl/project13/maven/git/GitCommitIdMojo.java", -1));
        String expResult = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin/commits/70a17c8ec9e066cebb94714d13c39f28f36c4785/src/main/java/pl/project13/maven/git/GitCommitIdMojo.java";
        assertEquals(expResult, result);
    }

    @Test
    public void testShowFileSSH() {
        String remote = "git@gitlab.com:elimane-gueye/maven-git-commit-id-plugin.git";
        String branchName = "master";
        String branchRevId = "70a17c8ec9e066cebb94714d13c39f28f36c4785";
        String result = new GitLabStrategyImpl().getUrl(RepoStrategy.Type.SHOW_FILE, remote, x(branchName, branchRevId, "src/main/java/pl/project13/maven/git/GitCommitIdMojo.java", 41));
        String expResult = "https://gitlab.com/elimane-gueye/maven-git-commit-id-plugin/blob/70a17c8ec9e066cebb94714d13c39f28f36c4785/src/main/java/pl/project13/maven/git/GitCommitIdMojo.java#L42";
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

}
