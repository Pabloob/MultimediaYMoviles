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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.FitCraft.ui.theme.MyApplicationTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VentanaPrincipal : ComponentActivity() {
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

class UsuarioViewModel : ViewModel() {
    var nombreUsuario by mutableStateOf("")
}

@Composable
fun IniciarSesion(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    // Referencia a Firebase
    val database = FirebaseDatabase.getInstance()
    val usuariosRef = database.getReference("personas")

    // Estados del formulario
    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf("") }

    // Función para validar credenciales
    fun validarCredenciales() {
        usuariosRef.orderByChild("nombreUsuario").equalTo(nombreUsuario)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val usuarioValido = snapshot.children.firstOrNull()?.getValue(Persona::class.java)
                        if (usuarioValido != null && usuarioValido.contrasena == contrasena) {
                            usuarioViewModel.nombreUsuario = usuarioValido.nombreUsuario
                            navController.navigate("VentanaInicio")
                        } else {
                            errorMensaje = "Usuario o contraseña incorrectos"
                        }
                    } else {
                        errorMensaje = "Usuario no encontrado"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException().printStackTrace()
                    errorMensaje = "Error al conectarse con Firebase"
                }
            })
    }


    // Diseño principal
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
            Spacer(modifier = Modifier.height(16.dp))

            // Campo Nombre de Usuario
            InputConImagen(
                value = nombreUsuario,
                onValueChange = {
                    nombreUsuario = it
                    errorMensaje = ""
                },
                placeholder = "Nombre de usuario",
                imageRes = R.drawable.info_nombre_usuario
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo Contraseña
            InputContrasenaConImagen(
                value = contrasena,
                onValueChange = {
                    contrasena = it
                    errorMensaje = ""
                },
                placeholder = "Contraseña",
                imageRes = R.drawable.info_contrasena
            )

            // Mensaje de error
            if (errorMensaje.isNotEmpty()) {
                Text(
                    text = errorMensaje,
                    color = Color.Red,
                    style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Iniciar Sesión
            GenericButton(
                text = "Iniciar sesión",
                onClick = {
                    validarCredenciales()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Texto interactivo: ¿Olvidaste tu contraseña?
            TextoInteractivo(
                texto = "¿Olvidaste la contraseña?",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { /* Manejar evento de recuperación */ }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Texto interactivo: Crear cuenta
            TextoInteractivo(
                texto = "Crear cuenta",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { navController.navigate("VentanaRegistrar") }
            )
        }
    }
}

@Preview
@Composable
private fun AndroidMedium1Preview() {
    val navController = rememberNavController()
    val usuarioViewModel = UsuarioViewModel()
    IniciarSesion(navController = navController, usuarioViewModel = usuarioViewModel)
}
