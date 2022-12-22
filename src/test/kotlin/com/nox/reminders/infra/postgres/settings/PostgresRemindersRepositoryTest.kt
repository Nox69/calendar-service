package com.nox.reminders.infra.postgres.settings

import com.nox.reminders.domain.occurrences.Occurrence
import com.nox.reminders.domain.reminders.Reminder
import com.nox.reminders.domain.reminders.RemindersRepository
import com.nox.reminders.infra.postgres.PostgresRemindersRepository
import com.nox.reminders.infra.postgres.container.PostgresSpringTest
import com.nox.reminders.infra.postgres.container.TestDatabase
import java.time.Instant
import org.jooq.DSLContext
import org.jooq.generated.Tables.OCCURRENCES
import org.jooq.generated.Tables.REMINDERS
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

/**
 * This class configures the postgres repository to be used in the tests defined in the RemindersSettingsRepositoryContractTest class.
 */
@ActiveProfiles("test-db")
@PostgresSpringTest
@SpringBootTest
internal class PostgresRemindersRepositoryTest : RemindersRepositoryContractTest {
    @Autowired
    private lateinit var database: TestDatabase

    @Autowired
    private lateinit var dslContext: DSLContext

    @AfterEach
    fun tearDown() {
        database.clearTable(OCCURRENCES.name)
        database.clearTable(REMINDERS.name)
    }

    override fun subjectWithData(
        existingReminders: Collection<Reminder>
    ): RemindersRepository {
        insertReminders(existingReminders)

        return PostgresRemindersRepository(dslContext)
    }

    private fun insertReminders(existingReminders: Collection<Reminder>) {
        database.provisionTable(
            tableName = REMINDERS.name,
            rows = existingReminders.map { it.toDatabaseRow() }
        )
    }

    private fun insertOccurrences(existingOccurrences: Collection<Occurrence>) {
        database.provisionTable(
            tableName = OCCURRENCES.name,
            rows = existingOccurrences.map { it.toDatabaseRow() }
        )
    }

    private fun Reminder.toDatabaseRow() = mapOf<String, Any?>(
        REMINDERS.ID.name to this.id,
        REMINDERS.EMPLOYEE_ID.name to this.employeeId,
        REMINDERS.TEXT.name to this.text,
        REMINDERS.TIMESTAMP.name to Instant.parse(this.date),
        REMINDERS.IS_RECURRING.name to this.isRecurring,
        REMINDERS.RECURRENCE_INTERVAL.name to this.recurringInterval,
        REMINDERS.RECURRENCE_FREQUENCY.name to this.recurringFrequency
    )

    private fun Occurrence.toDatabaseRow() = mapOf<String, Any?>(
        OCCURRENCES.ID.name to this.id,
        OCCURRENCES.REMINDER_ID.name to this.reminder.id,
        OCCURRENCES.TIMESTAMP.name to Instant.parse(this.date),
        OCCURRENCES.IS_ACKNOWLEDGED.name to this.isAcknowledged
    )
}
