(ns
  ^{:author vijayrc}
  com.vijayrc.clj.concurrency.atoms)

;atom gives atomic transaction
;mutates the variable
;swap! blocks until modification is done
;no locks, if atom is modified simultaneously by another thread, then current thread re-executes again with newer values

(def stan (atom {:name "Stan" :age 8 :friends ["kyle" "cartman"]}))

(swap! stan #(update-in % [:age] + 3)) ;swap! takes in a atom and a function to work on stan; Again update-in takes in a + func with 3 arg
(println stan) ;{:age 11, :friends [kyle cartman], :name Stan}

(swap! stan update-in [:age] + 2) ; sugar
(println stan) ;{:age 13, :friends [kyle cartman], :name Stan}

(swap! stan (comp #(update-in % [:age] + 1) #(assoc % :friends ["kyle" "cartman" "kenny"]))); composition of functions
(println stan) ;{:age 14, :friends [kyle cartman kenny], :name Stan}

;--------------------------------------------------------------------------------------------------------------
