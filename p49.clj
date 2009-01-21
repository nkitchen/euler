(ns euler)

(load-file "euler.clj")

(doseq [x (range 1000 10000)]
  (when (prime? x)
    (let [d (digits x)]
      (doseq [d2 (rest (successors d))]
        (when (prime? (from-digits d2))
          (let [y (from-digits d2)
                del (- y x)
                z (+ y (- y x))]
            (when (and (= (sort (digits y))
                          (sort (digits z)))
                       (prime? z))
              (prn [x y z]))))))))

