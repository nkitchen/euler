(ns euler)

(load-file "euler.clj")

(defn tails [s]
  (take-while identity (iterate rest s)))

(defn heads [s]
  (take-while identity (iterate butlast s)))

(defn p [n]
  (let [d (digits n)]
    (every? prime? (lazy-cat (map from-digits (tails d))
                             (map from-digits (heads d))))))
