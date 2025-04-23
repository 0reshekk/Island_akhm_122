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
        print("1. Да\n2. Нет\nВведите номер режима: ")
        val answer = takeInt(1, 2)
        if (answer == 1) {
            print("Введите желаемый размер острова\nКоличество строк от 1 до 500: ")
            val rows = takeInt(1, 500)
            print("Количество столбцов от 1 до 500: ")
            val columns = takeInt(1, 500)
            Island.getInstance().initializeLocations(rows, columns)
        } else {
            Island.getInstance().initializeLocations()
        }
    }

    private fun changePredatorsSize(): Int {
        println("Хотите ли вы изменить количество хищников (50)?")
        print("1. Да\n2. Нет\nВведите номер режима: ")
        var countPredators = 0
        val answer = takeInt(1, 2)
        if (answer == 1) {
            print("Введите желаемое количество хищников от 5 до 500!\nКоличество хищников: ")
            countPredators = takeInt(5, 500)
        }
        return countPredators
    }

    private fun changeHerbivoresSize(): Int {
        println("Хотите ли вы изменить количество травоядных (100)?")
        print("1. Да\n2. Нет\nВведите номер режима: ")
        var countHerbivores = 0
        val answer = takeInt(1, 2)
        if (answer == 1) {
            print("Введите желаемое количество травоядных от 10 до 500!\nКоличество травоядных: ")
            countHerbivores = takeInt(10, 500)
        }
        return countHerbivores
    }

    private fun changePlantsSize(): Int {
        println("Хотите ли вы изменить количество растений (250)?")
        print("1. Да\n2. Нет\nВведите номер режима: ")
        var countPlants = 0
        val answer = takeInt(1, 2)
        if (answer == 1) {
            print("Введите желаемое количество растений от 10 до 500!\nКоличество растений: ")
            countPlants = takeInt(10, 500)
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
                    print("Ошибка! Введенное число не находится в заданном диапазоне.\nВведите значение еще раз: ")
                }
            } else {
                scanner.next()
                print("Ошибка! Введено некорректное значение.\nВведите значение еще раз: ")
            }
        }
    }
}
