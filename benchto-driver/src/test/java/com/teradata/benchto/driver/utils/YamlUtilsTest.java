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

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class YamlUtilsTest
{

    @Test
    public void testLoadChained() throws IOException
    {
        FileReader fileReader = mock(FileReader.class);
        when(fileReader.readAll(Paths.get("path/goofy.yaml"))).thenReturn(
                "base: ../include/pet.yaml\n" +
                "type: dog\n" +
                "name: goofy\n" +
                "map:\n" +
                "  k1: v1\n"
        );
        when(fileReader.readAll(Paths.get("path/../include/pet.yaml"))).thenReturn(
                "type: pet\n" +
                "food: tasty\n" +
                "map:\n" +
                "  k1: v0\n" +
                "  k2: w0\n"
        );

        Map<String, Object> yaml = YamlUtils.loadBenchmarkYamlFromFile(Paths.get("path/goofy.yaml"), fileReader);
        assertEquals(4, yaml.size());
        assertEquals("goofy", yaml.get("name"));
        assertEquals("dog", yaml.get("type"));
        assertEquals("tasty", yaml.get("food"));
        Map<String, Object> yamlMap = (Map<String, Object>) yaml.get("map");
        assertEquals(2, yamlMap.size());
        assertEquals("v1", yamlMap.get("k1"));
        assertEquals("w0", yamlMap.get("k2"));
    }
}
