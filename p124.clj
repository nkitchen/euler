(ns euler.p124
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)
(set! *print-length* 6)

(defn rad-extend [[r fs ms [p & qs]]]
  (if (= 1 (sum ms))
    [[p (conj fs p) (vec (cons 0 ms)) qs]]
    nil))

(defn rad-inc [[r fs ms next-primes]]
  (for [i (range (count ms))
        :while (zero? (ms i))]
    [(* r (fs i)) fs (assoc ms i 1) next-primes]))

(defn radicals
  "Returns a sorted sequence of products of prime factors."
  []
  (let [f (fn f [queue]
            (let [[r & other :as entry] (first queue)]
              (lazy-seq
                (cons r
                      (f (reduce conj (disj queue entry)
                                 (concat (rad-extend entry)
                                         (rad-inc entry))))))))]
    (f (sorted-set [1 [] [] primes]
                   [2 [2] [1] (next primes)]))))


(defn inc-factors [entry]
  (let [[n fs ms] entry]
    (for [[i f] (indexed fs)]
      (with-meta [(* f n) fs (update-in ms [i] inc)]
                 (meta entry)))))

#_(defn radicals [max-n]
  (let [entry1 (with-meta [1 [] []] {:rest primes})]
    (loop [queue (sorted-set entry1)
           radicals []]
      (let [entry (first queue)
            [n fs ms] entry
            _ (do (prn n) (flush))
            r (prod (for [[i m] (indexed ms)
                          :when (pos? m)]
                      (fs i)))]
        (cond
          (> n max-n) radicals
          :else
            (recur (reduce conj (disj queue entry)
                           (concat (add-factor entry)
                                   (inc-factors entry)))
                   (conj radicals [n r])))))))

(defn p124 [max-n k]
  (first (nth (sort-by second (radicals max-n)) (dec k))))

(repl)
