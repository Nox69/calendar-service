package com.nox.reminders.infra.postgres

import com.nox.reminders.domain.reminders.Reminder
import com.nox.reminders.domain.reminders.RemindersRepository
import java.time.Instant
import java.util.UUID
import org.jooq.DSLContext
import org.jooq.generated.tables.Occurrences.OCCURRENCES
import org.jooq.generated.tables.Reminders.REMINDERS
import org.jooq.generated.tables.records.RemindersRecord
import org.springframework.stereotype.Repository

/**
 * This is the default implementation of the reminder's occurrences repository.
 * This class implements a repository which integrates with the Postgres database.
 */
@Repository
class PostgresRemindersRepository(
    /**
     * The following properties are injected by Spring's Dependency Injection container,
     * during the instantiation of this controller.
     *
     * The `DSLContext` property is the JOOQ's (https://www.jooq.org/) abstraction that interacts with the database.
     */
    private val dslContext: DSLContext
) : RemindersRepository {

    override fun create(reminder: Reminder) {
        /*
         * The `REMINDERS` constant, and its class, are auto-generated by JOOQ.
         *
         * JOOQ generates one class per table from your database,
         * i.e. a `reminders` table will result in a `Reminders` class in JOOQ.
         *
         * These classes are then used to create compiled queries,
         * like the ones you can check in this file.
         *
         * Check the README file to learn how to execute JOOQ's code generation task.
         */
        dslContext.insertInto(
            REMINDERS
        ).columns(
            REMINDERS.ID,
            REMINDERS.EMPLOYEE_ID,
            REMINDERS.TEXT,
            REMINDERS.TIMESTAMP,
            REMINDERS.IS_RECURRING,
            REMINDERS.RECURRENCE_INTERVAL,
            REMINDERS.RECURRENCE_FREQUENCY
        ).values(
            reminder.id,
            reminder.employeeId,
            reminder.text,
            Instant.parse(reminder.date),
            reminder.isRecurring,
            reminder.recurringInterval,
            reminder.recurringFrequency
        ).execute()
    }

    override fun findAll(employeeId: UUID): Collection<Reminder> {
        val records = dslContext.selectFrom(REMINDERS)
            .where(REMINDERS.EMPLOYEE_ID.eq(employeeId))
            .fetch()

        return records.map { it.toReminder() }
    }

    override fun findBy(id: UUID): Reminder? {
        val record = dslContext.selectFrom(REMINDERS)
            .where(REMINDERS.ID.eq(id))
            .fetchOne() ?: return null

        return record.toReminder()
    }

    private fun RemindersRecord.toReminder(): Reminder {
        return Reminder(
            id = this.id,
            employeeId = this.employeeId,
            text = this.text,
            date = this.timestamp.toString(),
            isRecurring = this.isRecurring,
            recurringInterval = this.recurrenceInterval,
            recurringFrequency = this.recurrenceFrequency
        )
    }
}
