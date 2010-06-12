(ns euler.p51
  (:use euler.core
        [clojure.contrib.lazy-seqs :only [primes]]))

(def limit 1000000)

(def small-primes (take-while #(<= % limit) primes))

(def small-prime? (into #{} small-primes))

(defn prime-family [digits idxs]
  (let [digits (vec digits)
        m (if (some zero? idxs) 1 0)]
    (filter small-prime? (for [d (range m 10)]
                           (from-digits (reduce #(assoc %1 %2 d)
                                                digits 
                                                idxs))))))

(def ans (first (for [p (drop-while #(<= % 10000) small-primes)
                      k (range 1 (count (digits p)))
                      idxs (combinations (range 0 (count (digits p))) k)
                      :when (and (apply = (map #(nth (digits p) %) idxs))
                                 (= 8
                                    (count (prime-family (digits p) idxs))))]
                  [p idxs])))
(prn ans) ; 120383
