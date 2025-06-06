package lifeform

abstract class LifeForm(
    val weight: Double,
    val maxPopulation: Int,
    val name: String
) {
    var row: Int = 0
        protected set

    var column: Int = 0
        protected set

    fun setPosition(row: Int, column: Int) {
        this.row = row
        this.column = column
    }
}
