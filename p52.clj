(ns euler.p52
  (:use euler.core))

(defn p [x]
  (apply = (map #(sort (digits (* % x)))
                [2 3 4 5 6])))

(def ans (first (filter p (iterate inc 1))))
(prn ans) ; 142857
