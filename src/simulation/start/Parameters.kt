package simulation.start

import field.Island
import simulation.IslandSimulation
import java.util.Scanner

class Parameters {

    fun changeParameters() {
        changeIslandSize()
        var countPredators = changePredatorsSize()
        var countHerbivores = changeHerbivoresSize()
        var countPlants = changePlantsSize()

        if (countHerbivores == 0) {
            countHerbivores = IslandSimulation.getInstance().getCountHerbivores()
        }
        if (countPredators == 0) {
            countPredators = IslandSimulation.getInstance().getCountPredators()
        }
        if (countPlants == 0) {
            countPlants = IslandSimulation.getInstance().getCountPlants()
        }

        IslandSimulation.getInstance().createIslandModel(countHerbivores, countPredators, countPlants)
    }

    private fun changeIslandSize() {
        println("Хотите ли вы изменить размер острова (10x4)?")
        println("1. Да\n2. Нет\nВведите номер режима: ")
        val answer = takeInt(1, 2)
        if (answer == 1) {
            println("Введите желаемый размер острова\nКоличество строк:")
            val rows = takeInt(1, 500)
            println("Количество столбцов:")
            val columns = takeInt(1, 500)
            Island.getInstance().initializeLocations(rows, columns)
        } else {
            Island.getInstance().initializeLocations()
        }
    }

    private fun changePredatorsSize(): Int {
        println("Хотите ли вы изменить количество хищников (25)?")
        println("1. Да\n2. Нет\nВведите номер режима: ")
        var countPredators = 0
        val answer = takeInt(1, 2)
        if (answer == 1) {
            println("Введите желаемое количество хищников от 5 до 500!\nКоличество хищников:")
            countPredators = takeInt(5, 500)
        }
        return countPredators
    }

    private fun changeHerbivoresSize(): Int {
        println("Хотите ли вы изменить количество травоядных (30)?")
        println("1. Да\n2. Нет\nВведите номер режима: ")
        var countHerbivores = 0
        val answer = takeInt(1, 2)
        if (answer == 1) {
            println("Введите желаемое количество травоядных от 10 до 500!\nКоличество травоядных:")
            countHerbivores = takeInt(10, 500)
        }
        return countHerbivores
    }

    private fun changePlantsSize(): Int {
        println("Хотите ли вы изменить количество растений (10)?")
        println("1. Да\n2. Нет\nВведите номер режима: ")
        var countPlants = 0
        val answer = takeInt(1, 2)
        if (answer == 1) {
            println("Введите желаемое количество растений от 1 до 500!\nКоличество растений:")
            countPlants = takeInt(1, 500)
        }
        return countPlants
    }

    fun takeInt(lowNum: Int, highNum: Int): Int {
        val scanner = Scanner(System.`in`)
        while (true) {
            if (scanner.hasNextInt()) {
                val number = scanner.nextInt()
                if (number in lowNum..highNum) {
                    return number
                } else {
                    println("Ошибка! Введенное число не находится в заданном диапазоне. Попробуйте еще раз")
                }
            } else {
                scanner.next()
                println("Ошибка! Введено некорректное значение. Попробуйте еще раз")
            }
        }
    }
}
