package simulation.thread.animalLifecycleTask.task

import field.Island
import field.Location
import lifeform.animal.Animal
import java.util.concurrent.CountDownLatch

class AnimalMultiplyTask(private val latch: CountDownLatch) : Runnable {
    var babies: Int = 0
        private set

    override fun run() {
        babies = 0
        val animals: List<Animal> = Island.getInstance()
            .getAllAnimals()
            .filterNotNull()
            .filter { it.isAlive }

        val animalsMultiplied = mutableSetOf<Animal>()

        for (currentAnimal in animals) {
            if (currentAnimal !in animalsMultiplied) {
                val location: Location = Island.getInstance().getLocation(currentAnimal.row, currentAnimal.column)
                var locationAnimals = location.getAnimals()

                if (locationAnimals.size > 1) {
                    locationAnimals = locationAnimals.filter { it.name == currentAnimal.name && it != currentAnimal }

                    if (locationAnimals.isNotEmpty()) {
                        val partner = locationAnimals[0]

                        if (partner !in animalsMultiplied) {
                            currentAnimal.multiply(partner)

                            animalsMultiplied.add(currentAnimal)
                            animalsMultiplied.add(partner)

                            babies++
                        }
                    }
                }
            }
        }
        latch.countDown()
    }
}
