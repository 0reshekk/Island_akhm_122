package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Duck : Herbivore(
    weight = 1.0,
    step = 4,
    maxHp = 2.0,
    maxPopulation = 200,
    name = "Утка"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Гусеница" -> 0.9
        "Растения" -> 0.8
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Duck) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Duck(), location.row, location.column)
        }
    }
}
