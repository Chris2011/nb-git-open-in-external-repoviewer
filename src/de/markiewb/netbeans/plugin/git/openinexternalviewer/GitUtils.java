/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.markiewb.netbeans.plugin.git.openinexternalviewer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.netbeans.libs.git.GitBranch;
import org.netbeans.libs.git.GitClient;
import org.netbeans.libs.git.GitException;
import org.netbeans.libs.git.GitRemoteConfig;
import org.netbeans.libs.git.GitRepository;
import org.netbeans.libs.git.progress.ProgressMonitor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author markiewb
 */
public class GitUtils {

    public static Collection<GitBranch> getBranches(final FileObject f, boolean localAndRemoteBranches) {
        final FileObject gitRepoDirectory = getGitRepoDirectory(f);
        if (null == gitRepoDirectory) {
            return Collections.emptyList();
        }
        GitRepository repo = GitRepository.getInstance(FileUtil.toFile(gitRepoDirectory));
        GitClient client = null;
        try {
            client = repo.createClient();
            ProgressMonitor progressMonitor = new ProgressMonitor.DefaultProgressMonitor();
            return client.getBranches(localAndRemoteBranches, progressMonitor).values();
        } catch (GitException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            if (null != client) {
                client.release();
            }
        }
        return Collections.emptyList();
    }
    public static String getRemote(final FileObject f, String remoteName) {
        final FileObject gitRepoDirectory = getGitRepoDirectory(f);
        if (null == gitRepoDirectory) {
            return null;
        }
        GitRepository repo = GitRepository.getInstance(FileUtil.toFile(gitRepoDirectory));
        GitClient client = null;
        try {
            client = repo.createClient();
            ProgressMonitor progressMonitor = new ProgressMonitor.DefaultProgressMonitor();
            Map<String, GitRemoteConfig> remotes = client.getRemotes(progressMonitor);
            final List<String> uris = remotes.get(remoteName).getUris();
            return uris.get(0);
            
        } catch (GitException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            if (null != client) {
                client.release();
            }
        }
        return null;
    }
    public static GitBranch getActiveBranch(final FileObject f) {
        for (GitBranch branch : getBranches(f, true)) {
            if (branch.isActive()) {
                return branch;
            }
        }
        return null;
    }

    static FileObject getGitRepoDirectory(FileObject file) {
        FileObject currentFile = file;
        while (currentFile != null) {
            if (currentFile.isFolder() && currentFile.getFileObject(".git", "") != null) {
                return currentFile;
            }
            currentFile = currentFile.getParent();
        }
        return null;
    }
    
}
