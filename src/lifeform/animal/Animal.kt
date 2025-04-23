package lifeform.animal

import field.Island
import lifeform.LifeForm
import lifeform.plant.Plant
import java.util.*
import java.util.concurrent.ThreadLocalRandom

abstract class Animal(
    weight: Double,
    val step: Int,
    val maxHp: Double,
    maxPopulation: Int,
    name: String
) : LifeForm(weight, maxPopulation, name) {

    var hp: Double = maxHp

    val isAlive: Boolean
        get() = hp > 0

    // может ли животное съесть пищу
    fun eat(food: Any): Boolean {
        val lifeForm = food as? LifeForm ?: run {
            println("Объект не является животным/растением.")
            return false
        }

        val foodName = lifeForm.name
        val chanceToEat = getChanceToEat(foodName)

        val animalEatFood = ThreadLocalRandom.current().nextDouble() < chanceToEat
        if (animalEatFood) {
            hp = (hp + lifeForm.weight).coerceAtMost(maxHp) // Увеличиваем здоровье

            val location = Island.getInstance().getLocation(lifeForm.row, lifeForm.column)

            when (lifeForm) {
                is Animal -> {
                    if (location.getAnimals().contains(lifeForm)) {
                        Island.getInstance().removeAnimal(lifeForm, location.row, location.column)
                    } else {
                        return false
                    }
                }
                is Plant -> {
                    if (location.getPlants().isNotEmpty()) {
                        Island.getInstance().removePlant(lifeForm, location.row, location.column)
                    } else {
                        return false
                    }
                }
            }
        }
        return animalEatFood
    }

    abstract fun getChanceToEat(foodName: String): Double
    abstract fun multiply(partner: Animal)

    fun move() {
        val island = Island.getInstance()
        val random = Random()

        var newRow: Int
        var newColumn: Int

        do {
            val randomCells = random.nextInt(step) + 1
            val direction = random.nextInt(4)

            newRow = row
            newColumn = column

            when (direction) {
                0 -> newRow -= randomCells // Вверх
                1 -> newRow += randomCells // Вниз
                2 -> newColumn -= randomCells // Влево
                3 -> newColumn += randomCells // Вправо
            }
        } while (newRow !in 0 until island.getNumRows() || newColumn !in 0 until island.getNumColumns())

        island.removeAnimal(this, row, column)

        row = newRow
        column = newColumn

        island.addAnimal(this, newRow, newColumn)
    }
}
