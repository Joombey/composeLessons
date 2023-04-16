package com.example.composelessons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.composelessons.ui.theme.ComposeLessonsTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var _flow: MutableStateFlow<Int> = MutableStateFlow(123)
        val stateFlow = _flow.asStateFlow()
        var printState = true
        runBlocking {
            setContent {
                var state by remember {
                    mutableStateOf(12)
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Hello ${state}!",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                printState = !printState
                                state = 120000
                            },
                        fontSize = 40.sp
                    )
                }
                LaunchedEffect(key1 = 1) {
                    launch {
                        while (true) {
                            if (!printState) {
                                delay(500L)
                                continue
                            }
                            _flow.value = Random.nextInt(1, 150)
                            delay(1000)
                        }
                    }

                    stateFlow
                        .filter {
                            it % 2 == 0
                        }
                        .map {
                            return@map listOf(it, it + 2, it * 100)
                        }.collect {
                            state = it.filter { it > 100 }.toList().get(0)
                        }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: Int, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = Modifier
            .fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLessonsTheme {
        Greeting(5)
    }
}