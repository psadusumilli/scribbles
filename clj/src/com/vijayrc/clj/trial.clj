(ns
  ^{:author vijayrc}
  com.vijayrc.clj.trial)

(println (apply + [1 2 3]))

;compose functions
(def get-neg-sum-as-str (comp str - +))
(println "-ve sum is " (get-neg-sum-as-str 1 2 4)); -7