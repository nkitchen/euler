(ns euler
  (:use clojure.contrib.duck-streams))

(load-file "euler.clj")

(defn integral? [n]
  (zero? (rem n 1)))

(defn triangle?
  "= 1/2 k (k + 1) for some integer k."
  [x]
  (zero? (rem (/ (+ -1 (Math/sqrt (+ 1 (* 8 x)))) 2)
              1)))

(def words (with-open [r (new java.io.PushbackReader (reader "words.clj"))]
             (read r)))

(defn alpha-value [word]
  (sum (map #(inc (- (int %) (int \a)))
            (.toLowerCase word))))

(def ans (count (filter triangle? (map alpha-value words))))
