package simulation.thread.animalLifecycleTask.task

import field.Island
import lifeform.animal.Animal
import java.util.concurrent.CountDownLatch

class AnimalMultiplyTask(private val latch: CountDownLatch) : Runnable {
    private var babies: Int = 0

    override fun run() {
        babies = 0
        val animals = Island.getInstance()
            .getAllAnimals()
            .filter { it.isAlive && it.hasEaten }

        val multiplied = mutableSetOf<Animal>()

        for (animal in animals) {
            if (animal !in multiplied) {
                val location = Island.getInstance().getLocation(animal.row, animal.column)
                val partners = location.getAnimals()
                    .filter { it.name == animal.name && it != animal && it.hasEaten }

                if (partners.isNotEmpty()) {
                    val partner = partners[0]
                    if (partner !in multiplied) {
                        animal.multiply(partner)
                        multiplied.add(animal)
                        multiplied.add(partner)
                        babies++
                    }
                }
            }
        }

        multiplied.forEach { it.hasEaten = false }

        latch.countDown()
    }
}
