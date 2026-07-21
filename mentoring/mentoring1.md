Hey! Yes, it's doable purely with streams — but your diagnosis in point 3 is spot on: you'll lose that short-circuit optimization on `isPalindrome`, because it depends on mutable state (`minPalindrome`/`maxPalindrome`) that only exists while you're iterating sequentially. Streams — especially parallel ones — don't guarantee that kind of running state, so there's no clean way to cut corners the same way without reintroducing shared state (which kind of defeats the point of using streams in the first place).

**1. The `mapToObj` bug**

Your nested code produces a `Stream<Stream<Tuple>>`, not a `Stream<Tuple>` — that's why the generic type collapses to `Object`. You need `flatMap`, not an outer `mapToObj`. Also, `IntStream` can only be consumed once; reusing the same `factors` variable both in the outer and inner call will blow up at runtime on the second use. You need to recreate the inner range each iteration:

​```java
record Tuple(int factor1, int factor2, long product) {}

var tuples = IntStream.rangeClosed(minFactor, maxFactor)
    .boxed()
    .flatMap(f1 -> IntStream.rangeClosed(f1, maxFactor)
        .filter(f2 -> isPalindrome((long) f1 * f2))
        .mapToObj(f2 -> new Tuple(f1, f2, (long) f1 * f2)))
    .toList();
​```

**2. Grouping to find min/max**

Group by product, then grab the min and max keys from that map:

​```java
var byProduct = tuples.stream()
    .collect(Collectors.groupingBy(Tuple::product));

var min = byProduct.keySet().stream().mapToLong(Long::longValue).min().orElseThrow();
var max = byProduct.keySet().stream().mapToLong(Long::longValue).max().orElseThrow();

Map<Long, List<List<Integer>>> result = new TreeMap<>();
for (var key : List.of(min, max)) {
    var pairs = byProduct.get(key).stream()
        .map(t -> List.of(t.factor1(), t.factor2()))
        .toList();
    result.put(key, pairs);
}
​```

That gives you the same shape of result as the original method: a map from palindrome product to all the factor pairs that produce it.

**3. About losing the optimization**

You're right, this one's real, and there's no clean "streams way" to get it back. The original short-circuit avoids calling `isPalindrome` for any number that you already know won't become a new min/max — but that requires knowing the current min/max, which only exists in a sequential, stateful fold. A declarative Stream (especially a parallel one) doesn't have a notion of "current" mid-computation.

A few alternatives, none perfect:

- Just eat the cost: compute `isPalindrome` for all n(n+1)/2 pairs. For small/medium ranges the difference is irrelevant; for large ranges it can hurt (like you already saw on your machine).
- Custom collector with mutable reduction: you could write a Collector that keeps min/max as internal mutable state and does the pruning inside the accumulator — but at that point you're basically reimplementing the imperative loop underneath a streams API, just harder to read. I'd avoid this.
- Two passes: one pass computes all palindromes (no pruning) and finds min/max; a second pass filters the pairs matching those values. Simpler to write declaratively, but walks the data twice.

In practice, for this problem, I'd lean toward the first option (eat the cost) if the clarity is worth it — and I'd keep the original imperative version for cases where performance really matters (e.g. 6-digit-plus ranges).

**4. Is it "law" that streams are more readable?**

No, that's not consensus in the Java community. There's a strong case for streams on simple transformations (linear map/filter/collect), because they communicate intent without iteration details. But there's also a well-argued view — including from people like Brian Goetz, streams' architect on the JDK — that not every problem benefits from streams: when the logic involves complex mutable state, multiple correlated outputs (like your min and max at once), or ordering-dependent optimizations, imperative code is often clearer, not less.

Your case is exactly that: a pruning optimization that depends on sequential accumulated state. Forcing it into streams tends to produce denser, less readable code than the original — and it still loses performance. So for this specific problem, your imperative version is probably the more correct solution on both axes (readability and performance). The streams version is a great learning exercise, but I wouldn't call it an "improvement."