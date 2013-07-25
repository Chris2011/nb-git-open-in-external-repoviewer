/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.GitBlitStrategyImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author markiewb
 */
public class GitHubBlitStrategyTest {

    @Test
    public void testGetUrl() {

        String remote = "https://github.com/markiewb/nb-git-branch-in-statusbar.git";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new GitHubStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "https://github.com/markiewb/nb-git-branch-in-statusbar/commits/master";
        assertEquals(expResult, result);
    }

}
