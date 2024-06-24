package com.clastic.droppoint

import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.full.functions

class DropPointMapViewModelTest {
    private lateinit var dropPointRepository: KClass<MockDropPointRepository>

    @Before
    fun setUp() {
        dropPointRepository = MockDropPointRepository::class
    }

    @Test
    fun `test if getDropPointList() exists`() {
        val method = dropPointRepository.functions.find { it.name == "getDropPointList" }
        assertNotNull("Method getDropPointList should exist", method)
    }

    @Test
    fun `test if getDropPointByOwnerId() exists`() {
        val method = dropPointRepository.functions.find { it.name == "getDropPointByOwnerId" }
        assertNotNull("Method getDropPointByOwnerId should exist", method)
    }

    @Test
    fun `test if getDropPointById() exists`() {
        val method = dropPointRepository.functions.find { it.name == "getDropPointById" }
        assertNotNull("Method getDropPointById should exist", method)
    }

    @Test
    fun `test if fetchArticles() does not exists`() {
        val method = dropPointRepository.functions.find { it.name == "fetchArticles" }
        assertNull("Method fetchArticles should not exist", method)
    }
}