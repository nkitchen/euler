(ns euler)

(load-file "euler.clj")

(defn consecutive? [s]
  (every? #(= % 1) (map - (rest s) s)))

(def ans
  (first (filter consecutive?
                 (partition 4 1 (filter #(= (count (factor %)) 4)
                                        (iterate inc 1000))))))
(prn ans)
