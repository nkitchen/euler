(ns euler)

(load-file "euler.clj")

(defn hexagonal [n]
  (* n (- (* 2 n) 1)))

(def ans (first (filter #(and (triangle? %) (pentagonal? %))
                        (map hexagonal (iterate inc 144)))))
