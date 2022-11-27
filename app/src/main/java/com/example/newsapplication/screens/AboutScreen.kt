package com.example.newsapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//Разметка экрана "О приложении"
@Composable
@Preview
fun AboutScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon Composable
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = "profile",
            tint = MaterialTheme.colorScheme.surfaceTint
        )
        // Text to Display the current Screen
        Text(text = "About")
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VKCard() //Карточка со ссылкой на ВК
            TelegramCard() //Карточка со ссылкой на Telegram
            //EmailCard() //Карточка со ссылкой на почту
        }
    }
}

@Composable
private fun VKCard() {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { uriHandler.openUri(uri = "https://vk.com/prikhodko_nk") }
            .padding(all = 5.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Box {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = com.example.newsapplication.R.drawable.vk),
                    contentDescription = "VK",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 70.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "Вконтакте", fontSize = 20.sp,
                )
            }
        }
    }
}

@Composable
private fun TelegramCard() {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { uriHandler.openUri(uri = "https://t.me/doc_fr10") }
            .padding(all = 5.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = com.example.newsapplication.R.drawable.telegram),
                    contentDescription = "Telegram",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 75.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "Telegram", fontSize = 20.sp,
                )
            }
        }
    }
}

/*
@Composable
private fun EmailCard() {
    //val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            //.clickable { uriHandler.openUri(uri = "docfr10@yandex.ru") }
            .padding(all = 5.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box {
            Image(
                painter = painterResource(id = com.example.newsapplication.R.drawable.email),
                contentDescription = "Email",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
            )
        }
    }
}
 */