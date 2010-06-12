(ns euler)

(load-file "euler.clj")

(defn sorted-permutations [s]
  (if (seq s)
    (for [x (sort s)
          ys (sorted-permutations (remove #{x} s))]
      (cons x ys))
    (list nil)))
(def sorted-permutations (memoize sorted-permutations))

(defn lexico-next-permutation [p]
  ; If there exists a pair [x y] at consecutive indices such that x <= y,
  ; then the permutation has a lexicographic successor.
  (let [v (vec p)]
    (loop [i (- (count v) 2)]
      (if (neg? i)
        nil
        (let [x (v i)
              y (v (inc i))]
          (if (< x y)
            (let [z (apply min (filter #(> % x) (subvec v (inc i))))]
              (concat (subvec v 0 i)
                      [z]
                      (remove-first #{z} (sort (subvec v i)))))
            (recur (dec i))))))))

(prn (nth (iterate lexico-next-permutation (range 10)) 999999))
;(prn (nth (sorted-permutations (range 10)) 999999))
