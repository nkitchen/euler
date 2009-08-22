(ns euler.p66
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn sqrt-quots
  "Continued fraction quotients"
  [x]
  (let [a0 (Integer. (int (Math/floor (Math/sqrt x))))
        step (fn [[a b c]]
               (let [d (/ (- x (* b b)) c)
                     ai (quot (+ a0 b) d)
                     bi (- (* ai d) b)
                     ci d]
                 [ai bi ci]))]
    (if (= x (* a0 a0))
      [a0]
      (map first (iterate step [a0 a0 1])))))

(defn convergents [quots]
  (let [aux (fn aux [pk-1 qk-1 pk qk [ai & as]]
              (lazy-seq (cons [pk qk]
                              (aux pk
                                   qk 
                                   (+ (* pk ai) pk-1)
                                   (+ (* qk ai) qk-1)
                                   as))))]
    (aux 1 0 (first quots) 1 (rest quots))))

(defn minimal-pells-sol [d]
  (let [f (fn [[x y]] (== 1 (- (* x x) (* d y y))))]
    (first (filter f (convergents (sqrt-quots d))))))

(defn square? [x]
  (let [r (Math/floor (Math/sqrt x))]
    (== x (* r r))))

(def ans
  (pipe (range 2 (inc 1000))
        (remove square?)
        (apply max-key #(first (minimal-pells-sol %)))))
(repl)
