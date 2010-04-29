(ns euler.p107
  (:use euler.core
        clojure.contrib.duck-streams
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn read-network []
  (->> (for [line (doall (line-seq (new java.io.BufferedReader *in*)))]
         (vec (for [entry (re-split #"," line)]
                (if (= entry "-")
                  nil
                  (Integer/parseInt entry)))))
       (vec)))
      
(defn find-rep [reps x]
  (let [y (reps x)]
    (if y
      (let [[compressed z] (find-rep reps y)]
        [(assoc compressed x z) z])
      [reps x])))

(defn total-weight [network]
  (let [n (count network)]
    (sum (for [i (range n)
               j (range n)
               :when (< i j)]
           (or (get-in network [i j]) 0)))))

(defn min-span-weight [network]
  (let [n (count network)
        edges (for [i (range n)
                    j (range n)
                    :when (< i j)
                    :let [w (get-in network [i j])]
                    :when w]
                [i j w])]
    (loop [[[i j w] & erest] (sort-by #(% 2) edges)
           reps {}
           weight 0]
      (if (nil? i)
        weight
        (let [[reps r] (find-rep reps i)
              [reps s] (find-rep reps j)]
          (if (= r s)
            (recur erest reps weight)
            (recur erest (assoc reps r s) (+ weight w))))))))

(def m (with-in-str "-,16,12,21,-,-,-
16,-,-,17,20,-,-
12,-,-,28,-,31,-
21,17,28,-,18,19,23
-,20,-,18,-,-,11
-,-,31,19,-,-,27
-,-,-,23,11,27,-" (read-network)))

(repl)
