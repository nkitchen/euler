(ns euler.p90
  (:use [euler.core :exclude [digits]]
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(def digits (apply sorted-set (range 0 9)))

(defn extend [a]
  (if (a 6)
    (conj a 9)
    a))

(defn have-squares? [a1 a2]
  (let [a1 (extend a1)
        a2 (extend a2)]
    (every? (fn [[d1 d2]]
              (or (and (a1 d1) (a2 d2))
                  (and (a1 d2) (a2 d1))))
            (map (fn [n]
                   (let [s (* n n)]
                     [(quot s 10) (mod s 10)]))
                 (range 1 (inc 9))))))

(def ans
(for [x1 (range 0 9)
      x2 (range (inc x1) 9)
      x3 (range 0 9)
      x4 (range (inc x3) 9)
      :let [a1 (-> digits (disj x1) (disj x2))
            a2 (-> digits (disj x3) (disj x4))]
      :when (have-squares? a1 a2) ]
  [a1 a2])
  )

(repl)

