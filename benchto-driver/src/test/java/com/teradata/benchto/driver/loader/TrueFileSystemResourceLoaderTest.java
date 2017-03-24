package com.teradata.benchto.driver.loader;

import org.junit.Test;
import org.springframework.core.io.Resource;

import static org.junit.Assert.assertEquals;

public class TrueFileSystemResourceLoaderTest
{
    @Test
    public void loadAbsolutePath()
            throws Exception
    {
        Resource resource = new TrueFileSystemResourceLoader()
                .getResource("/dev/null");

        assertEquals("file:/dev/null", resource.getURL().toString());
    }
}
