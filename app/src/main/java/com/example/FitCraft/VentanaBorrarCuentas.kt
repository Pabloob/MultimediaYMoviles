package com.example.FitCraft

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.FitCraft.ui.theme.MyApplicationTheme

class VentanaBorrarCuentas : ComponentActivity() {
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
internal fun Borrar(navController: NavController) {
    val conexionPersonas = ConexionPersonas()
    val usuarios = remember { mutableStateListOf<Persona>() }
    conexionPersonas.CargarPersonasDesdeFirebase(usuarios)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.9f)
                .align(Alignment.TopCenter)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(350.dp)
                    .height(90.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(color = Color(0xff1C1E26))
            ) {
                Text(
                    text = "Cuentas",
                    color = Color.White,
                    lineHeight = 2.5.em,
                    style = TextStyle(
                        fontSize = 30.sp, fontWeight = FontWeight.Bold
                    )
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
                    .width(350.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(color = Color(0xff2D2F3A))
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp)
                ) {
                    for (usr in usuarios) {
                        Row {
                            Text(
                                text = usr.nombreUsuario,
                                color = Color.Red,
                                style = TextStyle(
                                    fontSize = 20.sp, fontWeight = FontWeight.Bold
                                ), modifier = Modifier
                                    .clickable {
                                        if (conexionPersonas.borrarPersonaDeFirebase(usr.nombreUsuario)) {
                                            usuarios.remove(usr)
                                            Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .height(70.dp)
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .background(color = Color(0xff1c1e26)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(100.dp, Alignment.CenterHorizontally),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_inicio),
                    contentDescription = "Imagen inicio",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            navController.navigate("VentanaInicio")
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.btn_mas),
                    contentDescription = "Imagen más",
                    modifier = Modifier.size(20.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.btn_configuracion),
                    contentDescription = "Imagen configuración",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AndroidMedium1Preview() {
    val navController = rememberNavController()
    Borrar(navController = navController)
}