package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Buffalo : Herbivore(
    weight = 700.0,
    step = 2,
    maxHp = 15.0,
    maxPopulation = 20,
    name = "Буйвол"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Растения" -> 0.8
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Buffalo) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Buffalo(), location.row, location.column)
        }
    }
}
