package com.luoyang.androidfunDemo.compose.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luoyang.androidfunDemo.R
import com.luoyang.androidfunDemo.compose.theme.PrimaryBackGround
import com.luoyang.androidfunDemo.compose.theme.PrimaryText
import com.luoyang.androidfunDemo.compose.theme.PrimaryTextTip
import com.luoyang.androidfunDemo.compose.viewmodel.AboutViewModel
import com.luoyang.androidfunDemo.util.Utils

@Composable
fun AboutView(
    context: Context,
    model: AboutViewModel,
    goBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = PrimaryBackGround)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 10.dp, top = 15.dp, end = 10.dp, bottom = 15.dp)
        ) {
            ToolBarView(
                stringResource(R.string.manager_about),
                goBack = { goBack() },
                model.uiState.changeValue
            )
            ContentView(context, model)

            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = Utils.getSafeString(R.string.manager_about),
                    color = PrimaryTextTip,
                    modifier = Modifier
                        .padding(top = 20.dp),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ContentView(context: Context, model: AboutViewModel) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(bottom = 40.dp)
    ) {
        Column(
            Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painterResource(R.drawable.ic_toolbar_search),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(PrimaryText),
                    modifier = Modifier
                        .size(60.dp)
                        .clickable(
                            onClick = { model.count() },
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            })
                )
                Text(
                    text = stringResource(R.string.app_name),
                    Modifier.padding(5.dp),
                    fontSize = 16.sp,
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "2.0.1",
                    Modifier.padding(bottom = 20.dp),
                    fontSize = 16.sp,
                    color = PrimaryTextTip,
                )
            }

            // 添加内容list
            LazyColumn {
                itemsIndexed(model.content) { _, message ->
                    AboutListItem(
                        title = Utils.getSafeString(message.title),
                        content = Utils.getSafeString(message.description),
                    ) {
                        model.jump(context, message.type, message.jumpData)
                    }
                }

            }
        }
    }

}
