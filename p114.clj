(ns euler.p114
  (:use euler.core
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn-memo fill-way-count [n]
  (assert (>= n 0))
  (cond
    (<= 0 n 2) 1
    :else (apply +
                 (fill-way-count (dec n))
                 1
                 (for [k (range 3 n)]
                   (fill-way-count (- n k 1))))))
     
(repl)
