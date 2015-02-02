(ns
  ^{:author vijayrc}
  com.vijayrc.clj.basics.bindings)


(def ^:dynamic v 1) ; v is a global binding, the :dynamic indicates its value can be changed in f4

(defn f1 []
  (println "f1: v:" v)
  )

(defn f2 []
  ; creates local binding v that shadows global one
  (println "f2: before let v:" v)
  ; local binding only within this let statement
  (let [v 2](println "f2: in let, v:" v)(f1));f1=1
  ; outside of this let, v refers to global binding
  (println "f2: after let v:" v)
  )

(defn f3 []
  ; same global binding with new, temporary value
  (println "f3: before binding v:" v)
  ; global binding, new value
  (binding [v 3](println "f3: within binding function v: " v) (f1));f1=3
  ; outside of binding v refers to first global value
  (println "f3: after binding v:" v)
  )

(defn f4 []
  ; changes the value of v in the global scope
  (def v 4)
  )

(println "(= v 1) => " (= v 1))
(println "Calling f2: --------------------------")(f2)
(println "Calling f3: --------------------------")(f3)
(println "Calling f4: --------------------------")(f4)
(println "after calling f4, v =" v)


;let allows statements in its binding vector
(let [x 1
      _ (println "x is " x) ;1 -underscore ignores the bindings
      x (+ x 1)]
  (println "x in let body is " x)) ;2
