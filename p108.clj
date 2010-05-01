(ns euler.p108
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn unfactor
  "Converts a factor map to the product."
  [factors]
  (reduce (fn [m [f k]]
            (apply * m (take k (repeat f))))
          1
          factors))

(def *init-count* 3)

(defn add-factor [entry]
  (let [[n factors] entry
        p (first (:rest (meta entry)))]
    (with-meta [(* n p) (conj factors [p 1])]
               (update-in (meta entry) [:rest] rest))))

(defn inc-factors [entry]
  (let [[n factors] entry]
    (for [[i [f m]] (indexed factors)]
      (with-meta [(* f n) (assoc factors i [f (inc m)])]
                 (meta entry)))))

(def ans
  (let [init-factors (->> (interleave (take *init-count* primes)
                                      (repeat 1))
                          (partition 2)
                          (into []))
        init (with-meta [(unfactor init-factors) init-factors]
                        {:rest (drop *init-count* primes)})]
    (loop [queue (sorted-set init)]
      (let [entry (first (seq queue))
            [n factors] entry
            d (prod (map #(+ 1 % %) (map second factors)))
            c (/ (inc d) 2)]
        (if (> c 1000)
          n
          (recur (reduce conj (disj queue entry)
                         (cons (add-factor entry)
                               (inc-factors entry)))))))))
(prn ans)
;(repl)
