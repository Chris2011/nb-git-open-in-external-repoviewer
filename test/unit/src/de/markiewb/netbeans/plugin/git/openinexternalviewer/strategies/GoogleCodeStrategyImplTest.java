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
package de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author markiewb
 */
public class GoogleCodeStrategyImplTest {

    @Test
    public void testGetUrl() {
        String remote = "https://code.google.com/p/gitblit/";
        String branchName = "bootstrap";
        String branchRevId = "1234";
        String result = new GoogleCodeStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "https://code.google.com/p/gitblit/source/list?name=bootstrap";
        assertEquals(expResult, result);
    }

}
