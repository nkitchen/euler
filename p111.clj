(ns euler.p111
  (:use euler.core
        euler.special-sum-sets
        clojure.contrib.combinatorics
        clojure.contrib.lazy-seqs
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn primes-with-repeated-digits
  "Returns a sequence of probable primes with n digits, each with the maximum number of repetitions of the digit d."
  ([n d]
   (loop [k (dec n)]
     (let [ps (primes-with-repeated-digits n d k)]
       (if (seq ps)
         ps
         (recur (dec k))))))
  ([n d k]
   (binding [*prime-certainty* (* 4 (int (/ (Math/log 1.0e11)
                                           (Math/log 2.0))))]
     (let [m (- n k)]
       (for [other-num (range (pow 10 m))
             :let [other-digits (->> (digits other-num)
                                     (concat (repeat m 0))
                                     (take-last m))]
             :when (not ((set other-digits) d))
             :when (< (apply max (vals (frequencies other-digits))) k)
             order (interleavings (repeat k d) other-digits)
             :when (pos? (first order))
             :let [p (from-digits order)]
             :when (prime? p)]
         p)))))

(def ans
  (delay
    (sum (apply concat (for [d (range 10)] (primes-with-repeated-digits 10 d))))))
(repl)
