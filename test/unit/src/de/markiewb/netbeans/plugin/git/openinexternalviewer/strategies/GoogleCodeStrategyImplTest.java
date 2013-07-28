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
public class GoogleCodeStrategyImplTest {

    @Test
    public void testGetUrl() {
        String remote = "https://code.google.com/p/gitblit/";
        String branchName = "bootstrap";
        String branchRevId = "1234";
        String result = new GoogleCodeStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "https://code.google.com/p/gitblit/source/list?name=bootstrap";
        assertEquals(expResult, result);
    }

}
