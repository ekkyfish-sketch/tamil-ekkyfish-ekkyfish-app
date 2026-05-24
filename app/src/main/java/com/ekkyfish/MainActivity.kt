@file:OptIn(ExperimentalMaterial3Api::class)

package com.ekkyfish

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ekkyfish.data.model.CartItem
import com.ekkyfish.data.model.Fish
import com.ekkyfish.network.OrderNotificationRequest
import com.ekkyfish.network.sendOrderNotifications
import com.ekkyfish.ui.theme.EkkyFishTheme
import com.ekkyfish.ui.viewmodel.CartViewModel
import com.ekkyfish.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EkkyFishTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val productViewModel = remember { ProductViewModel() }
    val cartViewModel = remember { CartViewModel() }
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf("home") }
    var selectedFish by remember { mutableStateOf<Fish?>(null) }

    when (currentScreen) {
        "home" -> HomeScreen(
            onNavigateToProducts = { currentScreen = "products" },
            cartItemCount = cartViewModel.getCartItemCount()
        )
        "products" -> ProductListScreen(
            productViewModel = productViewModel,
            onSelectProduct = {
                selectedFish = it
                currentScreen = "detail"
            },
            onNavigateToCart = { currentScreen = "cart" },
            onNavigateHome = { currentScreen = "home" },
            cartItemCount = cartViewModel.getCartItemCount()
        )
        "detail" -> ProductDetailScreen(
            fish = selectedFish,
            cartViewModel = cartViewModel,
            onBack = { currentScreen = "products" },
            onNavigateToCart = { currentScreen = "cart" }
        )
        "cart" -> CartScreen(
            cartViewModel = cartViewModel,
            onNavigateToCheckout = { currentScreen = "checkout" },
            onBack = { currentScreen = "products" }
        )
        "checkout" -> CheckoutScreen(
            cartViewModel = cartViewModel,
            onOrderPlaced = { ownerMessage, customerMessage, customerPhone ->
                val ownerPhone = "9884958545"
                val request = OrderNotificationRequest(
                    ownerPhone = ownerPhone,
                    ownerMessage = ownerMessage,
                    customerPhone = customerPhone,
                    customerMessage = customerMessage
                )

                currentScreen = "success"
                cartViewModel.clearCart()

                launchBackendOrderNotification(
                    context = context,
                    request = request,
                    fallbackOwnerPhone = ownerPhone,
                    fallbackOwnerMessage = ownerMessage,
                    fallbackCustomerPhone = customerPhone,
                    fallbackCustomerMessage = customerMessage
                )
            },
            onBack = { currentScreen = "cart" }
        )
        "success" -> OrderSuccessScreen(
            onContinueShopping = {
                currentScreen = "home"
            }
        )
    }
}

private fun launchBackendOrderNotification(
    context: Context,
    request: OrderNotificationRequest,
    fallbackOwnerPhone: String,
    fallbackOwnerMessage: String,
    fallbackCustomerPhone: String,
    fallbackCustomerMessage: String
) {
    kotlinx.coroutines.CoroutineScope(Dispatchers.Main).launch {
        val sentSuccessfully = withContext(Dispatchers.IO) {
            sendOrderNotifications(request)
        }

        if (!sentSuccessfully) {
            sendWhatsAppOrderNotification(context, fallbackOwnerPhone, fallbackOwnerMessage)
            sendCustomerSmsNotification(context, fallbackCustomerPhone, fallbackCustomerMessage)
        }
    }
}

private fun sendWhatsAppOrderNotification(context: Context, ownerPhone: String, message: String) {
    val sanitizedPhone = ownerPhone.filter { it.isDigit() }
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
        putExtra("jid", "$sanitizedPhone@s.whatsapp.net")
        setPackage("com.whatsapp")
    }

    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        val fallback = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$sanitizedPhone?text=${Uri.encode(message)}"))
        context.startActivity(fallback)
    }
}

private fun sendCustomerSmsNotification(context: Context, customerPhone: String, message: String) {
    val sanitizedPhone = customerPhone.filter { it.isDigit() }
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("smsto:$sanitizedPhone")
        putExtra("sms_body", message)
    }

    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        val fallback = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$sanitizedPhone?body=${Uri.encode(message)}"))
        context.startActivity(fallback)
    }
}

