(ns
  ^{:author vijayrc}
  com.vijayrc.clj.datastructures.sequences)

;abstraction 1  = sequence
;The sequence abstraction defines a way to obtain and traverse sequential views over some source of values:
(println (seq [1 2 3 4]))
(println (seq '(1 2 3 4)))
(println (seq {:x1 1 :x2 2 :x3 3 :x4 4}))

;sequences are costly since it requires full traversal on count unlike list which keeps track
;so lazy unlimited sequences are possible
;count - not-lazy version - execution order matters here, not sequence or list
; first call to range takes hit on range creation
(time (count (apply list (range 1e6)))) ;577ms
(time (count (range 1e6))); range returns a sequence ;150ms

;count - lazy version
(let [s (apply list (range 1e6))];0.02ms
  (time (count s)))

(let [s (range 1e6)];90ms
  (time (count s)))

;create sequences without underlying list/map...
(println (cons 1 (range 2 5))) ; 1 2 3 4
(println (list* 1 2 (range 3 5))) ; 1 2 3 4

;lazy sequence
(defn random-ints [limit]
  (lazy-seq (cons (rand-int limit) (random-ints limit)));stackoverflow without the lazy-seq wrap
  )
(println "lazy sequence: " (take 10 (random-ints 100)))