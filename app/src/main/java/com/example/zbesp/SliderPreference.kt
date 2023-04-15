package com.example.zbesp

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import com.jamal.composeprefs.ui.LocalPrefsDataStore
import com.jamal.composeprefs.ui.prefs.TextPref
import com.jamal.composeprefs.ui.roundToDP
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

class SliderPreference{

    var currentValue: Float = 0f

    fun setValue(value: Float) {
        currentValue = value
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun AddSliderPreference(
        key: String,
        title: String,
        modifier: Modifier = Modifier,
        defaultValue: Float = 0f,
        onValueChangeFinished: ((Float) -> Unit)? = null,
        valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
        showValue: Boolean = false,
        steps: Int = 0,
        textColor: Color = MaterialTheme.colors.onBackground,
        enabled: Boolean = true,
        leadingIcon: @Composable (() -> Unit)? = null,
    ) {

        val selectionKey = floatPreferencesKey(key)
        val scope = rememberCoroutineScope()

        val datastore = LocalPrefsDataStore.current
        val prefs by remember { datastore.data }.collectAsState(initial = null)

        var value by remember { mutableStateOf(defaultValue) }

        LaunchedEffect(Unit) {
            prefs?.get(selectionKey)?.also { value = it }
        }

        LaunchedEffect(datastore.data) {
            datastore.data.collectLatest { pref ->
                pref[selectionKey]?.also {
                    value = it
                }
            }
        }
        fun edit() = run {
            scope.launch {
                try {
                    datastore.edit { preferences ->
                        preferences[selectionKey] = value
                    }
                    onValueChangeFinished?.invoke(value)
                } catch (e: Exception) {
                    Log.e("SliderPref", "Could not write pref $key to database. ${e.printStackTrace()}")
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
        ) {

            TextPref(
                title = title,
                modifier = modifier,
                textColor = textColor,
                minimalHeight = true,
                leadingIcon = leadingIcon,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Slider(
                    value = value,
                    onValueChange = { value = it },
                    modifier = Modifier
                        .weight(2.1f)
                        .padding(start = 16.dp, end = 16.dp),
                    valueRange = valueRange,
                    steps = steps,
                    onValueChangeFinished = { edit() },
                    enabled = enabled
                )

                if (showValue) {
                    Text(
                        text = roundToDP(value, 2).toString(),
                        color = textColor,
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(start = 8.dp)
                    )
                }
            }

        }
    }
}

