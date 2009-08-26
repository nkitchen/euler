(ns euler.p78
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn partition-count
  "Returns the number of ways in which n can be written as a sum of positive
  integers."
  ([n max-part]
   (if (= n 1)
     0
     (sum (for [k (range 1 (inc max-part))
                :while (< k n)]
            (let [d (- n k)
                  pc (partition-count d k)]
              (if (and (<= d max-part)
                       (<= d k))
                (inc pc)
                pc))))))
  ([n] (partition-count n (dec n))))
(def partition-count (memoize partition-count))

(def ans
  (first (filter #(== 0 (rem (inc (partition-count %)) 1000000))
                 (iterate inc 10))))
(repl)
