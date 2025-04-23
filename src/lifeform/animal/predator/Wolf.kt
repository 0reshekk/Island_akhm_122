package lifeform.animal.predator

import field.Island
import lifeform.animal.Animal

class Wolf : Predator(
    weight = 50.0,
    step = 3,
    maxHp = 8.0,
    maxPopulation = 30,
    name = "Волк"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Кролик" -> 0.6
        "Мышь" -> 0.8
        "Коза" -> 0.6
        "Овца" -> 0.7
        "Кабан" -> 0.15
        "Буйвол" -> 0.1
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Wolf) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Wolf(), location.row, location.column)
        }
    }
}