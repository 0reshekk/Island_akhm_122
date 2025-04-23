package simulation.start

import field.Island
import simulation.IslandSimulation

class Menu {
    private val parameters: Parameters = Parameters()


    fun startSimulation() {
        println("----------------------------------\nХотите ли вы внести свои параметры перед началом симуляции?")
        print("1. Да\n2. Нет\nВведите номер режима: ")
        val answer = parameters.takeInt(1, 2)

        if (answer == 1) {
            parameters.changeParameters()
        } else {
            Island.getInstance().initializeLocations()
            IslandSimulation.getInstance().createIslandModel()
        }
        println("\n----------------------------------\nЗагрузка симуляции острова...\n----------------------------------\n")
    }
}
