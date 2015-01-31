(ns
  ^{:author vijayrc}
  com.vijayrc.clj.datastructures.lists)

;-----------------------------------------------------------------------------------------------
;list is done by adding a ' or mention 'list'
;Lists are ordered collections of items.
;They are ideal when new items will be added to or removed from the front (constant-time).
;They are not efficient (linear time) for finding items by index (using nth)
;and there is no efficient way to change items by index.

(println "------------------LISTS----------------------")

(def boys '("Stan" "Cartman" "Kyle" "Kenny"))
(println "is any token?"(some #(= % "Token") boys))
(println "is any kenny?"(some #(= % "Kenny`") boys))
(println "contains? " (contains? (set boys) "Kenny"))

(println "omg they killed kenny!" (remove #(= % "Kenny") boys ))

(def new-boys (conj boys "Jimmy")) ;vector
(println "concatenation: " new-boys )

(def boys1 ["Stan" "Cartman" "Kyle" "Kenny"])
(def all-boys (into boys  boys1))
(println "union of  collections" all-boys)

(println "get wont work " (get boys 1 "unknown"))

(println "peek: " (peek boys ))  ;operates on the start element so Stan
(println "pop: " (pop boys ))

