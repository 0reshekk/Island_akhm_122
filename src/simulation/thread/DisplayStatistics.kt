package simulation.thread

import field.Island
import lifeform.animal.Animal
import simulation.IslandSimulation
import kotlin.system.exitProcess

class DisplayStatisticsTask : Runnable {
    @Volatile
    private var isTimeOver: Boolean = false

    companion object {
        @Volatile
        private var currentDay: Int = 1  // День начинается с 1

        fun getCurrentDay(): Int = currentDay
    }

    override fun run() {
        try {
            val timeNow = IslandSimulation.getInstance().getTimeNow()
            isTimeOver = checkTime(timeNow)

            printStats()

            if (isTimeOver) {
                println("\n----------------------------------\nКонец симуляции острова...\n----------------------------------\n")
                IslandSimulation.getInstance().executorService?.shutdown()
                exitProcess(0)
            }

            currentDay++
        } catch (e: Exception) {
            println("Ошибка в DisplayStatisticsTask: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun checkTime(timeNow: Long): Boolean {
        return timeNow / 60 >= 5
    }

    private fun printStats() {
        val island = Island.getInstance()
        val animals = island.getAllAnimals()
        val plantsCount = island.getAllPlants().size

        val aliveAnimals = animals.filter { it.isAlive }

        val animalOrder = listOf(
            "Медведь",
            "Волк",
            "Лиса",
            "Орел",
            "Удав",
            "Кабан",
            "Утка",
            "Мышь",
            "Лошадь",
            "Олень",
            "Кролик",
            "Коза",
            "Овца",
            "Буйвол",
            "Гусеница"
        )

        val groupedAnimals = aliveAnimals.groupBy { it.name }

        val statsAnimals = animalOrder.mapNotNull { name ->
            val count = groupedAnimals[name]?.size ?: 0
            if (count > 0) "$name: $count" else null
        }

        val emojiGrid = buildEmojiGrid(animals, plantsCount, 60)

        val stats = """
        |==================== СТАТИСТИКА: ДЕНЬ $currentDay ====================
        |${statsAnimals.joinToString("\n")}
        |
        |Животных всего: ${aliveAnimals.count()}
        |Растений всего: $plantsCount
        |==============================================================
        |
        |$emojiGrid
        |
    """.trimMargin()

        println(stats)
    }

    private fun buildEmojiGrid(animals: List<Animal>, plantsCount: Int, rowLength: Int = 60): String {
        val plantEmoji = "🌱"
        val animalEmojiMap = mapOf(
            "Кабан" to "🐗",
            "Буйвол" to "🐃",
            "Гусеница" to "🐛",
            "Олень" to "🦌",
            "Утка" to "🦆",
            "Коза" to "🐐",
            "Лошадь" to "🐎",
            "Мышь" to "🐭",
            "Кролик" to "🐇",
            "Овца" to "🐑",
            "Медведь" to "🐻",
            "Орел" to "🦅",
            "Лиса" to "🦊",
            "Удав" to "🐍",
            "Волк" to "🐺"
        )

        val plantEmojis = List(plantsCount.coerceAtMost(100)) { plantEmoji }
        val aliveAnimals = animals.filter { it.isAlive }
        val animalEmojis = aliveAnimals.map { animalEmojiMap[it.name] ?: " " }
        val allEmojis = plantEmojis + animalEmojis

        // Разбиваем список эмодзи на строки по rowLength (ширина строки)
        val lines = allEmojis.chunked(rowLength)
        // Формируем итоговый текст — каждая строка эмодзи без разделителей между ними
        return lines.joinToString(separator = "\n") { it.joinToString(separator = "") }
    }

}
