(ns euler.p98
  (:use euler.core
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

; Max is 9 letters, all unique.

;(print ans)
(repl)

