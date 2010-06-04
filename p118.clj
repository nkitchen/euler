(ns euler.p118
  (:use euler.core
        clojure.contrib.combinatorics
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(def primes (for [n (range 1 9)
                  c (combinations (range 1 10) n)
                  :when (or (< n 8) (some #{1} c))
                  p (permutations c)
                  :let [q (from-digits p)]
                  :when (prime? q)]
              q))
(prn (take-last 100 primes))
;(repl)
