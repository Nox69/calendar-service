package com.nox.reminders.usecases.reminders.find

import com.nox.reminders.domain.reminders.RemindersRepository
import java.util.UUID
import org.springframework.stereotype.Service

/**
 * This class is a use case responsible for find all reminders of a specific employee.
 */
@Service
class FindRemindersUseCase(
    private val remindersSettingsRepository: RemindersRepository
) {

    /**
     * This method is invoked by the controller.
     */
    fun findAll(employeeId: UUID) = remindersSettingsRepository.findAll(employeeId)
}
