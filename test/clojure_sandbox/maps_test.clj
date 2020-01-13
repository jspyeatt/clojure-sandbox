(ns clojure-sandbox.maps-test
  (:require [clojure.test :refer :all]))

(def simple-map {:firstname "John" :lastname "Pyeatt" :age 57})

(deftest test-keys-and-vals
  (testing "extract keys"
    (is (= [:firstname :lastname :age] (keys simple-map)))
    (is (= ["John" "Pyeatt" 57] (vals simple-map)))))

(deftest test-sorted-map
  (is (= {:age       57
          :firstname "Ken"
          :lastname  "Bills"} (sorted-map :firstname "Ken" :lastname "Bills" :age 57))))

(deftest test-contains
  (is (contains? simple-map :lastname))
  (is (false? (contains? simple-map :height))))
