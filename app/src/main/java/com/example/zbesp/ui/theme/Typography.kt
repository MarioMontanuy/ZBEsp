package com.example.zbesp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.zbesp.R


@Composable
fun TitleText(text: String, alignment: TextAlign, style: TextStyle) {
    if (isSystemInDarkTheme()) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = style,
            textAlign = alignment
        )
    } else {
        Text(
            text = text,
            color = SapphireBlue,
            fontWeight = FontWeight.Bold,
            style = style,
            textAlign = alignment
        )
    }
}

@Composable
fun BigTitleText(text: String, alignment: TextAlign) {
    if (isSystemInDarkTheme()) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h3,
            textAlign = alignment
        )
    } else {
        Text(
            text = text,
            color = SapphireBlue,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h3,
            textAlign = alignment
        )
    }
}

@Composable
fun MediumTitleText(text: String, alignment: TextAlign) {
    if (isSystemInDarkTheme()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h4,
            textAlign = alignment
        )
    } else {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            text = text,
            color = SapphireBlue,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h4,
            textAlign = alignment
        )
    }
}

@Composable
fun TitleText(text: String, alignment: TextAlign) {
    if (isSystemInDarkTheme()) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            textAlign = alignment
        )
    } else {
        Text(
            text = text,
            color = SapphireBlue,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            textAlign = alignment
        )
    }
}

@Composable
fun TopBarTittle(text: String, alignment: TextAlign) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.body1,
        textAlign = alignment
    )
}

@Composable
fun TitleTextRed(text: String, alignment: TextAlign) {
    Text(
        text = text,
        color = errorColor,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.body1,
        textAlign = alignment
    )
}

@Composable
fun SubtitleText(text: String, alignment: TextAlign, style: TextStyle) {
    Text(
        text = text,
        style = style,
        textAlign = alignment
    )
}

@Composable
fun SubtitleText(text: String, alignment: TextAlign) {
    Text(
        text = text,
        style = MaterialTheme.typography.body2,
        textAlign = alignment
    )
}

@Composable
fun OwnerTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SapphireBlue,
        contentColor = SapphireBlue,
        modifier = modifier.semantics { heading() }
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}
@Composable
fun CommentField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    hintText: String = "",
    textStyle: TextStyle = MaterialTheme.typography.body1,
    maxLines: Int = 3,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        textStyle = textStyle,
        maxLines = maxLines,
        label = { Text(
            text = hintText,
            color = Color.Black
        ) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            placeholderColor = Color.Black,
            backgroundColor = Color.White,
            trailingIconColor = Color.Black,
            cursorColor = SapphireBlue
        ),
        trailingIcon = trailingIcon,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp, vertical = 5.dp)
    )
}