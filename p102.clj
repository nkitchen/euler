(ns euler.p102
  (:use euler.core
        clojure.contrib.duck-streams
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn same-side?
  "Returns true if points p and q are on the same side of the line containing
  points a and b."
  [[xp yp] [xq yq] [[xa ya] [xb yb]]]
  (if (= xa xb)
    (= (<= xp xa) (<= xq xa))
    (let [m (/ (- ya yb) (- xa xb))
          h (- ya (* m xa))]
      (= (<= yp (+ (* m xp) h))
         (<= yq (+ (* m xq) h))))))

(defn in-triangle? [p tri]
  (every? (fn [[q a b]] (same-side? p q [a b]))
          (rotations tri)))

(defn go []
  (->> (for [line (read-lines "triangles.txt")]
         (let [points (->> (re-split #"," line)
                           (map #(Integer/parseInt %))
                           (partition 2))]
           (in-triangle? [0 0] points)))
       (filter identity)
       (count)))

(repl)
