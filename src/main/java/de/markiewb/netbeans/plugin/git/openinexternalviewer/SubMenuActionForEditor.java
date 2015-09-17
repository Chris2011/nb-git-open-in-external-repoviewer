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
package de.markiewb.netbeans.plugin.git.openinexternalviewer;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.actions.AbstractRepositoryPopupAction;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.RepoStrategy;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.RepoStrategy.Type;
import java.util.EnumSet;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

@ActionID(category = "Git", id = "de.markiewb.netbeans.plugin.git.openinexternalviewer.SubMenuActionForEditor")
@ActionRegistration(lazy = false, displayName = "openinexternalviewer.SubMenuActionForEditor")
@ActionReferences({
    @ActionReference(path = "Editors/TabActions", position = 299),
})
public final class SubMenuActionForEditor extends AbstractRepositoryPopupAction {

    private final EnumSet<RepoStrategy.Type> SUPPORTEDTYPES = EnumSet.of(RepoStrategy.Type.SHOW_FILE_HISTORY, RepoStrategy.Type.SHOW_FILE);

    @Override
    public EnumSet<Type> getSUPPORTEDTYPES() {
        return SUPPORTEDTYPES;
    }

}
