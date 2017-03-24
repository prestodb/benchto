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

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static com.google.common.collect.Maps.immutableEntry;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Useful methods for dealing with Yaml files.
 */
public final class YamlUtils
{
    private static final String BASE_KEY_NAME = "base";

    @SuppressWarnings("unchecked")
    public static Map<String, Object> loadYamlFromString(String string)
    {
        Yaml yaml = new Yaml();
        return (Map<String, Object>) yaml.load(string);
    }

    public static Map<String, List<String>> stringifyMultimap(Map<String, Object> variableMap)
    {
        return variableMap.entrySet()
                .stream()
                .map(entry -> immutableEntry(entry.getKey(),
                        asStringList(requireNonNull(entry.getValue(), "Null value for key: " + entry.getKey()))))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static List<String> asStringList(Object object)
    {
        if (!(object instanceof Iterable<?>)) {
            return ImmutableList.copyOf(Splitter.on(",").trimResults().omitEmptyStrings().split(object.toString()));
        }
        else {
            Iterable<?> iterable = (Iterable<?>) object;
            return StreamSupport.stream(iterable.spliterator(), false)
                    .map(Object::toString)
                    .collect(toList());
        }
    }

    public static Map<String, Object> loadBenchmarkYamlFromFile(Path benchmarkFile, FileReader fileReader)
            throws IOException
    {
        String content = fileReader.readAll(benchmarkFile);
        Map<String, Object> yaml = loadYamlFromString(content);
        if (yaml.containsKey(BASE_KEY_NAME)) {
            Object value = yaml.get(BASE_KEY_NAME);
            if (!(value instanceof String)) {
                throw new IOException(BASE_KEY_NAME + " is not a string in " + benchmarkFile);
            }
            yaml.remove(BASE_KEY_NAME);
            Path parent = benchmarkFile.getParent();
            Path baseBenchmarkFile = parent.resolve((String) value);
            Map<String, Object> baseYaml = loadBenchmarkYamlFromFile(baseBenchmarkFile, fileReader);
            mergeYaml(yaml, baseYaml);
        }
        return yaml;
    }

    public static Map<String, Object> loadBenchmarkYamlFromFile(Path benchmarkFile)
            throws IOException
    {
        return loadBenchmarkYamlFromFile(benchmarkFile, fileName -> new String(readAllBytes(benchmarkFile), UTF_8));
    }

    private static void mergeYaml(Map<String, Object> yaml, Map<String, Object> baseYaml)
    {
        for (Map.Entry<String, Object> entry : baseYaml.entrySet()) {
            if (yaml.containsKey(entry.getKey())) {
                if (entry.getValue() instanceof Map) {
                    mergeYaml((Map<String, Object>) yaml.get(entry.getKey()), (Map<String, Object>) entry.getValue());
                }
            }
            else {
                yaml.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private YamlUtils()
    {
    }
}
