(ns euler.p100
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn sqrt-quots
  "Continued fraction quotients"
  [x]
  (let [a0 (Integer. (int (Math/floor (Math/sqrt x))))
        step (fn [[a b c]]
               (let [d (/ (- x (* b b)) c)
                     ai (quot (+ a0 b) d)
                     bi (- (* ai d) b)
                     ci d]
                 [ai bi ci]))]
    (if (= x (* a0 a0))
      [a0]
      (map first (iterate step [a0 a0 1])))))

(defn convergents [quots]
  (let [aux (fn aux [pk-1 qk-1 pk qk [ai & as]]
              (lazy-seq (cons [pk qk]
                              (aux pk
                                   qk 
                                   (+ (* pk ai) pk-1)
                                   (+ (* qk ai) qk-1)
                                   as))))]
    (aux 1 0 (first quots) 1 (rest quots))))

;;; m * (m - 1) / n / (n - 1) = 1/2
;;; 2*m^2 - 2*m = n^2 - n
;;; Complete the squares => 2 * (m - 1/2)^2 = (n - 1/2)^2 + 1/4
;;; Let u = 2*m - 1, v = 2*n - 1.
;;; 2 * (u/2)^2 = (v/2)^2 + 1/4
;;; 2 * u^2 = v^2 + 1
;;; 2 u^2 - v^2 = 1
;;; Solution is among convergents of roots of 2 x^2 - 1 => sqrt(2)/2.
;;; To get integer m and n, we need u and v both odd.
(def ans
  (loop [[[u v] & s] (convergents (concat [0 1] (repeat 2)))]
    (if (and (odd? u) (odd? v))
      (let [m (/ (+ u 1) 2)
            n (/ (+ v 1) 2)]
        (if (> n 1.0e12)
          m
          (recur s)))
      (recur s))))

(repl)
