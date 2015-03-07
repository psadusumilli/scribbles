(ns
  ^{:author vijayrc}
  com.vijayrc.clj.features.meta)
(import '(java.lang.System))

(def a1 ^{:created (System/currentTimeMillis)} [1 3 4])
(println (meta a1))
(meta ^:private [1 2 3]) ; private=true (shorthand for boolean meta)
(def a2 (vary-meta a1 assoc :created (System/currentTimeMillis)))
(println a2)
