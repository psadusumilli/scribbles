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
(defn factorial-1 [number]
  (loop [n number factorial 1]; n=number factorial=1 initialized
    (if (zero? n)
      factorial
      (recur (dec n) (* factorial n)))));jumps to the loop

(println "5!="(factorial-1 5))

;predicates
;class?, coll?, decimal?, delay?, float?, fn?, instance?, integer?, isa?, keyword?,
;list?, macro?, map?, number?, seq?, set?, string? and vector?
;<, <=, =, not=, ==, >, >=, compare, distinct? identical?
;and, or, not, true?, false? nil?
;empty?, not-empty, every?, not-every?, not-any?
;even?, neg?, odd?, pos?, zero?