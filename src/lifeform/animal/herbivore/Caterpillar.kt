package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Caterpillar : Herbivore(
    weight = 0.01,
    step = 1,
    maxHp = 0.01,
    maxPopulation = 1000,
    name = "Гусеница"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Растения" -> 1.0
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Caterpillar) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Caterpillar(), location.row, location.column)
        }
    }
}
