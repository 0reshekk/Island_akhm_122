package lifeform.animal.herbivore

import field.Island
import lifeform.animal.Animal

class Rabbit : Herbivore(
    weight = 2.0,
    step = 2,
    maxHp = 0.45,
    maxPopulation = 150,
    name = "Кролик"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Растения" -> 1.0
        "Гусеница" -> 0.4
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Rabbit) {
            val island = Island.getInstance()
            val currentPopulation = island.getAllAnimals().count { it.name == "Кролик" && it.isAlive }
            if (currentPopulation < maxPopulation) {
                val location = island.getLocation(partner.row, partner.column)
                island.addAnimal(Rabbit(), location.row, location.column)
            }
        }
    }
}
