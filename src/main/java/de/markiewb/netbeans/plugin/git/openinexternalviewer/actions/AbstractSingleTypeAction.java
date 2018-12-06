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
package de.markiewb.netbeans.plugin.git.openinexternalviewer.actions;

import de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies.RepoStrategy;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.Lookups;

/**
 * Action, which can invoke exactly one {@link RepoStrategy.Type).
 * Useful to provide single actions, which can be invoked without opening the context menu.
 * @author markiewb
 */
public abstract class AbstractSingleTypeAction implements ActionListener {

    private final FileObject context;

    public AbstractSingleTypeAction(FileObject context) {
        this.context = context;
    }

    protected abstract RepoStrategy.Type getSupportedType();

    @Override
    public void actionPerformed(ActionEvent ev) {
        AbstractRepositoryPopupAction contextMenuCreator = new AbstractRepositoryPopupAction() {
            @Override
            protected EnumSet<RepoStrategy.Type> getSUPPORTEDTYPES() {
                return EnumSet.of(getSupportedType());
            }
        };

        // create a context menu for exactly one type
        JMenu menu = contextMenuCreator.createMenu(Lookups.fixed(context));

        // if there is one sub menu entry, then call it
        if (null != menu && menu.getMenuComponentCount() > 0) {
            Component component = menu.getMenuComponent(0);

            if (component instanceof JMenuItem) {
                JMenuItem item = (JMenuItem) component;
                final Action action1 = item.getAction();

                action1.actionPerformed(ev);
            }
        }
    }
}
