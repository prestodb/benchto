/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.teradata.benchto.driver.utils;

import com.google.common.io.ByteSource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

import static com.facebook.presto.jdbc.internal.guava.base.Preconditions.checkNotNull;

public final class ResourceUtils
{
    public static ByteSource asByteSource(Resource resource)
    {
        checkNotNull(resource);
        return new ByteSource()
        {
            @Override
            public InputStream openStream()
                    throws IOException
            {
                return resource.getInputStream();
            }
        };
    }

    private ResourceUtils() {}
}
