package com.luoyang.androidfunDemo.unittest

import android.content.Context
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 *
 *
 * @author luoyang
 * @date 2022/9/1
 */
class PatternUtilsTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun isValidEmail() {
        assertEquals(false,PatternUtils.isValidEmail("test"))
        assertEquals(true,PatternUtils.isValidEmail("testsdd@qq.com"))
    }

    @Test
    fun isValidPhoneNumber() {
        assertEquals(false,PatternUtils.isValidPhoneNumber("fdgd"))
        assertEquals(true,PatternUtils.isValidPhoneNumber("12345678901"))
    }
//    @Mock
//    lateinit var mContext: Context
//    private val FAKE_STRING = "Hello"
//
//    @Test
//    fun getMyString() {
//            Mockito.`when`(mContext.getString(R.string.mylibrary)).thenReturn(FAKE_STRING)
//        val myString = PatternUtils.getMyString(mContext)
//        assertEquals(FAKE_STRING, myString)
//    }

    @MockK
    private lateinit var context: Context
    private val FAKE_STRING = "Hello111"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        //另外一种 mock 对象的方法
        //context = mockk()
    }

    @Test
    fun getMyString() {
        every {
            context.getString(any())
        }.returns(FAKE_STRING)
        assertEquals(FAKE_STRING, PatternUtils.getMyString(context))
        verify {
            context.getString(any())
        }
    }
}