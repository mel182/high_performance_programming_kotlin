package collections.binary_search_tree

// This is binary search tree example
fun main() {

    val data = mutableSetOf(1,2,3,4,5,6,7,8,9,10)
    val root = data.maxOrNull()?.div(2)

    if (data.contains(root))
        data.remove(root)

    // Balance tree
    val exampleTree = BinarySearchTree<Int>().apply {

        root?.let { rootTree ->

            insert(rootTree)

            for (item in data)
            {
                insert(item)
            }
        }

        // Original example
//        insert(3)
//        insert(1)
//        insert(4)
//        insert(0)
//        insert(2)
//        insert(5)
    }

    "Building a BST" example {
        println(exampleTree)
    }

    "Finding a node" example {

        if (exampleTree.contains(5))
        {
            println("Found 5!")
        } else {
            println("Couldn't find 5")
        }
    }

    "Remove a node" example {

        println("Tree before removal")
        println(exampleTree)
        exampleTree.remove(3)
        println("Tree after removing root:")
        println(exampleTree)
    }
}