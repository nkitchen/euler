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

(defn all-terms [k max-count]
  (cond
    (<= max-count 0) nil
    (= k 1) [#{1}]
    :else
      (let [ps (for [a (reverse (range 1 (inc (quot k 2))))
                     s (all-terms a (- max-count 2))
                     t (all-terms (- k a) (- max-count 1))]
                 (union #{k} s t))]
        (filter #(<= (count %) max-count) ps))))

(defn best-terms [k]
  [(first (for [m (range 1 (dec k))
                ps (all-terms k m)]
            ps))])

(defn p122 []
  (sum (for [k (range 1 201)]
         (do
           (print ".")
           (flush)
           (count (first (best-terms k)))))))

(repl)
