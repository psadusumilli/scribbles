(ns
  ^{:author vijayrc}
  com.vijayrc.clj.concurrency.delays)


(def d1 (delay (println "i ran..")))
;deref or @d1 but function wrapped by delay is executed only once per refernce being dereferenced.
(deref d1) ; will block until d1 is realized (delays, future, promise all block)
@d1 ;

(def a-link {:site "vijayrc"
             :type "blog"
             :content (delay (do (println "fetching content..")(slurp "http://vijayrc.com/")))})

(println a-link) ; {:site vijayrc, :type blog, :content #<Delay@2beee7ff: :pending>}
(deref (get a-link :content)); fetching content..
(println "check if delay is realized?" (realized? (:content a-link)))