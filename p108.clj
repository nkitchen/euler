(ns euler.p108
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn solutions
  "Returns each [x y] such that 1/x + 1/y = 1/n and x and y are integers."
  [n]
  (assert (= 0 (rem n 1)))
  (->> (for [p (divisors n)
             :while (<= (* p p) n)
             :let [q (/ n p)]
             a (drop 1 (iterate inc q))
             :let [x (* a p)]
             :while (<= x (+ n n))
             :let [c (* n x)
                   d (- x n)]
             :when (zero? (rem c d))
             :let [y (/ c d)]]
         [x y])
       (distinct)))

;(def ans
;  (first (->> (iterate inc 5)
;              (filter #(> (let [k (count (solutions %))]
;                            (prn [% k])
;                            k)
;                          1000)))))
(repl)
