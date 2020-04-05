package dev.yoghurt1131.weatherapi.infrastructure

import dev.yoghurt1131.weatherapi.domain.City
import dev.yoghurt1131.weatherapi.domain.Temperature
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.mockito.MockitoAnnotations
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.ValueOperations
import kotlin.test.assertEquals
import kotlin.test.assertNull


object CustomRedisTemplateTest: Spek( {

    // object setup
    val connectionFactory by memoized {  mockk<RedisConnectionFactory>() }
    val valueOperations by memoized { mockk<ValueOperations<String, City>>() }
    val type by memoized { City::class.java }
    val target by memoized { spyk(CustomRedisTemplate(connectionFactory, type)) }

    // data setup
    val cityName by memoized { "Tokyo" }

    val city by memoized { City("Tokyo", emptyList(), mockk<Temperature>())}

    describe(".read()") {
        beforeEachTest {
            MockitoAnnotations.initMocks(this)
        }

        it("returns value if present") {
            every { valueOperations.get(cityName) } returns city
            every { target.opsForValue() } returns valueOperations

            val actual = target.read(cityName)

            assertEquals(city, actual);
        }

        it("returns null when value is not in Cache.") {
            every { valueOperations.get(cityName) } returns null
            every { target.opsForValue() } returns valueOperations

            val actual = target.read(cityName)

            assertNull(actual)
        }

        it("returns null when exception has occurred") {
            every { valueOperations.get(cityName) } throws RuntimeException("DUMMY")
            every { target.opsForValue() } returns valueOperations

            val actual = target.read(cityName)

            assertNull(actual)
        }
    }

})