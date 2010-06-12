(ns euler)

(load-file "euler.clj")

(defn py-triple [a p]
  (let [b (/ (- (* p p) (* 2 p a))
             (* 2 (- p a)))]
    (if (or (ratio? b) (< b a))
      nil
      [a b (- p a b)])))

(defn pythagorean-triples [perimeter]
  (remove nil? (map #(py-triple % perimeter)
                    (range 1 (inc (quot perimeter 3))))))

