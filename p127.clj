(ns euler.p127
  (:use euler.core
        clojure.stacktrace
        clojure.contrib.pprint
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)
(set! *print-length* 8)

(defn add-factor [[n fs ms [p & qs]]]
  (if (= 1 (sum ms))
    [[p (conj fs p) (vec (cons 0 ms)) qs]]
    nil))

(defn inc-factors [[n fs ms next-primes]]
  (when-let [k (first (for [[i m] (indexed ms) :when (pos? m)] i))]
    (for [i (range (inc k))]
      [(* n (fs i)) fs (update-in ms [i] inc) next-primes])))

(defn factored-ints []
  (let [f (fn f [queue]
            (let [[n fs ms & other :as entry] (first queue)]
              (lazy-seq
                (cons [n (into {} (for [[f m :as e] (map vector fs ms)
                                        :when (pos? m)]
                                    e))]
                      (f (reduce conj (disj queue entry)
                                 (concat (add-factor entry)
                                         (inc-factors entry))))))))]
    (f (sorted-set [1 [] [] primes]
                   [2 [2] [1] (next primes)]))))
;
;(defn products [factors max]
;  (if (empty? factors)
;    [1]
;    (for [k (iterate inc 1)
;          :let [p (pow (first factors) k)]
;          :while (<= p max)
;          q (products (rest factors) (quot max p))]
;      (* p q))))
;
;(defn p124 [max k]
;  (let [s (apply concat (for [fs (radical-factors)]
;                          (sort (products fs max))))]
;    (nth s (dec k))))

(repl)
