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

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

@ActionID(category = "Git", id = "de.markiewb.netbeans.plugin.git.openinexternalviewer.OpenAction")
@ActionRegistration(lazy = false, displayName = "openinexternalviewer.OpenAction")
@ActionReferences({
    @ActionReference(path = "Projects/Actions", position = 500)
})
@NbBundle.Messages("LBL_OpenAction=Open '<remotebranch>' at '<label>'")
public final class OpenAction extends UrlAction {

    public OpenAction() {
        super(RepoStrategy.Type.OPEN, Bundle.LBL_OpenAction());
    }
}
