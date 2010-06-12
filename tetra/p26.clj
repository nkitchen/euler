(ns euler)

(load-file "euler.clj")

(defn residues [d]
  (let [f (fn [r] (rem (* 10 r) d))]
    (iterate f 1)))

(defn recip-cycle-length
  "Returns the length of the recurring cycle in the decimal expansion of 1/d."
  [d]
  (loop [r 1
         i 1
         ; residues maps each remainder found during long division to the
         ; iteration in which it is first found.
         residues {}]
    (let [s (rem (* 10 r) d)]
      (cond
        (zero? s) 0
        (residues s) (- i (residues s))
        :else (recur s (inc i) (conj residues {s i}))))))

(recip-cycle-length 2)
