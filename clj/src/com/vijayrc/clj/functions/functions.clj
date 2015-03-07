(ns
  ^{:author vijayrc}
  com.vijayrc.clj.functions.functions)

;fn
(println "simple fn functions " ((fn [x y] (+ x y 5)) 5 15)); 25


;defn defines a function = def+fn
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
(#(println "sum is " (+ %1 %2)) 1 2)

(def years [1940 1944 1961 1985 1987])
(println (filter #(> % 1950) years))


;anonymous function with a name
(defn pair-test "to check pairs sum is even"
  [filter-fn n1 n2] (if (filter-fn n1 n2 ) "pass" "no")
  )
(println "pair-test" (pair-test #(even? (+ %1 %2)) 3,4))

;function overloading on type no arity
(defmulti what-am-i class)
(defmethod what-am-i Number [arg] (println arg "is number"))
(defmethod what-am-i String [arg] (println arg "is String"))
(defmethod what-am-i :default [arg] (println arg "is Alien"))
(what-am-i "hey")
(what-am-i 1)
(what-am-i true)

;-------------------------------------------------------------------------------
;function with variable args
(defn f1 [x1 x2 x3] (println (+ x1 x2 x3)))
(defn f2 [x1 _ x3] (println (+ x1 x3)))
(f1 1 2 3)
(f2 1 2 3)

;complement
(defn teenager? [age] (and (>= age 13) (< age 20)))
(def non-teen? (complement teenager?))
(println (non-teen? 47)) ; -> true

;9881120883
;composition of gunctions
(defn times2 [n] (* n 2))
(defn minus3 [n] (- n 3))
; Note the use of def instead of defn because comp returns a function that is then bound to "my-composition".
(def my-composition (comp minus3 times2))
(println "composition " (my-composition 4)) ; 4*2 - 3 -> 5

;partial
(def my-multiply (partial * 2))
(println (my-multiply 3 4)) ; 2*3*4

;-------------------------------------------------------------------------------
;memoise where a map of args-results are stored thereby repeated calls with same args will be faster without execution
(defn f [x] (println "f called" x))
(def memo-f (memoize f))

(println "setup call")
(time (f 2))

(println "without memoization")
; Note the use of an underscore for the binding that isn't used.
(dotimes [_ 3] (time (f 2)))

(println "with memoization")
(dotimes [_ 3] (time (memo-f 2)))
