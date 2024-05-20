package com.example.superherosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superherosapp.data.heroes
import com.example.superherosapp.ui.theme.SuperHerosAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperHerosAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    SuperHerosApp()
                }
            }
        }
    }
}


@Composable
fun HeroIcon(
    @DrawableRes heroIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small)).clip(MaterialTheme.shapes.small),
        contentScale= ContentScale.Crop,
        painter = painterResource(heroIcon),
        contentDescription = null
    )
}


@Composable
fun HeroInformation(
    @StringRes heroName: Int,
    @StringRes heroDescription: Int,
    @DrawableRes heroIcon :Int,
    modifier: Modifier = Modifier
) {
    Card(modifier=Modifier.height(104.dp)){
        Row( modifier = Modifier.fillMaxWidth()
            .padding(16.dp).animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )){
            Column(modifier = modifier.fillMaxHeight().weight(1f)) {
                Text(
                    text = stringResource(heroName),
                    fontSize=23.sp,
                    fontWeight= FontWeight.ExtraBold,
                )
                Text(
                    text = stringResource(heroDescription),
                )
            }
            Column(modifier=Modifier){
                Image(
                    painter = painterResource(heroIcon),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp).fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )
            }

        }
    }
}

@Composable
fun SuperHerosApp() {
    Scaffold(topBar = {
        SuperHerosTopAppBar()
    }) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(1), // Use 2 columns
            contentPadding = PaddingValues(
                start = 16.dp,
                top = padding.calculateTopPadding() + 16.dp, // Add top padding from Scaffold
                end = 16.dp,
                bottom = 16.dp
            ), // Padding around the grid
            verticalArrangement = Arrangement.spacedBy(16.dp), // Vertical spacing between cards
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Horizontal spacing between cards
        ) {
            items(heroes) { hero ->
                HeroInformation(hero.nameRes,hero.descriptionRes,hero.imageRes);
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHerosTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier=Modifier.padding(top=10.dp),
                verticalAlignment = Alignment.CenterVertically,

            ) {

                Text(
                    text = stringResource(R.string.app_name),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize=25.sp
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = false)
@Composable
fun SuperHerosPreview() {
    SuperHerosAppTheme(darkTheme = false) {
        SuperHerosApp()
    }
}

@Preview(showBackground = true)
@Composable
fun SuperHerosDarkPreview() {
    SuperHerosAppTheme(darkTheme = true) {
        SuperHerosApp()
    }
}