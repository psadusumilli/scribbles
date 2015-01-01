;times
(dotimes [x 3] (print x))
(dotimes [_ 3] (print "* "))

;while
(def x 1)
(while (< x 4) (print x)(def x (+ x 1)))

;for
(def cols "ABCD")
(def rows (range 1 5))

(dorun
  (for [row rows col cols]
    (print row col "| ")) ; for is lazy, dorun forces evaluation
  )
(println "")
;with filter
(dorun
  (for [col cols :when (not= col \B) ; \B is 'B' character literal
        row rows :while (< row 3)]
    (print row col "| "))
  )
(println "")
;doseq
(doseq [col cols :when (not= col \B) ; \B is 'B' character literal
        row rows :while (< row 3)]
  (print row col "| "))
(println "")

;recursion - computes the factorial of a positive integer in a way that doesn't consume stack space
; loop and recur work like the old goto : recur jumps to either 'loop' or a 'function' start
(loop [x 5] (if (neg? x) x
              (do (println x)(recur (dec x)))))

(defn factorial-1 [number]
  (loop [n number factorial 1]; n=number factorial=1 initialized
    (if (zero? n)
      factorial
      (recur (dec n) (* factorial n)))));jumps to the loop

(println "5!="(factorial-1 5))

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

