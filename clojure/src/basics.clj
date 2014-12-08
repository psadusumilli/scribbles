;syntax
(comment syntax study)
(println (+  1  2 ));
(println "Hello machi");
(def x 3)
(def y 5)
(* x y )
(def z (+ x y ))
(println "z =" z)

;to change variable value..immutable create a new variable
(def y (+ y 1))
(println "y =" y)

;defn defines a function
(defn greet "will greet anyone" [name] (str "Hello ", name))
(greet "vijay")

;if and when
(if (= x 3) (println "x=3") (println "x!=3"))
(if (= x 4) (println "x=4") (do (println "x!=4") (println "just do somethingelse"))) ; 'do' required for multiple statements in else
(when (= x 3) (println "x=3"))
(when-not (= x 4) (println "x!=3") (println "sleep"))

;if-let assigns the variable 'name' and also checks condition based on its existence
(defn process_queue [items]
  (if-let [name (first items)] (println name "is next") (println "no one in queue")))
(process_queue [])
(process_queue ["cartman","kenny","stan"])

;switch condp
(defn numbero [no] (condp = no
                     1 (println "u gave 1")
                     2 (println "u gave 2")
                     3 (println "u gave 3")
                     (println "donno u")
                     ))
(numbero 1)(numbero 5)


;cond
(println "about x:" (cond
  (<= x 2) "x <= 2"
  (> x 2) "x > 2"
  true "donno"
  ))

