(ns euler.p114
  (:use euler.core
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn-memo fill-way-count [n]
  (assert (>= n 0))
  (cond
    (<= 0 n 2) 1
    :else (apply +
                 (fill-way-count (dec n))
                 1
                 (for [k (range 3 n)]
                   (fill-way-count (- n k 1))))))
     
(defn overwrite [u i v]
  (reduce #(assoc %1 (+ i %2) (v %2))
          u
          (range (count v))))

(defn-memo fill-ways [n]
  (let [base (vec (repeat n 0))]
    (cond
      (neg? n) nil
      (<= 0 n 2) [base]
      :else
         (concat (for [w (fill-ways (dec n))]
                   (overwrite base 1 w))
                 (for [k (range 3 n)
                       :let [v (vec (repeat k 1))
                             new-base (overwrite base 0 v)]
                       w (fill-ways (- n k 1))]
                   (overwrite new-base (+ k 1) w))
                 [(vec (repeat n 1))]))))

(def ans
  (delay (first (for [n (iterate inc 7)
               :when (not= (fill-way-count n)
                           (count (fill-ways n)))]
           n))))
(repl)
