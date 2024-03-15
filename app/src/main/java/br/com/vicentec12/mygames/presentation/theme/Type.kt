package br.com.vicentec12.mygames.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val TitleText = TextStyle(
    color = Color.Black,
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold
)

val SubtitleText = TextStyle(
    color = Color.Gray,
    fontSize = 16.sp,
)

val ErrorText = TextStyle(
    color = Color.Red,
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center
)