/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.GitWebStrategyImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author markiewb
 */
public class GitWebStrategyTest {

    @Test
    public void testGetUrlHttp() {
        String remote = "http://devel.dolmen-project.org/dolmen.menu.git";
        String branchName = "master";
        String branchRevId = "1234";
        String result = new GitWebStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "http://gitweb.dolmen-project.org/dolmen.menu.git/shortlog/refs/heads/master";
        assertEquals(expResult, result);
    }
}
