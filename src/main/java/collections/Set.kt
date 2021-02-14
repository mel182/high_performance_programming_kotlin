package collections

// A set is a data structure that stores unrepeated values without any particular order.
// A common use of this data structure is to check whether a value or subset is included with a set or whether
// two subsets have common elements.
//
// The most common operations on sets are:
// Union: Returns a new set that contains elements from two given sets without duplicates
// Intersection: Returns a set that contains all common items between two given sets
// Difference: Returns a new set of items that are stored in one set but aren't stored in another
// Subset: Returns a Boolean value that shows whether all items from one set, are contained in another
//
fun main() {

//    readOnlySet()

//    preventDuplicateInSet()

//    customObjectListExample()

//    IntSetExample()

//    setIndexing()

//    setCountExample()

//    setFirstLastElement()

//    setForEachIndexedLoop()

//    sortSetExample()

//    setContainsExample()

//    mutableSetExample()

//    unionSetsExample()

//    setMaxExample()

//    filterSetExample()

//    mapSetsExample()

//    setReductionExample()

//    setFoldingExample()

//    setChunkedExample()

//    partitionSetExample()

//    setGroupBy()

//    setAnyExample()

//    setAllExample()

//    setDropExample()

//    setTakeExample()
}

//region Read-only set example
private fun readOnlySet()
{
    // The setOf() method creates a new read-only set in Kotlin.
    // This example creates a new set of words with setOf().
    // The size of the set is determined with the size attribute.
    val words = setOf("Apple","Pear","Watermelon","strawberry","cherry")
    println("The set contains ${words.size} elements.")

}
//endregion

//region Prevent duplicate values in set Example
private fun preventDuplicateInSet()
{
    // An example of set that cannot contain duplicate elements.
    // Even though we have added two pens into the setOf(),
    // there will be only one element of pen.
    val words2 = setOf("pen","cup","dog","pen","spectacles")
    for (word in words2)
    {
        println("Word found: ${word}")
    }
}
//endregion

//region Custom object list example
private fun customObjectListExample()
{
    // This example demonstrate another a set that avoid duplicated
    // objects.
    val personList = ArrayList<Person>()
    personList.add(Person("Melchior1",33))
    personList.add(Person("Melchior1",33))
    personList.add(Person("Melchior2",34))

    val personArray = personList.toSet()
    for (person in personArray)
    {
        println("Person found: ${person}")
//        Person found: Person(name=Melchior1, age=33)
//        Person found: Person(name=Melchior2, age=34)
    }
}
//endregion

//region Int set example
private fun IntSetExample()
{
    // Int set example
    // The example creates a set of numbers and computes some statistics.
    // We compute the number of values, maximum, minimum, sum, and the average of the values.
    val numbers = setOf(11, 5, 3, 8, 1, 9, 6, 2)
    println("Length: ${numbers.count()}")
    println("Max: ${numbers.maxOrNull()}")
    println("Min: ${numbers.minOrNull()}")
    println("Sum: ${numbers.sum()}")
    println("Avg: ${numbers.average()}")

//    val sortedList = numbers.sorted() // Ascending sorting
    val sortedList = numbers.sortedDescending() // Descending sorting
    for (number in sortedList)
    {
        println("Number sorted found: $number")
    }
}
//endregion

//region Set indexing
private fun setIndexing()
{
    // Kotlin set indexing
    val listExample = setOf("pen", "cup", "dog", "person",
        "cement", "coal", "spectacles", "cup", "bread")

    //The example presents Kotlin Set indexing operations.
    // An element is retrieved with the elementAt() method.
    // The method takes an index of the element to be retrieved as a parameter.
    val elementIndex0 = listExample.elementAt(0) // pen
    println("Element at index 0: $elementIndex0")

    // The indexOf() returns the index of the first occurrence of the word in the set.
    val elementIndexOfCup = listExample.indexOf("cup") // 1
    println("Element index of cup: $elementIndexOfCup")

    // The indexOf() returns the index of the first occurrence of the word in the set.
    val elementLastIndexOfCup = listExample.lastIndexOf("cup")
    println("Element last index of cup: $elementLastIndexOfCup")
}
//endregion

//region Set count example
private fun setCountExample()
{
    val rawList = ArrayList<Int>()
    rawList.add(4)
    rawList.add(5)
    rawList.add(3)
    rawList.add(2)
    rawList.add(2)
    rawList.add(1)
    rawList.add(-1)
    rawList.add(7)
    rawList.add(6)
    rawList.add(-8)
    rawList.add(9)
    rawList.add(-12)

//    val nums = setOf(4, 5, 3, 2, 1, -1, 7, 6, -8, 9, -12)
    val nums = rawList.toSet()

    val length = nums.count()
    println("There are $length elements")

    val size = nums.size
    println("Set size: $size")

    val negativeValues = nums.count { e -> e < 0 }
    println("Negative values count: $negativeValues")

    val evenValuesCount = nums.count { e -> e % 2 == 0 }
    println("Even values count: $evenValuesCount")

    val filter = nums.filter { e -> e > 0 }
    println("Filter list: $filter")

}
//endregion

