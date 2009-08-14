(ns euler.p60
  (:use euler.core
        [clojure.contrib.lazy-seqs :only [primes]]
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn concat-digits
  ([x y]
   (let [m (Math/pow 10 (+ 1 (Math/floor (Math/log10 y))))]
     (int (+ (* m x) y))))
  ([x y & zs]
   (reduce concat-digits (concat-digits x y) zs)))

(defn prime-cats? [x y]
  (and (prime? (concat-digits x y))
       (prime? (concat-digits y x))))
(def prime-cats? (memoize prime-cats?))

(def odd-primes (drop 1 primes))

(defn next-pwpc-sets
  "Given a sequence of vectors of size k containing numbers which pairwise have
  prime concatenations, returns a sequence of vectors of size k + 1 satisfying
  the same property."
  [k-sets]
  (apply concat
          (for [p odd-primes]
            (pipe k-sets
                  (take-while #(< (peek %) p))
                  (filter (fn [s] (every? #(prime-cats? p %) s)))
                  (map #(conj % p))))))

(def pwpc-sets (iterate next-pwpc-sets (map vector odd-primes)))

;(repl)
(time (prn (first (nth pwpc-sets 4))))
(time (prn (second (nth pwpc-sets 4))))
;(let [ans (first (nth pwpc-sets 4))]
;  (prn ans))

;;; ITERATE IS GREAT!
