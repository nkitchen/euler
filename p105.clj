(ns euler.p105
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

(def ans
  (->> (for [line (read-lines "sets.txt")]
         (let [a (->> (re-split #"," line)
                      (map #(Integer/parseInt %))
                      (sort)
                      (vec))]
           (print ".")
           (flush)
           (if (special-sum-set? a)
             (sum a)
             0)))
       (sum)))
(print ans)
;(repl)
