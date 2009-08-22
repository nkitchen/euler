(ns euler.p67
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(def tri (read-string (slurp "triangle.clj")))

(def ans
  (let [update (fn [best [i j]]
                 (let [x (get-in tri [i j])]
                   (assoc-in best [i j]
                             (if (= i (dec (count best)))
                               x
                               (+ x (max (get-in best [(inc i) j])
                                         (get-in best [(inc i) (inc j)])))))))
        best (reduce update tri (for [i (reverse (range (count tri)))
                                      j (range (inc i))]
                                  [i j]))]
    (get-in best [0 0])))

(repl)
