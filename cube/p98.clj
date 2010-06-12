(ns euler.p98
  (:use euler.core
        clojure.contrib.combinatorics
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(def words
  (with-open [r (new java.io.FileReader "words.txt")]
    (doall (iterator-seq (doto (new java.util.Scanner r)
                           (.useDelimiter #"(?x) \" (?:,\")? "))))))

(def anagrams
  (->> 
    (reduce (fn [m [k v]]
              (update-in m [k] conj v))
            {}
            (for [w words]
              [(sort (seq w)) w]))
    (filter (fn [[_ words]] (> (count words) 1)))
    (into {})))

(defn square? [n]
  (let [r (quot (Math/sqrt n) 1)]
    (== (* r r) n)))

(defn max-square
  ([letters words]
    (println "Finding max square for " words)
    (let [n (count letters)]
      (apply max
             (for [digits (mapcat permutations (combinations (range 10) n))]
               (max-square letters words digits)))))
  ([letters words digits]
    (let [n (count letters)
          assignment (apply hash-map (interleave letters digits))
          squares (for [w words
                        :let [p (from-digits (map assignment w))]
                        :when (square? p)]
                    p)]
      (if (and (> (count squares) 1)
               (not-any? #(== 0 (assignment (first %))) words))
        (apply max squares)
        0))))
          
(def ans
  (apply max
         (for [[letters words] anagrams]
           (max-square (distinct letters) words))))

(print ans)
;(repl)

