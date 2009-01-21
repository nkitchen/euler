(ns euler.p53
  (:use euler.core))

(def ans (count (filter #(> % 1000000)
                        (for [n (range 1 (inc 100))
                              r (range 1 n)]
                          (choose n r)))))
(prn ans) 
