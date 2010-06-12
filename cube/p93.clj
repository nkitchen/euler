(ns euler.p93
  (:use euler.core
        clojure.contrib.combinatorics
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn sdiv [a b]
  (if (zero? b)
    Double/POSITIVE_INFINITY
    (/ a b)))

(defn targets
  "Returns all possible values obtained by combining VALUES with OPS in order
  (that is, the values obtained by all different groupings)."
  ([ops values]
   (assert (= (count ops) (dec (count values))))
   (if (empty? ops)
     (list (values 0))
     (for [r (range (count ops))
           left (targets (subvec ops 0 r) (subvec values 0 (inc r)))
           right (targets (subvec ops (inc r)) (subvec values (inc r)))]
       ((ops r) left right))))
  ([digits]
   (->> (for [ops (selections [+ - * sdiv] (dec (count digits)))
              values (permutations digits)]
          (targets (vec ops) (vec values)))
        (apply concat)
        (filter #(= % (int %)))
        (filter pos?))))

(defn run-length [digits]
  (let [hits (reduce conj (sorted-set) (targets digits))
        [a b] (first (->> (partition 2 1 hits)
                          (remove (fn [[a b]] (= a (dec b))))))]
    (or a -1)))

(def ans
  (apply max-key
         second
         (for [digits (combinations (range 0 10) 4)]
           [digits (run-length digits)])))
(repl)

