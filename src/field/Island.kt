package field

import lifeform.animal.Animal
import lifeform.plant.Plant

class Island private constructor() {

    private lateinit var locations: Array<Array<Location>>
    private val numRows = 10
    private val numColumns = 4

    companion object {
        @Volatile
        private var instance: Island? = null

        fun getInstance(): Island {
            return instance ?: synchronized(this) {
                instance ?: Island().also { instance = it }
            }
        }
    }

    fun initializeLocations(numRows: Int, numColumns: Int) {
        locations = Array(numRows) { row ->
            Array(numColumns) { col ->
                Location(row, col)
            }
        }
    }

    fun initializeLocations() {
        initializeLocations(numRows, numColumns)
    }

    @Synchronized
    fun getLocation(row: Int, column: Int): Location {
        return locations[row][column]
    }

    fun addAnimal(animal: Animal, row: Int, column: Int) {
        getLocation(row, column).addAnimal(animal)
    }

    fun removeAnimal(animal: Animal, row: Int, column: Int) {
        getLocation(row, column).removeAnimal(animal)
    }

    fun addPlant(plant: Plant, row: Int, column: Int) {
        getLocation(row, column).addPlant(plant)
    }

    fun removePlant(plant: Plant, row: Int, column: Int) {
        getLocation(row, column).removePlant(plant)
    }

    @Synchronized
    fun getAllAnimals(): List<Animal> {
        return locations.flatMap { row -> row.flatMap { it.getAnimals() } }
    }

    fun getAllPlants(): List<Plant> {
        return locations.flatMap { row -> row.flatMap { it.getPlants() } }
    }

    fun getNumRows() = numRows

    fun getNumColumns() = numColumns
}
