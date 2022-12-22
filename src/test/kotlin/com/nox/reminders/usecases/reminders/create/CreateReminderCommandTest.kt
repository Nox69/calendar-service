package com.nox.reminders.usecases.reminders.create

import com.nox.reminders.domain.reminders.Reminder
import com.nox.reminders.helpers.MotherObject
import java.time.Instant
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Unit tests for the CreateReminderCommand class.
 */
internal class CreateReminderCommandTest {

    @Test
    fun `should generate reminder from command`() {
        val command = CreateReminderCommand(
            employeeId = UUID.randomUUID(),
            text = "",
            date = Instant.now(MotherObject.clock).toString(),
            isRecurring = false,
            recurringInterval = null,
            recurringFrequency = null
        )

        val reminder = Reminder.fromCommand(command)

        assertEquals(command.employeeId, reminder.employeeId)
        assertEquals(command.text, reminder.text)
        assertEquals(command.date, reminder.date)
        assertEquals(command.isRecurring, reminder.isRecurring)
        assertEquals(command.recurringInterval, reminder.recurringInterval)
        assertEquals(command.recurringFrequency, reminder.recurringFrequency)
    }
}
