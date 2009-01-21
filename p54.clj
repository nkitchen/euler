(ns euler.p54
  (:use euler.core
        [clojure.contrib.duck-streams :only [reader]])
  (:import (poker Card Hand HandEvaluator)))

(def hand-pairs
  (with-open [r (reader "poker.txt")]
    (for [line (line-seq (reader "poker.txt"))]
      [(Hand. (subs line 0 15))
       (Hand. (subs line 15))])))

(def ans (count (filter #(= 1 %)
                        (map (fn [[h1 h2]] 
                               (.compareHands (HandEvaluator.) h1 h2))
                             hand-pairs))))
(prn ans)

