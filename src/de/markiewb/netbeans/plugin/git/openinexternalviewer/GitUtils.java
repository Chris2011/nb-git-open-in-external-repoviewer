/**
 * Copyright 2013 markiewb
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
