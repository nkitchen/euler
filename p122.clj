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

(def *max-k* 1000)

(defn seq-compare [[x & r :as a] [y & s :as b]]
  (let [c (compare x y)]
    (cond
      (and (empty? a) (empty? b)) 0
      (neg? c) -1
      (zero? c) (recur r s)
      (pos? c) 1)))

(defn lcm
  ([a] a)
  ([a b] (/ (* a b) (gcd a b)))
  ([a b & r] (reduce lcm (lcm a b) r)))

(defn entry-compare [[m p a] [n q b]]
  (let [c (compare [m p] [n q])]
    (cond
      (neg? c) -1
      (pos? c) 1
      :else
        (- (seq-compare a b)))))

(defn best-terms []
  (loop [best {1 `(1)}
         queue (sorted-set-by entry-compare [1 1 `(1)])
         expansions nil]
    (cond
      (= (count best) *max-k*)
        best
      (and (empty? expansions) (empty? queue))
        best
      (empty? expansions)
        (let [[_ __ s :as e] (first queue)
              m (first s)]
          (recur best
                 (disj queue e)
                 (for [x (seq s)
                       :let [y (+ x m)]
                       :when (<= y *max-k*)]
                   (conj s y))))
      :else
        (let [s (first expansions)
              m (first (seq s))]
          ;(prn s (count best))
          (cond
            (nil? (best m))
              (let [nb (assoc best m s)]
                (recur (do
                         (print ".")
                         (flush)
                         nb)
                       (conj queue [(count s) (reduce lcm s) s])
                       (next expansions)))
            (= (count s) (count (best m)))
              (recur best
                     (conj queue [(count s) (reduce lcm s) s])
                     (next expansions))
            :else
              (recur best queue (next expansions)))))))

(defn p122 []
  (sum (map #(dec (count %)) (vals (best-terms)))))


(prn (p122))
;(repl)
