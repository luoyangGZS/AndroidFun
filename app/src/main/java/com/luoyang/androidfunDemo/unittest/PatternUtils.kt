package com.luoyang.androidfunDemo.unittest
import android.content.Context
import com.luoyang.androidfunDemo.R
import java.util.regex.Pattern
/**
 *
 *
 * @author luoyang
 * @date 2022/9/1
 */
object PatternUtils {
    /**
     * 是否有效的邮箱
     * */
    fun isValidEmail(email: String?): Boolean {
        if (email == null)
            return false
        val regEx1 =
            "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
        val p = Pattern.compile(regEx1)
        val m = p.matcher(email)
        return m.matches()
    }

    /**
     * 是否有效的手机号，只判断位数
     * */
    fun isValidPhoneNumber(phone: String?): Boolean {
        if (phone == null)
            return false
        return phone.length == 11
    }

    fun getMyString(context: Context): String {
        return context.getString(R.string.home_game)
    }
}
