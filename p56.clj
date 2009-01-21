(ns euler.p56
  (:use euler.core))

(def ans (apply max (for [a (range 1 100)
                          b (range 1 100)]
                      (sum (digits (pow a b))))))
(prn ans)