private fun buildOrderMessage(
    fullName: String,
    phone: String,
    address: String,
    paymentMethod: String,
    cartItems: List<CartItem>,
    totalPrice: Double
): String {
    val itemsText = cartItems.joinToString("\n") { item ->
        "- ${item.quantity}x ${item.fish.name} = ₹${item.fish.price * item.quantity}"
    }

    return """
        New EkkyFish Order
        Customer: $fullName
        Phone: $phone
        Address: $address
        Payment: $paymentMethod
        Total: ₹$totalPrice

        Items:
        $itemsText
    """.trimIndent()
}

private fun buildCustomerOrderMessage(
    fullName: String,
    phone: String,
    address: String,
    paymentMethod: String,
    cartItems: List<CartItem>,
    totalPrice: Double
): String {
    val itemsText = cartItems.joinToString("\n") { item ->
        "- ${item.quantity}x ${item.fish.name} = ₹${item.fish.price * item.quantity}"
    }

    return """
        Dear $fullName,

        Your EkkyFish order has been placed successfully.
        Delivery Address: $address
        Payment: $paymentMethod
        Total: ₹$totalPrice

        Items:
        $itemsText

        We will contact you shortly on $phone.
    """.trimIndent()
}

@Composable
fun HomeScreen(
    onNavigateToProducts: () -> Unit,
    cartItemCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🐟 EkkyFish",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Welcome to EkkyFish!",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Your ultimate fish ordering and delivery application",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Key Features",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                FeatureItem("✓ Fresh fish delivery to your doorstep")
                FeatureItem("✓ Real-time order tracking")
                FeatureItem("✓ Multiple payment options")
                FeatureItem("✓ Best prices guaranteed")
                FeatureItem("✓ Customer support 24/7")
            }
        }

        Button(
            onClick = onNavigateToProducts,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Start Shopping",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "Version 1.0.0",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}

@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel,
    onSelectProduct: (Fish) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateHome: () -> Unit,
    cartItemCount: Int
) {
    val fishList by productViewModel.fishList
    val searchQuery by productViewModel.searchQuery

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Fish Products") },
            navigationIcon = {
                IconButton(onClick = onNavigateHome) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
            },
            actions = {
                BadgedBox(
                    badge = {
                        if (cartItemCount > 0) {
                            Badge {
                                Text(text = cartItemCount.toString())
                            }
                        }
                    }
                ) {
                    IconButton(onClick = onNavigateToCart) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            }
        )

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { productViewModel.searchFish(it) },
            placeholder = { Text("Search fish...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        // Fish List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(fishList) { fish ->
                FishProductCard(
                    fish = fish,
                    onSelect = { onSelectProduct(fish) }
                )
            }
        }
    }
}

