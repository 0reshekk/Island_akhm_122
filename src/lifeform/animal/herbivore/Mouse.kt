package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Mouse : Herbivore(
    weight = 0.05,
    step = 1,
    maxHp = 0.01,
    maxPopulation = 500,
    name = "Мышь"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Гусеница" -> 0.9
        "Растения" -> 0.9
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Mouse) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Mouse(), location.row, location.column)
        }
    }
}
