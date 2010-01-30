(ns euler.p101
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils))

(set! *warn-on-reflection* true)

(def *coeffs* (take 11 (interleave (repeat 1) (repeat -1))))
;(def *coeffs* [0 0 0 1])

(defn eval-poly [coeffs x]
  (if (seq coeffs)
    (+ (first coeffs) (* x (eval-poly (next coeffs) x)))
    0))

(defn terms [coeffs]
  (map #(eval-poly coeffs %) (iterate inc 1)))

(defn snap [x]
  (let [y (Math/round (double x))]
    (if (< (Math/abs (double (- x y))) 1e-4)
      y
      x)))

(defn bind-rows [& rows]
  (vec (map vec rows)))

(def matrix identity)

(defn cancel-with-row [A i]
  (let [a (get-in A [i i])
        scaled-row (into [] (map #(/ % a) (A i)))]
    (reduce (fn [A j]
              (let [b (get-in A [j i])]
                (assoc A j (into [] (map #(- %1 (* b %2))
                                         (A j)
                                         scaled-row)))))
            (assoc A i scaled-row)
            (remove #{i} (range (count A))))))

(defn gauss-jordan [A b]
  (let [n (count A)
        C (vec (for [[i row] (indexed A)]
                 (conj row (nth b i))))]
    (->> (reduce #(cancel-with-row %1 %2) C (range n))
         (map last))))

(def solve gauss-jordan)

(defn fit [terms]
  (let [n (count terms)
        A (apply bind-rows (for [i (range 1 (inc n))]
                             (take n (iterate #(* i %) 1))))]
    (if (= n 1)
      (map snap terms)
      (map snap (solve A (matrix terms))))))

(defn first-incorrect-term [k]
  (let [fitted (fit (take k (terms *coeffs*)))]
    (loop [[t1 & r1] (terms fitted)
           [t2 & r2] (terms *coeffs*)]
      (if (> (Math/abs (double (- t1 t2))) 1e-4) 
        (snap t1)
        (recur r1 r2)))))

(defn go []
  (sum (for [k (range 1 (count *coeffs*))]
         (first-incorrect-term k))))

(repl)
