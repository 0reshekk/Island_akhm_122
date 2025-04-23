package simulation.thread.animalLifecycleTask.task

import field.Island

class AnimalMoveTask : Runnable {
    override fun run() {
        val animals = Island.getInstance()
            .getAllAnimals()
            .filter { it.isAlive && it.step > 0 }

        for (animal in animals) {
            animal.move()
        }
    }
}
