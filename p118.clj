(ns euler.p118
  (:use euler.core
        clojure.contrib.combinatorics
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn-memo prime-partitions
  "Returns a sequence of all the ways that the digits can be partitioned
  so that each partition is a prime number (preserving the order of the
  digits."
  [digits]
  (if (empty? digits)
    [nil]
    (for [k (range 1 (inc (count digits)))
          :let [p (from-digits (take k digits))]
          :when (prime? p)
          r (prime-partitions (drop k digits))]
      (cons p r))))

(defn p188 []
  (count (distinct (map set
                        (apply concat (for [p (permutations (range 1 10))]
                                        (prime-partitions p)))))))

(repl)
