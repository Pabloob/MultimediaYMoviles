package com.example.FitCraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.FitCraft.ui.theme.MyApplicationTheme


class VentanaCrearRutina : ComponentActivity() {
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
fun CrearRutina(navController: NavController) {
    val conexionJSONPersonas = ConexionJSONPersonas()
    val usuarios = remember { mutableStateListOf<Persona>() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        usuarios.clear()
        usuarios.addAll(conexionJSONPersonas.leerPersonasDesdeJson(context))
    }

    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var inicioSesion by remember { mutableStateOf(false) }

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
            Titulo("Crear rutina")

            Spacer(modifier = Modifier.height(16.dp))



            InputConImagen(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                placeholder = "Nombre rutina",
                imageRes = R.drawable.info_crear_rutina
            )

            DividerConEspaciado()

            Boton("Añadir ejercicio",onClick = {
            navController.navigate("MostrarEjercicios")
            }
            )

            Boton("Crear rutina",onClick = {
                navController.navigate("VentanaInicio")
            }
            )


        }
    }
}

@Preview
@Composable
private fun AndroidMedium1Preview() {
    val navController = rememberNavController()
    CrearRutina(navController = navController)
}