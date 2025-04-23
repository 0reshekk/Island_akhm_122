package lifeform.animal.predator

import lifeform.animal.Animal

abstract class Predator(
    weight: Double,
    step: Int,
    maxHp: Double,
    maxPopulation: Int,
    name: String
) : Animal(weight, step, maxHp, maxPopulation, name)
