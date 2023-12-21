package com.sciepot.cookai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sciepot.cookai.data.TableSelected
import com.sciepot.cookai.ui.theme.CookAITheme


class MainActivity : ComponentActivity() {


    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookAITheme {
                MainScreen(mainViewModel)
            }
        }
    }
}