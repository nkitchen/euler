(ns euler.p116
  (:use euler.core
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn-memo fill-way-count
  ([n]
   (- (sum (for [m [2 3 4]] (fill-way-count m n)))
      3)) ; Don't count empty rows.
  ([m n]
   (assert (>= n 0))
   (cond
     (<= 0 n (dec m)) 1
     :else (+ (fill-way-count m (dec n))
              (if (= m n)
                1
                (fill-way-count m (- n m)))))))
     
(repl)
