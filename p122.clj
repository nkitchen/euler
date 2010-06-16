(ns euler.p122
  (:use euler.core
        clojure.set
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn trace-last [& xs]
  (apply prn xs)
  (last xs))

(defn all-terms [k max-count]
  (cond
    (<= max-count 0) nil
    (= k 1) [#{1}]
    :else
      (let [ps (for [a (reverse (range 1 (inc (quot k 2))))
                     s (all-terms a (- max-count 2))
                     t (all-terms (- k a) (- max-count 1))]
                 (union #{k} s t))]
        (filter #(<= (count %) max-count) ps))))

(defn best-terms [k]
  [(first (for [m (range 1 (dec k))
                ps (all-terms k m)]
            ps))])

(def *max-k* 200)

(defn seq-compare [[x & r :as a] [y & s :as b]]
  (let [c (compare x y)]
    (cond
      (and (empty? a) (empty? b)) 0
      (neg? c) -1
      (zero? c) (recur r s)
      (pos? c) 1)))

(defn entry-compare [[m a] [n b]]
  (let [c (compare m n)]
    (cond
      (neg? c) -1
      (pos? c) 1
      (zero? c) (seq-compare a b))))

(defn best-terms []
  (let [entry (fn [e] [(count e) e])]
    (loop [queue (sorted-set-by entry-compare (entry `(1)))
           expansions nil
           best {1 `(1)}]
      (cond
        (= (count best) *max-k*)
        best
        (and (empty? expansions) (empty? queue))
        best
        (empty? expansions)
          (let [[_ s :as e] (first queue)
                m (first s)]
            (recur (disj queue e)
                   (for [x (seq s)
                         :let [y (+ x m)]
                         :when (<= y *max-k*)]
                     (conj s y))
                   best))
        :else
          (let [s (first expansions)
                m (first (seq s))]
            (prn s (count best))
            (if (or (nil? (best m))
                    (< (count s) (count (best m))))
              (recur (conj queue (entry s))
                     (next expansions)
                     (do
                       (print ".")
                       (flush)
                       (assoc best m s)))
              (recur (conj queue (entry s))
                     (next expansions)
                     best)))))))

(defn p122 []
  (sum (map #(dec (count %)) (vals (best-terms)))))


(repl)
