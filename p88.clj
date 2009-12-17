(ns euler.p88
  (:use euler.core
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn allocations
  "Returns a sequence of all ways to assign n items to k buckets."
  [n k]
  (cond
    (zero? n)
      (list (vec (repeat k 0)))
    (= k 1)
      (list [n])
    :else
      (for [m (range 0 (inc n))
            a (allocations (- n m) (dec k))]
        (conj a m))))

(prn (allocations 2 2))
;(defn groupings
;  ([factors]
;   "Returns a sequence of all groupings of factors."
;   (apply concat (map #(groupings % factors)
;                      (range 2 (inc (sum (vals factors)))))))
;  ([k factors]
;   "Returns a sequence of all groupings of factors if size k."
;   (when (>= (sum (vals factors)) 2)
;     (let [[p m] (key (first factors))]
;       (for [i 


(repl)

