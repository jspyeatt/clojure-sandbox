(ns clojure-sandbox.regex-test
  (:require [clojure.test :refer :all]))

(deftest test-re-find
  (testing "Using a regex with a matcher"
    (let [matcher (re-matcher #"\d+" "abc12345def987a111")]
      (is (= "12345" (re-find matcher)) "first match found")
      (is (= "987" (re-find matcher)) "matcher maintains state so a subsequent call starts where last call ended.")
      (is (= "111" (re-find matcher)))
      (is (nil? (re-find matcher)) "No more matches found")))

  (testing "When there are groupings in the regex re-find returns a vector with n elements:
           The first element will be the part of the string which matches the entire regex.
           The subsequent elements will be each matching group."
    (let [matcher (re-matcher #"(\S+):(\d+)" "fred steve:123 bill:432 fred:dave 333:555")]
      (is (= ["steve:123"
              "steve"
              "123"] (re-find matcher)))
      (is (= ["bill:432"
              "bill"
              "432"] (re-find matcher)))
      (is (= ["333:555"
              "333"
              "555"] (re-find matcher)))
      (is (nil? (re-find matcher)) "nothing left")))

  (testing "You can also use re-find directly without a matcher"
    (is (= "123" (re-find #"\d+" "abc123def")))))

(deftest test-re-seq
  (testing "returns a lazy sequence of everything that matches."
    (is (= (seq ["mary" "had" "a" "little" "lamb"]) (re-seq #"\w+" "mary had a little lamb"))))
  (testing "no match"
    (is (nil? (re-seq #"\d+" "mary had a little lamb"))))

  (testing "using groupings"
    (is (= (seq [["steve:123" "steve" "123"]
                 ["bill:432" "bill" "432"]
                 ["333:555" "333" "555"]]) (re-seq #"(\S+):(\d+)" "fred steve:123 bill:432 fred:dave 333:555")))))

(deftest test-re-matches
  (testing "re-find tries to match any part of the string. re-matches only matches if entire string matches."
    (is (nil? (re-matches #"hello" "hello, world.")) "Not a complete match of the string")
    (is (= "hello, world." (re-matches #"hello.*" "hello, world.")) "comnplete match"))
  (testing "with grouping. returns the entire string match in the first element and the subsequent grouping in other elements,"
    (is (= ["hello, world."
            "world."] (re-matches #"hello, (.*)" "hello, world."))))

  (testing "case insensitive match."
    (let [reg #"(?i)hello"]
      (is (= "Hello" (re-matches reg "Hello")))
      (is (= "HELLO" (re-matches reg "HELLO")))
      (is (nil? (re-matches reg "fred")) "no match"))))

(deftest test-re-pattern
  ; returns an instance of the java pattern
  (testing "re-pattern"
    (let [pattern (re-pattern #"\w+")
          mary "mary had a little lanb"
          hello "hello, world"]
      (is (= "mary" (re-find pattern mary)))
      (is (= "hello" (re-find pattern hello)))
      (is (= ["hello" "world"] (re-seq pattern hello))))))

(deftest test-re-groups
  ; This one is a bit weird because it works with re-find. re-groups returns the most recent find. Really
  ; pay attention to the flow of this.

  (let [matcher (re-matcher #"((\d+)-(\d+))" "672-345-456-3212")]

    (is (= ["672-345" "672-345" "672" "345"] (re-find matcher)) "first match found in re-find")
    (is (= ["672-345" "672-345" "672" "345"] (re-groups matcher)) "returns the same thing as the re-find.")

    (is (= ["456-3212" "456-3212" "456" "3212"] (re-find matcher)) "second re-find match")
    (is (= ["456-3212" "456-3212" "456" "3212"] (re-groups matcher)) "same result as re-find")
    (is (= ["456-3212" "456-3212" "456" "3212"] (re-groups matcher)) "STILL the same as last re-find")

    (is (nil? (re-find matcher)))
    (is (thrown? IllegalStateException (re-groups matcher)) "Throws exception because re-find returns nil.")))

