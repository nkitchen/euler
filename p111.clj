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
  "Returns a sequence of probable primes, each with n digits, k of which
  are repeated."
  [n k]
  (binding [*prime-certainty* (* 2 (int (/ (Math/log 1.0e11)
                                           (Math/log 2.0))))]
    (for [repeated-digit (range 10)
          :let [m (- n k)]
          other-num (with-digits m)
          :let [other-digits (digits other-num)]
          :when (not ((set other-digits) repeated-digit))
          :when (< (apply max (vals (frequencies other-digits))) k)
          order (interleavings (repeat k repeated-digit) other-digits)
          :when (pos? (first order))
          :let [p (from-digits order)]
          :when (prime? p)]
      p)))

(def ans
  (delay
    nil))
(repl)
