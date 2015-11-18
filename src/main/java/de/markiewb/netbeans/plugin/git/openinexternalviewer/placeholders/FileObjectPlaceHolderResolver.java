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
package de.markiewb.netbeans.plugin.git.openinexternalviewer.placeholders;

import java.util.HashMap;
import java.util.Map;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.netbeans.editor.BaseDocument;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 *
 * @author markiewb
 */
public class FileObjectPlaceHolderResolver implements PlaceHolderResolver {

    private final FileObject fo;
    private final FileObject gitRepoDirectory;

    public FileObjectPlaceHolderResolver(FileObject fo, FileObject gitRepoDirectory) {
        this.fo = fo;
        this.gitRepoDirectory = gitRepoDirectory;
    }

    protected static class Config {

        Integer linenumber;
        String relativePath;
        String fileName;
    }

    @Override
    public Map<String, String> resolve() {
        Config config = initFromEditor(fo, gitRepoDirectory);
        Map<String, String> result = new HashMap<>();
        if (null != config.linenumber) {
            result.put("<linenumber\\|0based>", config.linenumber.toString());
            result.put("<linenumber\\|1based>", String.valueOf(config.linenumber + 1));
        } else {
            result.put("<linenumber\\|0based>", "0");
            result.put("<linenumber\\|1based>", "1");
        }
        if (null != config.relativePath) {
            result.put("<fullfilepath>", config.relativePath);
            result.put("<fullfilepath\\|escapeSlashWithBang>", escapeSlashWithBang(config.relativePath));
        }
        if (null != config.fileName) {
            result.put("<filename>", config.fileName);
        }
        return result;
    }

    //VisibleForTesting
    protected Config initFromEditor(FileObject fileObject, FileObject gitRepoDirectory) {
        Config result = new Config();
        if (null == gitRepoDirectory) {
            return result;
        }
        if (null != fileObject) {
            result.relativePath = org.openide.filesystems.FileUtil.getRelativePath(gitRepoDirectory, fileObject);
            result.fileName = fileObject.getNameExt();
        }
        return result;
    }

    private String escapeSlashWithBang(String repo) {
        return repo.replaceAll("/", "!");
    }

}
