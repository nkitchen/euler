(ns euler)

(load-file "euler.clj")

(defn abundant? [n]
  (> (sum (proper-divisors n)) n))
(def abundant? (memoize abundant?))

(def abundants (filter abundant? (iterate inc 2)))

(def n 28123)

(defn abundant-sum? [n]
  (some abundant? (take-while pos? (map #(- n %) abundants))))

(def ans (sum (remove abundant-sum? (range 1 (inc n)))))

