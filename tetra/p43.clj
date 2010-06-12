(ns euler)

(load-file "euler.clj")

(defn p [digits]
  (when (apply <= (rest digits))
    (prn digits))
  (every? (fn [[n d]] (divides? n (from-digits d)))
          (map vector
               primes
               (drop 1 (partition 3 1 digits)))))

(def ans (sum (map from-digits
                   (filter p
                           (successors [8 0 1 2 3 4 5 6 7 9])))))
(prn ans)
