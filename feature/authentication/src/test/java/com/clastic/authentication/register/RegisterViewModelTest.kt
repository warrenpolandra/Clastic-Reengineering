package com.clastic.authentication.register

import com.clastic.authentication.mock.MockUserRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import org.junit.Before
import org.junit.Test

class RegisterViewModelTest {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var mockUserRepository: MockUserRepository
    private val name = "SAMPLE_NAME"
    private val email = "SAMPLE_EMAIL"
    private val password = "SAMPLE_PASSWORD"
    private val errorMessage = "RETURN_FAIL"

    @Before
    fun setUp() {
        mockUserRepository = MockUserRepository("", true)
        viewModel = RegisterViewModel(mockUserRepository)
        viewModel.setNameInput(name)
        viewModel.setEmailInput(email)
        viewModel.setPasswordInput(password)
    }

    @Test
    fun `should return success when register with email is used`() {
        viewModel.registerWithEmail(
            onResultSuccess = { assertEquals(viewModel.authState.value.isAuthSuccessful, true) },
            onResultFailed = { fail("Test should return onResultSuccess") }
        )
    }

    @Test
    fun `should return error message when register with email is used`() {
        mockUserRepository = MockUserRepository(errorMessage, false)
        viewModel = RegisterViewModel(mockUserRepository)
        viewModel.setNameInput(name)
        viewModel.setEmailInput(email)
        viewModel.setPasswordInput(password)
        viewModel.registerWithEmail(
            onResultSuccess = { fail("Test should return onResultFailed") },
            onResultFailed = { assertEquals("RETURN_FAIL", it) }
        )
    }
}