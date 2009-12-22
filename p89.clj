(ns euler.p89
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(def letter-value {\I 1
                   \V 5
                   \X 10
                   \L 50
                   \C 100
                   \D 500
                   \M 1000})
(def value-letters [[1000 "M"]
                    [900 "CM"]
                    [500 "D"]
                    [400 "CD"]
                    [100 "C"]
                    [90 "XC"]
                    [50 "L"]
                    [40 "XL"]
                    [10 "X"]
                    [9 "IX"]
                    [5 "V"]
                    [4 "IV"]
                    [1 "I"]])

(defn parse-roman [r]
  (loop [n 0
         [cur & [next & ttail :as tail]] (map letter-value (seq r))]
    (cond
      (nil? cur) n
      (and next (< cur next))
        (recur (+ n (- next cur)) ttail)
      :else
        (recur (+ n cur) tail))))
        
(defn minimal-roman [n]
  (loop [n n
         letters []
         [[v l] & vltail :as vls] value-letters]
    (cond
      (zero? n)
        (apply str letters)
      (>= n v)
        (recur (- n v) (conj letters l) vls)
      :else
        (recur n letters vltail))))

(prn (minimal-roman 1998))
(repl)

