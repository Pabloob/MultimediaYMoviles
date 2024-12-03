package com.example.FitCraft

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

// Estilos globales
val InputPlaceholderColor = Color(0xFFB0B0B0)
val ButtonBackgroundColor = Color(0xFF3A3B3C)
val ButtonTextColor = Color.White
val TextTitleStyle = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color.White)

@Composable
fun NavController(
    navHostController: NavHostController,
    usuarioViewModel: UsuarioViewModel
) {

    NavHost(
        navController = navHostController,
        startDestination = "VentanaIniciarSesion"
    ) {
        composable("VentanaIniciarSesion") {
            IniciarSesion(navHostController, usuarioViewModel)
        }
        composable("VentanaRegistrar") {
            Registrar(navHostController)
        }
        composable("VentanaInicio") {
            Inicio(navHostController, usuarioViewModel)
        }
        composable("VentanaAjustes") {
            Ajustes(navHostController, usuarioViewModel)
        }
        composable("VentanaBorrarCuentas") {
            Borrar(navHostController)
        }
        composable("VentanaEjercicios") {
            MostrarEjercicios()
        }
        composable("VentanaCrearRutina") {
            CrearRutina(navHostController)
        }
    }
}


@Composable
fun Titulo(titulo: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        Text(text = titulo, style = TextTitleStyle)
    }
}

@Composable
fun GenericButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonBackgroundColor,
            contentColor = ButtonTextColor
        )
    ) {
        Text(text)
    }
}

@Composable
fun CenteredRow(
    paddingBottom: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = paddingBottom),
        content = content
    )
}

@Composable
fun HeaderText(text: String) {
    CenteredRow {
        Text(
            text = text,
            color = Color.White,
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun InputConImagen(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    imageRes: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xFFB0B0B0)) },
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            modifier = Modifier
                .weight(1f)
                .height(60.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun InputContrasenaConImagen(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    imageRes: Int
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xFFB0B0B0)) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                Text(
                    text = if (passwordVisible) "Ocultar" else "Mostrar",
                    color = Color(0xFFB0B0B0),
                    modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                )
            },
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            modifier = Modifier
                .weight(1f)
                .height(60.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    }
}


@Composable
fun DividerConEspaciado() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        color = Color.Black
    )
}

@Composable
fun Boton(texto: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A3B3C),
                contentColor = Color.White
            )
        ) {
            Text(texto)
        }
    }
}

@Composable
fun TextoInteractivo(texto: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Text(
        text = texto,
        color = Color(0xffd1d5db),
        modifier = modifier
            .clickable { onClick() }
            .padding(top = 16.dp)
    )
}

@Composable
fun BotonCrearCuenta(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3A3B3C),
            contentColor = Color.White
        )
    ) {
        Text("Crear cuenta")
    }
}

@Composable
fun RowCard(nombreRutina: String, horaInicio: String, horaFin: String, imageRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xff2D2F3A))
            .height(180.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp)
        ) {
            Text(
                text = nombreRutina,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "$horaInicio - $horaFin",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InputPlaceholderColor
                )
            )
        }
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp, bottom = 16.dp)
                .align(Alignment.Bottom)
        )
    }
}

@Composable
fun LineChartComponent() {
    AndroidView(
        modifier = Modifier
            .padding(top = 30.dp)
            .width(400.dp)
            .height(200.dp),
        factory = { context ->
            LineChart(context).apply {
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(false)
                description.isEnabled = false
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.textColor = Color.White.toArgb()
                xAxis.setDrawGridLines(false)
                xAxis.valueFormatter = IndexAxisValueFormatter(
                    listOf("Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom")
                )

                axisLeft.textColor = Color.White.toArgb()
                axisLeft.setDrawGridLines(true)
                axisLeft.axisMinimum = 0f
                axisLeft.axisMaximum = 6f
                axisLeft.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "${value.toInt()}h"
                    }
                }

                axisRight.isEnabled = false
                legend.isEnabled = false
                data = createLineData()
                animateX(1500)
            }
        }
    )
}

@Composable
fun BottomNavBar(navController: NavController, modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .height(70.dp)
            .fillMaxWidth()
            .background(Color(0xff1c1e26)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(100.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth()
        ) {
            BottomNavIcon(
                imageRes = R.drawable.btn_inicio,
                onClick = { navController.navigate("VentanaInicio") }
            )
            BottomNavIcon(
                imageRes = R.drawable.btn_mas,
                onClick = { navController.navigate("VentanaCrearRutina") }
            )
            BottomNavIcon(
                imageRes = R.drawable.btn_configuracion,
                onClick = { navController.navigate("VentanaAjustes") }
            )
        }
    }
}

@Composable
fun BottomNavIcon(imageRes: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier
            .size(20.dp)
            .clickable { onClick() }
    )
}


@Composable
fun InputFieldWithLabel(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = Color.White,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.width(100.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, color = Color(0xFFB0B0B0)) },
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            modifier = Modifier
                .weight(1f)
                .height(60.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ClickableRow(text: String, color: Color, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            color = color,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun CartaEjercicio(ejercicio: Ejercicio) {
    // Estados para cada campo
    var auxSeries by remember { mutableStateOf("") }
    var auxRepeticiones by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xff2D2F3A))
            .padding(16.dp)
            .height(220.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // Permite que la columna ocupe espacio proporcional
        ) {
            // Título del ejercicio
            Text(
                text = ejercicio.nombreEjercicio,
                color = Color.White,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Campo para series
            TextField(
                value = auxSeries,
                onValueChange = { auxSeries = it },
                placeholder = { Text("Series", color = Color(0xFFB0B0B0)) },
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(50.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // Campo para repeticiones
            TextField(
                value = auxRepeticiones,
                onValueChange = { auxRepeticiones = it },
                placeholder = { Text("Repeticiones", color = Color(0xFFB0B0B0)) },
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(50.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // Campo para descripcion
            Text(
                text = ejercicio.descripcion,
                color = Color.White,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .verticalScroll(rememberScrollState())

            )
        }

        // Imagen a la derecha
        Image(
            painter = painterResource(
                id = if (ejercicio.tipoEjercicio == "Pierna") {
                    R.drawable.ejercicio_pierna
                } else if (ejercicio.tipoEjercicio == "Espalda") {
                    R.drawable.ejercicio_espalda
                } else if (ejercicio.tipoEjercicio == "Pecho") {
                    R.drawable.ejercicico_pecho
                } else if (ejercicio.tipoEjercicio == "Brazo") {
                    R.drawable.ejercicio_brazo
                } else {
                    R.drawable.def
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp) // Tamaño ajustado de la imagen
                .padding(start = 16.dp) // Separación con la columna
                .align(Alignment.CenterVertically) // Centrar verticalmente en el Row
        )
    }
}


@Preview(showBackground = true)
@Composable
fun prev() {
    // Inicialización de un ejemplo de Ejercicio
    val ejercicio = Ejercicio(
        nombreEjercicio = "Flexiones",
        repeticiones = 15,
        series = 2,
        descripcion = "Flexiones",
        tipoEjercicio = "Pierna"
    )
    // Llamada a la función composable
    CartaEjercicio(ejercicio = ejercicio)
}
