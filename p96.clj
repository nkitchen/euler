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

(defn group [i j dim]
  (if (< dim 2)
    (for [k (range 0 *size*)]
      (assoc [i j] dim k))
    (for [k (range 0 3)
          l (range 0 3)]
      [(+ k (* 3 (quot i 3)))
       (+ l (* 3 (quot j 3)))])))

(defn neighbors
  ([i j dim] (remove #{[i j]} (group i j dim)))
  ([i j] (apply concat (for [dim (range 3)] (neighbors i j dim)))))

(defn exclude
  "Given known value at [i j], removes that value from neighbors."
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
        (reduce exclude new-board new-unit)))))

(defn required
  "Returns values that remain in only one square in their groups."
  [board]
  (when board
    (for [i (range 0 *size*)
          j (range 0 *size*)
          :let [cands (get-in board [i j])]
          value cands
          :when (->> (range 3)
                     (some (fn [dim]
                             (not-any? #((get-in board %) value)
                                       (neighbors i j dim)))))
          :when (not= 1 (count cands))]
      [i j value])))

(defn assign [board [i j value]]
  (when (and board
             ((get-in board [i j]) value))
    (assoc-in board [i j] #{value})))

(defn solve [board known]
  (let [assigned (reduce assign board known)
        new-board (reduce exclude assigned known)
        inferred (required new-board)]
    (if (seq inferred)
      (recur (reduce assign new-board inferred)
             inferred)
      new-board)))

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

(defn solved? [board]
  (when board
    (every? #(= 1 (count %)) (for [row board, square row] square))))

(defn code [board]
  (from-digits (for [j (range 3)] (first (get-in board [0 j])))))

(def games (read-games "sudoku.txt"))
(def ans
  (sum (for [[n game] (indexed games)]
         (let [final (solve (empty-board) game)]
           (if (solved? final)
             (code final)
             (do
               (println "Not solved:" n)
               0))))))

(repl)

