(ns clojure-sandbox.collections-test
  (:require [clojure.test :refer :all]))

(def simple-map {:firstname "John" :lastname "Pyeatt" :age 57})
(def nested-map (-> simple-map
                    (assoc :kids {:son "Sam" :daughter "Gwen"})))
(def simple-vec ["Thorin" "Clifford" "Spock"])
(def nested-vec ["Dogs" ["Thorin" "Clifford"]])

(deftest test-count
  (is (=  3 (count simple-map)) "for a map it's counting the keys, not the total elements.")
  (is (=  4 (count nested-map)) "for a map it's counting the keys, not the total elements.")
  (is (=  3 (count simple-vec)))
  (is (=  2 (count nested-vec)))
  (is (zero? (count [])))
  (is (zero? (count nil)) "nil vector returns 0, just like empty vector"))

(deftest test-empty
  (is (= {} (empty simple-map)) "returns an empty collection of the same type")
  (is (= [] (empty simple-vec)))

  (is (= simple-vec (not-empty simple-vec)))
  (is (= simple-map (not-empty simple-map)))
  (is (nil? (not-empty [])) "source is empty, returns nil")
  (is (nil? (not-empty nil)) "source is nil, returns nil"))

(deftest test-empty?
  (is (not (empty? simple-map)))
  (is (empty? nil))
  (is (empty? [])))

(deftest test-into
  ; When using into to convert to seq you will note the values are flipped because when adding items
  ; to an seq they are appended to the front. When doing vectors they are added to the end.
  (is (= [[:firstname "John"] [:lastname "Pyeatt"] [:age 57]] (into [] simple-map)) "converts maps to nested vector")
  (is (= (seq '( [:age 57] [:lastname "Pyeatt"] [:firstname "John"])) (into '() simple-map)) "converts maps to nested seq")
  (is (= (seq [ "Spock"  "Clifford" "Thorin"]) (into '() simple-vec)) "converts vector to seq, reversing the values")

  (is (= [5 "Thorin" "Clifford" "Spock"] (into [5] simple-vec)) "like merge")
  (is (= {:x 5 :y 4 :z 3} (into {:x 5} [{:y 4} {:z 3}])) "creates a new map from a source map and a vector of map-entries")

  (is (= simple-vec (into simple-vec nil)) "nil 2nd arg returns original value")
  ; now try using a transducer as the second argument.
  (is (= [5 12 6 21 27] (into [5] (map #(* 3 %)) [4 2 7 9])) "the initial value isn't run through the transducer"))

(deftest test-conj
  (is (= [4 3 2 1 5] (conj [4 3] 2 1 5)) "appends new values to the end of a vector")
  (is (= '(5 1 2 4 3) (conj '(4 3) 2 1 5)) "appends new values to the beginning of a sequence")

  (is (= [1 2 [4 5] [6 7]] (conj [1 2] [4 5] [6 7])) "nesting vectors inside another vector")

  (is (= '(3 2 1) (conj nil 1 2 3)) "default when first arg is nil is a seq"))
