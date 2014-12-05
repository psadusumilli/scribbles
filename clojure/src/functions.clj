;defn defines a function
(defn greet "will greet anyone" [name] (str "Hello ", name))
(println (greet "vijay"))
(defn curse "to cuss" [name] (str "fuck u ", name))
(println (curse "butters"))

;function overloading
(defn parting
  "returns a String parting in a given language"
  ([] (parting "World"))
  ([name] (parting name "en"))
  ([name language]
    (condp = language ;similar to switch case
      "en" (str "Goodbye, " name)
      "es" (str "Adios, " name)
      (throw (IllegalArgumentException.
               (str "unsupported language " language))))))

(println (parting)) ; -> Goodbye, World
(println (parting "Mark")) ; -> Goodbye, Mark
(println (parting "Mark" "es")) ; -> Adios, Mark
(println (parting "Mark", "en"))

;anonymous function
(def years [1940 1944 1961 1985 1987])
(println (filter #(> % 1950) years))
