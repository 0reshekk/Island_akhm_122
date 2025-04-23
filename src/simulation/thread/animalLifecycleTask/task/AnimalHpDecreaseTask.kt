package simulation.thread.animalLifecycleTask.task

import field.Island
import field.Location
import lifeform.animal.Animal
import simulation.IslandSimulation
import java.util.concurrent.CountDownLatch

class AnimalHpDecreaseTask(private val latch: CountDownLatch) : Runnable {
    private var percentOfHpToDecrease = 15.0
    var animalsDiedByHungry: Int = 0
        private set

    override fun run() {
        animalsDiedByHungry = 0
        val animals: List<Animal> = Island.getInstance()
            .getAllAnimals()
            .filterNotNull()
            .filter { it.isAlive && it.maxHp > 0 }

        // Увеличиваем голод через 3 минуты симуляции
        if (IslandSimulation.getInstance().getTimeNow() / 60 >= 3) {
            percentOfHpToDecrease = 30.0 // удвоение
        }

        for (animal in animals) {
            val hpToDecrease = animal.maxHp * percentOfHpToDecrease / 100.0
            if (animal.hp - hpToDecrease > 0) {
                animal.hp -= hpToDecrease
            } else {
                val location: Location = Island.getInstance().getLocation(animal.row, animal.column)
                Island.getInstance().removeAnimal(animal, location.row, location.column)
                animalsDiedByHungry++
            }
        }
        latch.countDown()
    }
}
