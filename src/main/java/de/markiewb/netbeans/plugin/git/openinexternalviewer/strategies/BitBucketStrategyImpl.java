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
package de.markiewb.netbeans.plugin.git.openinexternalviewer.strategies;

import org.openide.util.lookup.ServiceProvider;

/**
 * https://bitbucket.org/elbrecht/git-blog-examples.git
 * https://bitbucket.org/elbrecht/git-blog-examples/commits/branch/master
 *
 * https://www.bitbucket.org/elbrecht/git-blog-examples/pull-request/new?source=elbrecht/git-blog-examples::branch_name
 * https://gist.github.com/pitr/8242ac5253a72ddf3838
 *
 * @author markiewb
 */
@ServiceProvider(service = RepoStrategy.class)
public final class BitBucketStrategyImpl extends AbstractRepoStrategy {

    public BitBucketStrategyImpl() {
        super("bitbucket");
    }

}
