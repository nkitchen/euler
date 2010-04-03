(ns euler.p105
  (:use euler.core
        clojure.contrib.combinatorics
        clojure.contrib.duck-streams
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn balanced-seqs 
  "Returns a sequence of all balanced sequences of length N."
  [n left right]
  (cond
    (zero? n) [[]]
    (= n 1) [[left right]] ; optimization--only the zero base case is needed.
    :else
      (for [k (range 0 n)
            s (balanced-seqs k left right)
            t (balanced-seqs (- n 1 k) left right)]
        (concat [left] s [right] t))))

(defn interleavings
  "Returns a sequence of all ways in which to interleave XS and YS."
  ([xs ys]
   (cond
     (empty? xs) [ys]
     (empty? ys) [xs]
     :else
       (concat (for [s (interleavings (rest xs) ys)]
                 (cons (first xs) s))
               (for [s (interleavings xs (rest ys))]
                 (cons (first ys) s)))))
  ([xs ys & others]
   (apply concat (for [s (interleavings xs ys)
                       zs others]
                   (interleavings s zs)))))

(defn unbalanced-matched-seqs
  "Returns a sequence of all sequences containing N copies of LEFT  and 
  N copies of RIGHT that start with LEFT and are not balanced."
  [n left right]
  (for [k (range 1 n)
        s (balanced-seqs k left right)
        t (interleavings (repeat (- n k) left)
                         (repeat (- n k 1) right))]
    (concat s [right] t)))

(defn distinct-subset-sums? [a]
  (assert (= a (sort a)))
  (->> (for [k (range 2 (inc (quot (count a) 2)))
             idxs (combinations (range (count a)) (* 2 k))
             signs (unbalanced-matched-seqs k 1 -1)]
         (sum (map #(* (get a %1) %2) idxs signs)))
       (not-any? zero?)))

(defn monotonic-subset-sums? [a]
  (let [n (count a)]
    (->> (for [k (range 1 n)
               :while (<= (+ k k 1) n)]
           (> (sum (subvec a 0 (inc k)))
              (sum (subvec a (- n k)))))
         (every? identity))))

(defn special-sum-set? [a]
  (and (monotonic-subset-sums? a)
       (distinct-subset-sums? a)))

(def ans
  (->> (for [line (read-lines "sets.txt")]
         (let [a (->> (re-split #"," line)
                      (map #(Integer/parseInt %))
                      (sort)
                      (vec))]
           (print ".")
           (flush)
           (if (special-sum-set? a)
             (sum a)
             0)))
       (sum)))
(print ans)
;(repl)
