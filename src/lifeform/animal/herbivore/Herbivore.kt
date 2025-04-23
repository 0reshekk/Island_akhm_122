package lifeform.animal.herbivore

import lifeform.animal.Animal

abstract class Herbivore(
    weight: Double,
    step: Int,
    maxHp: Double,
    maxPopulation: Int,
    name: String
) : Animal(weight, step, maxHp, maxPopulation, name)