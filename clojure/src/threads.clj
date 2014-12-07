(import '(java.lang.Thread))

;every clj function implements Runnable -  new Thread(runnable).start()
(defn delay_print [delay msg] (Thread/sleep delay)(println msg))
(.start (Thread. #(delay_print 1000 " World")))
(print "Hello") ; Hello - 1sec wait - world
