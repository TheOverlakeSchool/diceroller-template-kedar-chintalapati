package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var _rolls by remember { mutableStateOf(0) }

    val stats_map = remember {
        mutableStateMapOf(
            "STR" to 0, "DEX" to 0, "CON" to 0,
            "INT" to 0, "WIS" to 0, "CHA" to 0
        )
    }

    var dice_list by remember { mutableStateOf(listOf(1, 1, 1, 1)) }

    val img_list = dice_list.map {
        if (it == 1) R.drawable.dice_1
        else if (it == 2) R.drawable.dice_2
        else if (it == 3) R.drawable.dice_3
        else if (it == 4) R.drawable.dice_4
        else if (it == 5) R.drawable.dice_5
        else R.drawable.dice_6
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            dice_list.forEach { d ->
                Image(
                    painter = painterResource(img_list[dice_list.indexOf(d)]),
                    contentDescription = d.toString(),
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Button(
            onClick = {
                if (_rolls < 6) {
                    val _temp_list = List(4) { (1..6).random() }
                    val sum = _temp_list.sorted().drop(1).sum()
                    val stat_keys = stats_map.keys.toList()

                    stats_map[stat_keys[_rolls]] = sum
                    dice_list = _temp_list
                    _rolls++
                } else {
                    for (k in stats_map.keys) {
                        stats_map[k] = 0
                    }
                    _rolls = 0
                }
            }
        ) {
            Text(text = stringResource(R.string.roll), fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        stats_map.forEach {
            Text(text = "${it.key}: ${it.value}", fontSize = 20.sp)
        }
    }
}
