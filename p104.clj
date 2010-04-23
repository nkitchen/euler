(ns euler.p104
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils))

;(set! *warn-on-reflection* true)

; TODO: Print actual number and high and low digits to check their correctness.

(def #^BigDecimal phi 
  1.6180339887498948482045868343656381177203091798057628621354486M)

(def #^BigDecimal invsqrt5
  0.44721359549995793928183473374625524708812367192230514485M)

(defn fibonacci [n]
  (with-precision 20
    (let [f (* invsqrt5 (- (.pow phi n #^java.math.MathContext *math-context*)
                           (.pow #^BigDecimal (- 1 phi)
                                 n
                                 #^java.math.MathContext *math-context*)))]
      (.toBigInteger (.round #^BigDecimal f *math-context*)))))

(loop [al 1
       bl 1
       i 1]
  (let [cl (rem (+ al bl) 1000000000)]
    (when (zero? (rem i 1000))
        (print ".") (flush))
    (when (pandigital? (digits al))
      (prn i al))
    (if (and (> al 111111111)
             (pandigital? (digits al))
             (pandigital? (take 9 (digits (fibonacci i)))))
      (do
      (prn i)
        (recur bl cl (inc i)))
      (recur bl cl (inc i)))))
(repl)
