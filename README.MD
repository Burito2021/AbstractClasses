List available tasks: To see all available JMH tasks, use:
./gradlew tasks --group="benchmark"

Run benchmarks: To execute benchmarks defined in your project, use:
./gradlew jmh

Customize benchmark execution: You can customize the benchmark execution by specifying the task name:
./gradlew jmh -PjmhInclude="MyBenchmarkClassName.benchmarkMethod"

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2) // Number of times to fork a single benchmark @Warmup(iterations = 3, time = 1, timeUnit =
TimeUnit.SECONDS) // Warmup iterations and time @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) //
Actual measurement iterations and time