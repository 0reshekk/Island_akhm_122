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
    var hasEaten: Boolean = false

    val isAlive: Boolean
        get() = hp > 0

    fun eat(food: LifeForm): Boolean {
        val chanceToEat = getChanceToEat(food.name)
        val success = ThreadLocalRandom.current().nextDouble() < chanceToEat
        if (success) {
            hp = (hp + food.weight).coerceAtMost(maxHp)
            val location = Island.getInstance().getLocation(food.row, food.column)
            when (food) {
                is Animal -> Island.getInstance().removeAnimal(food, location.row, location.column)
                is Plant -> Island.getInstance().removePlant(food, location.row, location.column)
            }
            hasEaten = true
        }
        return success
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
        } while (newRow !in 0..<island.getNumRows() || newColumn !in 0..<island.getNumColumns())

        island.removeAnimal(this, row, column)

        row = newRow
        column = newColumn

        island.addAnimal(this, newRow, newColumn)
    }

}
