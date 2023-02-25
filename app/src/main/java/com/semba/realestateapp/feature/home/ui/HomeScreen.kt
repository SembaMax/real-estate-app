package com.semba.realestateapp.feature.home.ui

import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.semba.realestateapp.R
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.design.component.ErrorView
import com.semba.realestateapp.design.component.LoadingView
import com.semba.realestateapp.design.navigation.ScreenDestination
import com.semba.realestateapp.feature.home.ui.state.ScrollState
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

private val MinTopBarHeight = 65.dp
private val MaxTopBarHeight = 75.dp
const val LIST_CONTENT_PADDING = 10

@Composable
fun HomeScreen(navigateTo: (screenDestination: ScreenDestination, args: Map<String, String>) -> Unit) {

    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val toolbarHeightRange = with(LocalDensity.current) {
        MinTopBarHeight.roundToPx()..MaxTopBarHeight.roundToPx()
    }
    val toolbarState = rememberToolbarState(toolbarHeightRange)
    val listState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                toolbarState.scrollTopLimitReached = listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                toolbarState.scrollOffset = toolbarState.scrollOffset - available.y
                return Offset(0f, toolbarState.consumed)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (available.y > 0) {
                    scope.launch {
                        animateDecay(
                            initialValue = toolbarState.height + toolbarState.offset,
                            initialVelocity = available.y,
                            animationSpec = FloatExponentialDecaySpec()
                        ) { value, velocity ->
                            toolbarState.scrollTopLimitReached = listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                            toolbarState.scrollOffset = toolbarState.scrollOffset - (value - (toolbarState.height + toolbarState.offset))
                            if (toolbarState.scrollOffset == 0f) scope.coroutineContext.cancelChildren()
                        }
                    }
                }

                return super.onPostFling(consumed, available)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().nestedScroll(nestedScrollConnection))
    {
        when (uiState)
        {
            is HomeScreenUiState.Error -> ErrorView(modifier = Modifier.align(Alignment.Center))
            HomeScreenUiState.Loading -> LoadingView(modifier = Modifier.align(Alignment.Center))
            is HomeScreenUiState.Success ->
            {
                HomeContent(
                    listModifier = Modifier.fillMaxSize()
                    .graphicsLayer { translationY = toolbarState.height + toolbarState.offset }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { scope.coroutineContext.cancelChildren() }
                        )
                    },
                    topBarModifier = Modifier
                        .fillMaxWidth()
                        .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                        .graphicsLayer { translationY = toolbarState.offset },
                    listState = listState,
                    listings = (uiState as HomeScreenUiState.Success).listings,
                    onItemClick = { listingItem -> navigateTo(ScreenDestination.DETAIL, listingItem.toArgs()) })
            }
        }
    }
}

@Composable
fun HomeContent(listModifier: Modifier = Modifier, topBarModifier: Modifier = Modifier, listState: LazyListState = rememberLazyListState(), listings: List<ListingModel>, onItemClick: (ListingModel) -> Unit)
{
        LazyColumn(
            modifier = listModifier,
            contentPadding = PaddingValues(start = LIST_CONTENT_PADDING.dp, end = LIST_CONTENT_PADDING.dp, bottom = LIST_CONTENT_PADDING.dp, top = LIST_CONTENT_PADDING.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            items(listings.size) {
                ListingItemView(
                    modifier = Modifier.clickable { onItemClick(listings[it]) },
                    item = listings[it]
                )
            }
        }

        CollapsingTopBar(modifier = topBarModifier)
}

@Composable
fun CollapsingTopBar(modifier: Modifier = Modifier) {

    Surface(modifier = modifier.clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))) {

        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.primary
                    )
                ))
            .blur(100.dp)
        ) {

            Row(modifier = Modifier.padding(start = 20.dp, top = 20.dp) , horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {

                Icon(painter = painterResource(id = R.drawable.ic_home), contentDescription = "address_icon", modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.onSecondary)

                Text(text = stringResource(id = R.string.header_title),
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary)
            }
        }
    }
}

@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): TopBarState {
    return rememberSaveable(saver = ScrollState.Saver) {
        ScrollState(toolbarHeightRange)
    }
}