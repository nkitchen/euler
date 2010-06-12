(ns euler.p78
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(def gen-pent-nums
  (map #(* 1/2 % (- (* 3 %) 1))
       (for [i (iterate inc 1)
             s [1 -1]]
         (* s i))))

(def ans
  (loop [n 2
         pmemo {0 1, 1 1}]
    (let [p (sum (for [[s k] (map vector
                                  (cycle [1 1 -1 -1])
                                  (map #(- n %) gen-pent-nums))
                       :while (>= k 0)]
                   (* s (pmemo k))))
          q (rem p 1000000)]
      (if (zero? q)
        n
        (recur (inc n) (assoc pmemo n q))))))

(repl)
