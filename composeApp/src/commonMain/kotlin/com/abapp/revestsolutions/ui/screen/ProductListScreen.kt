package com.abapp.revestsolutions.ui.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abapp.revestsolutions.LogD
import com.abapp.revestsolutions.model.Product
import com.abapp.revestsolutions.ui.screen_element.FormInputField
import com.abapp.revestsolutions.ui.screen_element.ProductItemView
import com.abapp.revestsolutions.ui.state.ProductUiState
import com.abapp.revestsolutions.viewmodel.ProductViewModel



@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    onItemClick: (Product) -> Unit,
    onBackPressed: () -> Unit
) {



    //ui state
    val state by viewModel.uiState.collectAsState()

    //data
    val products by viewModel.products.collectAsState()
    val searchResult by viewModel.searchResults.collectAsState()
    val filterResults by viewModel.filterResults.collectAsState()



    //search
    val focusRequester = remember { FocusRequester() }
    var searchText by remember { mutableStateOf("") }



    //list
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()



    //display list
    val displayList = when {
        selectedCategory != null && searchText.isEmpty() -> filterResults
        searchText.isNotEmpty() -> searchResult
        else -> products
    }







    //update search text
    LaunchedEffect(searchText) {
        viewModel.onSearchQueryChange(searchText)
    }




    LaunchedEffect(displayList) {
        LogD("tag", "displayList $displayList")
    }




    Scaffold(
        topBar = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(12.dp)
            ) {

                //top bar
                Text(
                    text = "Revest Solutions",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(Modifier.height(4.dp))

                //search bar
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    FormInputField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        hint = "Search product...",
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester),
                        trailingContent = {

                        }
                    )

                    Spacer(Modifier.width(8.dp))

                    if (searchText.isNotEmpty()) {
                        IconButton(
                            onClick = { searchText = "" }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                }
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {


            //filter when not search
            if (searchText.isEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 8.dp)
                ) {
                    Button(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = { viewModel.onCategorySelected(null) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (selectedCategory == null)
                                    MaterialTheme.colorScheme.primary
                                else Color.Gray.copy(alpha = 0.2f)
                        )
                    ) {
                        Text(
                            text = "All",
                            color =
                                if (selectedCategory == null)
                                    MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    categories.forEach { category ->
                        Button(
                            onClick = { viewModel.onCategorySelected(category) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    if (category == selectedCategory)
                                        MaterialTheme.colorScheme.primary
                                    else Color.Gray.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.padding(end = 8.dp),
                        ) {
                            Text(
                                text = category.replaceFirstChar { it.uppercase() },
                                color =
                                    if (category == selectedCategory)
                                        MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }



            //rest ui

            when(state) {

                // Loading
                is ProductUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                // Error
                is ProductUiState.Error -> {
                    val message = (state as ProductUiState.Error).message

                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = message ?: "Network Error",
                            style = MaterialTheme.typography.titleMedium.copy(

                            ),
                            color = Color.Red
                        )

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = { viewModel.loadProducts() }
                        ) {
                            Text("Retry")
                        }
                    }
                }


                //empty
                is ProductUiState.Empty -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        val message = when {
                            filterResults.isNotEmpty() -> "No products in this category"
                            searchText.isNotEmpty() -> "No products match your search"
                            else -> "No products available"
                        }

                        Text(
                            text = message,
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }



                // Product List
                is ProductUiState.Success -> {
                    //is empty
                    if (displayList.isEmpty()){
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            val message = when {
                                filterResults.isNotEmpty() -> "No products in this category"
                                searchText.isNotEmpty() -> "No products match your search"
                                else -> "No products available"
                            }

                            Text(
                                text = message,
                                modifier = Modifier.align(Alignment.Center),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    //show list
                    else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentPadding = PaddingValues(vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            items(displayList) { product ->
                                ProductItemView(
                                    product = product,
                                    onClick = {
                                        onItemClick(product)
                                    }
                                )
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}