abstract class Pet(var name: String) {

}

class Cat(name : String) : Pet(name) {

}

class Dog(name : String) : Pet(name) {

}

class Fish(name : String) : Pet(name) {

}

class Vet<T: Pet> {
    fun treat(t: Pet) {
        println("Treat pet ${t.name}")
    }
}

class Contest<T: Pet> (var vet : Vet<in T>) {
    val scores : MutableMap<T, Int> = mutableMapOf()

    fun add_score (t: T, score: Int = 0) {
        if (score >= 0) scores.put(t, score)
    }

    fun get_winner(): MutableSet<T> {
        val high_score = scores.values.maxOrNull()
        val winner: MutableSet<T> = mutableSetOf()

        for ((t, score) in scores) {
            if (score == high_score) winner.add(t)
        }
        return winner
    }
}

interface Retailer<out T> {
    fun sell() : T
}

class CatRetailer : Retailer<Cat> {
    override fun sell() : Cat {
        println("Sell Cat")
        return Cat("")
    }
}

class DogRetailer : Retailer<Dog> {
    override fun sell() : Dog {
        println("Sell Dog")
        return Dog("")
    }
}

class FishRetailer : Retailer<Fish> {
    override fun sell() : Fish {
        println("Sell Fish")
        return Fish("")
    }
}

fun main(args: Array<String>) {
    val catFuzz = Cat("Fuzz Lightyear")
    val catKatsu = Cat("Katsu")
    val fishFinny = Fish("Finny McGraw")

    val catVet = Vet<Cat>()
    val fishVet = Vet<Fish>()
    val petVet = Vet<Pet>()
    catVet.treat(catFuzz)
    petVet.treat(catKatsu)
    petVet.treat(fishFinny)

    val catContest = Contest<Cat>(catVet)
    catContest.add_score(catFuzz, 50)
    catContest.add_score(catKatsu, 45)
    val topCat = catContest.get_winner().first()
    println("Cat contest winner is ${topCat.name}")
    val petContest = Contest<Pet>(petVet)
    petContest.add_score(catFuzz, 50)
    petContest.add_score(fishFinny, 56)
    val topPet = petContest.get_winner().first()
    println("Pet contest winner is ${topPet.name}")
    val fishContest = Contest<Fish>(petVet)
    println("Generality")
    val dogRetailer: Retailer<Dog> = DogRetailer()
    val catRetailer: Retailer<Cat> = CatRetailer()
    val petRetailer: Retailer<Pet> = CatRetailer()
    petRetailer.sell()
}
