(ns
  ^{:author vijayrc}
  com.vijayrc.clj.concurrency.futures)

(defn total "get sum of numbers" [list] (apply + list))

(def output (future (total [1,2,3]))); total is run by separate thread from cached thread pool
(println "output from future = " @output)
(shutdown-agents)
