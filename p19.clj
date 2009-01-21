;;; How many Sundays fell on the first of the month during the twentieth
;;; century (1 Jan 1901 to 31 Dec 2000)?

(ns euler
  (:import java.util.Calendar))

(defn inc-month [cal]
  (doto (.clone cal)
    (.add Calendar/MONTH 1)))

(def df (new java.text.SimpleDateFormat "yyyy-MM-dd EEE"))

(defmethod print-method Calendar [cal, #^java.io.Writer w]
  (.write w "#<")
  (.write w (.format df (.getTime cal)))
  (.write w ">"))

(def start (doto (Calendar/getInstance)
             (.set Calendar/DAY_OF_MONTH 1)
             (.set Calendar/MONTH Calendar/JANUARY)
             (.set Calendar/YEAR 1901)))
(def end (doto (Calendar/getInstance)
           (.set Calendar/DAY_OF_MONTH 31)
           (.set Calendar/MONTH Calendar/DECEMBER)
           (.set Calendar/YEAR 2000)))

(def month-firsts (take-while #(neg? (compare % end))
                              (iterate inc-month start)))

(defn sunday? [cal]
  (= (.get cal Calendar/DAY_OF_WEEK) Calendar/SUNDAY))

(prn (count (filter sunday? month-firsts)))
