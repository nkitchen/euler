(ns euler.p87
  (:use euler.core
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(def ans
  (let [M 50000000
        amax (Math/pow M 0.5)
        bmax (Math/pow M (double 1/3))
        cmax (Math/pow M 0.25)]
  (set (for [a (take-while #(<= % amax) primes)
              b (take-while #(<= % bmax) primes)
              c (take-while #(<= % cmax) primes)
              :let [s (+ (* a a) (* b b b) (* c c c c))]
              :when (< s M)]
         s))))

(repl)

