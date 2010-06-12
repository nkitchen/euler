(ns euler)

(load-file "euler.clj")

(defn concat-product [a n]
  (reduce num-cat (map #(* a %) (range 1 (inc n)))))
