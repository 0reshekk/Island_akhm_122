package lifeform.animal.predator

import field.Island
import lifeform.animal.Animal

class Bear : Predator(
    weight = 500.0,
    step = 2,
    maxHp = 12.0,
    maxPopulation = 5,
    name = "Медведь"
) {
    override fun getChanceToEat(foodName: String): Double = when (foodName) {
        "Удав" -> 0.8
        "Лошадь" -> 0.4
        "Олень" -> 0.8
        "Кролик" -> 0.8
        "Мышь" -> 0.9
        "Коза" -> 0.7
        "Овца" -> 0.7
        "Кабан" -> 0.5
        "Буйвол" -> 0.2
        "Утка" -> 0.1
        else -> 0.0
    }

    override fun multiply(partner: Animal) {
        if (partner is Bear) {
            val location = Island.getInstance().getLocation(partner.row, partner.column)
            Island.getInstance().addAnimal(Bear(), location.row, location.column)
        }
    }
}