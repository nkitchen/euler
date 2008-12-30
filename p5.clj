;;; 2520 is the smallest number that can be divided by each of the numbers from
;;; 1 to 10 without any remainder.

;;; What is the smallest number that is evenly divisible by all of the numbers
;;; from 1 to 20?

(ns euler
  (:use [clojure.contrib.lazy-seqs :only [primes]]))

(defn divides?
  "Returns true if n is an integer multiple of k."
  [k n]
  (= (rem n k) 0))

(defn factor
  "Returns the prime factors of m as a map to multiplicities."
  [n]
  (loop [n n
         candidates primes
         factors {}]
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
      
(defn unfactor
  "Converts a factor map to the product."
  [factors]
  (reduce (fn [m [f k]]
            (apply * m (take k (repeat f))))
          1
          factors))

(defn lcm
  "Returns the least common multiple of the arguments."
  [& xs]
  (unfactor (apply merge-with max (map factor xs))))

(prn (factor 600851475143))
