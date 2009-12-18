(ns euler.p88
  (:use euler.core
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn successors [factors]
  (concat (when (apply = 2 factors)
            (list (conj factors 2)))
          (for [i (range (count factors))
                :when (or (zero? i)
                          (> (factors (dec i)) (factors i)))]
            (update-in factors [i] inc))))

(def max-k 12000)

(def monitor (agent {}))

(def ans
  (loop [q (doto (new java.util.PriorityQueue
                      11
                      (proxy [java.util.Comparator] []
                        (compare [a b] (compare (:k a) (:k b)))))
             (.add {:factors [2 2] :n 4 :k 2}))
         best (sorted-map)]
    (let [{:keys [factors n k]} (.poll q)]
      (if (> k max-k)
        (sum (set (vals best)))
        (do
          (doseq [s (successors factors)]
            (.add q {:factors s :n (prod s) :k (+ (count s)
                                                  (- (prod s) (sum s)))}))
          (recur q
                 (if (< n (get best k Integer/MAX_VALUE))
                   (let [newbest (assoc best k n)]
                     (send monitor
                           (fn [prev cur]
                             (when (not= (count prev) (count cur))
                               (prn (count cur)))
                             cur)
                           newbest)
                     newbest)
                   best)))))))
(print \newline)

(repl)

