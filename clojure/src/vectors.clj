;[] is a vector more like a array
;Vectors are also ordered collections of items. They are ideal when new items will be added to or removed from the back (constant-time).
; This means that using conj is more efficient than cons for adding items.
; They are efficient (constant time) for finding (using nth) or changing (using assoc) items by index.
;Function definitions specify their parameter list using a vector.
; # is anonymous function and % is each item from list
(println "------------------VECTORS----------------------")

(println "count:" (count [19 "yellow" true]))
(println "reverse:" (reverse [2 4 7]))
(println "map:" (map #(+ % 3) [2 4 7])) ;applies function (+ % 3) on each element (%) in list
(println "map:" (map + [1 3 4][4 6 4][1 3 5 7]))
(println "apply: " (apply + [3 5 6]))

(def boys ["Stan" "Cartman" "Kyle" "Kenny"])
(#(println %) boys)
(println "access via index " (boys 2))

(println "pick: " (first boys)(second boys)(last boys)(nth boys 2))
(println "filter: "(filter #(> (count %) 5) boys))

(println "predicates:"
  (every? #(instance? String %) boys)
  (not-every? #(instance? String %) boys)
  (some #(instance? Number %) boys)
  (not-any? #(instance? Number %) boys)
  )

(println "get within index: "  (get boys 0 "unknown"))
(println "get outside index: " (get boys 5 "unknown"))
(println "assoc: "  (assoc boys 1 "Butters") )
(println "subvec: "  (subvec boys 2 ) )

(println "peek: " (peek boys ))  ;operates on the end element so Kenny
(println "pop: " (pop boys ))
(println "cons: " (cons "butters" boys)) ; appends to front
(println "conj: " (conj boys "bebe")) ; appends to back

