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
public class EditorPlaceHolderResolver implements PlaceHolderResolver {
    private final JTextComponent ed;
    private final FileObject gitRepoDirectory;


    public EditorPlaceHolderResolver(JTextComponent ed, FileObject gitRepoDirectory) {
        this.ed = ed;
        this.gitRepoDirectory = gitRepoDirectory;
    }
    protected static class Config{
        Integer linenumber;
        String relativePath;
    }

    @Override
    public Map<String, String> resolve() {
        Config config = initFromEditor(ed, gitRepoDirectory);
        Map<String, String> result = new HashMap<String, String>();
        if (null != config.linenumber) {
            result.put("<linenumber\\|0based>", config.linenumber.toString());
            result.put("<linenumber\\|1based>", String.valueOf(config.linenumber+1));
        }
        if (null != config.relativePath) {
            result.put("<fullfilepath>", config.relativePath);
            result.put("<fullfilepath\\|escapeSlashWithBang>", escapeSlashWithBang(config.relativePath));
        }
        return result;
    }

    //VisibleForTesting
    protected Config initFromEditor(JTextComponent ed, FileObject gitRepoDirectory) {
        Config result=new Config();
        if (null == ed) {
            return result;
        }
        if (null == gitRepoDirectory) {
            return result;
        }
        BaseDocument doc = org.netbeans.editor.Utilities.getDocument(ed);
        if (null == doc) {
            return result;
        }
        FileObject fileObject = org.netbeans.modules.editor.NbEditorUtilities.getFileObject(doc);
        if (null != fileObject) {
            result.relativePath=org.openide.filesystems.FileUtil.getRelativePath(gitRepoDirectory, fileObject);
        }
        //org.netbeans.editor.Utilities
        //org.netbeans.modules.editor.NbEditorUtilities
        try {

            int lineEnd = org.netbeans.editor.Utilities.getLineOffset(doc, ed.getSelectionEnd());
            int lineStart = org.netbeans.editor.Utilities.getLineOffset(doc, ed.getSelectionStart());
            if (lineEnd == lineStart) {
                //single line 
                result.linenumber = lineStart;

            } else {
                //multiple lines
                result.linenumber = null;

            }
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
        return result;
    }


    private String escapeSlashWithBang(String repo) {
        return repo.replaceAll("/", "!");
    }

}
