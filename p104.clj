(ns euler.p104
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils))

(set! *warn-on-reflection* true)

; TODO: Print actual number and high and low digits to check their correctness.

(loop [ah 1M
       bh 1M
       al 1M
       bl 1M
       i 1]
  (let [ch (bigdec (with-precision 18 (+ ah bh)))
        cl (rem (+ al bl) 1000000000)]
    (when (zero? (rem i 1000))
        (print ".") (flush))
    (let [dh (take 9 (digits (.unscaledValue #^java.math.BigDecimal ah)))
          dl (digits al)]
      (when (zero? (rem (- (.scale ch)) 1000))
        (print "/") (flush))
      (if (and (> ah 111111111M)
               (pandigital? dh)
               (pandigital? dl))
      (prn i)
      (recur bh ch bl cl (inc i))))))
;(repl)
