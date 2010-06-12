(ns euler)

(defn f [n]
  (if (even? n)
    (/ n 2)
    (+ (* 3 n) 1)))

(defn chain-length [n]
  (if (= n 1)
    1
    (inc (chain-length (f n)))))

(def chain-length (memoize chain-length))

(def n 1000000)
(def ans (apply max-key chain-length (range 1 (inc n))))
(prn ans)
