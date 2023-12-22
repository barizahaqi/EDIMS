package com.bangkit.edims.core.utils

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    initialDateMillis: Long,
    onDateSelected: (String) -> Unit,
    onDateMillisSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return true
            }
        },
        initialSelectedDateMillis = initialDateMillis
    )

    val selectableDateMillis = datePickerState.selectedDateMillis ?: initialDateMillis
    val selectableDate = DateConverter.convertMillisToString(selectableDateMillis)

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(selectableDate)
                    onDateMillisSelected(selectableDateMillis)
                    onDismiss()
                }
            ) {
                Text("Ok")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}