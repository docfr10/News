package com.example.newsapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapplication.R

@Preview
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Red)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.news),
                        contentDescription = "News photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = "News")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = "Button1")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = "Button2")
            }
            Button(onClick = {/*TODO*/ }, modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = "About")
            }
        }
    }
}