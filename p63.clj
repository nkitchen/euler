(ns euler.p63
  (:use euler.core
        clojure.contrib.repl-ln
        [clojure.contrib.math :only [expt]]))

(set! *warn-on-reflection* true)

(def ans
  (count (for [b (range 1 (inc 9))
               n (iterate inc 1)
               :while (= n (count (digits (expt b n))))]
           1)))
(repl)
