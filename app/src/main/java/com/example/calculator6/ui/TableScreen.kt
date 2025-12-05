package com.example.calculator6.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator6.data.ElectricalReceiver
import com.example.calculator6.data.WorkshopGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreen() {
    // Инициализация с данными по умолчанию
    val defaultShR1Receivers = remember {
        mutableListOf(
            ElectricalReceiver("Шліфувальний верстат", 0.85, 0.6, 0.38, 4, 20.0, 0.15),
            ElectricalReceiver("Свердлильний верстат", 0.85, 0.6, 0.38, 2, 14.0, 0.12),
            ElectricalReceiver("Фугувальний верстат", 0.85, 0.6, 0.38, 4, 42.0, 0.15),
            ElectricalReceiver("Циркулярна пила", 0.85, 0.6, 0.38, 1, 36.0, 0.3),
            ElectricalReceiver("Прес", 0.85, 0.8, 0.38, 1, 20.0, 0.5),
            ElectricalReceiver("Полірувальний верстат", 0.85, 0.7, 0.38, 1, 40.0, 0.2),
            ElectricalReceiver("Фрезерний верстат", 0.85, 0.7, 0.38, 2, 32.0, 0.2),
            ElectricalReceiver("Вентилятор", 0.85, 0.8, 0.38, 1, 20.0, 0.65)
        )
    }
    
    val defaultShR2Receivers = remember {
        defaultShR1Receivers.map { 
            ElectricalReceiver(it.name, it.efficiency, it.cosPhi, it.voltage, it.count, it.nominalPower, it.kUtil)
        }.toMutableList()
    }
    
    val defaultShR3Receivers = remember {
        defaultShR1Receivers.map { 
            ElectricalReceiver(it.name, it.efficiency, it.cosPhi, it.voltage, it.count, it.nominalPower, it.kUtil)
        }.toMutableList()
    }
    
    val defaultLargeEPs = remember {
        mutableListOf(
            ElectricalReceiver("Зварювальний трансформатор", 0.85, 0.35, 0.38, 1, 200.0, 0.2),
            ElectricalReceiver("Сушильна шафа", 0.85, 1.0, 0.38, 1, 240.0, 0.8)
        )
    }
    
    var shR1Group by remember { mutableStateOf(WorkshopGroup("ШР1", defaultShR1Receivers)) }
    var shR2Group by remember { mutableStateOf(WorkshopGroup("ШР2", defaultShR2Receivers)) }
    var shR3Group by remember { mutableStateOf(WorkshopGroup("ШР3", defaultShR3Receivers)) }
    var largeEPs by remember { mutableStateOf(WorkshopGroup("Крупні ЕП", defaultLargeEPs)) }
    var updateKey by remember { mutableStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Text(
            text = "Таблиця розрахунку електричних навантажень",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )
        
        // Горизонтальная прокрутка для таблицы
        val horizontalScrollState = rememberScrollState()
        val verticalScrollState = rememberScrollState()
        
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .horizontalScroll(horizontalScrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(verticalScrollState)
                ) {
                // Заголовок таблицы
                TableHeader()
                
                // Строки ШР1
                shR1Group.receivers.forEachIndexed { index, receiver ->
                    key("shR1_${index}_${updateKey}") {
                        ReceiverRow(
                            receiver = receiver,
                            subdivision = "ШР1",
                            onReceiverChange = {
                                val newReceivers = shR1Group.receivers.toMutableList()
                                newReceivers[index] = it
                                shR1Group = WorkshopGroup(shR1Group.name, newReceivers)
                            },
                            onRemove = {
                                val newReceivers = shR1Group.receivers.toMutableList()
                                newReceivers.removeAt(index)
                                shR1Group = WorkshopGroup(shR1Group.name, newReceivers)
                            }
                        )
                    }
                }
                
                // Итоговая строка ШР1
                GroupTotalRow(group = shR1Group)
                
                // Строки ШР2
                shR2Group.receivers.forEachIndexed { index, receiver ->
                    key("shR2_${index}_${updateKey}") {
                        ReceiverRow(
                            receiver = receiver,
                            subdivision = "ШР2",
                            onReceiverChange = {
                                val newReceivers = shR2Group.receivers.toMutableList()
                                newReceivers[index] = it
                                shR2Group = WorkshopGroup(shR2Group.name, newReceivers)
                            },
                            onRemove = {
                                val newReceivers = shR2Group.receivers.toMutableList()
                                newReceivers.removeAt(index)
                                shR2Group = WorkshopGroup(shR2Group.name, newReceivers)
                            }
                        )
                    }
                }
                
                // Итоговая строка ШР2
                GroupTotalRow(group = shR2Group)
                
                // Строки ШР3
                shR3Group.receivers.forEachIndexed { index, receiver ->
                    key("shR3_${index}_${updateKey}") {
                        ReceiverRow(
                            receiver = receiver,
                            subdivision = "ШР3",
                            onReceiverChange = {
                                val newReceivers = shR3Group.receivers.toMutableList()
                                newReceivers[index] = it
                                shR3Group = WorkshopGroup(shR3Group.name, newReceivers)
                            },
                            onRemove = {
                                val newReceivers = shR3Group.receivers.toMutableList()
                                newReceivers.removeAt(index)
                                shR3Group = WorkshopGroup(shR3Group.name, newReceivers)
                            }
                        )
                    }
                }
                
                // Итоговая строка ШР3
                GroupTotalRow(group = shR3Group)
                
                // Крупні ЕП
                largeEPs.receivers.forEachIndexed { index, receiver ->
                    key("largeEP_${index}_${updateKey}") {
                        ReceiverRow(
                            receiver = receiver,
                            subdivision = "Крупні ЕП",
                            onReceiverChange = {
                                val newReceivers = largeEPs.receivers.toMutableList()
                                newReceivers[index] = it
                                largeEPs = WorkshopGroup(largeEPs.name, newReceivers)
                            },
                            onRemove = {
                                val newReceivers = largeEPs.receivers.toMutableList()
                                newReceivers.removeAt(index)
                                largeEPs = WorkshopGroup(largeEPs.name, newReceivers)
                            }
                        )
                    }
                }
                
                // Общая итоговая строка
                TotalRow(groups = listOf(shR1Group, shR2Group, shR3Group, largeEPs))
            }
        }
        
        // Кнопка расчета
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    // Обновляем состояние всех групп для пересчета вычисляемых значений
                    // Создаем новые экземпляры с теми же данными, чтобы триггерить пересчет
                    val newShR1Receivers = shR1Group.receivers.map { 
                        ElectricalReceiver(it.name, it.efficiency, it.cosPhi, it.voltage, it.count, it.nominalPower, it.kUtil)
                    }.toMutableList()
                    val newShR2Receivers = shR2Group.receivers.map { 
                        ElectricalReceiver(it.name, it.efficiency, it.cosPhi, it.voltage, it.count, it.nominalPower, it.kUtil)
                    }.toMutableList()
                    val newShR3Receivers = shR3Group.receivers.map { 
                        ElectricalReceiver(it.name, it.efficiency, it.cosPhi, it.voltage, it.count, it.nominalPower, it.kUtil)
                    }.toMutableList()
                    val newLargeEPsReceivers = largeEPs.receivers.map { 
                        ElectricalReceiver(it.name, it.efficiency, it.cosPhi, it.voltage, it.count, it.nominalPower, it.kUtil)
                    }.toMutableList()
                    
                    // Обновляем группы - это заставит Compose перерисовать все вычисляемые значения
                    shR1Group = WorkshopGroup(shR1Group.name, newShR1Receivers)
                    shR2Group = WorkshopGroup(shR2Group.name, newShR2Receivers)
                    shR3Group = WorkshopGroup(shR3Group.name, newShR3Receivers)
                    largeEPs = WorkshopGroup(largeEPs.name, newLargeEPsReceivers)
                    
                    // Увеличиваем ключ обновления для принудительного пересоздания компонентов
                    updateKey++
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text("Розрахувати", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .widthIn(min = 1200.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(1.dp, Color.Gray)
    ) {
        TableCell("Підрозділи", 1.2f, false, fontWeight = FontWeight.Bold)
        TableCell("Найменування ЕП", 1.8f, false, fontWeight = FontWeight.Bold)
        TableCell("η", 0.5f, false, fontWeight = FontWeight.Bold)
        TableCell("cos φ", 0.6f, false, fontWeight = FontWeight.Bold)
        TableCell("U, кВ", 0.6f, false, fontWeight = FontWeight.Bold)
        TableCell("n", 0.4f, false, fontWeight = FontWeight.Bold)
        TableCell("Рн, кВт", 0.6f, false, fontWeight = FontWeight.Bold)
        TableCell("n·Рн", 0.6f, false, fontWeight = FontWeight.Bold)
        TableCell("Кв", 0.5f, false, fontWeight = FontWeight.Bold)
        TableCell("tgφ", 0.5f, false, fontWeight = FontWeight.Bold)
        TableCell("n·Рн·Кв", 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell("n·Рн·Кв·tgφ", 0.9f, false, fontWeight = FontWeight.Bold)
        TableCell("n·Рн²", 0.6f, false, fontWeight = FontWeight.Bold)
        TableCell("nе", 0.5f, false, fontWeight = FontWeight.Bold)
        TableCell("Кр", 0.5f, false, fontWeight = FontWeight.Bold)
        TableCell("Рд, кВт", 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell("Qp, кВАр", 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell("Sд, кВ·А", 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell("Ід, А", 0.6f, false, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ReceiverRow(
    receiver: ElectricalReceiver,
    subdivision: String,
    onReceiverChange: (ElectricalReceiver) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .widthIn(min = 1200.dp)
            .border(1.dp, Color.LightGray)
    ) {
        // Підрозділи - не редактируемое
        TableCell(subdivision, 1.2f, false)
        
        // Название - редактируемое
        EditableCell(
            value = receiver.name,
            onValueChange = { onReceiverChange(receiver.copy(name = it)) },
            weight = 1.8f
        )
        
        // η - редактируемое (желтое)
        EditableCell(
            value = String.format("%.2f", receiver.efficiency),
            onValueChange = { 
                it.toDoubleOrNull()?.let { v -> 
                    onReceiverChange(receiver.copy(efficiency = v))
                }
            },
            weight = 0.5f,
            isEditable = true
        )
        
        // cos φ - редактируемое (желтое)
        EditableCell(
            value = String.format("%.2f", receiver.cosPhi),
            onValueChange = { 
                it.toDoubleOrNull()?.let { v -> 
                    onReceiverChange(receiver.copy(cosPhi = v))
                }
            },
            weight = 0.6f,
            isEditable = true
        )
        
        // U - редактируемое (желтое)
        EditableCell(
            value = String.format("%.2f", receiver.voltage),
            onValueChange = { 
                it.toDoubleOrNull()?.let { v -> 
                    onReceiverChange(receiver.copy(voltage = v))
                }
            },
            weight = 0.6f,
            isEditable = true
        )
        
        // n - редактируемое (желтое)
        EditableCell(
            value = receiver.count.toString(),
            onValueChange = { 
                it.toIntOrNull()?.let { v -> 
                    onReceiverChange(receiver.copy(count = v))
                }
            },
            weight = 0.4f,
            isEditable = true
        )
        
        // Рн - редактируемое (желтое)
        EditableCell(
            value = String.format("%.1f", receiver.nominalPower),
            onValueChange = { 
                it.toDoubleOrNull()?.let { v -> 
                    onReceiverChange(receiver.copy(nominalPower = v))
                }
            },
            weight = 0.6f,
            isEditable = true
        )
        
        // n·Рн - вычисляемое
        TableCell(String.format("%.1f", receiver.nTimesPn), 0.6f, false)
        
        // Кв - редактируемое (желтое)
        EditableCell(
            value = String.format("%.2f", receiver.kUtil),
            onValueChange = { 
                it.toDoubleOrNull()?.let { v -> 
                    onReceiverChange(receiver.copy(kUtil = v))
                }
            },
            weight = 0.5f,
            isEditable = true
        )
        
        // tgφ - вычисляемое
        TableCell(String.format("%.2f", receiver.tgPhi), 0.5f, false)
        
        // n·Рн·Кв - вычисляемое
        TableCell(String.format("%.2f", receiver.nTimesPnTimesKv), 0.7f, false)
        
        // n·Рн·Кв·tgφ - вычисляемое
        TableCell(String.format("%.2f", receiver.nTimesPnTimesKvTimesTgPhi), 0.9f, false)
        
        // n·Рн² - вычисляемое
        TableCell(String.format("%.0f", receiver.nTimesPnSquared), 0.6f, false)
        
        // nе, Кр, Рд, Qp, Sд, Ід - только для итоговых строк
        TableCell("", 0.5f, false)
        TableCell("", 0.5f, false)
        TableCell("", 0.7f, false)
        TableCell("", 0.7f, false)
        TableCell("", 0.7f, false)
        TableCell("", 0.6f, false)
        
        // Кнопка удаления
        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(28.dp)
        ) {
            Text("×", fontSize = 14.sp)
        }
    }
}

@Composable
fun GroupTotalRow(group: WorkshopGroup) {
    Row(
        modifier = Modifier
            .widthIn(min = 1200.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(2.dp, Color.DarkGray)
    ) {
        TableCell("ВСЬОГО ${group.name}", 1.2f, false, fontWeight = FontWeight.Bold)
        TableCell("", 1.8f, false)
        TableCell("", 0.5f, false)
        TableCell("", 0.6f, false)
        TableCell("", 0.6f, false)
        TableCell(group.totalCount.toString(), 0.4f, false, fontWeight = FontWeight.Bold)
        TableCell("", 0.6f, false)
        TableCell(String.format("%.0f", group.totalNTimesPn), 0.6f, false, fontWeight = FontWeight.Bold)
        TableCell(String.format("%.2f", group.averageKUtil), 0.5f, false, fontWeight = FontWeight.Bold)
        TableCell("", 0.5f, false)
        TableCell("", 0.7f, false)
        TableCell("", 0.9f, false)
        TableCell("", 0.6f, false)
        
        // Дополнительные вычисляемые колонки
        TableCell(String.format("%.1f", group.effectiveCount), 0.5f, false, fontWeight = FontWeight.Bold)
        TableCell(String.format("%.2f", group.calculatedKp), 0.5f, false, fontWeight = FontWeight.Bold)
        TableCell(String.format("%.1f", group.calculatedActiveLoad), 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell(String.format("%.0f", group.calculatedReactiveLoad), 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell(String.format("%.1f", group.apparentPower), 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell(String.format("%.1f", group.calculatedCurrent), 0.6f, false, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TotalRow(groups: List<WorkshopGroup>) {
    val totalCount = groups.sumOf { it.totalCount }
    val totalNTimesPn = groups.sumOf { it.totalNTimesPn }
    val totalActive = groups.sumOf { it.calculatedActiveLoad }
    val totalReactive = groups.sumOf { it.calculatedReactiveLoad }
    val totalApparent = Math.sqrt(totalActive * totalActive + totalReactive * totalReactive)
    val avgVoltage = groups.flatMap { it.receivers }.map { it.voltage }.average()
    val totalCurrent = if (avgVoltage > 0 && totalApparent > 0) {
        totalApparent * 1000 / (Math.sqrt(3.0) * avgVoltage * 1000)
    } else 0.0
    
    Row(
        modifier = Modifier
            .widthIn(min = 1200.dp)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .border(3.dp, Color.Black)
    ) {
        TableCell("Всього, навантаження цеху", 1.2f, false, fontWeight = FontWeight.Bold)
        TableCell("", 1.8f, false)
        TableCell("", 0.5f, false)
        TableCell("", 0.6f, false)
        TableCell("", 0.6f, false)
        TableCell(totalCount.toString(), 0.4f, false, fontWeight = FontWeight.Bold)
        TableCell("", 0.6f, false)
        TableCell(String.format("%.0f", totalNTimesPn), 0.6f, false, fontWeight = FontWeight.Bold)
        TableCell("", 0.5f, false)
        TableCell("", 0.5f, false)
        TableCell("", 0.7f, false)
        TableCell("", 0.9f, false)
        TableCell("", 0.6f, false)
        TableCell("", 0.5f, false)
        TableCell("", 0.5f, false)
        TableCell(String.format("%.0f", totalActive), 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell(String.format("%.0f", totalReactive), 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell(String.format("%.1f", totalApparent), 0.7f, false, fontWeight = FontWeight.Bold)
        TableCell(String.format("%.1f", totalCurrent), 0.6f, false, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    isEditable: Boolean,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Box(
        modifier = Modifier
            .weight(weight)
            .padding(1.dp)
            .fillMaxHeight()
            .then(
                if (isEditable) {
                    Modifier.background(Color(0xFFFFFF99)) // Желтый фон для редактируемых
                } else {
                    Modifier
                }
            )
            .border(1.dp, Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 9.sp,
            fontWeight = fontWeight,
            maxLines = 2
        )
    }
}

@Composable
fun RowScope.EditableCell(
    value: String,
    onValueChange: (String) -> Unit,
    weight: Float,
    isEditable: Boolean = true
) {
    Box(
        modifier = Modifier
            .weight(weight)
            .padding(1.dp)
            .fillMaxHeight()
            .then(
                if (isEditable) {
                    Modifier.background(Color(0xFFFFFF99)) // Желтый фон
                } else {
                    Modifier
                }
            )
            .border(1.dp, Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxSize(),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFFF99),
                unfocusedContainerColor = Color(0xFFFFFF99),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}
