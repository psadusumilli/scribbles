(ns
  ^{:author vijayrc}
  com.vijayrc.clj.features.eval)

;eval evaulates the clojure expression
(println (eval :foo)) ; :foo
(println (eval [1,2,3])) ; [1,2,3]
(println (eval (count [1,2,3])))

;good to read dynamic code and eval
(eval (read-string "(println 123)")); 123

(def x 5)
(println "x is " x) ; 5
(println "x with quotes is " 'x) ;  x - quote suppresses the evaluation