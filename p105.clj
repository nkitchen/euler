(ns euler.p105
  (:use euler.core
        clojure.contrib.duck-streams
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn balanced-seqs 
  "Returns a sequence of all balanced sequences of length n."
  [n left right]
  (cond
    (zero? n) [[]]
    (= n 1) [[left right]] ; optimization--only the zero base case is needed.
    :else
      (for [k (range 0 n)
            s (balanced-seqs k left right)
            t (balanced-seqs (- n 1 k) left right)]
        (concat [left] s [right] t))))

(repl)
