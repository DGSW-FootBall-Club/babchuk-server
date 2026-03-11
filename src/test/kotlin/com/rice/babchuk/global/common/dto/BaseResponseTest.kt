package com.rice.babchuk.global.common.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class BaseResponseTest {

    @Test
    fun `ok 응답은 200 상태와 데이터를 포함한다`() {
        val response = `BaseResponse.kt`.ok(data = "token", message = "로그인 성공")

        assertEquals(200, response.statusCode.value())
        assertEquals(200, response.body?.status)
        assertEquals("로그인 성공", response.body?.message)
        assertEquals("token", response.body?.data)
    }

    @Test
    fun `created 응답은 201 상태와 null 데이터를 포함한다`() {
        val response = `BaseResponse.kt`.created("회원가입 완료")

        assertEquals(201, response.statusCode.value())
        assertEquals(201, response.body?.status)
        assertEquals("회원가입 완료", response.body?.message)
        assertNull(response.body?.data)
    }

    @Test
    fun `error 응답은 전달한 상태와 메시지를 포함한다`() {
        val response = `BaseResponse.kt`.error(message = "유효하지 않은 요청", status = 400)

        assertEquals(400, response.statusCode.value())
        assertEquals(400, response.body?.status)
        assertEquals("유효하지 않은 요청", response.body?.message)
        assertNull(response.body?.data)
    }
}

