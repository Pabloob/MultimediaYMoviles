package com.example.FitCraft

import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun Ajustes(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    val conexionPersonas = ConexionPersonas()
    var nombreUsuario by remember { mutableStateOf(usuarioViewModel.nombreUsuario) }
    val usuario = remember { mutableStateOf<Persona?>(null) }

    conexionPersonas.CargarPersonaPorNombreDesdeFirebase(nombreUsuario, usuario)

    var auxAltura by remember { mutableStateOf("") }
    var auxPeso by remember { mutableStateOf("") }

    LaunchedEffect(usuario.value) {
        usuario.value?.let {
            auxAltura = it.altura.toString()
            auxPeso = it.peso.toString()
        }
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.9f)
                .align(Alignment.TopCenter)
        ) {
            HeaderText("Ajustes")

            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color(0xff2D2F3A))
                    .padding(20.dp)
            ) {
                InputFieldWithLabel(
                    label = "Nombre",
                    value = nombreUsuario,
                    onValueChange = { nombreUsuario = it }
                )
                InputFieldWithLabel(
                    label = "Peso",
                    value = auxPeso,
                    onValueChange = { auxPeso = it }
                )
                InputFieldWithLabel(
                    label = "Altura",
                    value = auxAltura,
                    onValueChange = { auxAltura = it }
                )

                Spacer(modifier = Modifier.height(10.dp))

                ClickableRow(
                    text = "Cerrar sesión",
                    color = Color.Red,
                    onClick = {
                        navController.navigate("VentanaIniciarSesion")
                    }
                )

                ClickableRow(
                    text = "Borrar cuenta",
                    color = Color.Red,
                    onClick = {
                        if (conexionPersonas.borrarPersonaDeFirebase(nombreUsuario)) {
                            Toast.makeText(context, "Cuenta eliminada", Toast.LENGTH_SHORT).show()
                            navController.navigate("VentanaInicio")
                        } else {
                            Toast.makeText(context, "Error al borrar cuenta", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )

                ClickableRow(
                    text = "Borrar otra cuenta",
                    color = Color.Red,
                    onClick = {
                        navController.navigate("VentanaBorrarCuentas")
                    }
                )
            }
        }

        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
    }
}

@Preview
@Composable
private fun AjustesPreview() {
    val navController = rememberNavController()
    val usuarioViewModel = UsuarioViewModel()
    Ajustes(navController = navController, usuarioViewModel)
}
