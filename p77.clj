(ns euler.p77
  (:use euler.core
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn prime-partition-count
  "Returns the number of ways in which n can be written as a sum of positive
  integers."
  ([n max-part]
   (if (= n 1)
     0
     (sum (for [k (take-while #(<= % max-part) primes)
                :while (< k n)]
            (let [d (- n k)
                  pc (prime-partition-count d k)]
              (if (and (<= d max-part)
                       (<= d k)
                       (prime? d))
                (inc pc)
                pc))))))
  ([n] (prime-partition-count n (dec n))))
(def prime-partition-count (memoize prime-partition-count))

(def ans
  (first (filter #(> (prime-partition-count %) 5000) (iterate inc 10))))
(repl)
