(ns euler.p98
  (:use euler.core
        clojure.contrib.duck-streams
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(def ans
  (with-open [r (reader "base_exp.txt")]
    (->> (line-seq r)
         (map (fn [line]
                (let [[base exp] (map #(Integer/parseInt %)
                                      (re-split #"," line))]
                  (* exp (Math/log base)))))
         (indexed)
         (apply max-key second))))

;(print ans)
(repl)

