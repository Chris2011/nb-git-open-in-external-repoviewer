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
public class GitBlitStrategyTest {

    @Test
    public void testGetUrl() {
        String remote = "http://user@my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new GitBlitStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "http://my.company.com/gitblit/log/category!my.complex.repo.git/refs!heads!feature!feature42D";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUrl2() {
        String remote = "https://demo-gitblit.rhcloud.com/git/gitblit.git";
        String branchName = "gh-pages";
        String branchRevId = "42D";
        String result = new GitBlitStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "https://demo-gitblit.rhcloud.com/log/gitblit.git/refs!heads!gh-pages";
        assertEquals(expResult, result);
    }
}
