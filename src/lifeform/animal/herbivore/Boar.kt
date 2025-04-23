package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Boar : Herbivore(
    weight = 15.0,
    step = 2,
    maxHp = 6.0,
    maxPopulation = 50,
    name = "Кабан"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Мышь" -> 0.5
        "Гусеница" -> 0.7
        "Растения" -> 0.9
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Boar) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Boar(), location.row, location.column)
        }
    }
}