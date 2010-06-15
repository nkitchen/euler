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

(defn trace-last [& xs]
  (apply prn xs)
  (last xs))

(defn-memo best-terms [k]
  (if (= k 1)
    [#{1}]
    (let [ps (for [a (range 1 (inc (quot k 2)))
                   s (best-terms a)
                   t (best-terms (- k a))]
               (union #{k} s t))
          m (apply min (map count ps))]
      (filter #(= m (count %)) ps))))

(defn p122 []
  (sum (for [k (range 1 201)]
         (do
           (print ".")
           (flush)
           (count (first (best-terms k)))))))

(repl)
