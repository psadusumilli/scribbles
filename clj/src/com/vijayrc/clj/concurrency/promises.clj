(ns
  ^{:author vijayrc}
  com.vijayrc.clj.concurrency.promises)

(def m1 (promise)) ; a promise is a data container expecting a value to be delivered
(println "got something?" (realized? m1))
(deliver m1 "package")
(println "got something?" (realized? m1) @m1)

(def a (promise))
(def b (promise))
(def c (promise))
(future  (println "waiting ..") (deliver c (+ @a @b)) (println "c is " @c)) ;  this future thread is blocked
(Thread/sleep 1000)
(deliver a 1)
(deliver b 3)
(shutdown-agents)