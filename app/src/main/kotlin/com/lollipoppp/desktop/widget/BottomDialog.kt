package com.lollipoppp.desktop

import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun BoxScope.BottomDialog(
        expand: Boolean,
        clickOut: () -> Unit,
        content: @Composable () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    AnimatedVisibility(
            visible = expand,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
    ) {
        Spacer(
                modifier = Modifier.fillMaxSize()
                        .background(color = Color(0x80000000))
                        .clickable(
                                indication = null,
                                interactionSource = interactionSource
                        ) {
                            clickOut()
                        }
        )
    }
    AnimatedVisibility(
            visible = expand,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
    ) {
        Card(
                elevation = 16.dp
        ) {
            content()
        }
    }

}