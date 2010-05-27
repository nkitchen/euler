(ns euler.p117
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
    (<= 0 n 1) 1
    :else (apply +
                 (fill-way-count (dec n))
                 (for [m [2 3 4]
                       :when (<= m n)]
                   (fill-way-count (- n m))))))
(repl)
