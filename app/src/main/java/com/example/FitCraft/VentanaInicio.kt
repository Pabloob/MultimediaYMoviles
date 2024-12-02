package com.example.FitCraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.FitCraft.ui.theme.MyApplicationTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


class VentanaInicio : ComponentActivity() {
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
fun Inicio(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    val conexionJSONRutinas = ConexionJSONRutinas()
    val rutina = remember { mutableStateOf<Rutina?>(null) }
    conexionJSONRutinas.CargarRutinaDesdeJsonPorDia(rutina)

    val nombreUsuario = usuarioViewModel.nombreUsuario

    var ejercicio by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }

    LaunchedEffect(rutina.value) {
        rutina.value?.let {
            ejercicio = it.nombreRutina
            horaInicio = it.horaInicio
            horaFin = it.horaFin
        } ?: run {
            ejercicio = "Sin rutina"
            horaFin = "No programado"
        }
    }


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
            HeaderText("Bienvenido $nombreUsuario")
            Spacer(modifier = Modifier.height(30.dp))
            RowCard(
                nombreRutina = ejercicio,
                horaInicio = horaInicio,
                horaFin = horaFin,
                imageRes = R.drawable.ejercicio_pierna
            )
            Spacer(modifier = Modifier.height(40.dp))
            LineChartComponent()
        }

        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
    }
}


fun createLineData(): LineData {
    val entries = listOf(
        Entry(0f, 1f),
        Entry(1f, 2f),
        Entry(2f, 3f),
        Entry(3f, 4f),
        Entry(4f, 5f),
        Entry(5f, 6f),
        Entry(6f, 7f)
    )

    val lineDataSet = LineDataSet(entries, "Gráfico horas").apply {
        color = Color.Blue.toArgb()
        setDrawCircles(true)
        setCircleColor(Color.Blue.toArgb())
        lineWidth = 2f
        circleRadius = 5f
        setDrawValues(false)
        setDrawHighlightIndicators(false)
        highLightColor = Color.White.toArgb()
        highlightLineWidth = 1f
        setDrawFilled(true)
        fillDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                Color.Blue.copy(alpha = 0.5f).toArgb(),
                Color.Transparent.toArgb()
            )
        )
    }

    return LineData(lineDataSet)
}

@Preview
@Composable
private fun AndroidMedium1Preview() {
    val navController = rememberNavController()
    val usuarioViewModel = UsuarioViewModel()
    Inicio(navController = navController, usuarioViewModel)
}