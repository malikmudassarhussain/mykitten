package com.mudassar.mykitten.kittens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mudassar.mykitten.components.AsyncImage
import com.mudassar.mykitten.components.Header
import com.mudassar.mykitten.util.inject
import com.mudassar.mykitten.kittens.Data.KittenUiItem

@Composable
internal fun KittensScreen(modifier: Modifier = Modifier) {
    val presenter by remember { inject<KittensPresenter>() }
    KittensUi(presenter.presenter(), modifier)
}

@Composable
private fun KittensUi(state: KittensUiState, modifier: Modifier = Modifier) {
    when (state) {
        Loading -> Loader()
        is ErrorState -> Error(state)
        is Data -> KittensUi(state, modifier)
    }
}

@Composable
private fun KittensUi(state: Data, modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier, topBar = { Header("My Kittens") }) {
        Column (modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            if (state.items.isEmpty()) {
                Text("No Kittens found", fontWeight = FontWeight.Bold)
                Button(onClick = { state.onRefresh.invoke() }) {
                    Text(text = "Refresh")
                }
            }

            LazyColumn {
                state.items.forEach {
                    item {
                        KittenItem(it, state.onClickDetail)
                    }
                }
            }
        }
    }
}

@Composable
private fun KittenItem(
    item: KittenUiItem,
    onClickDetail: (KittenUiItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = { onClickDetail(item) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            url = item.url,
            contentDescription = null,
            modifier = Modifier.width(100.dp)
                .height(80.dp)
        )

        Spacer(Modifier.width(8.dp))
        val bread = item.breadInfo.first()
        Column {
            Text(text = bread.name, fontWeight = FontWeight.Bold)
            Text(text = bread.description, maxLines = 2)
        }
    }
}

@Composable
private fun Loader(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.size(80.dp).align(Alignment.Center))
    }
}

@Composable
private fun Error(state: ErrorState, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Something went wrong!!")
            Button(onClick = state.onRetry) {
                Text(text = "Retry")
            }
        }
    }
}
