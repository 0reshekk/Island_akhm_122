package simulation.thread.animalLifecycleTask.task

import field.Island
import field.Location
import lifeform.LifeForm
import lifeform.animal.Animal
import lifeform.plant.Plant
import simulation.IslandSimulation
import simulation.thread.DisplayStatisticsTask
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class AnimalEatTask(private val latch: CountDownLatch) : Runnable {
    var animalsEaten: Int = 0
        private set

    override fun run() {
        animalsEaten = 0
        val animals = Island.getInstance().getAllAnimals().filterNotNull().filter { it.isAlive }.toMutableList()
        val lifeFormsEaten = mutableSetOf<LifeForm>()

        if (animals.isNotEmpty() && animals.any { it.name != "Гусеница" }) {
            val iterator = animals.iterator()

            while (iterator.hasNext()) {
                val currentAnimal = iterator.next()
                if (currentAnimal.maxHp > 0) {
                    val location: Location = Island.getInstance().getLocation(currentAnimal.row, currentAnimal.column)
                    val locationLifeForms = location.lifeForms

                    if (locationLifeForms.isNotEmpty()) {
                        for (lifeForm in locationLifeForms) {
                            if (currentAnimal.getChanceToEat(lifeForm.name) > 0 && lifeForm !in lifeFormsEaten) {
                                val isEaten = currentAnimal.eat(lifeForm)

                                if (isEaten) {
                                    when (lifeForm) {
                                        is Animal -> {
                                            if (location.getAnimals().contains(lifeForm)) {
                                                Island.getInstance().removeAnimal(lifeForm, location.row, location.column)
                                            }
                                            lifeFormsEaten.add(lifeForm)
                                            animalsEaten++
                                        }
                                        is Plant -> {
                                            if (location.getPlants().isNotEmpty()) {
                                                Island.getInstance().removePlant(lifeForm, location.row, location.column)
                                            }
                                        }
                                    }
                                }
                                break
                            }
                        }
                    }
                }
                iterator.remove()
            }
        } else if (animals.isEmpty()) {
            println("ВСЕ ЖИВОТНЫЕ УМЕРЛИ НА ${DisplayStatisticsTask.getCurrentDay()} ДЕНЬ!")
            IslandSimulation.getInstance().executorService?.shutdown()
            exitProcess(0)
        } else {
            println("В ЖИВЫХ ОСТАЛИСЬ ТОЛЬКО ГУСЕНИЦЫ НА ${DisplayStatisticsTask.getCurrentDay()} ДЕНЬ!")
            IslandSimulation.getInstance().executorService?.shutdown()
            exitProcess(0)
        }
        latch.countDown()
    }
}
