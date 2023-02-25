package com.semba.realestateapp.feature.detail.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.semba.realestateapp.R
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.design.component.ErrorView
import com.semba.realestateapp.design.component.LoadingView
import kotlin.math.roundToInt

const val DETAIL_ROWS_SPACE = 10

@Composable
fun DetailScreen(listingId: Int) {
    val viewModel: DetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.loadListingDetail(listingId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is DetailUiState.Error -> ErrorView(modifier = Modifier.align(Alignment.Center))
            DetailUiState.Loading -> LoadingView(modifier = Modifier.align(Alignment.Center))
            is DetailUiState.Success -> { DetailContent(listing = (uiState as DetailUiState.Success).listing) }
        }
    }
}

@Composable
fun DetailContent(listing: ListingModel) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        DetailImageSection(listing)
        DetailTopBar()
    }
}

@Composable
fun DetailTopBar() {
    Box(modifier = Modifier.padding(10.dp)) {
        val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

        IconButton(onClick = { dispatcher?.onBackPressed() }, modifier = Modifier
            .size(35.dp)
            .align(Alignment.CenterStart)) {
            Icon(modifier = Modifier.size(40.dp), painter = painterResource(id = R.drawable.ic_back), contentDescription = "back_button", tint = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun DetailImageSection(listingItem: ListingModel) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        ImagePreview(modifier = Modifier.weight(1f), listingItem.imageUrl)
        BottomDetailsSection(modifier = Modifier, listingItem)
    }
}

@Composable
fun ImagePreview(modifier: Modifier = Modifier, imageURL: String? = null) {

    AsyncImage(
        modifier = modifier
            .fillMaxWidth(),
        model = imageURL,
        contentDescription = "detail_image",
        error = painterResource(id = R.drawable.property_not_found)
    )
}

@Composable
fun BottomDetailsSection(modifier: Modifier = Modifier, listingItem: ListingModel) {
    Card(modifier = modifier
        .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.surfaceVariant,
        ), shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(modifier = modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceAround
        ) {

            listingItem.professional?.let {
                Spacer(modifier = Modifier.height(DETAIL_ROWS_SPACE.dp))
                CompanyProfile(name = it)
            }
            Spacer(modifier = Modifier.height(DETAIL_ROWS_SPACE.dp))
            DetailPriceSection(listingItem.price?.roundToInt()?.toString())
            Spacer(modifier = Modifier.height(DETAIL_ROWS_SPACE.dp))
            DetailSpecsSection(listingItem)
        }
    }
}

@Composable
fun DetailPriceSection(price: String? = stringResource(id = R.string.not_available)) {
    Divider(
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
    Box(modifier = Modifier.fillMaxWidth())
    {
        price?.let {
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.euro_sign))
                    append(it)
                },
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(DETAIL_ROWS_SPACE.dp)
            )
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
}

@Composable
fun CompanyProfile(name: String) {
    Row(
        Modifier
            .wrapContentSize()
            .padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        CircleImage(modifier = Modifier, size = 50.dp)
        Text(text = name, color = MaterialTheme.colorScheme.onSurface, fontSize = 15.sp)
    }
}

@Composable
fun CircleImage(modifier: Modifier = Modifier, size: Dp) {
    Box(modifier = modifier
        .size(size)
        .aspectRatio(1f, matchHeightConstraintsFirst = true)
        .border(1.dp, MaterialTheme.colorScheme.onSurface, shape = CircleShape)
        .padding(3.dp))
    {
        Image(painter = painterResource(id = R.drawable.property_placeholder), contentDescription = "company_profile", contentScale = ContentScale.Crop , modifier = Modifier.clip(
                CircleShape
                ))
    }
}

@Composable
fun DetailSpecsSection(listingItem: ListingModel) {

    listingItem.city?.let {
        Row(
            modifier = Modifier
                .padding(DETAIL_ROWS_SPACE.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "detail_address_icon",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = it,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = DETAIL_ROWS_SPACE.dp, end = DETAIL_ROWS_SPACE.dp)
            )
        }
    }

    listingItem.propertyType?.toString()?.let {
        Row(
            modifier = Modifier
                .padding(DETAIL_ROWS_SPACE.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_property),
                contentDescription = "area_icon",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = it,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = DETAIL_ROWS_SPACE.dp, end = DETAIL_ROWS_SPACE.dp)
            )
        }
    }

    listingItem.area?.roundToInt()?.toString()?.let {
        Row(
            modifier = Modifier
                .padding(DETAIL_ROWS_SPACE.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_area),
                contentDescription = "area_icon",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = buildAnnotatedString {
                    append(it)
                    append(" ${stringResource(id = R.string.meter_square)}")
                },
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = DETAIL_ROWS_SPACE.dp, end = DETAIL_ROWS_SPACE.dp)
            )
        }
    }

    listingItem.rooms?.toString()?.let {
        Row(
            modifier = Modifier
                .padding(DETAIL_ROWS_SPACE.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_room),
                contentDescription = "room_icon",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = buildAnnotatedString {
                    append(it)
                    append(" ${stringResource(id = R.string.room)}")
                },
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = DETAIL_ROWS_SPACE.dp, end = DETAIL_ROWS_SPACE.dp)
            )
        }
    }

    listingItem.bedrooms?.toString()?.let {
        Row(
            modifier = Modifier
                .padding(DETAIL_ROWS_SPACE.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_bedroom),
                contentDescription = "room_icon",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = it,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = DETAIL_ROWS_SPACE.dp, end = DETAIL_ROWS_SPACE.dp)
            )
        }
    }
}
