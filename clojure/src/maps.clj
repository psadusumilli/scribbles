;(def boys (sorted-map :k1 :Stan, :k2 :Cartman , :k3 :Kenny , :k4 :Kyle ))
;(def boys (hash-map :k1 :Stan, :k2 :Cartman , :k3 :Kenny , :k4 :Kyle ))

(println "---------------MAPS------------")

(def boys {:k1 :Stan, :k2 :Cartman , :k3 :Kenny , :k4 :Kyle})

(println (boys :k1 ))
(println (keys boys))
(println (vals boys))
(println (assoc boys :k1 :Butters, :k5 :Clyde ))
(println (dissoc boys :k1  ))
(println (select-keys boys [:k1, :k2]))


(def person {
              :name "Mark Volkmann"
              :address {
                         :street "644 Glen Summit"
                         :city "St. Charles"
                         :state "Missouri"
                         :zip 63304}
              :employer {
                          :name "Object Computing, Inc."
                          :address {
                                     :street "12140 Woodcrest Executive Drive, Suite 250"
                                     :city "Creve Coeur"
                                     :state "Missouri"
                                     :zip 63141}}})

(println "employer zip of person is " (((person :employer) :address) :zip))