(ns euler)

(load-file "euler.clj")

(defn consec-primes [a b]
  (let [f (fn [n] (+ (* n n) (* a n) b))]
    (count (take-while prime? (map f (iterate inc 0))))))

(def coeffs (mapcat #(vector % (- %))
                    (take-while #(< % 1000) (cons 1 primes))))

(def ans (apply max-key
                #(apply consec-primes %)
                (for [a coeffs
                      b coeffs]
                  [a b])))
(prn ans)

