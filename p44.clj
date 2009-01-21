(ns euler)

(load-file "euler.clj")

(defn pentagonal [n]
  (/ (* n (- (* 3 n) 1))
     2))

(defn pentagonal? [x]
  (zero? (rem (/ (+ 1 (Math/sqrt (+ 1 (* 24 x)))) 6)
              1)))

(def pentagonals (map pentagonal (iterate inc 1)))

(def ans (first (filter (fn [[a b]] (pentagonal? (+ a b)))
                        (for [d pentagonals
                              n (range 1 (quot (dec d) 3));
                              :when (pentagonal? (+ (pentagonal n) d))]
                          (let [p (pentagonal n)]
                            [p (+ p d)])))))

(prn ans)
