package lifeform.animal.predator

import field.Island
import lifeform.animal.Animal

class Fox : Predator(
    weight = 8.0,
    step = 3,
    maxHp = 4.0,
    maxPopulation = 30,
    name = "Лиса"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Кролик" -> 0.7
        "Мышь" -> 0.9
        "Утка" -> 0.6
        "Гусеница" -> 0.4
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Fox) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Fox(), location.row, location.column)
        }
    }
}
