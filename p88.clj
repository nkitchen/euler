(ns euler.p88
  (:use euler.core
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn factor-groups
  "Returns a sequence of decompositions of n into factors."
  [n min-factor]
  (concat (list [n])
          (for [i (range min-factor (inc (/ n 2)))
                :while (<= (* i i) n)
                :when (zero? (mod n i))
                g (factor-groups (quot n i) i)]
            (conj g i))))

(def max-k 12000)

(def monitor (agent {}))

(def ans
  (loop [n 2
         best {}]
    (if (= (count best) (dec max-k))
      (sum (set (vals best)))
      (recur (inc n)
             (reduce (fn [m g]
                       (let [k (+ (count g) (- n (sum g)))]
                         (if (and (<= k max-k)
                                  (< n (get m k Integer/MAX_VALUE)))
                           (do
                             #_(send monitor
                                   (fn [prev cur]
                                     (when (not= (count prev) (count cur))
                                       (prn (count cur)))
                                     cur)
                                   (assoc m k n))
                             (assoc m k n))
                           m)))
                     best
                     (filter #(> (count %) 1) (factor-groups n 2)))))))

(prn ans)
;(repl)

