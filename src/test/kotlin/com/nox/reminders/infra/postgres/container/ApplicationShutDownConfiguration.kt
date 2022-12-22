package com.nox.reminders.infra.postgres.container

import javax.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration

@TestConfiguration
class ApplicationShutDownConfiguration {

    @Autowired
    private lateinit var testContainer: PostgresContainer

    @PreDestroy
    fun onShutDown() {
        testContainer.stop()
    }
}
