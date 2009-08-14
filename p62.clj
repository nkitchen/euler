(ns euler.p62
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

;; Happens to work for 5 permutations, but fails for 6, because a greater cube
;; with five other permutations, including a lesser one, may still be found.
(def ans
  (loop [n 100
         permutations {}]
    (let [c (* n n n)
          p (sort (digits c))
          qs (get permutations p [])]
      (if (= (count qs) 4)
        (first qs)
        (recur (inc n)
               (assoc permutations p (conj qs c)))))))
(repl)
