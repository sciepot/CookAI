package com.sciepot.cookai.measurement

enum class MUnit(val show: String) {
    ML("mL"), DL("dL"), L("L"), MG("mg"), G("g"), KG("kg"), TSP("tsp"), TBSP("tbsp"),
    FLOZ("fl oz"), OZ("oz"), LB("lb"), GILL("gill"), CUP("cup"), PINT("pint"),
    QUART("quart"), GALLON("gallon"), DROP("drop"), PINCH("pinch"), DASH("dash");
    companion object {
        val size = entries.size
        private val unitMap = (0 until entries.size).associateWith{ key -> entries[key] }
        operator fun get(index: Int): MUnit? {
            return unitMap[index]
        }
    }
}