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

;(defn partition-count
;  "Returns the number of ways in which n can be written as a sum of positive
;  integers."
;  [n]
;  (cond
;    (neg? n) 0
;    (<= n 1) 1
;    :else
;    (rem
;      (sum (for [[s t] (map vector
;                            (cycle [1 1 -1 -1])
;                            (map #(partition-count (- n %)) gen-pent-nums))
;                 :while (pos? t)]
;             (rem (* s t) 1000000)))
;      1000000)))
;(def partition-count (memoize partition-count))

;(def ans
;  (first (filter #(== 0 (rem (inc (partition-count %)) 1000000))
;                 (map #(if (= 0 (rem % 1000000))
;                         (do (prn %) %)
;                         %)
;                        (iterate inc 10))))
;  )
(repl)
