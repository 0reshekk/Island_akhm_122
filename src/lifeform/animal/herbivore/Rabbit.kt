package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Rabbit : Herbivore(
    weight = 2.0,
    step = 2,
    maxHp = 0.45,
    maxPopulation = 150,
    name = "Кролик"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Растения" -> 0.8
        "Гусеница" -> 0.5
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Rabbit) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Mouse(), location.row, location.column)
        }
    }

}
