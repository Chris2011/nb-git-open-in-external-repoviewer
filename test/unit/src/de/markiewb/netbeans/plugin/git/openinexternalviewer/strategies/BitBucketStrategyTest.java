/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author markiewb
 */
public class BitBucketStrategyTest {

    @Test
    public void testGetUrl() {

        String remote = "https://bitbucket.org/elbrecht/git-blog-examples.git";
//        String branchName = "feature/feature42D";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new BitBucketStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "https://bitbucket.org/elbrecht/git-blog-examples/commits/branch/master";
        assertEquals(expResult, result);
    }

}
