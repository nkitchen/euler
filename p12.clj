(ns euler
  (:use [clojure.contrib.lazy-seqs :only [primes reductions]]))

(def n 500)

(defn divides?
  "Returns true if n is an integer multiple of k."
  [k n]
  (= (rem n k) 0))

(defn factor
  "Returns the prime factors of m as a map to multiplicities."
  [n]
  (loop [n n
         candidates primes
         factors (sorted-map)]
    (let [f (first candidates)]
      (cond
        (= 1 n) factors
        (divides? f n)
          (recur (/ n f)
                 candidates
                 (merge-with + factors {f 1}))
        (< f n)
          (recur n (rest candidates) factors)
        :else [:error]))))

(def triangle-numbers (reductions + (iterate inc 1)))

(defn count-divisors [n]
  (apply * (map inc (vals (factor n)))))

(def ans (first (filter #(> (count-divisors %) n) triangle-numbers)))
(prn ans)
