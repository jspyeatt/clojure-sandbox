(ns clojure-sandbox.destructuring-test
  (:require [clojure.test :refer :all]))

(def simple-map-1-layer {:firstname "John" :lastname "Pyeatt" :age 59 :hometown "Somewhere, USA"})
(def simple-map-2-layer {:firstname "John"
                         :age 57
                         :kid-map {:son "Sam" :daughter "Gwen"}
                         :pet-vector ["Clifford" "Thorin"]})
(def simple-vec-1-layer ["John"
                         "Pyeatt"
                         57])
(def simple-vec-2-layer ["74"
                         ["fred" "steve"]
                         42])

(deftest test-simple-in-let
  (testing "destructure to 3 args from a vector based on position.
            Essentially you are taking the source vector and binding values
            in another vector of variables.
            Also note we are ignoring the final element of simple-vec-1-layer :hometown"
    (let [[f-name l-name a] simple-vec-1-layer]
      (is (= f-name (nth simple-vec-1-layer 0)))
      (is (= l-name (nth simple-vec-1-layer 1)))
      (is (= a (nth simple-vec-1-layer 2)))))

  (testing "destructure a vector which has another vector in it into individual values."
    (let [[h [f steve] f2] simple-vec-2-layer]
      (is (= h (first simple-vec-2-layer)))
      (is (= f "fred"))
      (is (= steve "steve"))
      (is (= f2 42))))

  (testing "destructure a vector which has another vector in it into first level response."
    (let [[h f-s-v f2] simple-vec-2-layer]
      (is (= h (first simple-vec-2-layer)))
      (is (= f-s-v ["fred" "steve"]))
      (is (= f2 42))))

  (testing "destructure a basic map. Note you specify in the curlys the bind variable followed by the key name"
    (let [{f-name :firstname l-name :lastname} simple-map-1-layer]
      (is (= f-name (:firstname simple-map-1-layer)))
      (is (= l-name (:lastname simple-map-1-layer)))))

  (testing "destructure a nested map. You basically work from the inside out from left to right."
    (let [{{s :son d :daughter } :kid-map} simple-map-2-layer]
      (is (= s "Sam"))
      (is (= d "Gwen"))))

  (testing "destructure the vector values out of a parent map. So the outer reference is :pet-vector
            and the inner one is a vector of dog names."
    (let [{[dog-1 dog-2] :pet-vector} simple-map-2-layer]
      (is (= dog-1 "Clifford"))
      (is (= dog-2 "Thorin")))))

