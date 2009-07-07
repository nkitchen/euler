(ns euler.p59
  (:use euler.core
        [clojure.contrib.duck-streams :only [reader writer]]
        [clojure.contrib.math]
        [clojure.contrib.repl-ln]))

(defn decode [key bytes]
  (map #(bit-xor % key) bytes))

(defn decode-str [key bytes]
  (apply str (map char (decode key bytes))))

(defn freqs [xs]
  (apply merge-with + {} (for [x xs] {x 1})))

(defn distro [fs]
  (let [z (apply + (vals fs))]
    (into {} (for [[x n] fs] [x (/ n z)]))))

(defn tvd
  "Total variation distance"
  [p q]
  (* 1/2 (sum (for [x (set (concat (keys p) (keys q)))]
                (abs (- (p x 0) (q x 0)))))))
(def tvd (memoize tvd))

(def english-distro
;  (with-open [#^java.io.BufferedReader r (reader "/tmp/corpus")]
;    (loop [f {}]
;      (let [c (int (.read r))]
;        (cond
;          (= c -1) (distro f)
;          (Character/isLetter c) (let [u (Character/toUpperCase c)]
;                                   (recur (merge-with + f {u 1})))
;          :else (recur f))))))
  (with-open [r (new java.io.PushbackReader (reader "english-distro.clj"))]
    (read r)))
;(with-open [w (writer "english-distro.clj")]
;  (.println w english-distro))

;(prn (#(for [[c n] %] [(char c) n]) (sort-by second (into [] english-distro))))

(def cipher
  (with-open [r (new java.io.PushbackReader (reader "cipher1.clj"))]
    (read r)))

(def as (map (fn [i] (apply concat (partition 1 3 (drop i cipher))))
             (range 3)))

(defn decodings [bytes]
  (into {} (for [key (range (int \a) (inc (int \z)))]
             {key (decode key bytes)})))

(defn tvd-to-english [bytes]
  (tvd (distro (freqs (map #(Character/toUpperCase (char %)) bytes)))
       english-distro))
(def tvd-to-english (memoize tvd-to-english))

(def best-decodings
  (map (fn [bytes]
         (let [ds (into [] (decodings bytes))]
           (last (sort-by #(tvd-to-english (second %)) ds))))
       as))

(prn (apply str (apply interleave best-decodings)))
;(repl)
