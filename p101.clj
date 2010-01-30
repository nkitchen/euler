(ns euler.p101
  (:use clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        incanter.core))

(set! *warn-on-reflection* true)

(def *coeffs* (take 11 (interleave (repeat 1) (repeat -1))))
;(def *coeffs* [0 0 0 1])

(defn eval-poly [coeffs x]
  (if (seq coeffs)
    (+ (first coeffs) (* x (eval-poly (next coeffs) x)))
    0))

(defn terms [coeffs]
  (map #(eval-poly coeffs %) (iterate inc 1)))

(defn fit [terms]
  (let [n (count terms)
        A (apply bind-rows (for [i (range 1 (inc n))]
                             (take n (iterate #(* i %) 1))))]
    (if (= n 1)
      terms
      (solve A (matrix terms)))))

(defn snap [x]
  (let [y (Math/round (double x))]
    (if (< (Math/abs (double (- x y))) 1e-6)
      y
      x)))

(defn first-incorrect-term [k]
  (let [fitted (fit (take k (terms *coeffs*)))]
    (loop [[t1 & r1] (terms fitted)
           [t2 & r2] (terms *coeffs*)]
      (if (not= t1 t2)
        (snap t1)
        (recur r1 r2)))))

(defn go []
  (sum (for [k (range 1 (count *coeffs*))]
         (first-incorrect-term k))))

(repl)
