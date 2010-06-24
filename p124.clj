(ns euler.p124
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn add-factor [entry]
  (let [[n fs ms] entry]
    (if (<= (sum ms) 1)
      (let [[p & qs] (:rest (meta entry))
            ms+ (conj (vec (repeat (count fs) 0)) 1)]
        [(with-meta [p (conj fs p) ms+]
                    (assoc (meta entry) :rest qs))])
      nil)))

(defn inc-factors [entry]
  (let [[n fs ms] entry]
    (for [[i f] (indexed fs)]
      (with-meta [(* f n) fs (update-in ms [i] inc)]
                 (meta entry)))))

(defn radicals [max-n]
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
