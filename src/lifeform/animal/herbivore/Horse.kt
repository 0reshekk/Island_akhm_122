package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Horse : Herbivore(
    weight = 400.0,
    step = 4,
    maxHp = 10.0,
    maxPopulation = 20,
    name = "Лошадь"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Растения" -> 0.8
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Horse) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Horse(), location.row, location.column)
        }
    }
}
