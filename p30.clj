(ns euler)

(load-file "euler.clj")

(def k 5)

(defn f [n]
  (sum (map #(.pow (bigint %) k) (digits n))))

(defn p [n]
  (= n (f n)))

