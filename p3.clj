(ns euler
  (:use [clojure.contrib.lazy-seqs :only [primes]]))

(defn divides?
  "Returns true if n is an integer multiple of k."
  [k n]
  (= (rem n k) 0))

(defn factor
  "Returns a sequence of prime factors of n (without multiplicity)."
  [n]
  (loop [n n
         candidates primes
         factors []]
    (let [f (first candidates)]
      (cond
        (= 1 n) factors
        (divides? f n)
          (recur (/ n f) candidates (conj factors f))
        (< f n) (recur n (rest candidates) factors)
        :else [:error]))))
      
(prn (factor 600851475143))
