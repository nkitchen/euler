(ns euler.p75
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn pythag-sums [smax]
  (for [m (range 1 (inc (/ (- (Math/sqrt (+ smax smax 1))
                              1)
                           2)))
        n (range 1 m)
        :when (= (rem (+ m n) 2) 1)
        :when (= (gcd m n) 1)
        :let [a (* 2 m n)
              b (- (* m m) (* n n))
              c (+ (* m m) (* n n))
              d (+ a b c)]
        k (iterate inc 1)
        :while (<= (* k d) smax)]
    (* k d)))

(def ans
  (let [ps (set (pythag-sums 1500000))
        c (apply merge-with + {} (for [s (pythag-sums 1500000)] {s 1}))]
    (count (filter #(= % 1) (vals c)))))
(repl)
