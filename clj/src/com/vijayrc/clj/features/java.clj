(ns
  ^{:author vijayrc}
  com.vijayrc.clj.features.java)

(import '(java.lang.Math))
(import '(java.util Calendar GregorianCalendar))

;calling static functions
(def r1 (. Math pow 2 4))
(def r2 (Math/pow 2 4))
(println r1 "|" r2)

;calling instance constructor and functions - new GregorianCalendar(year, month, dayOfMonth)
(def cal1 (new GregorianCalendar 2014 Calendar/APRIL 16))
(def cal2 (GregorianCalendar. 2014 Calendar/APRIL 1))
(println cal1 "|" cal2)
(. cal1 add Calendar/MONTH 2)
(println (. cal1 get Calendar/MONTH)); -> 5
(.add cal2 Calendar/MONTH 2)
(println (.get cal2 Calendar/MONTH)) ; -> 5

;macro
(println "long way = " (. (. cal1 getTimeZone) getDisplayName))
(println "macro way = " (.. cal1 getTimeZone getDisplayName))

;doto to call functions on the same object nicely
(doto cal1 (.set Calendar/YEAR 1980) (.set Calendar/MONTH Calendar/MAY) ) ; setting fields here
(def formatter (java.text.DateFormat/getDateInstance)) ; direct without import
(println (.format formatter (.getTime cal1)))

;map a java function
(println (map #(.substring %1 %2) ["cartman", "kenny","stan"] [1,2,3])) ; (artman nny n)
(println (map (memfn substring beginIndex) ["cartman", "kenny","stan"] [1,2,3])) ; (artman nny n)




