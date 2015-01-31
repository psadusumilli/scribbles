(ns
  ^{:author vijayrc}
  com.vijayrc.clj.features.macros)

;run different functions based on how close to zero
(defmacro around-zero [number negative-expr zero-expr positive-expr]
  `(let [number# ~number] ; so number is only evaluated once
     (cond
       (< (Math/abs number#) 1e-15) ~zero-expr ; e to the power -15
       (pos? number#) ~positive-expr
       true ~negative-expr)))

(println (around-zero 0.1 "-ve" "0" "+ve"))
