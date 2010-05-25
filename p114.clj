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
  (cond
    (neg? n) 0
    (<= 0 n 2) 1
    :else (apply +
                 (fill-way-count (dec n))
                 1
                 (for [k (range 3 n)]
                   (fill-way-count (- n k 1))))))
     
(defn-memo fill-ways
  ([n base]
   (let [m (- (count base) n)]
     (cond
       (neg? n) nil
       (<= 0 n 2) [base]
       :else
       (concat (fill-ways (dec n) base)
               [(reduce #(assoc %1 (+ %2 m) 1) base (range 0 n))]
               (for [k (range 3 n)
                     w (fill-ways (- n k 1)
                                  (reduce #(assoc %1 (+ %2 m) 1)
                                          base
                                          (range 0 k)))]
                 w)))))
  ([n] (fill-ways n (vec (repeat n 0)))))

(def ans
  (delay (first (for [n (iterate inc 7)
               :when (not= (fill-way-count n)
                           (count (fill-ways n)))]
           n))))
(repl)
