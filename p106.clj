(ns euler.p106
  (:use euler.core
        euler.special-sum-sets
        clojure.contrib.combinatorics
        clojure.contrib.duck-streams
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn subset-equality-check-count [n]
  (count (for [k (range 2 (inc (quot n 2)))
             idxs (combinations (range n) (* 2 k))
             signs (unbalanced-matched-seqs k 1 -1)]
         1)))

(repl)
