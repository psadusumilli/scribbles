(ns
  ^{:author vijayrc}
  com.vijayrc.clj.basics.basics)

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

