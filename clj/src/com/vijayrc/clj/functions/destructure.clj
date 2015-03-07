(ns
  ^{:author vijayrc}
  com.vijayrc.clj.functions.destructure)

;to break input params
(defn summer "add item1 and item3 in given list "
  [[x1 _ x3]] (+ x1 x3)); not the embedded square brackets
(println "sum =" (summer [1 2 4 5])) ; 1+4

;&
(defn name-summary [[name1 name2 & others]]
  (println (str name1 ", " name2) "and" (count others) "others"))
(name-summary ["Moe" "Larry" "Curly" "Shemp"]) ; -> Moe, Larry and 2 others

;sum of first and 3rd items divided by sum of everything indicated by :as
(defn first-and-third-percentage [[n1 _ n3 :as coll]]
  (/ (+ n1 n3) (apply + coll)))
(println "1-3/% = " (first-and-third-percentage [4 5 6 7]));

;pull by index
(def x [1,2,3,4,5] )
(let [{y 1} x] (println "y is " y))

;extracting from map
(defn summer-sales-percentage
  ; The keywords below indicate the keys whose values
  ; should be extracted by destructuring.
  ; The non-keywords are the local bindings
  ; into which the values are placed.
  [{june :june july :july august :august :as all}]
  (let [summer-sales (+ june july august)
        all-sales (apply + (vals all))]
    (/ summer-sales all-sales)))

(def sales {
             :january   100 :february 200 :march      0 :april    300
             :may       200 :june     100 :july     400 :august   500
             :september 200 :october  300 :november 400 :december 600})

(println "summer sales = " (summer-sales-percentage sales)) ; ratio reduced from 1000/3300 -> 10/33

;if keys names match
(def chas {:name "Chas" :age 31 :location "Massachusetts"})
(let [{:keys [name age location]} chas]
  (println (format "%s is %s years old and lives in %s." name age location)))

(def brian {"name" "Brian" "age" 31 "location" "British Columbia"})
(let [{:strs [name age location]} brian]
  (println (format "%s is %s years old and lives in %s." name age location)))

(def christophe {'name "Christophe" 'age 33 'location "Rhône-Alpes"})
(let [{:syms [name age location]} christophe]
  (println (format "%s is %s years old and lives in %s." name age location)))

(def user-info ["robert8990" 2011 :name "Bob" :city "Boston"])
(let [[username account-year & extra-info] user-info
      {:keys [name city]} (apply hash-map extra-info)]
  (println (format "%s is in %s" name city)))
(let [[username account-year & {:keys [name city]}] user-info]
  (println (format "%s is in %s" name city)))

;-------------------------------------------------------------------------------------------------
;or -default value x is given 2 since no-key does not exist in input {:key 3}
(let [{x :no-key :or { x 2}} {:key 3}] (println "default x is " x))

;combining vector and map
(def m {:a 5 :b 6
        :c [7 8 9]
        :d {:e 10 :f 11}
        "foo" 88
        42 false})
(let [{[x _ y] :c} m]
  (println "x+y=" (+ x y))) ; 16
