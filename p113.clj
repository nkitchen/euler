(ns euler.p113
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(def decreasing-count-memo (atom {}))
(defn decreasing-count
  "Returns the number of positive n-digit numbers whose digits decrease
  monotonically and whose first digit is <= d."
  [n d]
  (if-let [res (@decreasing-count-memo [n d])]
    res
    (let [res
          (cond
            (= n 1) d
            (= d 1) n ; 10...0, 110...0, 1110...0, ..., 11...1
            :else (+ (#'decreasing-count (dec n) d)
                     (#'decreasing-count n (dec d))
                     1))] ; for d0...0
      (swap! decreasing-count-memo assoc [n d] res)
      res)))

(def increasing-count-memo (atom {}))
(defn increasing-count
  "Returns the number of positive n-digit numbers whose digits increase
  monotonically and whose first digit is >= d."
  [n d]
  (if-let [res (@increasing-count-memo [n d])]
    res
    (let [res
          (cond
            (= n 1) (- 10 d)
            (= d 9) 1
            :else (+ (#'increasing-count (dec n) d)
                     (#'increasing-count n (inc d))))]
      (swap! increasing-count-memo assoc [n d] res)
      res)))

(defn monotonic-count [n]
  (+ (decreasing-count n 9)
     (increasing-count n 1)
     -9)) ; Don't double-count strings of repeated digits.

(def ans
  (delay
    (sum (for [n (range 1 101)]
           (monotonic-count n)))))
(prn ans)

(repl)
