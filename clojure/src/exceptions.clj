(defn throw-given [type] (condp = type
                             "null" (throw (NullPointerException.))
                             "number-format" (throw (NumberFormatException.))
                           (throw (RuntimeException.))
                           ))
(defn try-catcher [f type] ((try
                             (f type)
                              (catch NullPointerException e (println "Null" e))
                              (catch NumberFormatException e (println "Bad Number" e))
                              (catch RuntimeException e (println ""))
                              );try
                    ))

(try-catcher #(throw-given %1) "null")