package com.luoyang.androidfunDemo.compose.theme

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.drawscope.ContentDrawScope


private val LightColorPalette = lightColors(
//    primary = PrimaryBackGround,
//    primaryVariant = PrimaryText,
//    secondary = PrimaryTextTip
    primary = PrimaryRed,
    primaryVariant = PrimaryGreen,
    secondary = PrimaryBlue
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(colors = LightColorPalette) {

        // 设置全局参数，去除默认点击效果
        CompositionLocalProvider(
            LocalIndication provides NoIndication
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.body1, content = content)
        }
    }
}

// null indication
object NoIndication : Indication {
    private object NoIndicationInstance : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        return NoIndicationInstance
    }
}