package com.nox.reminders.infra.postgres.container

import javax.sql.DataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

/**
 * Exposes PostgresContainer, DataSource and DSLContext as beans which can be used in Tests.
 * It is added implicitly as part of @PostgresSpringTest
 *
 */
@TestConfiguration
class DBTestConfiguration {
    /**
     * Adds PostgresContainer to the Spring Context
     * @return PostgresContainer
     */
    @Bean
    fun testContainer(): PostgresContainer {
        return PostgresContainer("postgres:13.1-alpine")
    }

    @Bean
    fun testDatabase(testContainer: PostgresContainer): TestDatabase {
        return testContainer.testDatabase
    }

    /**
     * Adds DataSource to the Spring Context
     * @return DataSource
     */
    @Bean
    fun dataSource(testContainer: PostgresContainer): DataSource {
        return testContainer.dataSource
    }

    /**
     * Adds DSLContext to the Spring Context
     * @return DSLContext
     */
    @Bean
    fun dslContext(dataSource: DataSource): DSLContext {
        return DefaultDSLContext(DefaultConfiguration().set(dataSource).set(SQLDialect.POSTGRES))
    }
}
