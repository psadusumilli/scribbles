;opt #1
(require 'clojure.string)
(println (clojure.string/join "&" [1,2,3]))

;opt #2
(require 'clojure.string);added to emphasize again
(alias 'cs 'clojure.string)
(println (cs/join "&" [1,2,3]))

;opt #3
(require 'clojure.string);added to emphasize again
(refer 'clojure.string)
(println (join "&" [1,2,3]))

;opt #4
(use 'clojure.string) ; combines require and refer, holds good as long there is no conflict
(println (join "&" [1,2,3]))

;create a new namespace
(def x 1)
(create-ns 'com.vijayrc)
(intern 'com.vijayrc 'x 2) ; define a new var x in com.vijayrc namespace
(println (+ com.vijayrc/x x)); 3

