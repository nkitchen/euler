(ns euler.p123
  (:use euler.core
        clojure.contrib.def
        clojure.contrib.lazy-seqs
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn f [n p]
  (let [p2 (* p p)]
    (rem (+ (mod-pow (dec p) n p2)
            (mod-pow (inc p) n p2))
         p2)))

(defn p123 [m]
  (first (for [[n p] (map vector (iterate inc 1) primes)
               :when (< m (f n p))]
           n)))

(repl)
