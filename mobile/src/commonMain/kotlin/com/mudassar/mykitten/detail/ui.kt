package com.mudassar.mykitten.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mudassar.mykitten.components.AsyncImage
import com.mudassar.mykitten.components.Header
import com.mudassar.mykitten.util.inject
import com.mudassar.mykitten.kittens.Data.KittenUiItem.BreadInfo
import com.mudassar.mykitten.navigation.Navigator

@Composable
internal fun KittenDetailScreen(item: KittenDetailItem, modifier: Modifier = Modifier) {
    val navigator by remember { inject<Navigator>() }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { Header("Kitten Detail", onClick = navigator::pop) }
    ) {
        val scrollState = rememberScrollState()
        Column(Modifier.verticalScroll(scrollState)) {
            AsyncImage(
                url = item.url,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp, max = 300.dp),
                contentScale = ContentScale.Crop
            )
            item.breadInfo.forEach {
                BreadUi(it, Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
private fun BreadUi(info: BreadInfo, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(info.name, fontWeight = FontWeight.Bold)
        BreadInfo("Origin", info.origin)
        BreadInfo("Weight", info.metricWeight)
        BreadInfo("LifeSpan", info.lifeSpan)
        WikipediaPage(info.wikipediaUrl)
        Text(info.description)
    }
}

@Composable
private fun BreadInfo(title: String, detail: String) {
    Row {
        Text(title, fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(16.dp))
        Text(detail)
    }
}

@Composable
private fun WikipediaPage(page: String) {
    Row {
        Text("Wikipedia page", fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(16.dp))
        val uriHandler = LocalUriHandler.current
        val text = buildAnnotatedString {
            pushStringAnnotation(tag = "page", annotation = page)
            withStyle(style = SpanStyle(color = Blue)) {
                append(page)
            }
            pop()
        }
        ClickableText(text = text, onClick = { uriHandler.openUri(page) })
    }
}
