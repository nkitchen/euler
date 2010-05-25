(ns euler.p115
  (:use euler.core
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn-memo fill-way-count [m n]
  (assert (>= n 0))
  (cond
    (<= 0 n (dec m)) 1
    :else (apply +
                 (fill-way-count m (dec n))
                 1
                 (for [k (range m n)]
                   (fill-way-count m (- n k 1))))))
     
(repl)
