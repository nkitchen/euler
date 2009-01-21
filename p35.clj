(ns euler
  (use [clojure.contrib.lazy-seqs :only [rotations]]))

(load-file "euler.clj")

(defn cat-digits [digits]
  (loop [n 0
         ds digits]
    (if (seq ds)
      (recur (+ (* 10 n) (first ds))
             (rest ds))
      n)))

(defn circular-prime? [n]
  (every? prime? (map cat-digits (rotations (digits n)))))

