package com.example.unrait

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.unrait.ui.theme.UnraitTheme

// Paleta de colores
val NavyBlue = Color(0xFF1B2A47)
val OrangePrimary = Color(0xFFE66A25)
val WhiteBackground = Color(0xFFF5F5F5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnraitTheme() {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    // Estados para controlar el menú lateral
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Envolvemos toda la pantalla en el Drawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent() // Aquí está el diseño del menú lateral
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = NavyBlue,
            topBar = {
                TopSection(onOpenDrawer = {
                    scope.launch { drawerState.open() }
                })
            },
            bottomBar = { BottomNavSection() }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mapa de Transporte",
                    color = Color.Gray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DrawerContent() {
    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        modifier = Modifier.width(300.dp)
    ) {
        // Encabezado del Drawer (Único y llamativo)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavyBlue)
                .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Foto de perfil",
                    tint = Color.White,
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("Carlos", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Estudiante - Ing. en Sistemas", color = Color.LightGray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de opciones
        DrawerMenuItem(icon = Icons.Filled.People, text = "Amigos")
        DrawerMenuItem(icon = Icons.Filled.Place, text = "Lugares")
        DrawerMenuItem(icon = Icons.Filled.History, text = "Historial")
        DrawerMenuItem(icon = Icons.Filled.DirectionsBus, text = "Viajes")
        DrawerMenuItem(icon = Icons.Filled.CheckCircle, text = "Disponibles")
        DrawerMenuItem(icon = Icons.Filled.LocationCity, text = "Localidades")
    }
}

@Composable
fun DrawerMenuItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Navegar a la pantalla */ }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = OrangePrimary, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = NavyBlue)
    }
}

@Composable
fun TopSection(onOpenDrawer: () -> Unit) {
    // Estado para el menú de los 3 puntos
    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onOpenDrawer) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "Perfil", tint = Color.White, modifier = Modifier.size(32.dp))
            }

            Text(
                text = "UNRAIT",
                color = OrangePrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )

            // Contenedor para anclar el DropdownMenu a los 3 puntos
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Opciones", tint = Color.White)
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = { Text("Ajustes", color = NavyBlue) },
                        onClick = { showMenu = false },
                        leadingIcon = { Icon(Icons.Filled.Settings, tint = Color.Gray, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Comentarios", color = NavyBlue) },
                        onClick = { showMenu = false },
                        leadingIcon = { Icon(Icons.Filled.Comment, tint = Color.Gray, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Reportes", color = NavyBlue) },
                        onClick = { showMenu = false },
                        leadingIcon = { Icon(Icons.Filled.Report, tint = Color.Gray, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Ayuda y Soporte", color = NavyBlue) },
                        onClick = { showMenu = false },
                        leadingIcon = { Icon(Icons.Filled.HelpOutline, tint = Color.Gray, contentDescription = null) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var searchQuery by remember { mutableStateOf("") }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f).height(50.dp),
                placeholder = { Text("Buscar destino en La Paz...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Color.Gray) },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = OrangePrimary
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(contentAlignment = Alignment.TopEnd) {
                IconButton(onClick = { /* TODO: Notificaciones */ }) {
                    Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones", tint = Color.White, modifier = Modifier.size(28.dp))
                }
                Box(
                    modifier = Modifier.size(10.dp).clip(CircleShape).background(OrangePrimary).align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Composable
fun BottomNavSection() {
    var selectedItem by remember { mutableStateOf(1) }

    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp, top = 8.dp).height(70.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedNavItem(icon = Icons.Filled.Home, isSelected = selectedItem == 0) { selectedItem = 0 }
        AnimatedNavItem(icon = Icons.Filled.Search, isSelected = selectedItem == 1, isCenter = true) { selectedItem = 1 }
        AnimatedNavItem(icon = Icons.Filled.DirectionsBus, isSelected = selectedItem == 2) { selectedItem = 2 }
    }
}

@Composable
fun AnimatedNavItem(
    icon: ImageVector,
    isSelected: Boolean,
    isCenter: Boolean = false,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(targetValue = if (isSelected) 1.2f else 1.0f, animationSpec = tween(durationMillis = 300), label = "scale")
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .size(if (isCenter) 60.dp else 48.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(if (isSelected && isCenter) OrangePrimary else Color.Transparent)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected && isCenter) Color.White else if (isSelected) OrangePrimary else Color.Gray,
            modifier = Modifier.size(if (isCenter) 32.dp else 28.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UnraitTheme() {
        HomeScreen()
    }
}