(ns
  ^{:author vijayrc}
  com.vijayrc.clj.features.lazy)

(map #(println %) [1,2,3]); nothing will be printed as map is lazy
(dorun (map #(print %) [1,2,3])); dorun forces evaulation
(doseq [x [1,2,3]] (print x)); doseq better than dorun as a duplicate sequence create is avoided
(doall (map #(print %) [1,2,3])); doseq similar to dorun, excepts it caches the results


;lazy evaluation can help in building infinite collections as they are evaluated on a need-by basis
(defn f
  "square the argument and divide by 2"
  [x]
  (println "calculating f of" x)
  (/ (* x x) 2.0))

; Create an infinite sequence of results from the function f
; for the values 0 through infinity.
; Note that the head of this sequence is being held in the binding "f-seq".
; This will cause the values of all evaluated items to be cached.
(def f-seq (map f (iterate inc 0)))

; Force evaluation of the first item in the infinite sequence, (f 0).
(println "first is" (first f-seq)) ; -> 0.0

; Force evaluation of the first three items in the infinite sequence.
; Since the (f 0) has already been evaluated,
; only (f 1) and (f 2) will be evaluated.
(doall (take 3 f-seq))

(println (nth f-seq 2)) ; uses cached result -> 2.0
