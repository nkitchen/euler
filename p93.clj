(ns euler.p93
  (:use euler.core
        clojure.contrib.combinatorics
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn targets
  "Returns all possible values obtained by combining VALUES with OPS in order
  (that is, the values obtained by all different groupings)."
  [ops values]
  (assert (= (count ops) (dec (count values))))
  (if (empty? ops)
    (list (values 0))
    (for [r (range (count ops))
          left (targets (subvec ops 0 r) (subvec values 0 (inc r)))
          right (targets (subvec ops (inc r)) (subvec values (inc r)))]
      ((ops r) left right))))

(repl)

