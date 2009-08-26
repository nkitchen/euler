(ns euler.p73
  (:use euler.core
        clojure.contrib.repl-ln)
  (:import clojure.lang.Ratio))

(set! *warn-on-reflection* true)

(defn numerator [#^Ratio r]
  (.numerator r))

(def ans
  (sum (for [d (range 2 (inc 10000))]
         (let [n1 (Math/ceil (/ d 3))
               n1 (if (== (* n1 3) d)
                    (inc n1)
                    n1)
               n2 (Math/floor (/ d 2))
               n2 (if (== (* n2 2) d)
                    (dec n2)
                    n2)]
           (pipe (range n1 (inc n2))
             (filter #(== % (numerator (/ % d))))
             count)))))
(repl)
