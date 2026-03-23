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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.unrait.ui.theme.UnraitTheme

// Colores de la app
val NavyBlue = Color(0xFF1B2A47)
val OrangePrimary = Color(0xFFE66A25)
val WhiteBackground = Color(0xFFF5F5F5)

// Variable para controlar qué pantalla mostrar
var mostrarPantalla = mutableStateOf("main")

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
    // Estado para controlar el menú lateral
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Según el valor de mostrarPantalla, mostramos una pantalla u otra
    when (mostrarPantalla.value) {
        "main" -> {
            // Pantalla principal con el menú
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(
                        alCerrarDrawer = { scope.launch { drawerState.close() } }
                    )
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
        "login" -> {
            // Pantalla de Iniciar Sesión
            LoginScreen()
        }
        "registro" -> {
            // Pantalla de Registro
            RegistroScreen()
        }
    }
}

@Composable
fun LoginScreen() {
    // Variables para guardar lo que escribe el usuario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "UNRAIT",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = OrangePrimary,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtítulo
        Text(
            text = "Bienvenido de vuelta",
            fontSize = 18.sp,
            color = NavyBlue,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de correo
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrangePrimary,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = OrangePrimary,
                unfocusedLabelColor = Color.Gray,
                cursorColor = OrangePrimary,
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrangePrimary,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = OrangePrimary,
                unfocusedLabelColor = Color.Gray,
                cursorColor = OrangePrimary,
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de iniciar sesión
        Button(
            onClick = {
                mostrarPantalla.value = "main"
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangePrimary
            )
        ) {
            Text(
                text = "Iniciar Sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Enlace para ir a registro
        TextButton(
            onClick = {
                mostrarPantalla.value = "registro"
            }
        ) {
            Text(
                text = "¿No tienes cuenta? Regístrate,",
                color = OrangePrimary
            )
        }

        // Enlace para contraseña olvidada
        TextButton(
            onClick = {
                // Aquí irá la funcionalidad después
            }
        ) {
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Color.Gray
            )
        }
    }
}

// FUNCIÓN AUXILIAR PARA COLORES DE CAMPOS
@Composable
fun getCampoColors(habilitado: Boolean, datosValidados: Boolean): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        // Colores para estado deshabilitado (cuando ya se validó)
        disabledTextColor = if (!habilitado && datosValidados) NavyBlue else Color.Transparent,
        disabledBorderColor = if (!habilitado && datosValidados) Color.Gray else Color.Transparent,
        disabledLabelColor = if (!habilitado && datosValidados) Color.Gray else Color.Transparent,
        disabledPlaceholderColor = if (!habilitado && datosValidados) Color.Gray else Color.Transparent,
        disabledLeadingIconColor = if (!habilitado && datosValidados) Color.Gray else Color.Transparent,
        disabledTrailingIconColor = if (!habilitado && datosValidados) Color.Gray else Color.Transparent,

        // Colores para estado habilitado (siempre definidos)
        focusedTextColor = NavyBlue,
        unfocusedTextColor = NavyBlue,
        focusedBorderColor = OrangePrimary,
        unfocusedBorderColor = Color.Gray,
        focusedLabelColor = OrangePrimary,
        unfocusedLabelColor = Color.Gray,
        cursorColor = OrangePrimary,

        // Colores de error
        errorBorderColor = Color.Red,
        errorLabelColor = Color.Red,
        errorTextColor = NavyBlue,

        // Colores de soporte
        focusedSupportingTextColor = Color.Gray,
        unfocusedSupportingTextColor = Color.Gray
    )
}

