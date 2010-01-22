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
  "Reads the file and returns a sequence of known squares for each game."
  [filename]
  (for [lines (->> (read-lines filename)
                   (remove #(re-find #"^Grid" %))
                   (partition 9))]
    (for [[i line] (indexed lines)
          [j c] (indexed line)
          :let [value (- (int c) (int \0))]
          :when (not= value 0)]
      [i j value])))

(defn neighbors [i j]
  (->> (concat (for [k (range 0 *size*)] [i k])
               (for [k (range 0 *size*)] [k j])
               (for [k (range 0 3)
                     l (range 0 3)]
                 [(+ k (* 3 (quot i 3)))
                  (+ l (* 3 (quot j 3)))]))
       (remove #{[i j]})))

(defn propagate
  "Updates the candidates in each square, given a known value at [i j]."
  [board [i j value]]
  (when board
    (let [new-board (reduce #(update-in %1 %2 disj value)
                            board
                            (neighbors i j))
          new-unit (for [k (range 0 *size*)
                         l (range 0 *size*)
                         :when (= 1 (count (get-in new-board [k l])))
                         :when (< 1 (count (get-in board [k l])))]
                     [k l (first (get-in new-board [k l]))])]
      (if (some #(some empty? %1) new-board)
        nil
        (reduce propagate new-board new-unit)))))

; TODO: For each remaining candidate, check if it's the only square in its
; row/column/block with that value and if so, set and propagate it.

(repl)

