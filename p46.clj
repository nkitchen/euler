(ns euler)

(load-file "euler.clj")

(defn goldbach? [n]
  ; p + 2*k^2
  ; 2*k^2 = n - p
  ; k^2 = 1/2(n-p)
  ; k = sqrt(1/2(n-p))
  (first (filter (fn [k]
                   (let [p (- n (* 2 k k))]
                     (and (pos? p) (prime? p))))
                 (range 1 (inc (quot (Math/sqrt (double (/ n 2))) 1))))))

(def ans (first (remove goldbach? (remove prime? (range 3 1000000 2)))))
