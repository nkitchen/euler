(ns euler
  (require [clojure.set :as set]))

(load-file "euler.clj")

(defn cancel [a b]
  (let [common (set/intersection (set (digits a))
                                 (set (digits b)))]
    (if (seq common)
      (try
        (apply / (map #(first (remove-first common (digits %))) [a b]))
        (catch java.lang.ArithmeticException e
          0))
      0)))

(defn cancellable?
  ([a b] (= (/ a b) (cancel a b)))
  ([[a b]] (cancellable? a b)))

(defn tens? [pair]
  (every? #(divides? 10 %) pair))

(def ans (remove tens?
                 (filter cancellable?
                         (for [a (with-digits 2)
                               b (with-digits 2)
                               :when (< a b)]
                           [a b]))))
(prn ans)

