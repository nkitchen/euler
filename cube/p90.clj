(ns euler.p90
  (:use euler.core
        clojure.contrib.combinatorics
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn extend [a]
  (cond
    (a 6) (conj a 9)
    (a 9) (conj a 6)
    :else a))

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
  (->
  (for [a1 (map set (combinations (range 0 10) 6))
        a2 (map set (combinations (range 0 10) 6))
        :when (<= (compare (vec a1) (vec a2)) 0)
        :when (have-squares? a1 a2)]
    [a1 a2])
    (distinct)))

(repl)

