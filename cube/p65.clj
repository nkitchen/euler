(ns euler.p65
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn cfquots->ratio [[a0 & as]]
  (if (seq as)
    (+ a0 (/ 1 (cfquots->ratio as)))
    a0))

(def e-quots (apply concat [2]
                     (for [k (iterate inc 1)]
                       [1 (* 2 k) 1])))

(defn numerator [x]
  (if (ratio? x)
    (.numerator #^clojure.lang.Ratio x)
    x))

(def ans 
  (-> (take 100 e-quots)
    cfquots->ratio
    numerator
    digits
    sum))

(repl)
