package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Deer : Herbivore(
    weight = 150.0,
    step = 3,
    maxHp = 10.0,
    maxPopulation = 20,
    name = "Олень"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Растения" -> 0.9
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Deer) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Deer(), location.row, location.column)
        }
    }
}
