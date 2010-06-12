(ns euler)

(defn factorial [n] (apply * (range 1 (inc n))))

(defn choose [n k]
  (/ (factorial n)
     (factorial k) (factorial (- n k))))


