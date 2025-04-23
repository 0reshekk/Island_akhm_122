package simulation.thread

import simulation.IslandSimulation

class PlantGrowthTask : Runnable {
    override fun run() {
        val countPlants = 20
        val islandSimulation = IslandSimulation.getInstance()
        if (islandSimulation.getTimeNow() >= 2) {
            islandSimulation.placePlants(countPlants / 2)
        } else {
            islandSimulation.placePlants(countPlants)
        }
    }
}
