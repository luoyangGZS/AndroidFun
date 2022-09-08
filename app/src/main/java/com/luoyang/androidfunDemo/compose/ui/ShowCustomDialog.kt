package com.luoyang.androidfunDemo.compose.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.luoyang.androidfunDemo.compose.theme.PrimaryBackGround
import com.luoyang.androidfunDemo.compose.theme.PrimaryButton
import com.luoyang.androidfunDemo.compose.theme.PrimaryText
import com.luoyang.androidfunDemo.compose.viewmodel.AboutViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ShowCustomDialog(model: AboutViewModel, context: Context) {
    CustomDialog(
        showDialog = model.uiState.showDialog.value,
        content =
        { TextFieldDemo(model.uiState.changeText) },
        cancelText = "Cancel",
        okText = "Summit",
        cancelOnClick =
        {
            model.uiState.changeText.value = ""
            model.uiState.showDialog.value = false
        },
        okOnClick =
        {
            model.startDebug(context)
            model.uiState.showDialog.value = false
        })
}

@Composable
fun TextFieldDemo(changeText: MutableState<String>) {
    TextField(
        // 显示文本
        value = changeText.value,
        // 文字改变时，就赋值给text
        onValueChange = { changeText.value = it },
        modifier = Modifier.background(PrimaryBackGround),
        label = { Text(text = "Enter password", fontSize = 13.sp) }
    )
}

@Composable
fun CustomDialog(
    showDialog: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    cancelText: String,
    okText: String,
    cancelOnClick: () -> Unit,
    okOnClick: () -> Unit,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
) {
    if (showDialog) {
        AlertDialog(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(9.dp)),
            shape = RoundedCornerShape(9.dp),
            properties = DialogProperties(
                dismissOnClickOutside = dismissOnClickOutside,
                dismissOnBackPress = dismissOnBackPress
            ),
            onDismissRequest = {
                // 点击外部view，同样关闭dialog
                cancelOnClick.invoke()
            },

            title = {
                Text(
                    text = "Debug",
                    modifier = Modifier
                        .padding(8.dp)
                        .paddingFromBaseline(bottom = 4.dp)
                )
            },
            text = null,
            buttons = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content()
                    Divider(color = Color.White, modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        TextButton(
                            modifier = Modifier
                                .weight(1f),
                            onClick = {
                                cancelOnClick.invoke()
                            },
                        ) {
                            Text(text = cancelText, color = PrimaryText, fontSize = 14.sp)
                        }
                        Divider(
                            modifier = Modifier
                                .width(5.dp)
                                .fillMaxHeight(),
                            color = Color.White
                        )
                        TextButton(
                            modifier = Modifier
                                .weight(1f),
                            onClick = {
                                okOnClick.invoke()
                            },
                        ) {
                            Text(okText, color = PrimaryButton, fontSize = 14.sp)
                        }
                    }
                }
            }
        )
    }
}





