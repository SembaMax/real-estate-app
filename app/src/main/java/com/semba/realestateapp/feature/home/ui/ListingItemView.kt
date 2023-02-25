package com.semba.realestateapp.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.semba.realestateapp.data.model.ListingModel
import com.semba.realestateapp.R
import java.util.concurrent.TimeoutException
import kotlin.math.roundToInt

const val TOP_BAR_HEIGHT = 40
const val CARD_TEXT_PADDING = 5
const val CARD_CONTENT_PADDING = 10
const val CARD_HEIGHT = 450

@Composable
fun ListingItemView(modifier: Modifier = Modifier, item: ListingModel) {
    Card(modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.surfaceVariant,
    ), shape = RoundedCornerShape(10.dp)) {
        Column(modifier = Modifier
            .height(CARD_HEIGHT.dp)
            .fillMaxWidth()) {

            TopBody(modifier = Modifier
                .fillMaxSize()
                .weight(0.65f),
            imageUrl = item.imageUrl,
            professional = item.professional)

            BottomBody(modifier = Modifier.weight(0.35f), item = item)
        }
    }
}

@Composable
fun TopBody(modifier: Modifier = Modifier, imageUrl: String? = null, professional: String? = null) {

    Box(modifier = modifier) {
        AsyncImage(modifier = Modifier
            .fillMaxSize(),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.property_not_found))

        professional?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TOP_BAR_HEIGHT.dp)
                    .background(MaterialTheme.colorScheme.outline)
                    .align(Alignment.TopCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = it,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = CARD_TEXT_PADDING.dp, end = CARD_TEXT_PADDING.dp)
                )
            }
        }
    }
}

@Composable
private fun BottomBody(modifier: Modifier = Modifier, item: ListingModel) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        item.city?.let {
            AddressSection(address = it)
        }
        ChipsSection(chips = listOfNotNull(item.propertyType, item.bedrooms?.let { "${item.bedrooms} ${stringResource(id = R.string.bedrooms)}" }))
        SpecsSection(price = item.price?.roundToInt()?.toString(), area = item.area?.roundToInt()?.toString(), rooms = item.rooms?.toString())
    }
}

@Composable
fun AddressSection(address: String) {
    Row(modifier = Modifier
        .padding(CARD_CONTENT_PADDING.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {

        Icon(painter = painterResource(id = R.drawable.ic_location), contentDescription = "address_icon", modifier = Modifier.size(25.dp), tint = MaterialTheme.colorScheme.onSurface)

        Text(
            text = address,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = CARD_TEXT_PADDING.dp, end = CARD_TEXT_PADDING.dp)
        )
    }
}

@Composable
fun ChipsSection(chips: List<String>) {

    LazyRow(contentPadding = PaddingValues(start = CARD_CONTENT_PADDING.dp, end = CARD_CONTENT_PADDING.dp, top = 5.dp, bottom = 5.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(chips.size) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(7.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
            )
            {
                Text(modifier = Modifier
                    .padding(CARD_TEXT_PADDING.dp), text = chips[it], color = MaterialTheme.colorScheme.onTertiary, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun SpecsSection(price: String? = stringResource(id = R.string.not_available), area: String? = null, rooms: String? = null) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(
            start = CARD_CONTENT_PADDING.dp,
            end = CARD_CONTENT_PADDING.dp,
            top = 5.dp,
            bottom = 5.dp
        ), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

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
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(start = CARD_TEXT_PADDING.dp, end = CARD_TEXT_PADDING.dp)
            )
        }

        Row(modifier = Modifier
            .padding(10.dp), horizontalArrangement = Arrangement.spacedBy(CARD_CONTENT_PADDING.dp), verticalAlignment = Alignment.CenterVertically) {
            area?.let {
                Text(
                    text = buildAnnotatedString {
                        append(it)
                        append(" ${stringResource(id = R.string.meter_square)}")
                    },
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(start = CARD_TEXT_PADDING.dp, end = CARD_TEXT_PADDING.dp)
                )
            }

            rooms?.let {
                Text(
                    text = buildAnnotatedString {
                        append(it)
                        append(" ${stringResource(id = R.string.room)}")
                    },
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(start = CARD_TEXT_PADDING.dp, end = CARD_TEXT_PADDING.dp)
                )
            }
        }
    }
}