@Composable
fun RegistroScreen() {
    // Variables para guardar lo que escribe el usuario
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // NUEVOS CAMPOS PARA RENAPO
    var curp by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var entidadNacimiento by remember { mutableStateOf("") }

    // Estados para la validación
    var isValidando by remember { mutableStateOf(false) }
    var errorValidacion by remember { mutableStateOf<String?>(null) }
    var datosValidados by remember { mutableStateOf(false) }

    // Estado para el menú de sexo
    var sexoMenuExpandido by remember { mutableStateOf(false) }

    // Variable para controlar si los campos están habilitados
    val camposHabilitados = !datosValidados

    // Scroll para que quepa todo
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "UNRAIT",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = OrangePrimary,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtítulo
        Text(
            text = "Crea tu cuenta",
            fontSize = 18.sp,
            color = NavyBlue,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== SECCIÓN DE CURP =====
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF0F0F0)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Validación con CURP",
                    fontWeight = FontWeight.Bold,
                    color = NavyBlue,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo CURP
                OutlinedTextField(
                    value = curp,
                    onValueChange = {
                        curp = it.uppercase().filter { char -> char.isLetterOrDigit() }
                        if (curp.length > 18) curp = curp.substring(0, 18)
                        datosValidados = false
                    },
                    label = { Text("CURP") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = curp.isNotEmpty() && curp.length != 18,
                    supportingText = {
                        if (curp.isNotEmpty() && curp.length != 18) {
                            Text(
                                text = "La CURP debe tener 18 caracteres",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = OrangePrimary,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = OrangePrimary,
                        focusedTextColor = NavyBlue,
                        unfocusedTextColor = NavyBlue,
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red
                    ),
                    trailingIcon = {
                        if (curp.length == 18 && !datosValidados) {
                            IconButton(
                                onClick = {
                                    // Aquí validarías con la API
                                    isValidando = true
                                    // Simulación de validación
                                    isValidando = false
                                    datosValidados = true
                                    errorValidacion = null

                                    // Auto-llenar campos con datos de ejemplo
                                    nombre = "JUAN"
                                    apellidoPaterno = "PEREZ"
                                    apellidoMaterno = "GOMEZ"
                                    fechaNacimiento = "15/05/1990"
                                    sexo = "Hombre"
                                    entidadNacimiento = "CIUDAD DE MÉXICO"
                                }
                            ) {
                                if (isValidando) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        strokeWidth = 2.dp,
                                        color = OrangePrimary
                                    )
                                } else {
                                    Icon(
                                        Icons.Default.Verified,
                                        contentDescription = "Validar",
                                        tint = OrangePrimary
                                    )
                                }
                            }
                        }
                    }
                )

                // Mensaje de error si falla validación
                errorValidacion?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== CAMPOS DE DATOS PERSONALES =====
        // Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it.uppercase() },
            label = { Text("Nombre(s)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = camposHabilitados,
            colors = getCampoColors(camposHabilitados, datosValidados)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Apellido Paterno
        OutlinedTextField(
            value = apellidoPaterno,
            onValueChange = { apellidoPaterno = it.uppercase() },
            label = { Text("Apellido Paterno") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = camposHabilitados,
            colors = getCampoColors(camposHabilitados, datosValidados)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Apellido Materno
        OutlinedTextField(
            value = apellidoMaterno,
            onValueChange = { apellidoMaterno = it.uppercase() },
            label = { Text("Apellido Materno") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = camposHabilitados,
            colors = getCampoColors(camposHabilitados, datosValidados)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Fecha de Nacimiento
        OutlinedTextField(
            value = fechaNacimiento,
            onValueChange = {
                // Formato automático DD/MM/AAAA
                var formatted = it.replace("/", "")
                if (formatted.length > 2) {
                    formatted = formatted.substring(0, 2) + "/" + formatted.substring(2)
                }
                if (formatted.length > 5) {
                    formatted = formatted.substring(0, 5) + "/" + formatted.substring(5)
                }
                if (formatted.length > 10) formatted = formatted.substring(0, 10)
                fechaNacimiento = formatted
            },
            label = { Text("Fecha de Nacimiento (DD/MM/AAAA)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = camposHabilitados,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = getCampoColors(camposHabilitados, datosValidados)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Sexo con opciones - VERSIÓN ULTRA SIMPLE
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Sexo",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
            )

            Box {
                OutlinedTextField(
                    value = sexo,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Selecciona una opción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { sexoMenuExpandido = !sexoMenuExpandido },
                    enabled = camposHabilitados,
                    colors = getCampoColors(camposHabilitados, datosValidados),
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = OrangePrimary
                        )
                    }
                )

                DropdownMenu(
                    expanded = sexoMenuExpandido,
                    onDismissRequest = { sexoMenuExpandido = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DropdownMenuItem(
                        text = { Text("Hombre") },
                        onClick = {
                            sexo = "Hombre"
                            sexoMenuExpandido = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Mujer") },
                        onClick = {
                            sexo = "Mujer"
                            sexoMenuExpandido = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Entidad de Nacimiento
        OutlinedTextField(
            value = entidadNacimiento,
            onValueChange = { entidadNacimiento = it.uppercase() },
            label = { Text("Entidad de Nacimiento") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = camposHabilitados,
            colors = getCampoColors(camposHabilitados, datosValidados)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ===== DATOS DE ACCESO =====
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF0F0F0)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Datos de Acceso",
                    fontWeight = FontWeight.Bold,
                    color = NavyBlue,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Correo electrónico
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = OrangePrimary,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = OrangePrimary,
                        focusedTextColor = NavyBlue,
                        unfocusedTextColor = NavyBlue
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = OrangePrimary,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = OrangePrimary,
                        focusedTextColor = NavyBlue,
                        unfocusedTextColor = NavyBlue
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Confirmar contraseña
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = confirmPassword.isNotEmpty() && password != confirmPassword,
                    supportingText = {
                        if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                            Text(
                                text = "Las contraseñas no coinciden",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = OrangePrimary,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = OrangePrimary,
                        focusedTextColor = NavyBlue,
                        unfocusedTextColor = NavyBlue,
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de registrarse - Habilitado solo si los datos son válidos
        Button(
            onClick = {
                mostrarPantalla.value = "main"
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = curp.length == 18 &&
                    datosValidados &&
                    email.isNotEmpty() &&
                    password.isNotEmpty() &&
                    password == confirmPassword,
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangePrimary,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(
                text = "Registrarse",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Enlace para ir a login
        TextButton(
            onClick = {
                mostrarPantalla.value = "login"
            }
        ) {
            Text(
                text = "¿Ya tienes cuenta? Inicia sesión",
                color = OrangePrimary
            )
        }
    }
}

@Composable
fun DrawerContent(alCerrarDrawer: () -> Unit) {
    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        modifier = Modifier.width(300.dp)
    ) {
        // Encabezado del Drawer
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

        // Opciones del menú
        DrawerMenuItem(icon = Icons.Filled.People, text = "Amigos")
        DrawerMenuItem(icon = Icons.Filled.Place, text = "Lugares")
        DrawerMenuItem(icon = Icons.Filled.History, text = "Historial")
        DrawerMenuItem(icon = Icons.Filled.DirectionsBus, text = "Viajes")
        DrawerMenuItem(icon = Icons.Filled.CheckCircle, text = "Disponibles")
        DrawerMenuItem(icon = Icons.Filled.LocationCity, text = "Localidades")

        // Separador
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)

        // Botón de Iniciar Sesión
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    alCerrarDrawer()  // Cierra el menú
                    mostrarPantalla.value = "login"  // Cambia a la pantalla de login
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Login,
                contentDescription = "Iniciar Sesión",
                tint = OrangePrimary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Iniciar Sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = NavyBlue
            )
        }
    }
}

// Esta función es para los otros items del menú
@Composable
fun DrawerMenuItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Por ahora no hacen nada
            }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = OrangePrimary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = NavyBlue
        )
    }
}

@Composable
fun TopSection(onOpenDrawer: () -> Unit) {
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
            // Botón para abrir el menú
            IconButton(onClick = onOpenDrawer) {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = "Perfil",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Título UNRAIT
            Text(
                text = "UNRAIT",
                color = OrangePrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )

            // Menú de 3 puntos
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "Opciones",
                        tint = Color.White
                    )
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
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var searchQuery by remember { mutableStateOf("") }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                placeholder = { Text("Buscar destino en La Paz...") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = OrangePrimary,
                    cursorColor = OrangePrimary
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Icono de notificaciones
            Box(contentAlignment = Alignment.TopEnd) {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = "Notificaciones",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                // Punto rojo de notificación
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(OrangePrimary)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Composable
fun BottomNavSection() {
    var selectedItem by remember { mutableStateOf(1) }
    var showModal by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp, top = 8.dp)
            .height(70.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón de Home
        AnimatedNavItem(
            icon = Icons.Filled.Home,
            isSelected = selectedItem == 1,
            isCenter = true
        ) {
            // No hace nada por ahora
        }

        // Botón de Search
        AnimatedNavItem(
            icon = Icons.Filled.Search,
            isSelected = selectedItem == 1,
            isCenter = true
        ){
            selectedItem = 1
            showModal = true
        }

        // Botón de Bus
        AnimatedNavItem(
            icon = Icons.Filled.DirectionsBus,
            isSelected = selectedItem == 2
        ) {
            selectedItem = 2
        }
    }

    // Ventana modal de búsqueda
    if (showModal) {
        SearchModal(
            onClose = { showModal = false }
        )
    }
}

@Composable
fun SearchModal(onClose: () -> Unit) {
    var origen by remember { mutableStateOf("Mi ubicación") }
    var destino by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {},
        text = {
            Column {
                // Título y botón cerrar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "¿A dónde vamos?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Campo de origen
                OutlinedTextField(
                    value = origen,
                    onValueChange = { origen = it },
                    label = { Text("Punto de partida") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = OrangePrimary,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = OrangePrimary
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Campo de destino
                OutlinedTextField(
                    value = destino,
                    onValueChange = { destino = it },
                    label = { Text("Destino") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = OrangePrimary,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = OrangePrimary
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Botón de buscar
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary
                    )
                ) {
                    Text("Buscar")
                }
            }
        }
    )
}

@Composable
fun AnimatedNavItem(
    icon: ImageVector,
    isSelected: Boolean,
    isCenter: Boolean = false,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1.0f,
        animationSpec = tween(durationMillis = 300),
        label = "scale"
    )
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .size(if (isCenter) 60.dp else 48.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(if (isSelected && isCenter) OrangePrimary else Color.Transparent)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected && isCenter) Color.White
            else if (isSelected) OrangePrimary
            else Color.Gray,
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