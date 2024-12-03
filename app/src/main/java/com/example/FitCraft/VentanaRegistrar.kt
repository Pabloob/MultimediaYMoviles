package com.example.FitCraft

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.FitCraft.ui.theme.MyApplicationTheme
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class VentanaSecundaria : ComponentActivity() {
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavController(
                    navHostController = navController,
                    usuarioViewModel = usuarioViewModel
                )
            }
        }
    }
}

@Composable
internal fun Registrar(navController: NavController) {
    val database = FirebaseDatabase.getInstance()
    val usuariosRef = database.getReference("personas")

    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("Seleccionar fecha") }
    var auxAltura by remember { mutableStateOf("") }
    var auxPeso by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            fechaNacimiento = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xff1c1e26))
                .padding(30.dp)
        ) {
            Titulo("FitCraft")

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = { Text("Nombre", color = Color(0xFFB0B0B0)) },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .align(Alignment.Top),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = apellidos,
                    onValueChange = { apellidos = it },
                    placeholder = { Text("Apellidos", color = Color(0xFFB0B0B0)) },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .align(Alignment.Top),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.info_datos_usuario),
                    contentDescription = "imagen nombre y apellidos",
                    modifier = Modifier.size(50.dp)
                )
            }

            DividerConEspaciado()

            // Fecha de nacimiento
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = fechaNacimiento,
                    color = Color(0xFFB0B0B0),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { datePickerDialog.show() }
                )
                Image(
                    painter = painterResource(id = R.drawable.info_fecha_nacimiento),
                    contentDescription = "Edad",
                    modifier = Modifier.size(50.dp)
                )
            }

            DividerConEspaciado()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = auxAltura,
                    onValueChange = { auxAltura = it },
                    placeholder = { Text("Altura", color = Color(0xFFB0B0B0)) },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .align(Alignment.Top),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = auxPeso,
                    onValueChange = { auxPeso = it },
                    placeholder = { Text("Peso", color = Color(0xFFB0B0B0)) },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .align(Alignment.Top),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            DividerConEspaciado()

            InputConImagen(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                placeholder = "Nombre de usuario",
                imageRes = R.drawable.info_nombre_usuario
            )

            DividerConEspaciado()

            InputConImagen(
                value = contrasena,
                onValueChange = { contrasena = it },
                placeholder = "Contraseña",
                imageRes = R.drawable.info_contrasena
            )

            DividerConEspaciado()

            // Botón para crear cuenta
            BotonCrearCuenta(
                onClick = {
                    val altura = auxAltura.toFloatOrNull() ?: 0f
                    val peso = auxPeso.toFloatOrNull() ?: 0f

                    if (validarEntrada(
                            nombre, apellidos, fechaNacimiento,
                            altura, peso, nombreUsuario, contrasena
                        )
                    ) {
                        val nuevaPersona = Persona(
                            idPersona = 0, // Será generado por Firebase
                            nombre = nombre,
                            apellidos = apellidos,
                            fechaNacimiento = fechaNacimiento,
                            altura = altura,
                            peso = peso,
                            nombreUsuario = nombreUsuario,
                            contrasena = contrasena
                        )
                        usuariosRef.push().setValue(nuevaPersona).addOnCompleteListener {
                            if (it.isSuccessful) {
                                navController.navigate("VentanaIniciarSesion")
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextoInteractivo(
                texto = "Iniciar sesión",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { navController.popBackStack() }
            )
        }
    }
}

fun validarEntrada(
    nombre: String,
    apellidos: String,
    fechaNacimiento: String,
    altura: Float,
    peso: Float,
    nombreUsuario: String,
    contrasena: String
): Boolean {
    return nombre.isNotEmpty() && apellidos.isNotEmpty() &&
            fechaNacimiento.isNotEmpty() && altura > 0 &&
            peso > 0 && nombreUsuario.isNotEmpty() &&
            contrasena.isNotEmpty()
}

@Preview
@Composable
private fun Prev() {
    val navController = rememberNavController()
    Registrar(navController)
}
