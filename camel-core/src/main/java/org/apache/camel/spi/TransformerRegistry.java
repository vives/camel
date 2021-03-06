/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.spi;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.StaticService;

/**
 * Registry to cache transformers in memory.
 * <p/>
 * The registry contains two caches:
 * <ul>
 *     <li>static - which keeps all the transformers in the cache for the entire lifecycle</li>
 *     <li>dynamic - which keeps the transformers in a {@link org.apache.camel.util.LRUCache} and may evict transformers which hasn't been requested recently</li>
 * </ul>
 * The static cache stores all the transformers that are created as part of setting up and starting routes.
 * The static cache has no upper limit.
 * <p/>
 * The dynamic cache stores the transformers that are created and used ad-hoc, such as from custom Java code that creates new transformers etc.
 * The dynamic cache has an upper limit, that by default is 1000 entries.
 *
 * @param <K> transformer key
 */
public interface TransformerRegistry<K> extends Map<K, Transformer>, StaticService {

    /**
     * Number of transformers in the static registry.
     */
    int staticSize();

    /**
     * Number of transformers in the dynamic registry
     */
    int dynamicSize();

    /**
     * Maximum number of entries to store in the dynamic registry
     */
    int getMaximumCacheSize();

    /**
     * Purges the cache (removes transformers from the dynamic cache)
     */
    void purge();

    /**
     * Whether the given transformer is stored in the static cache
     *
     * @param key  the transformer key
     * @return <tt>true</tt> if in static cache, <tt>false</tt> if not
     */
    boolean isStatic(String key);

    /**
     * Whether the given transformer is stored in the dynamic cache
     *
     * @param key  the transformer key
     * @return <tt>true</tt> if in dynamic cache, <tt>false</tt> if not
     */
    boolean isDynamic(String key);

    /**
     * Cleanup the cache (purging stale entries)
     */
    void cleanUp();

}
