package com.luoyang.androidfunDemo.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luoyang.androidfunDemo.R
import com.luoyang.androidfunDemo.compose.theme.PrimaryText
import com.luoyang.androidfunDemo.compose.theme.PrimaryTextTip


@Composable
fun AboutListItem(
    title: String,
    content: String,
    jump: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = jump, indication = null, interactionSource = remember {
                MutableInteractionSource()
            })
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = PrimaryText,
            modifier = Modifier.padding(start = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = content,
            fontSize = 14.sp,
            color = PrimaryTextTip,
            modifier = Modifier.padding(end = 5.dp)
        )
        Icon(
            painterResource(R.mipmap.icon_right_back), contentDescription = null,
            Modifier
                .size(16.dp)
        )
    }
}

@Preview
@Composable
fun previewListItem() {
    AboutListItem("test", "hello", {})
}