(ns euler.p81
  (:use euler.core
        clojure.contrib.duck-streams
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn min-path-sum [mat]
  (let [m (count mat)
        n (count (mat 0))
        succs (fn [[i j]]
                (concat (if (< - i (dec m))
                          [[(inc i) j]]
                          []))
                (concat (if (< - j (dec n))
                          [[i (inc j)]]
                          [])))
        aux (fn [best [i j :as ij]]
              (let [x (get-in mat ij)
                    s (cond
                        (and (== i (dec m))
                             (== j (dec n)))
                          x
                        (and (== i (dec m))
                             (not= j (dec n)))
                          (+ x (get-in best [i (inc j)]))
                        (and (not= i (dec m))
                             (== j (dec n)))
                          (+ x (get-in best [(inc i) j]))
                        :else
                          (+ x (min (get-in best [i (inc j)])
                                    (get-in best [(inc i) j]))))]
                (assoc-in best ij s)))
        best (reduce aux mat (for [i (reverse (range m))
                                           j (reverse (range n))]
                                       [i j]))]
    (get-in best [0 0])))

;(def m [[131	673	234	103	18]
;        [201	96	342	965	150]
;        [630	803	746	422	111]
;        [537	699	497	121	956]
;        [805	732	524	37	331]])

(def ans
  (let [m (with-open [r (reader "matrix.txt")]
            (into [] (for [line (line-seq r)]
                       (into [] (for [word (.split line ",")]
                                  (Integer/parseInt word))))))]
    (min-path-sum m)))
(repl)
