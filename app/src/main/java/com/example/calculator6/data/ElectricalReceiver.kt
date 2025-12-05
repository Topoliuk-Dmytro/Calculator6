package com.example.calculator6.data

/**
 * Модель электроприемника (ЕП) с полными параметрами для таблицы
 */
data class ElectricalReceiver(
    var name: String = "",
    var efficiency: Double = 0.85, // η - КПД
    var cosPhi: Double = 0.8, // cos φ
    var voltage: Double = 0.38, // U, кВ
    var count: Int = 1, // n, шт
    var nominalPower: Double = 0.0, // Рн, кВт
    var kUtil: Double = 0.7 // Кв - коэффициент использования
) {
    /**
     * tg φ - вычисляется из cos φ
     */
    val tgPhi: Double
        get() {
            val sinPhi = Math.sqrt(1 - cosPhi * cosPhi)
            return if (cosPhi > 0) sinPhi / cosPhi else 0.0
        }
    
    /**
     * n·Рн, кВт
     */
    val nTimesPn: Double
        get() = count * nominalPower
    
    /**
     * n·Рн·Кв, кВт
     */
    val nTimesPnTimesKv: Double
        get() = nTimesPn * kUtil
    
    /**
     * n·Рн·Кв·tgφ, кВАр
     */
    val nTimesPnTimesKvTimesTgPhi: Double
        get() = nTimesPnTimesKv * tgPhi
    
    /**
     * n·Рн²
     */
    val nTimesPnSquared: Double
        get() = count * nominalPower * nominalPower
}
