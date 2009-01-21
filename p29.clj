(ns euler)

(load-file "euler.clj")

(def s (apply sorted-set (for [a (range 2 (inc 100))
                               b (range 2 (inc 100))]
                           (.pow (bigint a) (int b)))))
