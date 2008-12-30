;;; Find the sum of all the primes below two million.

(ns euler
  (:use [clojure.contrib.lazy-seqs :only [primes]]))

(def n 2000000)

(def ans (apply + (take-while #(< % n) primes)))
(prn ans)
