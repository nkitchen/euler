(ns euler.p126
  (:use euler.core
        clojure.set
        clojure.contrib.pprint
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(def face-dirs (for [i (range 3)
                     d [-1 1]]
                 (assoc [0 0 0] i d)))

(defn vadd [u v]
  (map + u v))

(defn cuboid [dims]
  (if (empty? dims)
    [[]]
    (for [x (range (first dims))
          s (cuboid (rest dims))]
      (vec (cons x s)))))

(defn visible-faces [cubes blockers]
  (for [c cubes
        d face-dirs
        :let [f [c d]]
        :when (not (blockers (vadd c d)))]
    f))
                    
(defn cmp-attrs [entry]
  [(count (:layer entry))
   (:dims entry)
   (:i entry)])

(defn cmp [a b]
  (compare (cmp-attrs a) (cmp-attrs b)))

(defn entry0 [dims]
  (let [cs (set (cuboid dims))]
    {:layer cs :whole cs :i 0 :dims dims}))

(defn successors [{:keys [layer whole i dims] :as entry}]
  (list* (let [vis (visible-faces layer whole)
               next-layer (set (for [[c d] vis] (vadd c d)))]
           {:layer next-layer
            :whole (union whole next-layer)
            :i (inc i)
            :dims dims})
         (if (zero? i)
           (for [j (range (count dims))
                 :when (or (zero? j) (> (dims (dec j)) (dims j)))
                 :let [new-dims (update-in dims [j] inc)]]
             (entry0 new-dims))
           nil)))
             
(defn p126 [target]
  (loop [queue (sorted-set-by cmp (entry0 [1 1 1]))
         counts {}
         guess nil]
    (let [entry (first queue)
          n (count (:layer entry))
          c (get counts n 0)
          next-queue (reduce conj (disj queue entry) (successors entry))
          next-counts (assoc counts n (inc c))]
      (cond
        (zero? (:i entry)) (recur next-queue counts guess)
        (= (inc c) target) (recur next-queue next-counts n)
        (> (inc c) target) (recur next-queue next-counts nil)
        guess guess
        :else (recur next-queue next-counts nil)))))

(prn (p126 1000))
;(repl)
