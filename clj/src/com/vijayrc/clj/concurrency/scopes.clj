(ns
  ^{:author vijayrc}
  com.vijayrc.clj.concurrency.vars)

(def ^:dynamic v 1) ; need "dynamic" metadata so v can be changed in a binding

(println "a) v =" v) ; -> 1

(defn change-it []
  (println "b) v =" v) ; -> 1
  (def v 2) ; changes root value
  (println "c) v =" v) ; -> 2

  (binding [v 3] ; binds a thread-local value
    (println "d) v =" v) ; -> 3
    (set! v 4) ; changes thread-local value
    (println "e) v =" v)) ; -> 4 binding end
  (println "f) v =" v)) ; thread-local value is gone now -> 2


(let [thread (Thread. #(change-it))]
  (.start thread)
  (.join thread)) ; wait for thread to finish

(println "g) v =" v) ; -> 2

