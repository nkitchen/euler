(ns euler.p112
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn bouncy? [n]
  (let [ds (digits n)]
    (and (not (apply <= ds))
         (not (apply >= ds)))))

(def ans
  (delay
    (loop [n 99
           b 0
           i 100]
      (let [f (/ b n)]
        (if (= f 99/100)
          n
          (recur (inc n)
                 (if (bouncy? i) (inc b) b)
                 (inc i)))))))

(repl)
