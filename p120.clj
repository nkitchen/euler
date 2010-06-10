(ns euler.p120
  (:use euler.core
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn mod-pow [a b m]
  (+ 0 (.modPow (bigint a) (bigint b) (bigint m))))

(defn rmax [a]
  (cond
    (odd? a) (* a (- a 1))
    (even? a)
      (let [a2 (* a a)
            f (fn [n] (rem (+ (mod-pow (dec a) n a2)
                              (mod-pow (inc a) n a2))
                           a2))
            s (map f (iterate #(+ % 2) 1))]
        (apply max (first s) (take-while #(not= % (first s))
                                         (next s))))))

(repl)
