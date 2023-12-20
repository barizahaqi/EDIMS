package com.bangkit.edims.core.utils

import java.util.concurrent.Executors

const val NOTIFICATION_CHANNEL_NAME = "EDIMS Channel"
const val NOTIFICATION_CHANNEL_ID = "notify-schedule"
const val NOTIFICATION_ID = 51
const val ID_REPEATING = 140

private val SINGLE_EXECUTOR = Executors.newSingleThreadExecutor()

fun executeThread(f: () -> Unit) {
    SINGLE_EXECUTOR.execute(f)
}