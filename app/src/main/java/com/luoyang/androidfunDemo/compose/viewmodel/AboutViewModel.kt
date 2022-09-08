package com.luoyang.androidfunDemo.compose.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.luoyang.androidfunDemo.R
import com.luoyang.androidfunDemo.compose.data.AboutUIState
import com.luoyang.androidfunDemo.compose.data.ListType
import com.luoyang.androidfunDemo.compose.data.Message
import com.luoyang.androidfunDemo.util.Utils
import java.text.SimpleDateFormat
import java.util.*

class AboutViewModel : ViewModel() {

    companion object {
        const val TAG: String = "AboutViewModel"
    }

    val uiState = AboutUIState()

    //什么是MutableStateFlow
//    private val uiState1 = MutableStateFlow(AboutUIState())
//
//    // the state exposed to UI.
//    val state: StateFlow<AboutUIState> = uiState1.asStateFlow()

    /**
     * 关于我们页的固定数据
     */

    /**
     * 关于我们页的固定数据
     */
    val content by mutableStateOf(
        listOf(
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            ),
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            ),
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            ),
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            ),
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            ),
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            ),
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            ),
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            ),
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            ),
            Message(
                R.string.official_website,
                R.string.official_website_value,
                ListType.OFFICIAL_JUMP, ""
            )

        )
    )

    fun count() {
        uiState.countClick.value++
        if (uiState.countClick.value >= 2) {
            uiState.countClick.value = 0
            Log.d(TAG, " state.value.showDialog = true")
            uiState.showDialog.value = true
        }
    }


    fun jump(context: Context, type: ListType, jumpData: String) {
        when (type) {
            ListType.OFFICIAL_JUMP -> {
                gotoOfficialWebsite(context)
            }
            ListType.CUT_TIPS -> {
                clipText(context, jumpData)
            }
            ListType.WEB_JUMP -> {
                startWebViewActivity(context, jumpData);
            }
        }
    }

    private fun gotoOfficialWebsite(context: Context) {
        try {
            val uri: Uri =
                Uri.parse("http://" + Utils.getSafeString(R.string.official_website_value))
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = uri
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "gotoWebsite Exception", e)
        }
    }

    private fun clipText(context: Context, text: String) {
//        val clipBoard: ClipboardManager =
//            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        val clipData = ClipData.newPlainText(getString(R.string.clip_to_board), text)
//        clipBoard.setPrimaryClip(clipData)
//        showTip(context, getString(R.string.clip_to_board))
    }

    private fun showTip(context: Context, text: String) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun startWebViewActivity(context: Context, url: String) {
//        val intent = Intent(
//            context,
//            WebViewActivity::class.java
//        )
//        intent.putExtra(Constant.URL, url)
//        context.startActivity(intent)
    }

    @SuppressLint("SimpleDateFormat")
    fun startDebug(context: Context) {
        val dataFormat = SimpleDateFormat("yyyy/MM/dd")
        val date: String = dataFormat.format(Date(System.currentTimeMillis())).replace("/", "")
        if (uiState.changeText.value == date) {
//            context.startActivity(
//                Intent(
//                    MainApplication
//                        .getInstance(),
//                    SettingsDebugActivity::class.java
//                )
//            )
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
        }
        // 清空输入
        uiState.changeText.value = ""
    }

    fun initRedPoint(num: Int) {
        Log.d(TAG, "initRedPoint num: $num")
        val downloadNum = if (num == 0) {
            ""
        } else {
            if (num > 99) "99+" else num.toString()
        }
        uiState.changeValue.value = downloadNum
    }
}