//region Set first and last element
private fun setFirstLastElement()
{
    println("Set first last element function called!")

    val nums = setOf(4, 5, 3, 2, 1, -1, 7, 6, -8, 9, -12)

    println("First: ${nums.first()}")
    println("Last: ${nums.last()}")

    val findLast2 = nums.findLast { e -> e == 2 }
    println("Last find: $findLast2")

    val findFirst2 = nums.first { e -> e == 2 }
    println("Last find: $findFirst2")

}
//endregion

//region Set for each indexed loop
private fun setForEachIndexedLoop()
{
    println("Set loop!")
    val words = setOf("pen", "cup", "dog", "person",
        "cement", "coal", "spectacles")

    words.forEachIndexed { index, value ->
        println("$index -> $value")
    }
}
//endregion

//region Set sorting example
private fun sortSetExample()
{
    val nums = setOf(1,2,12,10,5,-1)
    println("List: $nums")

    val sortAscending = nums.sorted()
    println("Sort ascending: $sortAscending") // [-1, 1, 2, 5, 10, 12]

    val sortDescending = nums.sortedDescending()
    println("Sort descending: $sortDescending") // [12, 10, 5, 2, 1, -1]

    val revNum = nums.reversed()
    println("Sort reversed: $revNum") // [-1, 5, 10, 12, 2, 1]

    println("|------------- Data class person example -------------|")

    val personList = ArrayList<Person>()
    personList.add(Person("Melchior1",33))
    personList.add(Person("Melchior1",33))
    personList.add(Person("Melchior2",34))

    val personSet = personList.toSet()
    val nameSorted = personSet.sortedBy { person -> person.name }
    for (person in nameSorted) {
        println(person)
    }

    println("|------------- Data class person example -------------|")
}
//endregion

//region Set contains example
private fun setContainsExample()
{
    // Int example
    val nums = setOf(4, 5, 3, 2, 1, -1, 7, 6, -8, 9, -12)

    val contains4 = nums.contains(4)
    println("Contains 4: $contains4") // true

    val setContainsBulk = nums.containsAll(setOf(1,-12))
    println("Contains bulk set: $setContainsBulk") // true

    // Object example
    val personSet = setOf(
        Person("Person 1", 23),
        Person("Person 2", 24),
        Person("Person 3", 25),
        Person("Person 4", 26),
    )

    val personBulk = personSet.containsAll(setOf(Person("Person 1", 23),Person("Person 2", 24)))
    println("Contains person bulk: $personBulk") // true
}
//endregion

//region Mutable set example
private fun mutableSetExample()
{
    // Int example
    val nums = mutableSetOf(4, 5, 3, 2, 1, -1, 7, 6, -8, 9, -12)

    nums.add(200)
    nums.add(100) // With mutable set you have the option to add items
    nums.remove(100) // Also the option to remove item from set

    val contains100 = nums.contains(100)
    println("Contains 100: $contains100") // true

    nums.retainAll(setOf(20))
    println("List after retain all 1: $nums") // As mutable sets retain all

    nums.addAll(setOf(100,2,3))
    println("List after add all: $nums") // As mutable sets addAll can be used

    val setContainsBulk = nums.containsAll(setOf(1,-12))
    println("Contains bulk set: $setContainsBulk") // true

    // Object example
    val personSet = setOf(
        Person("Person 1", 23),
        Person("Person 2", 24),
        Person("Person 3", 25),
        Person("Person 4", 26),
    )

    val personBulk = personSet.containsAll(setOf(Person("Person 1", 23),Person("Person 2", 24)))
    println("Contains person bulk: $personBulk") // true
}
//endregion

//region Union sets example
private fun unionSetsExample()
{
    val nums = setOf(1,2,3)
    val nums2 = setOf(4,5,6)

    val numUnion = nums.union(nums2)

    println("Union result: $numUnion")
}
//endregion

//region Set max example
private fun setMaxExample()
{
    // With int example

    val nums = setOf(11,5,23,8,1,9,6,2)
    println("Nums max: ${nums.maxOrNull()}")

    // Object example
    val personSet = setOf(
        Person("Person 1", 23),
        Person("Person 2", 24),
        Person("Person 3", 25),
        Person("Person 4", 26),
    )

    val personMax = personSet.maxByOrNull {
        person -> person.age
    }
    println("Person max: $personMax")
}
//endregion

