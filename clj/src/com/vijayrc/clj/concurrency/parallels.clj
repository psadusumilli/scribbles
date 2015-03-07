(ns
  ^{:author vijayrc}
  com.vijayrc.clj.concurrency.parallels.clg)

;extract phone numbers from string input
(defn phone-numbers
  [string]
  (re-seq #"(\d{3})[\.-]?(\d{3})[\.-]?(\d{4})" string))
(println (phone-numbers " Sunil: 617.555.2937, Betty: 508.555.2218"))

;simulate a big string input
(def files (repeat 100
             (apply str(concat (repeat 1000000 \space)
                         "Sunil: 617.555.2937, Betty: 508.555.2218"))))
(time (dorun (map phone-numbers files)))
(time (dorun (pmap phone-numbers files))) ; pmap parallel map determines the best number of futures based on cpu cores
(shutdown-agents)
