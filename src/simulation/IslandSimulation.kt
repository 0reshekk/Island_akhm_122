package simulation

import field.Island
import lifeform.animal.herbivore.*
import lifeform.animal.predator.*
import lifeform.plant.Plant
import simulation.thread.DisplayStatisticsTask
import simulation.thread.PlantGrowthTask
import simulation.thread.animalLifecycleTask.AnimalLifecycleTask
import java.util.*
import java.util.concurrent.*
import kotlin.collections.ArrayList

class IslandSimulation private constructor() {
    private val startTime: Long
    private val countHerbivores = 10
    private val countPlants = 15
    private val countPredators = 5

    @Volatile
    var executorService: ScheduledExecutorService? = null
        private set

    init {
        startTime = System.currentTimeMillis()
    }

    private fun runIslandModel() {
        executorService = Executors.newScheduledThreadPool(3)

        val animalLifecycleTask = AnimalLifecycleTask()
        val plantGrowthTask = PlantGrowthTask()
        val statisticsTask = DisplayStatisticsTask()

        executorService?.scheduleAtFixedRate(animalLifecycleTask, 1, 4, TimeUnit.SECONDS)
        executorService?.scheduleAtFixedRate(plantGrowthTask, 20, 15, TimeUnit.SECONDS)
        executorService?.scheduleAtFixedRate(statisticsTask, 0, 4, TimeUnit.SECONDS)
    }

    private fun createHerbivores(countHerbivores: Int): List<Herbivore> {
        val herbivores: MutableList<Herbivore> = ArrayList()
        val random = Random()

        // Создаем по одному животному каждого вида
        herbivores.add(Buffalo())
        herbivores.add(Caterpillar())
        herbivores.add(Deer())
        herbivores.add(Duck())
        herbivores.add(Goat())
        herbivores.add(Horse())
        herbivores.add(Mouse())
        herbivores.add(Rabbit())
        herbivores.add(Sheep())
        herbivores.add(Boar())

        // Генерируем случайное количество животных каждого вида, не менее 1
        val remainingCount = countHerbivores - herbivores.size
        for (i in 0 until remainingCount) {
            val randomIndex = random.nextInt(herbivores.size)
            val randomHerbivore = herbivores[randomIndex]
            try {
                val newHerbivore = randomHerbivore.javaClass.getDeclaredConstructor().newInstance() as Herbivore
                herbivores.add(newHerbivore)
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return herbivores
    }

    private fun createPredators(countPredators: Int): List<Predator> {
        val predators: MutableList<Predator> = ArrayList()
        val random = Random()

        // Создаем по одному животному каждого вида
        predators.add(Bear())
        predators.add(Eagle())
        predators.add(Fox())
        predators.add(Snake())
        predators.add(Wolf())

        // Генерируем случайное количество животных каждого вида, не менее 1
        val remainingCount = countPredators - predators.size
        for (i in 0 until remainingCount) {
            val randomIndex = random.nextInt(predators.size)
            val randomPredator = predators[randomIndex]
            try {
                val newPredator = randomPredator.javaClass.getDeclaredConstructor().newInstance() as Predator
                predators.add(newPredator)
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return predators
    }

    private fun createPlants(countPlants: Int): List<Plant> {
        val plants: MutableList<Plant> = ArrayList()
        for (i in 0 until countPlants) {
            plants.add(Plant())
        }
        return plants
    }


    fun placeHerbivores(countHerbivores: Int) {
        val herbivores = createHerbivores(countHerbivores)
        val random = ThreadLocalRandom.current()
        for (herbivore in herbivores) {
            var placed = false
            while (!placed) {
                val row = random.nextInt(Island.getInstance().getNumRows())
                val column = random.nextInt(Island.getInstance().getNumColumns())
                val location = Island.getInstance().getLocation(row, column)
                if (location.getAnimals().stream().filter { c -> c.name == herbivore.name }.toList().size <= herbivore.maxPopulation) {
                    Island.getInstance().addAnimal(herbivore, row, column)
                    placed = true
                }
            }
        }
    }

    fun placePredators(countPredators: Int) {
        val predators = createPredators(countPredators)
        val random = ThreadLocalRandom.current()
        for (predator in predators) {
            var placed = false
            while (!placed) {
                val row = random.nextInt(Island.getInstance().getNumRows())
                val column = random.nextInt(Island.getInstance().getNumColumns())
                val location = Island.getInstance().getLocation(row, column)
                if (location.getAnimals().stream().filter { c -> c.name == predator.name }.toList().size <= predator.maxPopulation) {
                    Island.getInstance().addAnimal(predator, row, column)
                    placed = true
                }
            }
        }
    }

    fun placePlants(countPlants: Int) {
        val plants = createPlants(countPlants)
        val random = ThreadLocalRandom.current()
        for (plant in plants) {
            var placed = false
            while (!placed) {
                val row = random.nextInt(Island.getInstance().getNumRows())
                val column = random.nextInt(Island.getInstance().getNumColumns())
                val location = Island.getInstance().getLocation(row, column)
                if (location.getPlants().size <= plant.maxPopulation) {
                    Island.getInstance().addPlant(plant, row, column)
                    placed = true
                }
            }
        }
    }


    fun getTimeNow(): Long {
        return (System.currentTimeMillis() - startTime) / 1000
    }

    fun getCountHerbivores(): Int {
        return countHerbivores
    }

    fun getCountPlants(): Int {
        return countPlants
    }

    fun getCountPredators(): Int {
        return countPredators
    }


    fun createIslandModel(countHerbivores: Int, countPredators: Int, countPlants: Int) {
        placeHerbivores(countHerbivores)
        placePredators(countPredators)
        placePlants(countPlants)

        runIslandModel()
    }

    fun createIslandModel() {
        placeHerbivores(countHerbivores)
        placePredators(countPredators)
        placePlants(countPlants)

        runIslandModel()
    }

    companion object {
        @Volatile
        private var instance: IslandSimulation? = null

        fun getInstance(): IslandSimulation {
            return instance ?: synchronized(this) {
                instance ?: IslandSimulation().also { instance = it }
            }
        }
    }
}