//region Filter set example
private fun filterSetExample()
{
    val words = setOf("pen", "cup", "dog", "person",
        "cement", "coal", "spectacles")

    val words2 = words.filter { e -> e.length == 3 }
    words2.forEach {
        println("words filtered: $it")
    }

    val words3 = words.filterNot { e -> e.length == 3 }
    words3.forEach {
        println("words not filtered: $it")
    }

    // Object example
    val personSet = setOf(
        Person("Person 1", 23),
        Person("Person 2", 24),
        Person("Person 3", 25),
        Person("Person 4", 26),
    )

    val personFilterList = personSet.filter { person -> person.age > 24  }
    personFilterList.forEach {
        println("person filter list: $it")
    }
}
//endregion

//region set map example
private fun mapSetsExample()
{
    // The mapping operation returns a modified list by applying a
    // transform function on each element of the set.
    val numbers = setOf(1,2,3,4,5,6)
    val numberMap = numbers.map { e -> e > 2 }
    println("Number map: ${numberMap}")
}
//endregion

//region Set reduction example
private fun setReductionExample()
{
    // Reduction is a terminal operation that aggregates set
    // values into a single value. The reduce() method applies
    // a function against an accumulator and each element (from left to right)
    // to reduce it to a single value.

    val numbers = setOf(1,2,3,4,5,6,7)
    // We calculate the sum of values. The total is the accumulator, the next
    // is the next value in the list.
    val sum = numbers.reduce { total, next -> total + next }
    println(sum) // 28
}
//endregion

//region Set folding example
private fun setFoldingExample()
{
    println("Set folding example!")
    // The folding operation is similar to the reduction.
    // Folding is a terminal operation that aggregates set values
    // into a single value. The difference is that folding starts
    // with an initial value.

    val initialValue = 10
    val numbers = setOf(1,2,3,4,5,6,7)
    val sum = numbers.fold(initialValue) { total, next -> total + next }
    println(sum) // 38
}
//endregion

//region Set chunked example
private fun setChunkedExample()
{
    // Sometimes we need to work with more elements of a
    // set when doing reductions. We can use the chunked()
    // method to split the set into a list of lists.
    val numbers = setOf(1,2,3,4,5,6,7)

    // In the example, we have a set of six values. We want to
    // achieve the following operation: 1*2 + 3*4 + 5*6 + 7. For this,
    // we need to split the list into chunks of two values.
    val result = numbers.chunked(2).fold(0) { total, next ->

        if (next.isNotEmpty())
        {
            if (next.size == 2)
            {
                total + next[0] * next[1]
            } else {
                total + next[0]
            }
        } else {
            total
        }
    }

    println(result) // 51
}
//endregion

//region Set partition example
private fun partitionSetExample()
{
    println("Partition set example")

    //The partition operation splits the original collection into pair of lists.
    // The first list contains elements for which the specified predicate yields
    // true, while the second list contains elements for which the predicate yields
    // false.
    val numbers = setOf(4,-5,3,2,-1,7,-6,8,9)

    val (nums2, nums3) = numbers.partition { e -> e < 0 }

    println("Predicate result: $nums2") // [-5, -1, -6] 'predicate result'
    println("Rest of element: $nums3") // [4, 3, 2, 7, 8, 9]

    // Object example
    val personList = ArrayList<Person>()
    personList.add(Person("Person 1", 23))
    personList.add(Person("Person 2", 24))
    personList.add(Person("Person 3", 25))
    personList.add(Person("Person 4", 26))

    val (olderThan24, youngerThan24) = personList.toSet().partition { e -> e.age > 24 }
    println("Older than 24: $olderThan24") // [Person(name=Person 3, age=25), Person(name=Person 4, age=26)]
    println("Younger than 24: $youngerThan24") // [Person(name=Person 1, age=23), Person(name=Person 2, age=24)]
}
//endregion

//region Set group by example
private fun setGroupBy()
{
    // The groupBy() method groups elements of the original set by
    // the key returned by the given selector function, applied to
    // each element. It returns a map where each group key is associated
    // with a list of corresponding elements.
    val numbers = setOf(1,2,3,4,5,6,7,8,9)
    val result = numbers.groupBy { if (it % 2 == 0) "even" else "odd" } // map with key 'even' and 'odd'

    val even = result["even"] // Even value
    println("Even result: $even") // [2, 4, 6, 8]

    val odd = result["odd"] // Odd value
    println("Odd result: $odd") // [1, 3, 5, 7, 9]

    // Object example
    val personList = ArrayList<Person>()
    personList.add(Person("Person 1", 23))
    personList.add(Person("Person 2", 24))
    personList.add(Person("Person 3", 25))
    personList.add(Person("Person 4", 26))

    val objectResult = personList.toSet().groupBy {

        if (it.age > 24)
            "olderThan24"
        else
            "youngerThan24"
    }

    val olderThan24 = objectResult["olderThan24"] // Older than 24
    println("Older than 24: $olderThan24") // [Person(name=Person 1, age=23), Person(name=Person 2, age=24)]

    val youngerThan24 = objectResult["youngerThan24"] // Younger than 24
    println("Younger than 24: $youngerThan24") // [Person(name=Person 1, age=23), Person(name=Person 2, age=24)]
}
//endregion

