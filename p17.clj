(ns euler)

(def num-names
  {1 "one", 2 "two", 3 "three", 4 "four", 5 "five",
   6 "six", 7 "seven", 8 "eight", 9 "nine", 10 "ten",
   11 "eleven", 12 "twelve", 13 "thirteen", 14 "fourteen", 15 "fifteen",
   16 "sixteen", 17 "seventeen", 18 "eighteen", 19 "nineteen",
   20 "twenty", 30 "thirty", 40 "forty", 50 "fifty",
   60 "sixty", 70 "seventy", 80 "eighty", 90 "ninety"})

(defn english [n]
  (let [d1000 (quot n 1000)
        d100 (-> n (rem 1000) (quot 100))
        r100 (rem n 100)
        d10 (quot r100 10)
        d1 (rem n 10)]
    (str
      (if (> d1000 0)
        (str (num-names d1000) " thousand")
        "")
      (if (> d100 0)
        (str 
          (num-names d100)
          " hundred"
          (if (> r100 0) " and " ""))
        "")
      (if (<= 10 r100 19)
        (num-names r100)
        (str
          (if (> d10 0)
            (str (num-names (* d10 10)) "-")
            "")
          (num-names d1))))))

(defn count-letters [s]
  (count (re-seq #"\p{Alpha}" s)))

(def n 1000)
(def ans (apply + (map #(count-letters (english %)) (range 1 (inc n)))))
(prn ans)
