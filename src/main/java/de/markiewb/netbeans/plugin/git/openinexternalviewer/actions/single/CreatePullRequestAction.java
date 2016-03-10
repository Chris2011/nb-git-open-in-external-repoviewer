/* 
 * Copyright 2016 markiewb.
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
package de.markiewb.netbeans.plugin.git.openinexternalviewer.actions.single;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.actions.AbstractSingleTypeAction;
import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.RepoStrategy;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Git",
        id = "de.markiewb.netbeans.plugin.git.openinexternalviewer.actions.single.CreatePullRequestAction"
)
@ActionRegistration(
        displayName = "#CTL_CreatePullRequestAction"
)
@Messages("CTL_CreatePullRequestAction=Create pull request (in browser)")
public final class CreatePullRequestAction extends AbstractSingleTypeAction {

    public CreatePullRequestAction(FileObject context) {
        super(context);
    }

    @Override
    protected RepoStrategy.Type getSupportedType() {
        return RepoStrategy.Type.PULL_REQUEST;
    }

}
