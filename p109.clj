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

(defn checkout-ways1 [total]
  (for [section (concat (range 1 (inc 20)) [25])
        :while (<= section total)
        :when (<= (* 2 section) total)
        c (throw-combinations (- total (* 2 section)) 2)]
    (vector [2 section] c)))

;(def multsym '{1 S, 2 D, 3 T})
(def multsym identity)
(defn checkout-ways [total]
  (->> (for [last-throw (concat (range 1 (inc 20)) [25])
             :let [t1 (- total (* 2 last-throw))]
             :while (>= t1 0)
             penult (concat (range 0 (inc 20)) [25])
             :while (<= penult t1)
             m1 (if (zero? penult) [1] [1 2 3])
             :let [p1 (* m1 penult)]
             :let [t2 (- t1 (* m1 penult))]
             :when (>= t2 0)
             antepenult (concat (range 0 (inc 20)) [25])
             :while (<= antepenult t2)
             m2 (if (zero? antepenult) [1] [1 2 3])
             :let [p2 (* m2 antepenult)]
             :let [t3 (- t2 (* m2 antepenult))]
             :when (= t3 0)]
         (let [[a b] (sort [[(multsym m2) antepenult]
                            [(multsym m1) penult]])]
           (filter #(pos? (second %))
                   [a b [(multsym 2) last-throw]])))
       (distinct)))

(repl)
