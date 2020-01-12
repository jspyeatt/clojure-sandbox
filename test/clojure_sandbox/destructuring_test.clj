(ns clojure-sandbox.destructuring-test
  (:require [clojure.test :refer :all]))

; The examples here are only demonstrating (let)
; But they can be used with function arguments as well.
; The only difference is that you don't actually pass in
; any arguments to the function definition.
; example:
; (defn my-func [[f-name l-name]]
;    .. function body with a vector passed in.
; )
; (defn my-func-2 [{f-name :firstname a :age}]
;    .. function body with map passed in.
; )

(defn flip-vector-to-string
  [[v1 v2]]
  (str v2 v1))
(defn flip-map-to-string-assigned-by-keys
  [{:keys [firstname age]}]
  (str age firstname))
(defn flip-map-to-string-assigned-by-keys-with-rest-of-map
  [{:keys [firstname age] :as entire-map}]
  (str age firstname (:hometown entire-map)))

(def simple-map {:firstname "John" :lastname "Pyeatt" :age 57 :hometown "Somewhere, USA"})
(def map-with-embedded-vector {:firstname "John"
                               :age             57
                               :kid-map         {:son "Sam" :daughter "Gwen"}
                               :pet-vector      ["Clifford" "Thorin"]})
(def simple-vector ["John"
                    "Pyeatt"
                    57])
(def vector-with-embedded-map ["74"
                               ["fred" "steve"]
                               42
                               {:state "completed" :success true}])

(deftest test-simple-in-let
  (testing "destructure to 3 args from a vector based on position.
            Essentially you are taking the source vector and binding values
            in another vector of variables.
            Also note we are ignoring the final element of simple-vec-1-layer :hometown"
    (let [[f-name l-name a] simple-vector]
      (is (= f-name (nth simple-vector 0)))
      (is (= l-name (nth simple-vector 1)))
      (is (= a (nth simple-vector 2)))))

  (testing "destructure a vector which has another vector in it into individual values. Notice the nested vectors
            in the let function."
    (let [[h [f steve] f2] vector-with-embedded-map]
      (is (= h (first vector-with-embedded-map)))
      (is (= f "fred"))
      (is (= steve "steve"))
      (is (= f2 42))))

  (testing "destructure a vector which has another vector in it into first level response."
    (let [[h f-s-v f2] vector-with-embedded-map]
      (is (= h (first vector-with-embedded-map)))
      (is (= f-s-v ["fred" "steve"]))
      (is (= f2 42))))

  (testing "destructure a basic map. Note you specify in the curlys the bind variable followed by the key name"
    (let [{f-name :firstname l-name :lastname} simple-map]
      (is (= f-name (:firstname simple-map)))
      (is (= l-name (:lastname simple-map)))))

  (testing "destructure a nested map. You basically work from the inside out from left to right."
    (let [{{s :son d :daughter } :kid-map} map-with-embedded-vector]
      (is (= s "Sam"))
      (is (= d "Gwen"))))

  (testing "destructure the vector values out of a parent map. So the outer reference is :pet-vector
            and the inner one is a vector of dog names."
    (let [{[dog-1 dog-2] :pet-vector} map-with-embedded-vector]
      (is (= dog-1 "Clifford"))
      (is (= dog-2 "Thorin"))))

  (testing "destructure the map values out of a parent vector."
    (let [[a b c {status :state succ :success}] vector-with-embedded-map]
      (is (= a "74"))
      (is (= status "completed"))
      (is (true? succ)))))

(deftest test-simple-in-fn
  (testing "simple function with vector"
    (is (= "PyeattJohn" (flip-vector-to-string simple-vector))))
  (testing "simple function with map"
    (is (= "57John" (flip-map-to-string-assigned-by-keys simple-map))))
  (testing "function with rest of map available as last parameter"
    (is (= "57JohnSomewhere, USA" (flip-map-to-string-assigned-by-keys-with-rest-of-map simple-map)))))