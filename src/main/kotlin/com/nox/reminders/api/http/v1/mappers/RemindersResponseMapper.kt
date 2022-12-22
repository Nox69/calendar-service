package com.nox.reminders.api.http.v1.mappers

import com.nox.reminders.api.http.v1.responses.RemindersResponse
import com.nox.reminders.domain.reminders.Reminder

class RemindersResponseMapper {
    companion object {
        fun toResponse(reminder: Reminder) =
            RemindersResponse(
                reminder.id.toString(),
                reminder.text,
                reminder.date,
                reminder.isRecurring,
                reminder.recurringInterval,
                reminder.recurringFrequency
            )
    }
}
