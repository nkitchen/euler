;;; How many Sundays fell on the first of the month during the twentieth
;;; century (1 Jan 1901 to 31 Dec 2000)?

(ns euler
  (:import (org.joda.time DateTimeConstants LocalDate)))

;(def df (new java.text.SimpleDateFormat "yyyy-MM-dd EEE"))
;
;(defmethod print-method Calendar [cal, #^java.io.Writer w]
;  (.write w "#<")
;  (.write w (.format df (.getTime cal)))
;  (.write w ">"))

(def start (LocalDate. 1901 1 1))
(def end (LocalDate. 2000 12 31))

(defn inc-month [#^LocalDate date]
  (.plusMonths date 1))

(def month-firsts (take-while #(neg? (compare % end))
                              (iterate inc-month start)))

(defn sunday? [#^LocalDate date]
  (when-let [p (= (.getDayOfWeek date) DateTimeConstants/SUNDAY)]
    (do (prn date) p)))

(prn (count (filter sunday? month-firsts)))
