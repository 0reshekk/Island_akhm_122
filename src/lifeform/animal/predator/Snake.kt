package lifeform.animal.predator

import field.Island
import lifeform.animal.Animal

class Snake : Predator(
    weight = 15.0,
    step = 2,
    maxHp = 5.0,
    maxPopulation = 20,
    name = "Удав"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Лиса" -> 0.15
        "Кролик" -> 0.2
        "Мышь" -> 0.4
        "Утка" -> 0.1
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Snake) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Snake(), location.row, location.column)
        }
    }
}
