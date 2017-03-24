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
package com.teradata.benchto.driver.loader;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

/**
 * A {@link org.springframework.core.io.ResourceLoader} that supports {@code classpath:}, {@code file:}
 * and other URLs and loads from file system if location does not look like an URL. It is <em>true</em>
 * file system loader because, unlike the {@link FileSystemResourceLoader}, it honors absolute paths.
 */
public class TrueFileSystemResourceLoader
        extends FileSystemResourceLoader
{
    public TrueFileSystemResourceLoader()
    {
    }

    public TrueFileSystemResourceLoader(ClassLoader classLoader)
    {
        this();
        setClassLoader(classLoader);
    }

    @Override
    protected Resource getResourceByPath(String path)
    {
        if (path != null && path.startsWith("/")) {
            // Workaround superclass's stripping of leading '/'.
            path = "/" + path;
        }
        return super.getResourceByPath(path);
    }
}
