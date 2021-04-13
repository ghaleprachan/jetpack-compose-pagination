package com.example.paginationincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.paginationincompose.ui.theme.PaginationInComposeTheme
import com.google.accompanist.coil.CoilImage

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setContent {
            PaginationInComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PhotoUI()
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(IPhotoRepository(RetrofitBuilder.apiService))
        )[MainViewModel::class.java]
    }

    @Composable
    fun PhotoUI() {
        val photos = viewModel.getPhotoPagination().collectAsLazyPagingItems()
        // Photos is of type LazyPagingItems<Photo>

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(photos) { photo ->
                Box {
                    photo?.downloadUrl?.let {
                        CoilImage(
                            data = it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 4.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(0.dp)
                                )
                                .height(250.dp)
                        )
                    }
                    BasicText(
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        text = "Clicked by: " + photo?.author.toString(),
                        modifier = Modifier
                            .padding(top = 8.dp, start = 4.dp)
                            .background(color = Color.White)
                            .padding(4.dp)
                    )
                }
            }


            photos.apply {
                when {
                    loadState.refresh is LoadState.Loading -> item {
                        ShowProgress()
                    }
                    loadState.append is LoadState.Loading -> {
                        item { ShowProgress() }
                    }
                }
            }

        }
    }

    @Composable
    private fun ShowProgress() {
        CircularProgressIndicator()
    }
}
