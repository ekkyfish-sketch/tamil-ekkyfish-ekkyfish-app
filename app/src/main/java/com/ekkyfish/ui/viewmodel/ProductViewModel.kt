package com.ekkyfish.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.ekkyfish.data.model.Fish

class ProductViewModel : ViewModel() {
    private val _fishList = mutableStateOf<List<Fish>>(getMockFishData())
    val fishList: State<List<Fish>> = _fishList

    private val _selectedFish = mutableStateOf<Fish?>(null)
    val selectedFish: State<Fish?> = _selectedFish

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    fun selectFish(fish: Fish) {
        _selectedFish.value = fish
    }

    fun clearSelectedFish() {
        _selectedFish.value = null
    }

    fun searchFish(query: String) {
        _searchQuery.value = query
        _fishList.value = getMockFishData().filter { fish ->
            fish.name.contains(query, ignoreCase = true) ||
            fish.category.contains(query, ignoreCase = true)
        }
    }

    fun getFishByCategory(category: String): List<Fish> {
        return getMockFishData().filter { it.category == category }
    }

    private fun getMockFishData(): List<Fish> {
        return listOf(
            Fish(
                id = "1",
                name = "Fresh Tilapia",
                description = "Premium quality fresh tilapia fish",
                price = 280.0,
                unit = "kg",
                category = "Tilapia",
                inStock = true,
                rating = 4.8f,
                reviews = 234
            ),
            Fish(
                id = "2",
                name = "Catfish",
                description = "Nutritious catfish with high protein content",
                price = 320.0,
                unit = "kg",
                category = "Catfish",
                inStock = true,
                rating = 4.6f,
                reviews = 187
            ),
            Fish(
                id = "3",
                name = "Prawns (Shrimp)",
                description = "Large size fresh prawns, ideal for special occasions",
                price = 450.0,
                unit = "kg",
                category = "Prawns",
                inStock = true,
                rating = 4.9f,
                reviews = 312
            ),
            Fish(
                id = "4",
                name = "Rohu",
                description = "Indian major carp, popular and affordable",
                price = 250.0,
                unit = "kg",
                category = "Rohu",
                inStock = true,
                rating = 4.5f,
                reviews = 156
            ),
            Fish(
                id = "5",
                name = "Mackerel",
                description = "Rich in omega-3, excellent for health",
                price = 380.0,
                unit = "kg",
                category = "Mackerel",
                inStock = true,
                rating = 4.7f,
                reviews = 198
            ),
            Fish(
                id = "6",
                name = "Carp",
                description = "Tender and flavorful carp fish",
                price = 290.0,
                unit = "kg",
                category = "Carp",
                inStock = true,
                rating = 4.4f,
                reviews = 142
            ),
            Fish(
                id = "7",
                name = "Salmon",
                description = "Premium imported salmon fillet",
                price = 650.0,
                unit = "kg",
                category = "Salmon",
                inStock = true,
                rating = 4.9f,
                reviews = 289
            ),
            Fish(
                id = "8",
                name = "Pomfret",
                description = "Delicate white fish, perfect for grilling",
                price = 420.0,
                unit = "kg",
                category = "Pomfret",
                inStock = true,
                rating = 4.8f,
                reviews = 223
            )
        )
    }
}
