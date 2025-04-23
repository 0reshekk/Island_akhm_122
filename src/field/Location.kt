package field

import lifeform.animal.Animal
import lifeform.LifeForm
import lifeform.plant.Plant

class Location(val row: Int, val column: Int) {
    private val animals: MutableList<Animal> = mutableListOf()
    private val plants: MutableList<Plant> = mutableListOf()

    val lifeForms: List<LifeForm>
        get() = animals + plants

    fun addAnimal(animal: Animal) {
        animals.add(animal)
    }

    fun removeAnimal(animal: Animal) {
        animals.remove(animal)
    }

    fun addPlant(plant: Plant) {
        plants.add(plant)
    }

    fun removePlant(plant: Plant) {
        plants.remove(plant)
    }

    fun getAnimals(): List<Animal> {
        return animals.toList()
    }

    fun getPlants(): List<Plant> {
        return plants.toList()
    }
}
