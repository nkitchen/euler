(ns euler.p61
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(def figurate-formulas
  {3 (fn [n] (* 1/2 n (+ n 1))),
   4 (fn [n] (* n n)),
   5 (fn [n] (* 1/2 n (- (* 3 n) 1))),
   6 (fn [n] (* n (- (* 2 n) 1))),
   7 (fn [n] (* 1/2 n (- (* 5 n) 3))),
   8 (fn [n] (* n (- (* 3 n) 2)))})

(def figurate-numbers
  (into {} (for [[k f] figurate-formulas]
             [k (pipe (map f (iterate inc 1))
                      (drop-while #(< % 1000))
                      (take-while #(<= % 9999)))])))

(defn separate-digits [n]
  [(quot n 100) (rem n 100)])

(defn group-digits [xs]
  (reduce (fn [m [hi lo]]
            (assoc m hi (conj (get m hi []) lo)))
          {}
          (map separate-digits xs)))

(def low-digits
  (into {} (for [[k nums] figurate-numbers]
             [k (group-digits nums)])))

(defn find-cycles [path succs]
  (cond
    (seq succs)
      (apply concat (for [[k m] succs
                          low (m (peek path))]
                      (find-cycles (conj path low) (dissoc succs k))))
    (and (> (count path) 1)
         (= (path 0) (peek path)))
      [path]))
    
(def path (first (apply concat (for [n (keys (low-digits 8))]
                                 (find-cycles [n] low-digits)))))
;(map #(+ (* 100 %1) %2) path (next path))

(repl)
