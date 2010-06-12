(ns euler)

(load-file "euler.clj")

(defn amicable? [n]
  (let [m (sum (proper-divisors n))]
    (if (and (= n (sum (proper-divisors m)))
             (not= m n))
      m
      nil)))

(prn (sum (filter amicable? (range 2 10000))))
