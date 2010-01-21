(ns euler.p96
  (:use euler.core
        clojure.contrib.duck-streams
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils))

(set! *warn-on-reflection* true)

(def *size* 9)

(defn empty-board 
  "Returns an empty Sudoku board."
  []
  (let [all (set (range 1 (inc *size*)))]
    (vec (for [i (range *size*)]
           (vec (for [j (range *size*)]
                  all))))))

(defn read-games
  "Reads the file and returns a sequence of fixed squares for each game."
  [filename]
  (for [lines (->> (read-lines filename)
                   (remove #(re-find #"^Grid" %))
                   (partition 9))]
    (for [[i line] (indexed lines)
          [j c] (indexed line)
          :let [value (- (int c) (int \0))]
          :when (not= value 0)]
      [i j value])))

(repl)

