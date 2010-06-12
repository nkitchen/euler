(ns euler
  (:use [clojure.contrib.lazy-seqs :only [fibs]]))

(def fibs-from-1-2 (drop 2 fibs))

(let [n (apply + (filter even? (take-while #(<= % 4000000) fibs-from-1-2)))]
  (prn n))
