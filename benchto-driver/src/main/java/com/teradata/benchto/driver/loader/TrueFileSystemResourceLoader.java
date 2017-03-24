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
