(ns euler.p74
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(let [memo (atom {169 3, 363601 3, 1454 3,
                  871 2, 45361 2,
                  872 2, 45362 2})]
  (defn pre-rep-count [n]
    (if-let [c (@memo n)]
      c
      (let [s (sum (map factorial (digits n)))]
        (if (= n s)
          0
          (let [c (inc (pre-rep-count s))]
            (swap! memo assoc n c)
            c))))))

(def ans
  (count (filter #(= % 60) (map pre-rep-count (range 10 1000001)))))

(repl)