@Composable
fun FishProductCard(
    fish: Fish,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = fish.inStock) { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("🐟", fontSize = 48.sp)
            }

            // Product Info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = fish.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = fish.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${fish.rating} (${fish.reviews})",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            // Price
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "₹${fish.price}/${fish.unit}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                if (!fish.inStock) {
                    Text(
                        text = "Out of Stock",
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(
    fish: Fish?,
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    if (fish == null) return

    var quantity by remember { mutableStateOf(1) }
    var showAddedMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        TopAppBar(
            title = { Text("Product Details") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("🐟", fontSize = 96.sp)
            }

            // Product Name
            Text(
                text = fish.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Category
            Text(
                text = "Category: ${fish.category}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Rating
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${fish.rating} (${fish.reviews} reviews)",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Description
            Text(
                text = fish.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // Price
            Text(
                text = "₹${fish.price}/${fish.unit}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Quantity Selector
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Select Quantity",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier.size(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("-", fontSize = 20.sp)
                        }

                        Text(
                            text = "$quantity ${fish.unit}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )

                        Button(
                            onClick = { quantity++ },
                            modifier = Modifier.size(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("+", fontSize = 20.sp)
                        }
                    }
                }
            }

            // Total Price
            Text(
                text = "Total: ₹${fish.price * quantity}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            // Add to Cart Button
            Button(
                onClick = {
                    cartViewModel.addToCart(fish, quantity)
                    showAddedMessage = true
                    quantity = 1
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Add to Cart", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            if (showAddedMessage) {
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(2000)
                    showAddedMessage = false
                }
                
                Text(
                    text = "✓ Added to cart!",
                    fontSize = 14.sp,
                    color = Color.Green,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onNavigateToCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("View Cart", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onNavigateToCheckout: () -> Unit,
    onBack: () -> Unit
) {
    val cartItems by cartViewModel.cartItems
    val totalPrice by cartViewModel.totalPrice

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Shopping Cart") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Your cart is empty",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Continue Shopping")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onQuantityChange = { cartViewModel.updateQuantity(item.fish.id, it) },
                        onRemove = { cartViewModel.removeFromCart(item.fish.id) }
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal:", fontWeight = FontWeight.SemiBold)
                        Text("₹$totalPrice", fontWeight = FontWeight.SemiBold)
                    }

                    Divider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Delivery:", fontWeight = FontWeight.SemiBold)
                        Text("₹50", fontWeight = FontWeight.SemiBold)
                    }

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Total:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "₹${totalPrice + 50}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Button(
                        onClick = onNavigateToCheckout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Proceed to Checkout", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: com.ekkyfish.data.model.CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("🐟", fontSize = 40.sp)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = item.fish.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "₹${item.fish.price}/${item.fish.unit}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onQuantityChange(item.quantity - 1) },
                        modifier = Modifier.size(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("-", fontSize = 12.sp)
                    }

                    Text(
                        text = "${item.quantity}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Button(
                        onClick = { onQuantityChange(item.quantity + 1) },
                        modifier = Modifier.size(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("+", fontSize = 12.sp)
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = "₹${item.fish.price * item.quantity}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Remove",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun CheckoutScreen(
    cartViewModel: CartViewModel,
    onOrderPlaced: (String, String, String) -> Unit,
    onBack: () -> Unit
) {
    val totalPrice by cartViewModel.totalPrice
    val cartItems by cartViewModel.cartItems
    val scope = rememberCoroutineScope()
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedPayment by remember { mutableStateOf("card") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = { Text("Checkout") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Delivery Details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Delivery Details",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) }
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Delivery Address") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        maxLines = 3,
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) }
                    )
                }
            }

            // Payment Method
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Payment Method",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    listOf("card", "upi", "wallet", "cod").forEach { method ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { selectedPayment = method },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedPayment == method,
                                onClick = { selectedPayment = method }
                            )
                            Text(
                                text = when (method) {
                                    "card" -> "Credit/Debit Card"
                                    "upi" -> "UPI"
                                    "wallet" -> "Digital Wallet"
                                    "cod" -> "Cash on Delivery"
                                    else -> ""
                                },
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // Order Summary
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Order Summary",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal:")
                        Text("₹$totalPrice")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Delivery Charge:")
                        Text("₹50")
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "₹${totalPrice + 50}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Place Order Button
            Button(
                onClick = {
                    if (fullName.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty()) {
                        isLoading = true
                        val ownerMessage = buildOrderMessage(
                            fullName = fullName,
                            phone = phone,
                            address = address,
                            paymentMethod = selectedPayment,
                            cartItems = cartItems,
                            totalPrice = totalPrice
                        )
                        val customerMessage = buildCustomerOrderMessage(
                            fullName = fullName,
                            phone = phone,
                            address = address,
                            paymentMethod = selectedPayment,
                            cartItems = cartItems,
                            totalPrice = totalPrice
                        )
                        scope.launch {
                            kotlinx.coroutines.delay(1500)
                            onOrderPlaced(ownerMessage, customerMessage, phone)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Place Order", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun OrderSuccessScreen(onContinueShopping: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color.Green
        )

        Text(
            text = "Order Placed Successfully! 🎉",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Thank you for your order. You will receive your fresh fish delivery within 24 hours.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onContinueShopping,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Continue Shopping", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Text(
            text = "Order ID: #ORD${System.currentTimeMillis() % 1000000}",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}

@Composable
fun FeatureItem(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 6.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    EkkyFishTheme {
        MainApp()
    }
}
