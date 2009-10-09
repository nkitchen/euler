(ns euler.p86
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn square? [x]
  (let [r (Math/sqrt x)]
    (== r (Math/floor r))))

(defn min-dist-int? [a b c]
  (assert (>= a b c))
  (let [bc (+ b c)]
    (square?  (+ (* a a) (* bc bc)))))

(def ans
  (loop [m 1
         total 0]
    (let [d (count (filter identity  (for [b (range 1 (inc m))
                                           c (range 1 (inc b))]
                                       (min-dist-int? m b c))))
          new-total (+ total d)]
      (if (> new-total 1000000)
        m
        (recur (inc m) new-total)))))


(repl)

