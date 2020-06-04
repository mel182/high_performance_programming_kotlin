package concurrency.runnable

import java.math.BigInteger
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

fun main() {

    // Check for the available processors
    val availableProcessors = Runtime.getRuntime().availableProcessors()
    println("Available processor: $availableProcessors")

    val startTime = System.currentTimeMillis()
    val cakes = Baker().order(amountOfCakes = 10)
    println("Number of cakes ${cakes.size}")
    val endTime = System.currentTimeMillis()
    println("Time taken order function: ${endTime - startTime}")

    val fastOrderStartTime = System.currentTimeMillis()
    val fastOrderCakes = Baker().fastOrder(amountOfCakes = 10)
    println("Fast orders, number of cakes ${fastOrderCakes.size}")
    val fastOrderEndTime = System.currentTimeMillis()
    println("Time taken fast order function: ${fastOrderEndTime - fastOrderStartTime}")

}

class Baker{

    fun fastOrder(amountOfCakes: Int):List<Cake>
    {
        val cakes = mutableListOf<Cake>()
        val baker = Baker()
        val countDownLatch = CountDownLatch(amountOfCakes)
        val bakingTask = Runnable {
            cakes.add(baker.bake())
            countDownLatch.countDown()
        }

        val executor = Executors.newFixedThreadPool(amountOfCakes)
        for (i in 0 until amountOfCakes)
        {
            executor.execute(bakingTask)
        }

        executor.shutdown()
        countDownLatch.await()

        return cakes
    }


    fun order(amountOfCakes:Int): List<Cake>
    {
        val cakes = mutableListOf<Cake>()
        val baker = Baker()
        val bakingTask = Runnable {
            cakes.add(baker.bake())
        }

        for (i in 0 until amountOfCakes)
        {
            bakingTask.run()
        }

        return cakes
    }


    fun bake(): Cake
    {
        for (i in 0..1_000_000)
        {
            BigInteger.ONE.modPow(BigInteger.TEN, BigInteger.TEN)
        }

        return Cake()
    }
}

class Cake {}