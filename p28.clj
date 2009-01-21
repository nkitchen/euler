(ns euler)

(load-file "euler.clj")

(defn spiral-diags [n]
  (loop [s 1
         delta 2
         latest 1]
    (if (= latest (* n n))
      s
      (let [diags (take 4 (rest (iterate #(+ delta %) latest)))]
      (recur (apply + s diags)
             (+ delta 2)
             (last diags))))))
