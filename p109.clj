(ns euler.p109
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn throw-combinations [total max-length]
  (cond
    (neg? total) []
    (zero? total) [[]]
    (zero? max-length) []
    :else
      (distinct (for [section (concat (range 1 (inc 20)) [25])
                      :while (<= section total)
                      m [1 2 3]
                      :let [p (* section m)]
                      :when (<= p 60)
                      c (throw-combinations (- total p) (dec max-length))
                      :when (< (count c) max-length)]
                  (sort (cons [m section] c))))))
(memoize throw-combinations)

(defn checkout-ways [total]
  (for [section (concat (range 1 (inc 20)) [25])
        :while (<= section total)
        :when (<= (* 2 section) total)
        c (throw-combinations (- total (* 2 section)) 2)]
    (vector [2 section] c)))

(repl)
