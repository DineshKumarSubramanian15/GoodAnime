package com.android.goodanime.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> GAListView(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    listContent: @Composable (T.() -> Unit),
    onFetchNextPage: () -> Unit,
    list: List<T>,
    emptyListContent: @Composable () -> Unit = { GAEmptyScreen() },
    showBottomLoading: Boolean = false,
    isLoading: Boolean,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
) {
    val atLastIndex by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }
    val refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(atLastIndex) {
        if (atLastIndex) onFetchNextPage()
    }

    Box(modifier = modifier) {
        when {
            list.isValidList() -> {
                LazyColumn(
                    state = listState,
                    contentPadding = contentPadding,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(items = list) {
                        listContent(it)
                    }
                    if (showBottomLoading) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                GALoadingIndicator()
                            }
                        }
                    }
                }
            }

            !list.isValidList() && !isLoading -> {
                emptyListContent()
            }
        }
    }
}

fun <T> List<T>.isValidList(): Boolean {
    return this.isNotEmpty()
}