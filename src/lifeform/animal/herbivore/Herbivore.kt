package lifeform.animal.herbivore

import lifeform.animal.Animal

abstract class Herbivore(
    weight: Double,
    step: Int,
    maxHp: Double,
    maxPopulation: Int,
    name: String
) : Animal(weight, step, maxHp, maxPopulation, name)

//{
//    override fun getChanceToEat(foodName: String): Double = when (foodName) {
//        "Растения" -> 1.0
//        else -> 0.0
//    }
//}