package concurrency.reactive_programming

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.math.BigInteger

fun main() {

    Baker().reactiveOrder(10).toObservable().blockingSubscribe{
        cake -> println("Number of cakes ${cake.size}")
    }
}

class Baker{

    fun reactiveOrder(amountOfCakes: Int):Single<List<Cake>>
    {
        val baker = Baker()
        return Observable.range(0, amountOfCakes).flatMapSingle {
            baker.singleBake()
        }.toList()
    }

    fun bake(): Cake
    {
        for (i in 0..1_000_000)
        {
            BigInteger.ONE.modPow(BigInteger.TEN, BigInteger.TEN)
        }

        return Cake()
    }

    fun singleBake():Single<Cake>
    {
        return Single.fromCallable{ bake()}.subscribeOn(Schedulers.computation())
    }
}

class Cake {}