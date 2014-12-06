;
;def (boys (hash-set  "Stan" "Kenny" "Cartman" "Kyle"))
;def (boys (sorted-set  "Stan" "Kenny" "Cartman" "Kyle"))
(println "------------------SETS----------------------")
(def boys #{  "Stan" "Kenny" "Cartman" "Kyle"})

(println "is Kyle there? " (contains?  boys "Kyle") )
(println "set as contains?"  (boys "Kyle"))
(println "set as contains?"  (boys "Tweek"))
(println "conjoin:"  (conj boys "Jimmy"))
(println "disjoin:"  (disj boys "Cartman"))