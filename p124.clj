(ns euler.p124
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn rad-extend [[r fs ms [p & qs]]]
  (if (= 1 (sum ms))
    [[p (conj fs p) (vec (cons 0 ms)) qs]]
    nil))

(defn rad-inc [[r fs ms next-primes]]
  (for [i (range (count ms))
        :while (zero? (ms i))]
    [(* r (fs i)) fs (assoc ms i 1) next-primes]))

(defn radical-factors
  "Returns a sequence of factor groups, sorted by radical (i.e, their product)."
  []
  (let [f (fn f [queue]
            (let [[r fs ms & other :as entry] (first queue)]
              (lazy-seq
                (cons (for [[f m] (map vector fs ms)
                            :when (pos? m)]
                        f)
                      (f (reduce conj (disj queue entry)
                                 (concat (rad-extend entry)
                                         (rad-inc entry))))))))]
    (f (sorted-set [1 [] [] primes]
                   [2 [2] [1] (next primes)]))))

(defn products [factors max]
  (if (empty? factors)
    [1]
    (for [k (iterate inc 1)
          :let [p (pow (first factors) k)]
          :while (<= p max)
          q (products (rest factors) (quot max p))]
      (* p q))))

(defn p124 [max k]
  (let [s (apply concat (for [fs (radical-factors)]
                          (sort (products fs max))))]
    (nth s (dec k))))

(repl)
