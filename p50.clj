(ns euler)

(load-file "euler.clj")

(def limit 1000000)

(def small-primes (take-while #(<= % limit) primes))

(def small-prime? (into #{} small-primes))

(defn longest-prime-series [ps]
  ;(prn :lps (first ps))
  (loop [i 1
         s (first ps)
         r (rest ps)
         best [s 1]]
    (if-let [t (first r)]
      (let [q (+ s t)]
        (cond
          (>= q limit) best
          (small-prime? q) (recur (inc i) q (rest r) [q (inc i)])
          :else (recur (inc i) q (rest r) best)))
      best)))

(def ans (reduce #(max-key second %1 %2)
                 (map longest-prime-series
                      (take-while #(<= (first %) (/ limit 2))
                                  (tails small-primes)))))
(prn ans)

