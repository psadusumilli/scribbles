(ns
  ^{:author vijayrc}
  com.vijayrc.clj.features.eval)

;eval evaulates the clojure expression
(println (eval :foo)) ; :foo
(println (eval [1,2,3])) ; [1,2,3]
(println (eval (count [1,2,3])))

;good to read dynamic code and eval
(eval (read-string "(println 123)")); 123

