package com.lollipoppp.desktop

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun App() {

    var appName by remember { mutableStateOf("") }
    var genericName by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var execPath by remember { mutableStateOf("") }
    var iconPath by remember { mutableStateOf("") }
    var appType by remember { mutableStateOf(DesktopFile.AppType.Application) }
    val categories = remember { mutableStateListOf<DesktopFile.Categories>() }
    var version by remember { mutableStateOf("") }
    var startupNotify by remember { mutableStateOf(false) }
    var terminal by remember { mutableStateOf(false) }

    var expandTypeDialog by remember { mutableStateOf(false) }
    var expandCategoriesDialog by remember { mutableStateOf(false) }

    MaterialTheme {
        Box(
                modifier = Modifier.fillMaxSize().background(Color.DarkGray).padding(8.dp),
        ) {
            Row {
                Card(
                        modifier = Modifier.fillMaxHeight().fillMaxWidth(0.4F),
                        backgroundColor = Color.White,
                ) {
                    Box {
                        Column(
                                modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            OutlinedTextField(
                                    modifier = inputModifier(),
                                    value = appName,
                                    onValueChange = { appName = it },
                                    label = { Text("名称") },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.LightGray)
                            )
                            OutlinedTextField(
                                    modifier = inputModifier(),
                                    value = genericName,
                                    onValueChange = { genericName = it },
                                    label = { Text("别名") },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.LightGray)
                            )
                            OutlinedTextField(
                                    modifier = inputModifier(),
                                    value = execPath,
                                    onValueChange = { execPath = it },
                                    label = { Text("路径") },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.LightGray)
                            )
                            OutlinedTextField(
                                    modifier = inputModifier(),
                                    value = iconPath,
                                    onValueChange = { iconPath = it },
                                    label = { Text("图标") },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.LightGray)
                            )
                            OutlinedTextField(
                                    modifier = inputModifier(),
                                    value = comment,
                                    onValueChange = { comment = it },
                                    label = { Text("描述") },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.LightGray)
                            )
                            OutlinedTextField(
                                    modifier = inputModifier(),
                                    value = version,
                                    onValueChange = { version = it },
                                    label = { Text("版本") },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.LightGray)
                            )

                            Row(
                                    modifier = switchModifier().clickable {
                                        expandTypeDialog = true
                                    }
                            ) {
                                Text(
                                        text = "类型",
                                        modifier = switchTextModifier()
                                )
                                Text(
                                        text = appType.name,
                                        modifier = switchBtnModifier(),
                                )
                            }

                            Row(modifier = switchModifier()) {
                                Text(
                                        text = "显示终端",
                                        modifier = switchTextModifier()
                                )
                                Spacer(Modifier.width(4.dp))
                                Switch(
                                        checked = terminal,
                                        modifier = switchBtnModifier(),
                                        onCheckedChange = { terminal = it },
                                )
                            }

                            Row(modifier = switchModifier()) {
                                Text(
                                        text = "启动通知",
                                        modifier = switchTextModifier()
                                )
                                Spacer(Modifier.width(4.dp))
                                Switch(
                                        checked = startupNotify,
                                        modifier = switchBtnModifier(),
                                        onCheckedChange = { startupNotify = it },
                                )
                            }

                            Column(
                                    modifier = switchModifier().clickable {
                                        expandCategoriesDialog = true
                                    }
                            ) {
                                Text(
                                        text = "标签",
                                        modifier = Modifier.padding(start = 14.dp, end = 14.dp, top = 16.dp)
                                )
                                Text(
                                        text = DesktopFile.getCategoriesValue(categories),
                                        modifier = Modifier.padding(start = 14.dp, end = 14.dp, top = 6.dp, bottom = 16.dp)
                                )
                            }

                        }

                        BottomDialog(expand = expandTypeDialog,
                                clickOut = { expandTypeDialog = false }
                        ) {
                            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                DesktopFile.AppType.values().forEach {
                                    Box(modifier = Modifier.fillMaxWidth().clickable {
                                        appType = it
                                        expandTypeDialog = false
                                    }) {
                                        Text(text = it.name, modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 24.dp, vertical = 16.dp)
                                                .fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }

                        BottomDialog(
                                expand = expandCategoriesDialog,
                                clickOut = { expandCategoriesDialog = false }
                        ) {
                            Column {
                                Box(modifier = Modifier.align(Alignment.End)
                                        .clickable { expandCategoriesDialog = false }) {
                                    Icon(
                                            imageVector = Icons.Filled.Close,
                                            contentDescription = "Close",
                                            modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                    DesktopFile.Categories.values().forEach { type ->

                                        val onItemClick: (() -> Unit) = {
                                            expandTypeDialog = false
                                            if (categories.contains(type)) {
                                                categories.remove(type)
                                            } else {
                                                categories.add(type)
                                            }
                                        }

                                        Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onItemClick)) {
                                            RadioButton(selected = categories.contains(type),
                                                    modifier = Modifier.padding(start = 24.dp)
                                                            .align(Alignment.CenterVertically),
                                                    onClick = onItemClick
                                            )
                                            Text(text = "${type.label}(${type.name})", modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 24.dp, vertical = 16.dp)
                                                    .fillMaxWidth()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Card(
                        modifier = Modifier.fillMaxSize(),
                        backgroundColor = Color.White,
                ) {
                    Text(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            style = TextStyle(
                                    color = Color.Black,
                                    textAlign = TextAlign.Start
                            ),
                            text = DesktopFile.createValue(
                                    appName,
                                    comment,
                                    genericName,
                                    iconPath,
                                    execPath,
                                    appType,
                                    categories,
                                    version,
                                    startupNotify,
                                    terminal
                            )
                    )
                }

            }
            ExtendedFloatingActionButton(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
                    text = { Text("完成") },
                    icon = { Icon(Icons.Filled.Done, "Done") },
                    onClick = {

                    }
            )
        }
    }
}

fun inputModifier(): Modifier {
    return Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
}

fun switchModifier(): Modifier {
    return inputModifier()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp))
}

fun RowScope.switchTextModifier(): Modifier {
    return Modifier
            .align(Alignment.CenterVertically)
            .weight(1F)
            .padding(horizontal = 14.dp, vertical = 16.dp)
}

fun RowScope.switchBtnModifier(): Modifier {
    return Modifier
            .align(Alignment.CenterVertically)
            .padding(horizontal = 14.dp)
}