//region Set any example
private fun setAnyExample()
{
    //The any() method returns true if at least one element matches the given predicate function.
    val numbers = setOf(4,5,3,2,-1,7,6,8,9)
    val hasGreaterThan10 = numbers.any{ e -> e > 10 }

    if (hasGreaterThan10)
        println("Has greater than 10")
    else
        println("No value greater than 10")

    // Object example
    val personList = ArrayList<Person>()
    personList.add(Person("Person 1", 23))
    personList.add(Person("Person 2", 24))
    personList.add(Person("Person 3", 25))
    personList.add(Person("Person 4", 26))

    val personOlderThan24 = personList.toSet().any { e -> e.age > 30 }
    if (personOlderThan24)
        println("Has person older than 24")
    else
        println("Has no person older than 24")
}
//endregion

//region Set all example
private fun setAllExample()
{
    // The all() returns true if all elements satisfy the given predicate function.
    val nums = setOf(4,5,3,2,-1,7,6,8,9)
    val nums2 = setOf(-3,-4,-2,-5,-7,-8)

    val result = nums.all { e -> e > 0 }
    println("Result: $result") // false since it contain one value below 0

    val result2 = nums2.all { e -> e < 0 }
    println("Result2: $result2") // true since it all values are below 0

    val personList = ArrayList<Person>()
    personList.add(Person("Person 1", 23))
    personList.add(Person("Person 2", 24))
    personList.add(Person("Person 3", 25))
    personList.add(Person("Person 4", 26))

    val result3 = personList.toSet().all { e -> e.age > 27}
    println("Result 3: $result3") // false since it does not contain person older than 24
}
//endregion

//region Set drop example
private fun setDropExample()
{
    // With the drop operations, we exclude some elements from the set.

    val nums = setOf(4,5,3,2,1,-1,7,6,-8,9,-12)

    val nums2 = nums.drop(3)
    println("nums 2: $nums2")

    val nums3 = nums.sorted().dropWhile { e -> e < 0 } // [1, 2, 3, 4, 5, 6, 7, 9]
    // (you must sort it so all negative values are at the top)
    println("nums 3: $nums3")

    val nums4 = nums.sortedDescending().dropWhile { e -> e > 0 } // [-1, -8, -12]
    // (you must sort it so all positive values are at the top)
    println("nums 4: $nums4")

    val nums5 = nums.sorted().dropLastWhile { e -> e > 0 } // [-12, -8, -1]
    // With the dropLastWhile() method, we exclude the last n elements that
    // satisfy the given predicate function.
    println("nums 5: $nums5")

    // Object example
    val personList = ArrayList<Person>()
    personList.add(Person("Person 1", 23))
    personList.add(Person("Person 2", 24))
    personList.add(Person("Person 3", 25))
    personList.add(Person("Person 4", 26))

    val nums6 = personList.toSet().sortedBy { e -> e.age }.dropWhile { e -> e.age < 24 }
    println("nums 6: $nums6")

    val nums7 = personList.toSet().sortedByDescending { e -> e.age }.dropWhile { e -> e.age > 24 }
    println("nums 7: $nums7")
}
//endregion

//region Set take example
private fun setTakeExample()
{
    println("Set take example")

    // The take operations are complementary to the drop operations.
    // The take methods form a new list by picking some of the set elements.
    val nums = setOf(5,4,3,2,1,-1,7,6,-8,9,-12)

    // The example shows the usage of various take methods.
    val nums2 = nums.take(3) // [5, 4, 3]
    println("Nums 2: $nums2")

    // The take() method creates a new list having the first three elements of the original set.
    val num3 = nums.sorted()
    println("Num3: $num3") // [-12, -8, -1, 1, 2, 3, 4, 5, 6, 7, 9]
    println("Num3 take: ${num3.take(3)}") // [-12, -8, -1]

    // The takeWhile() takes the first n elements that satisfy the predicate function.
    val takeWhile = nums.takeWhile { e -> e > 0 }
    println("take while result: $takeWhile") // [5, 4, 3, 2, 1]

    val takeWhileDescending = nums.sortedDescending().takeWhile { e -> e > 0 }
    println("take while descending result: $takeWhileDescending") // [9, 7, 6, 5, 4, 3, 2, 1]

    // The takeIf() methods takes all elements of the set if the condition in the predicate function is met.
    val takeIfResult = nums.takeIf { e -> e.contains(6) }
    println("take if result: $takeIfResult") // [5, 4, 3, 2, 1, -1, 7, 6, -8, 9, -12]
}
//endregion

data class Person (val name:String, val age:Int)