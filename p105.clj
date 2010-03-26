(ns euler.p105
  (:use euler.core
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

(repl)
