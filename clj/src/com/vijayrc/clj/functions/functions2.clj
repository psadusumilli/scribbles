(ns
  ^{:author vijayrc}
  com.vijayrc.clj.functions.functions2)

;-------------------------------------------------------------------------------------------------------------
;predicates
;class?, coll?, decimal?, delay?, float?, fn?, instance?, integer?, isa?, keyword?,
;list?, macro?, map?, number?, seq?, set?, string? and vector?
;<, <=, =, not=, ==, >, >=, compare, distinct? identical?
;and, or, not, true?, false? nil?
;empty?, not-empty, every?, not-every?, not-any?
;even?, neg?, odd?, pos?, zero?

;-------------------------------------------------------------------------------------------------------------
;FUNCTIONS - apply/reduce/partial/comp

;reduce repeatedly applies the same function
(println "reduce sum is " (reduce + [1 2 3]));6
;convert a list to a map of x:x^2
(println
  (reduce
    (fn [map n] (assoc map n (* n n)))
    {}
    [1,2,3]))
;{3 9, 2 4, 1 1}

(println (reduce + (list 1 2 3 4 5))); translates to: (+ (+ (+ (+ 1 2) 3) 4) 5)
(println (apply + (list 1 2 3 4 5))); translates to: (+ 1 2 3 4 5)
(println (apply * 0.5 2 [1 2 3]));6

(println "apply" (apply hash-map [:a 5 :b 6])) ;= {:a 5, :b 6}
(println "reduce" (reduce hash-map [:a 5 :b 6])) ;= {{{:a 5} :b} 6}

;partial similar to currying
(def input [1,"abc",2,"cde"])
(println (filter string? input)) ; normal filter
(def only-string (partial filter string? ));partial prefixed to filter, input is given later
(println "partial: " (only-string input))

;compose functions
(def get-neg-sum-as-str (comp str - +))
(println "-ve sum is " (get-neg-sum-as-str 1 2 4)); -7

(require '[clojure.string :as str])
(println  (str/split "assCake" #"[A-Z]"))

(def break-camels (comp
                    #(str/join "-" % )
                    #(str/split % #"[A-Z]")))
(println "break camels" (break-camels "assCake"));ass-ake

;camel to keyword
(def camel->keyword (comp keyword
                      str/join
                      (partial interpose \-)
                      (partial map str/lower-case)
                      #(str/split % #"(?<=[a-z])(?=[A-Z])")))
(camel->keyword "assCake") ; :ass-cake
;-------------------------------------------------------------------------------------------------------------

;logger
;anonymous function gives u the %; the power to pick future arguments
(defn my-logger "modifies *out* to an custom pipe" [writer] #(binding [*out* writer] (println %)))
(def my-out-logger (my-logger *out*))
(my-out-logger "hey there")
(def my-file-logger (my-logger (java.io.FileWriter. "/home/vijayrc/Projs/VRC5/scribbles/clj/io.log")))
(my-file-logger "hey there")

;multi-logger
(defn multi-logger [& loggers]
  #(doseq [f loggers] (f %))); returs a anonymous function that loops through every logger function to log
((multi-logger my-file-logger my-out-logger) "hey there multi")



;fn function values - no definition here, so wont be re-used again
;# anonymous functions are sugar for fn
; defn is macro of def + fn
((fn [y] (println "y+1" (inc y))) 10) ;11



