;(def boys (sorted-map :k1 :Stan, :k2 :Cartman , :k3 :Kenny , :k4 :Kyle ))
;(def boys (hash-map :k1 :Stan, :k2 :Cartman , :k3 :Kenny , :k4 :Kyle ))

(println "---------------MAPS------------")

(def boys {:k1 :Stan, :k2 :Cartman , :k3 :Kenny , :k4 :Kyle})

(println (boys :k1 ))
(println (keys boys))
(println (vals boys))

(println (assoc boys :k1 :Butters, :k5 :Clyde ))
(println (dissoc boys :k1  ))