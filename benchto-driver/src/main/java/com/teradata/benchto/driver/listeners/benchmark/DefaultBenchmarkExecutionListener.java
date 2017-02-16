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
package com.teradata.benchto.driver.listeners.benchmark;

import com.teradata.benchto.driver.Benchmark;
import com.teradata.benchto.driver.execution.BenchmarkExecutionResult;
import com.teradata.benchto.driver.execution.QueryExecution;
import com.teradata.benchto.driver.execution.QueryExecutionResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class DefaultBenchmarkExecutionListener
        implements BenchmarkExecutionListener
{

    @Override
    public int getOrder()
    {
        return 0;
    }

    @Override
    public Future<?> benchmarkStarted(Benchmark benchmark)
    {
        return CompletableFuture.completedFuture("");
    }

    @Override
    public Future<?> benchmarkFinished(BenchmarkExecutionResult benchmarkExecutionResult)
    {
        return CompletableFuture.completedFuture("");
    }

    @Override
    public Future<?> executionStarted(QueryExecution queryExecution)
    {
        return CompletableFuture.completedFuture("");
    }

    @Override
    public Future<?> executionFinished(QueryExecutionResult execution)
    {
        return CompletableFuture.completedFuture("");
    }
}
