package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Sheep : Herbivore(
    weight = 70.0,
    step = 3,
    maxHp = 5.0,
    maxPopulation = 140,
    name = "Овца"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Растения" -> 0.9
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Sheep) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Sheep(), location.row, location.column)
        }
    }
}
