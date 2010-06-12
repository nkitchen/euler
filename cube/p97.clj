(ns euler.p97
  (:use euler.core
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(def *modulus* (bigint 1e10))

(def ans
  (-> (.modPow (bigint 2) (bigint 7830457) *modulus*)
      (.multiply (bigint 28433))
      (.add (bigint 1))
      (.mod *modulus*)))

(print ans)
;(repl)

