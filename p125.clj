(ns euler.p125
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn p125 [max-sum]
  (sum (set (apply concat (for [a (iterate inc 1)
                                :while (< (* a a) max-sum)]
                            (loop [b (inc a)
                                   prev-sum (* a a)
                                   acc []]
                              (if (>= (* b b) max-sum)
                                acc
                                (let [s (+ prev-sum (* b b))]
                                  (cond
                                    (>= s max-sum) acc
                                    (palindrome? s)
                                      (recur (inc b) s (conj acc s))
                                    :else
                                    (recur (inc b) s acc))))))))))
                    
;(repl)
(prn (p125 100000000))
