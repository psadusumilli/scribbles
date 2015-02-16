(ns
  ^{:author vijayrc}
  com.vijayrc.clj.datastructures.maps)
;implementation
;(def boys (sorted-map :k1 :Stan, :k2 :Cartman , :k3 :Kenny , :k4 :Kyle ))
;(def boys (hash-map :k1 :Stan, :k2 :Cartman , :k3 :Kenny , :k4 :Kyle ))

(println "---------------MAPS------------")

(def boys {:k1 :Stan, :k2 :Cartman , :k3 :Kenny , :k4 :Kyle})

(println (boys :k1))
(println (keys boys))
(println (vals boys))
(println (assoc boys :k1 :Butters , :k5 :Clyde))
(println (dissoc boys :k1))
(println (select-keys boys [:k1 , :k2]))
(doseq [[key boy] boys]
  (println (name key) " is southpark boy " (name boy)))

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
(println "employer state of person is " (get-in person [:employer , :address , :state]))
(println "employer city of person " (-> person :employer :address :city)) ; thread macro passes one func output as input to other
(println "employer city of person " (reduce get person [:employer :address :city])) ; reduce repeatedly applies 'get' function on each item in collection

(def new_person (assoc-in person [:employer :address :city] "Richmond")) ; modify the nested value
(println "modified city: " (-> new_person :employer :address :city))

(println "sequence: " (seq boys))
(def sm (sorted-map :x1 1 :x5 5 :x2 2 :x4 4 :x3 3))
(println "sorted-map: " sm)
(println "subseq: " (subseq sm <= :x3))

;-------------------------------------------------------------------------------------------------------------
;groupby
(println "group-by"
  (group-by :grade [{:name "b1" :grade "third"} {:name "b2" :grade "third"} {:name "b3" :grade "first"}])
  )


(defn reduce-by
  [key-fn f init coll]
  (reduce (fn [summaries x]
            (let [k (key-fn x)]
              (assoc summaries k (f (summaries k init) x))))
    {} coll)
  )

(def orders
  [{:product "Clock", :customer "Wile Coyote", :qty 6, :total 300}
   {:product "Dynamite", :customer "Wile Coyote", :qty 20, :total 5000}
   {:product "Shotgun", :customer "Elmer Fudd", :qty 2, :total 800}
   {:product "Shells", :customer "Elmer Fudd", :qty 4, :total 100}
   {:product "Hole", :customer "Wile Coyote", :qty 1, :total 1000}
   {:product "Anvil", :customer "Elmer Fudd", :qty 2, :total 300}
   {:product "Anvil", :customer "Wile Coyote", :qty 6, :total 900}])

(println "total orders per customer: " (reduce-by :customer #(+ %1 (:total %2)) 0 orders))
(println "products per customer: " (reduce-by :product #(conj %1 (:customer %2)) {} orders))






