(ns
  ^{:author vijayrc}
  com.vijayrc.clj.features.reducer)

; legal forms -> (reduce f collection)(reduce f seed collection)
; repeatedly apply function f on each item in collection

(println (reduce + [1 2 3 4])) ; 10
(println (reduce + 1 [2 3 4])) ; 10
(println "make a map of numbers and squares = " (reduce
                                                  (fn [m x] (assoc m x (* x x)))
                                                  {}
                                                  [1 2 3 4]))
