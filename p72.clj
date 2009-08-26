(ns euler.p72
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn totient [n]
  (if (prime? n)
    (dec n)
    (reduce * n (for [[p m] (factor n)] (/ (dec p) p)))))

(def ans
  (sum (for [d (range 2 (inc 1000000))]
         (totient d))))
(repl)
