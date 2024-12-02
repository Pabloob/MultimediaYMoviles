package com.example.FitCraft

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class ConexionJSONRutinas {

    @Composable
    fun CargarRutinaDesdeJson(rutina: SnapshotStateList<Rutina>) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            runCatching {
                leerRutinaDesdeJson(context)
            }.onSuccess { rutinas ->
                rutina.clear()
                rutina.addAll(rutinas)
            }.onFailure { e ->
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("NewApi")
    @Composable
    fun CargarRutinaDesdeJsonPorDia(
        rutina: MutableState<Rutina?>
    ) {
        val diaActual = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES")).lowercase()

        val context = LocalContext.current
        LaunchedEffect(Unit) {
            runCatching {
                leerRutinaDesdeJson(context).find { rutina ->
                    rutina.dia.map { it.lowercase() }.contains(diaActual)
                }
            }.onSuccess { rutinaEncontrada ->
                rutina.value = rutinaEncontrada
            }.onFailure { e ->
                e.printStackTrace()
            }
        }
    }


    fun agregarRutinaAlJson(context: Context, rutina: Rutina) {
        runCatching {
            val rutinas = leerRutinaDesdeJson(context).toMutableList()
            rutinas.add(rutina)
            guardarRutinaEnJson(context, rutinas)
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    fun borrarRutinaDelJson(context: Context, nombreRutina: String): Boolean {
        return runCatching {
            val rutinas = leerRutinaDesdeJson(context).toMutableList()
            val rutinaEliminada = rutinas.removeIf { it.nombreRutina == nombreRutina }
            if (rutinaEliminada) {
                guardarRutinaEnJson(context, rutinas)
            }
            rutinaEliminada
        }.getOrElse { e ->
            e.printStackTrace()
            false
        }
    }

    fun leerRutinaDesdeJson(context: Context): List<Rutina> {
        return runCatching {
            val file = File(context.filesDir, "rutinas.json")

            val json = if (file.exists()) {
                file.readText()
            } else {
                context.assets.open("rutinas.json").bufferedReader().use { it.readText() }
            }

            val gson = Gson()
            val listType = object : TypeToken<List<Rutina>>() {}.type
            gson.fromJson<List<Rutina>>(json, listType)

        }.getOrElse { e ->
            e.printStackTrace()
            emptyList()
        }
    }


    private fun guardarRutinaEnJson(context: Context, rutinas: List<Rutina>) {
        runCatching {
            val gson = Gson()
            val json = gson.toJson(rutinas)
            val file = File(context.filesDir, "rutinas.json")
            FileWriter(file).use { writer ->
                writer.write(json)
            }
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}