package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Goat : Herbivore(
    weight = 60.0,
    step = 3,
    maxHp = 4.0,
    maxPopulation = 140,
    name = "Коза"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Растения" -> 0.9
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Goat) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Goat(), location.row, location.column)
        }
    }
}
