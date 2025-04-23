package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Duck : Herbivore(
    weight = 1.5,
    step = 2,
    maxHp = 2.0,
    maxPopulation = 120,
    name = "Утка"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Гусеница" -> 0.9
        "Растения" -> 1.0
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Duck) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Duck(), location.row, location.column)
        }
    }
}
