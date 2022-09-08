package com.luoyang.androidfunDemo.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luoyang.androidfunDemo.compose.theme.PrimaryBackGround
import com.luoyang.androidfunDemo.compose.theme.PrimaryButton
import com.luoyang.androidfunDemo.compose.theme.PrimaryText
import com.luoyang.androidfunDemo.R


@Composable
fun ToolBarView(
    title: String,
    goBack: () -> Unit = {},
    changeValue: MutableState<String>
) {
    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
            .background(color = PrimaryBackGround)
    )
    Box(
        modifier = Modifier
            .background(color = PrimaryBackGround)
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(R.drawable.ic_toolbar_back),
                contentDescription = null,
                colorFilter = ColorFilter.tint(PrimaryText),
                modifier = Modifier
                    .clickable(onClick = goBack)
                    .padding(end = 15.dp),
            )
            Text(
                text = title,
                fontSize = 18.sp,
                color = PrimaryText,
                fontWeight = FontWeight.Bold
            )

        }

        Row(
            Modifier.align(alignment = Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_toolbar_search),
                contentDescription = null,
                colorFilter = ColorFilter.tint(PrimaryText),
                modifier = Modifier
                    .clickable(onClick = {
//                        MainApplication
//                            .getInstance()
//                            .startActivity(
//                                Intent(
//                                    MainApplication
//                                        .getInstance(),
//                                    SearchActivity::class.java
//                                )
//                            )
                    })
                    .padding(start = 15.dp, end = 15.dp),
            )
            Box(
                Modifier
                    .padding(end = 15.dp)
                    .height(45.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_toolbar_download),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(PrimaryText),
                    modifier = Modifier
//                        .clickable(onClick = {
//                            MainApplication
//                                .getInstance()
//                                .startActivity(
//                                    Intent(
//                                        MainApplication
//                                            .getInstance(),
//                                        DownloadActivity::class.java
//                                    )
//                                )
//                        })
                        .padding(top = 10.dp)
                )
                var backgroundColor =
                    if (changeValue.value == "") PrimaryBackGround else PrimaryButton
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 18.dp, top = 5.dp)
                        .background(
                            backgroundColor,
                            shape = RoundedCornerShape(40.dp),
                        )
                        .size(15.dp)

                ) {
                    Text(
                        text = changeValue.value, color = Color.White, fontSize = 10.sp
                    )
                }
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewBarView() {
    ToolBarView(title = "title", {}, remember {
        mutableStateOf("")
    })
}
