package simulation.thread.animalLifecycleTask.task

import field.Island
import lifeform.LifeForm
import lifeform.animal.Animal
import simulation.IslandSimulation
import simulation.thread.DisplayStatisticsTask
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class AnimalEatTask(private val latch: CountDownLatch) : Runnable {
    private var animalsEaten: Int = 0

    override fun run() {
        Island.getInstance().getAllAnimals().forEach { it.hasEaten = false }

        animalsEaten = 0
        val animals = Island.getInstance().getAllAnimals().filter { it.isAlive }.toMutableList()
        val eatenLifeForms = mutableSetOf<LifeForm>()

        if (animals.isNotEmpty()) {
            val iterator = animals.iterator()
            while (iterator.hasNext()) {
                val animal = iterator.next()
                val location = Island.getInstance().getLocation(animal.row, animal.column)
                val lifeForms = location.lifeForms.toMutableList()

                for (lifeForm in lifeForms) {
                    if (animal.getChanceToEat(lifeForm.name) > 0 && lifeForm !in eatenLifeForms) {
                        val isEaten = animal.eat(lifeForm)

                        //println("${animal.name} пытается съесть ${lifeForm.name}: ${if (isEaten) "Успешно" else "Неудачно"}")

                        if (isEaten) {
                            eatenLifeForms.add(lifeForm)
                            animalsEaten++
                            break
                        }
                    }
                }
                iterator.remove()
            }
        } else {
            println("ВСЕ ЖИВОТНЫЕ УМЕРЛИ НА ${DisplayStatisticsTask.getCurrentDay()} ДЕНЬ!")
            IslandSimulation.getInstance().executorService?.shutdown()
            exitProcess(0)
        }
        latch.countDown()
    }
}
