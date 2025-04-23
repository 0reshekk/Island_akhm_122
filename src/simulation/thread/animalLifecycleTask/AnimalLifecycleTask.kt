package simulation.thread.animalLifecycleTask

import simulation.thread.animalLifecycleTask.task.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class AnimalLifecycleTask : Runnable {

    private lateinit var animalEatTask: AnimalEatTask
    private lateinit var animalHpDecreaseTask: AnimalHpDecreaseTask
    private lateinit var animalMultiplyTask: AnimalMultiplyTask

    private val animalMoveTask = AnimalMoveTask()

    override fun run() {
        val latch = CountDownLatch(3)

        animalEatTask = AnimalEatTask(latch)
        animalHpDecreaseTask = AnimalHpDecreaseTask(latch)
        animalMultiplyTask = AnimalMultiplyTask(latch)

        animalEatTask.run()
        animalHpDecreaseTask.run()
        animalMultiplyTask.run()

        try {
            val completed = latch.await(10, TimeUnit.SECONDS)
            if (!completed) {
                println("Предупреждение: Не все задачи завершились!")
            }
        } catch (e: InterruptedException) {
            println("Задачи прерваны: ${e.message}")
            Thread.currentThread().interrupt()
        }

        animalMoveTask.run()
    }
}
