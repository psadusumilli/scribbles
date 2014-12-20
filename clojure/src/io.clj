; special symbols *in*, *out* and *err* are set to stdin, stdout and stderr by default
(binding [*out* (java.io.FileWriter. "/home/vijayrc/projs/VRC5/scribbles/clojure/io.log")]
  (print "this goes to my io.log file")
  (flush)
  )
;pr and prn functions are like their print and println counterparts,
; but their output is in a form that can be read by the Clojure reader
(let [x1 "catty" x2 {:pi Math/PI}]
  (println x1 x2)
  (println "pr =")
  (pr x1 x2)
  (println "\nprn =")
  (prn x1 x2)
  )