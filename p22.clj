(ns euler
  (:use [clojure.contrib.duck-streams :only [reader]]
        [clojure.contrib.seq-utils :only [indexed]]))

(load-file "euler.clj")

(def names
  (sort
    (with-open [file (new java.io.PushbackReader (reader "names.clj"))]
      (read file))))

(defn alpha-value [name]
  (sum (map #(inc (- (int %) (int \a)))
            (.toLowerCase name))))

(def scores (for [[i n] (indexed names)]
              (* (inc i) (alpha-value n))))

(prn (sum scores))
