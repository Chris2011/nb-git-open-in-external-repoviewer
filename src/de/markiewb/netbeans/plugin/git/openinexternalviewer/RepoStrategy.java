/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer;

/**
 *
 * @author markiewb
 */
public interface RepoStrategy {

    String getUrl(String remote, String branchName, String branchRevId);

    boolean supports(String remote);
    
    String getLabel();
}
