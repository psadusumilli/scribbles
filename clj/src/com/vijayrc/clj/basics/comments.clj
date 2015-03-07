(ns
  ^{:author vijayrc}
  com.vijayrc.clj.basics.comments)

;#_ comments the adjacent form
(print (eval (read-string "(+ 1 2 #_(* 2 2) 8)")))
;= (+ 1 2 8) => 11
