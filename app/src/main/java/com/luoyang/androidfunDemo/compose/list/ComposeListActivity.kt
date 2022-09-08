package com.luoyang.androidfunDemo.compose.list

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luoyang.androidfunDemo.compose.list.ComposeListActivity.Companion.TAG
import com.luoyang.androidfunDemo.compose.list.ui.theme.AndroidFunDemoTheme

class ComposeListActivity : ComponentActivity() {

    companion object {
        const val TAG = "ExposureAndComposeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidFunDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ContentView()
                }
            }
        }
    }
}

@Composable
private fun ContentView() {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(15.dp)
    ) {
        // 添加内容list
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(200) { index ->
                var view = Text(
                    text = "Item------------ : $index",
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    fontSize = 16.sp,
                    //文字居中
//                    textAlign = TextAlign.Center
                )

                Log.i(TAG, "第$index 已经生成 $view")
            }
        }
    }

}