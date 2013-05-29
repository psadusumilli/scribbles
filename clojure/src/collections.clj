(println (count [19 "yellow" true]))
(println (reverse [2 4 7]))
(println (map #(+ % 3) [2 4 7])) ;applies function (+ % 3) on each element (%) in list
(println (map + [1 3 4][4 6 4][1 3 5 7]))
(println (apply + [3 5 6]))

;[] is a vector more like a array
(def boyz ["Stan" "Cartman" "Kyle" "Kenny"])
(#(println %) boyz)
(println(first boyz)(second boyz)(last boyz)(nth boyz 2))
(println(filter #(> (count %) 5) boyz))
(println
  (every? #(instance? String %) boyz)
  (not-every? #(instance? String %) boyz)
  (some #(instance? Number %) boyz)
  (not-any? #(instance? Number %) boyz)
 )

;list is done by adding a ' or mention 'list'
(def bots '("Stan" "Cartman" "Kyle" "Kenny"))
(println "is any token?"(some #(= % "Token") bots))
(println "is any kenny?"(some #(= % "Kenny") bots))
(contains? (set bots) "Kenny")
