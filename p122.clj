(ns euler.p122
  (:use euler.core
        clojure.set
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn all-terms [k max-count]
  (if (= k 1)
    [#{1}]
    (let [ps (for [a (reverse (range 1 (inc (quot k 2))))
                   s (all-terms a (- max-count 2))
                   t (all-terms (- k a) (- max-count 1))]
               (union #{k} s t))]
      (filter #(<= (count %) max-count) ps))))

(defn best-terms [k]
  (let [ps (all-terms k (dec k))
        m (apply min (map count ps))]
    (filter #(= m (count %)) ps)))

(defn p122 []
  (sum (for [k (range 1 201)]
         (count (first (best-terms k))))))

(repl)
