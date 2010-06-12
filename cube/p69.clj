(ns euler.p69
  (:use euler.core
        [clojure.contrib.math :only [expt]]
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn totient [n]
  (reduce * n (for [[p m] (factor n)] (/ (dec p) p))))
  ;(prod (for [[p m] (factor n)] (* (dec p) (expt p (dec m))))))

(def ans (reduce #(max-key second %1 %2)
                 (for [n (range 2 (inc 1000000))]
                   [n (/ n(totient n))])))
(repl)
