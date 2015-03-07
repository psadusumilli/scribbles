(ns
  ^{:author vijayrc}
  com.vijayrc.clj.datastructures.transients)

(def v1 (transient [1 2 3 4 5]))
(println "count-v1:" (count v1))
(def v2 (conj! v1 6)) ; using normal conj will give a classcast exception on creating persistent v2 from transient v1
(println "count-v2:" (count v2))
(println "count-v1:" (count v1))
(def v3 (persistent! v2))
(println "count-v3:" (count (conj v3 7)))
;(println "count-v2: " (count v2)) will throw error since v2 is now gone
;(println "count-v1:" (count v1))will throw error since v1 is now gone
;: conj! , assoc! , dissoc! , disj! , and pop!
; a transient can be modified in the same thread only