(ns euler.p68
  (:use euler.core
        [clojure.contrib.combinatorics :only [permutations]]
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn grouped [perm]
  (for [i (range 0 5)]
    [(nth perm i)
     (nth perm (+ i 5))
     (nth perm (+ (mod (inc i) 5) 5))]))

(defn magic? [perm]
  (and (= (first perm)
          (apply min (take 5 perm)))
       (= 1 (count (distinct (map sum (grouped perm)))))))

(defn grouped-str [perm]
  (apply str (apply concat (grouped perm))))

(def ans
  (let [strs (apply sorted-set (pipe 
                                 (permutations (range 1 (inc 10)))
                                 (filter #(some #{10} (take 5 %)))
                                 (filter magic?)
                                 (map grouped-str)))]
    (first (rseq strs))))
(repl)
