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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.FitCraft.ui.theme.MyApplicationTheme


class VentanaEjercicios : ComponentActivity() {
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
    fun MostrarEjercicios() {
    val conexionJSONEjercicios = ConexionJSONEjercicios()
    val ejercicios = remember { SnapshotStateList<Ejercicio>() }

    conexionJSONEjercicios.cargarEjerciciosDesdeJson(ejercicios)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(50.dp))
                .background(Color(0xff1c1e26))
                .padding(30.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Titulo("Lista de Ejercicios")
            Spacer(modifier = Modifier.height(16.dp))

            ejercicios.forEach { ejercicio ->
                CartaEjercicio(ejercicio = ejercicio)
                DividerConEspaciado()
            }
        }
    }
}

@Preview
@Composable
private fun AndroidMedium1Preview() {
    MostrarEjercicios()
}