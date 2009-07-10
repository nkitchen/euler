(ns euler.p59
  (:use euler.core
        [clojure.contrib.duck-streams :only [reader writer]]
        [clojure.contrib.math]
        [clojure.contrib.repl-ln]
        [clojure.contrib.str-utils]))

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

(def *top-count* 3)

(def lowercase-letters (range (int \a) (inc (int \z))))

(def all-keys (for [k1 lowercase-letters
                    k2 lowercase-letters
                    k3 lowercase-letters]
                [k1 k2 k3]))

(def ans (reduce + (map int (decode "god" cipher-text))))
(prn ans)
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
;;
;;(prn top-decodings)

;;(repl)
