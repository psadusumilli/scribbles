(ns
  ^{:author vijayrc}
  com.vijayrc.clj.concurrency.atoms)
;atom gives atomic transaction; implement synchronous (block), uncoordinated(multiple actors), atomic compare-and-set modification.
;mutates the variable
;swap! blocks until modification is done
;no locks, if atom is modified simultaneously by another thread, then current thread re-executes again with newer values

(def stan (atom {:name "Stan" :age 8 :friends ["kyle" "cartman"]}))

(swap! stan #(update-in % [:age] + 3)) ;swap! takes in a atom and a function to work on stan; Again update-in takes in a + func with 3 arg
(println stan) ;{:age 11, :friends [kyle cartman], :name Stan}

(swap! stan update-in [:age] + 2) ; sugar
(println stan) ;{:age 13, :friends [kyle cartman], :name Stan}

;watch function
(defn stalker [key object old new] (println old "->" new))
(add-watch stan :w1 stalker)
(swap! stan (comp #(update-in % [:age] + 1) #(assoc % :friends ["kyle" "cartman" "kenny"]))); composition of functions
;{:age 13, :friends [kyle cartman], :name Stan} -> {:age 14, :friends [kyle cartman kenny], :name Stan}
(remove-watch stan :w1)


;validator function
(def n (atom 1 :validator pos?))
(swap! n + 500)
(try
  (swap! n - 1000)
  (catch IllegalStateException e (println "n cannot be -ve")))

(def randy (atom {:name "Marsh" :age 39}))
;set validator can be used for already created atoms
(set-validator! randy #(or (pos? (:age %))
                   (throw (IllegalStateException. "age must be +ve"))))
(swap! randy update-in [:age] + 20)
(swap! randy update-in [:age] - 60)