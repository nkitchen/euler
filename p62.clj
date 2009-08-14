(ns euler.p62
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(def *goal* 6)

(def ans
  (loop [n 100
         permutations {}
         candidates (sorted-set)]
    (let [c (* n n n)
          p (sort (digits c))
          qs (get permutations p [])
          pn (assoc permutations p (conj qs c))]
      (cond
        (= (count qs) *goal*)
          (first candidates)
        (= (count qs) (dec *goal*))
          (recur (inc n)
                 pn
                 (conj candidates (first qs)))
        :else
          (recur (inc n) pn candidates)))))
(repl)
