(ns
  ^{:author vijayrc}
  com.vijayrc.clj.concurrency.futures)

(defn total "get sum of numbers" [list] (apply + list))

(def output (future (total [1,2,3]))); total is run by separate thread from cached thread pool
(println "output from future = " @output); will block until output is realized (delays, future, promise all block)

(println (deref (future (Thread/sleep 5000) :done!)
  1000
  :impatient!)); only futures can have timeout interval and fallback values
(shutdown-agents)
