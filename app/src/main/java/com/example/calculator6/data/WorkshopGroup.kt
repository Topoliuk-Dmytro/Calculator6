package com.example.calculator6.data

/**
 * Группа электроприемников для расчета (например, все ЕП на ШР1)
 */
data class WorkshopGroup(
    val name: String, // Например "ШР1"
    val receivers: MutableList<ElectricalReceiver> = mutableListOf()
) {
    /**
     * n - общее количество ЕП
     */
    val totalCount: Int
        get() = receivers.sumOf { it.count }
    
    /**
     * n·Рн - сумма
     */
    val totalNTimesPn: Double
        get() = receivers.sumOf { it.nTimesPn }
    
    /**
     * Кв - средний коэффициент использования
     */
    val averageKUtil: Double
        get() {
            val sum = receivers.sumOf { it.nTimesPnTimesKv }
            return if (totalNTimesPn > 0) sum / totalNTimesPn else 0.0
        }
    
    /**
     * nе - эффективное количество ЕП
     */
    val effectiveCount: Double
        get() {
            val sumNPnSquared = receivers.sumOf { it.nTimesPnSquared }
            val sumNPn = totalNTimesPn
            return if (sumNPn > 0) {
                (sumNPn * sumNPn) / sumNPnSquared
            } else {
                0.0
            }
        }
    
    /**
     * Кр - расчетный коэффициент активной мощности
     */
    val calculatedKp: Double
        get() {
            val ne = effectiveCount
            return when {
                ne <= 1 -> 1.0
                ne <= 2 -> 0.9
                ne <= 4 -> 0.8
                ne <= 5 -> 0.75
                ne <= 10 -> 0.7
                ne <= 20 -> 0.65
                ne <= 50 -> 0.6
                else -> 0.55
            }
        }
    
    /**
     * Рд - расчетное активное навантаження, кВт
     */
    val calculatedActiveLoad: Double
        get() {
            val sumNPnKv = receivers.sumOf { it.nTimesPnTimesKv }
            val maxNPn = receivers.maxOfOrNull { it.nTimesPn } ?: 0.0
            val kp = calculatedKp
            return kp * sumNPnKv + maxNPn * (1 - kp)
        }
    
    /**
     * Qp - расчетное реактивное навантаження, кВАр
     */
    val calculatedReactiveLoad: Double
        get() {
            val sumNPnKvTgPhi = receivers.sumOf { it.nTimesPnTimesKvTimesTgPhi }
            val maxNPnKvTgPhi = receivers.maxOfOrNull { it.nTimesPnTimesKvTimesTgPhi } ?: 0.0
            val kp = calculatedKp
            return kp * sumNPnKvTgPhi + maxNPnKvTgPhi * (1 - kp)
        }
    
    /**
     * Sд - повна потужність, кВ·А
     */
    val apparentPower: Double
        get() = Math.sqrt(
            calculatedActiveLoad * calculatedActiveLoad +
            calculatedReactiveLoad * calculatedReactiveLoad
        )
    
    /**
     * Ід - розрахунковий груповий струм, А
     */
    val calculatedCurrent: Double
        get() {
            val avgVoltage = receivers.map { it.voltage }.average()
            return if (avgVoltage > 0 && apparentPower > 0) {
                apparentPower * 1000 / (Math.sqrt(3.0) * avgVoltage * 1000)
            } else {
                0.0
            }
        }
}

