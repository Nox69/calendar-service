package com.nox.reminders.infra.postgres.settings

import com.nox.reminders.domain.reminders.Reminder
import com.nox.reminders.domain.reminders.RemindersRepository

/**
 * This class configures the in-memory repository to be used in the tests defined in the RemindersSettingsRepositoryContractTest class.
 */
internal class InMemoryRemindersRepositoryTest : RemindersRepositoryContractTest {
    override fun subjectWithData(
        existingReminders: Collection<Reminder>
    ): RemindersRepository {
        return InMemoryRemindersRepository(
            existingReminders.toMutableList()
        )
    }
}
