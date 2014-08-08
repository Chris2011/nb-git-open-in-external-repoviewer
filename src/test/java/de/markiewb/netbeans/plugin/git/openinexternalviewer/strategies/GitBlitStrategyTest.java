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
public class GitBlitStrategyTest {

    @Test
    public void testGetUrl_Category() {
        String remote = "http://my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new GitBlitStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "http://my.company.com/gitblit/log/category!my.complex.repo.git/refs!heads!feature!feature42D";
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetUrl_CategoryAndUsername() {
        String remote = "http://user@my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new GitBlitStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "http://my.company.com/gitblit/log/category!my.complex.repo.git/refs!heads!feature!feature42D";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUrl_Username() {
        String remote = "http://user@my.company.com/gitblit/git/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new GitBlitStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "http://my.company.com/gitblit/log/category!my.complex.repo.git/refs!heads!feature!feature42D";
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUrl2() {
        String remote = "https://demo-gitblit.rhcloud.com/git/gitblit.git";
        String branchName = "gh-pages";
        String branchRevId = "42D";
        String result = new GitBlitStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "https://demo-gitblit.rhcloud.com/log/gitblit.git/refs!heads!gh-pages";
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetUrl3() {
        String remote = "http://user@my.company.com/gitblit/r/category/my.complex.repo.git";
        String branchName = "feature/feature42D";
        String branchRevId = "1234";
        String result = new GitBlitStrategyImpl().getUrl(remote, branchName, branchRevId);
        String expResult = "http://my.company.com/gitblit/log/category!my.complex.repo.git/refs!heads!feature!feature42D";
        assertEquals(expResult, result);
    }
}
