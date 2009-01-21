(ns euler.p58
  (:use euler.core))

(def ans
  (loop [n 3
         delta 2
         latest 1
         np 0
         nc 1]
    (let [diags (take 4 (rest (iterate #(+ delta %) latest)))
          np* (+ np (count (filter prime? diags)))
          nc* (+ nc (count (remove prime? diags)))
          r (/ np* (+ np* nc*))]
      (prn n (double r))
      (if (< r 0.10)
        n
        (recur (+ n 2)
               (+ delta 2)
               (last diags)
               np*
               nc*)))))
(prn ans)

