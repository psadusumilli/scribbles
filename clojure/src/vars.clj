(def ^:dynamic v 1) ; need "dynamic" metadata so v can be changed in a binding

(println "1) v =" v) ; -> 1

(defn change-it []
  (println "2) v =" v) ; -> 1
  (def v 2) ; changes root value
  (println "3) v =" v) ; -> 2

  (binding [v 3] ; binds a thread-local value
    (println "4) v =" v) ; -> 3
    (set! v 4) ; changes thread-local value
    (println "5) v =" v)) ; -> 4 binding end
  (println "6) v =" v)) ; thread-local value is gone now -> 2


(let [thread (Thread. #(change-it))]
  (.start thread)
  (.join thread)) ; wait for thread to finish

(println "7) v =" v) ; -> 2
