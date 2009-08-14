(ns euler.p59
  (:use euler.core
        clojure.contrib.math
        clojure.contrib.repl-ln
        clojure.contrib.str-utils
        clojure.parallel
        [clojure.contrib.duck-streams :only [reader writer]]))

(defn decode [[k1 k2 k3 :as key] text]
  (apply str (map #(char (bit-xor (byte (int %1))
                                  (byte (int %2))))
                  (cycle key)
                  text)))

(def english-words
  (set (map #(.toLowerCase #^String %)
            (re-split #"\n" (slurp "/usr/share/dict/words")))))

(defn english-count [text]
  (pipe text
        (re-seq #"[\w']+")
        (map #(.toLowerCase %))
        (filter english-words)
        count))

(defn english-share [text]
  (let [english-chars (pipe text
                            (re-seq #"[\w']+")
                            (map #(.toLowerCase %))
                            (filter english-words)
                            (map count)
                            (reduce +))]
    (/ english-chars (count text))))

(def cipher-text
  (with-open [r (new java.io.PushbackReader (reader "cipher1.clj"))]
    (read r)))

(def lowercase-letters (range (int \a) (inc (int \z))))

(def all-keys (for [k1 lowercase-letters
                    k2 lowercase-letters
                    k3 lowercase-letters]
                [k1 k2 k3]))

;;(def ans (reduce + (map int (decode "god" cipher-text))))
;;(prn ans)

(defn compare-score [a b]
  (compare (:score a) (:score b)))

(def *top-count* 3)

(defn top-merge [m1 m2]
  (let [all (merge m1 m2)]
    (if (<= (count all) *top-count*)
      all
      (reduce dissoc all (take (- (count all) *top-count*)
                               (keys all))))))

(defn apply-key [k]
  (let [decoded (decode k cipher-text)
        score (english-share decoded)]
    (sorted-map-by compare-score {:key k :decoded decoded :score score} 1)))

(def decodings (doall (pmap apply-key all-keys)))
(def top-decodings (preduce top-merge (sorted-map-by compare-score) decodings))
;(def top-decodings
;  (let [decodings (for [k all-keys]
;                    (let [decoded (decode k cipher-text)
;                          score (english-share decoded)]
;                      {{:key k :decoded decoded :score score} 1}))]
;    (preduce top-merge (sorted-map-by compare-score) decodings)))

;;(def top-decodings
;;  (loop [rkeys all-keys
;;         best (sorted-map-by #(compare (:score %1) (:score %2)))]
;;    (if (seq rkeys)
;;      (let [key (first rkeys)
;;            decoded (decode key cipher-text)
;;            score (english-share decoded)
;;            item {:key key :decoded decoded :score score}]
;;        (cond
;;          (< (count best) *top-count*)
;;            (recur (next rkeys) (assoc best item 1))
;;          (> score (:score (ffirst best)))
;;            (recur (next rkeys)
;;                   (-> best
;;                     (dissoc (ffirst best))
;;                     (assoc item 1)))
;;          :else (recur (next rkeys) best)))
;;      best)))

(prn top-decodings)

;;(repl)
