(ns euler.p57
  (:use euler.core))

(def expansions (map #(+ 1 %) (iterate #(/ (+ 2 %)) 1/2)))

(defn p [#^clojure.lang.Ratio x]
  (> (count (digits (.numerator x)))
     (count (digits (.denominator x)))))

(def ans (count (filter p (take 1000 expansions))))
(prn ans)

