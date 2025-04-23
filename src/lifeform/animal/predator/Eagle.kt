package lifeform.animal.predator

import field.Island
import lifeform.animal.Animal

class Eagle : Predator(
    weight = 6.0,
    step = 3,
    maxHp = 3.0,
    maxPopulation = 20,
    name = "Орел"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Лиса" -> 0.1
        "Кролик" -> 0.9
        "Мышь" -> 0.9
        "Утка" -> 0.8
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Eagle) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Eagle(), location.row, location.column)
        }
    }
}
