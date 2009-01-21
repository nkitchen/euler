(ns euler)

(load-file "euler.clj")

(def *modulus* (.pow (bigint 10) 10))

(defn mod-add [a b]
  (.mod (bigint (+ a b)) *modulus*))

(defn f [n]
  (let [b (bigint n)]
  (.modPow b b *modulus*)))

(def ans (reduce mod-add (map f (range 1 (inc 1000)))))
