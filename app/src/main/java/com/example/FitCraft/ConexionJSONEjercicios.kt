package com.example.FitCraft

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter

class ConexionJSONEjercicios {

    @Composable
    fun cargarEjerciciosDesdeJson(ejercicios: SnapshotStateList<Ejercicio>) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            runCatching {
                leerEjerciciosDesdeJson(context)
            }.onSuccess { listaEjercicios ->
                ejercicios.clear()
                ejercicios.addAll(listaEjercicios)
            }.onFailure { e ->
                e.printStackTrace()
            }
        }
    }

    fun agregarEjercicioAlJson(context: Context, ejercicio: Ejercicio) {
        runCatching {
            val ejercicios = leerEjerciciosDesdeJson(context).toMutableList()
            ejercicios.add(ejercicio)
            guardarEjerciciosEnJson(context, ejercicios)
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    fun borrarEjercicioDelJson(context: Context, nombreEjercicio: String): Boolean {
        return runCatching {
            val ejercicios = leerEjerciciosDesdeJson(context).toMutableList()
            val ejercicioEliminado = ejercicios.removeIf { it.nombreEjercicio == nombreEjercicio }
            if (ejercicioEliminado) {
                guardarEjerciciosEnJson(context, ejercicios)
            }
            ejercicioEliminado
        }.getOrElse { e ->
            e.printStackTrace()
            false
        }
    }

    fun leerEjerciciosDesdeJson(context: Context): List<Ejercicio> {
        return runCatching {
            val file = File(context.filesDir, "ejercicios.json")

            val json = if (file.exists()) {
                file.readText()
            } else {
                context.assets.open("ejercicios.json").bufferedReader().use { it.readText() }
            }

            val gson = Gson()
            val listType = object : TypeToken<List<Ejercicio>>() {}.type
            gson.fromJson<List<Ejercicio>>(json, listType)

        }.getOrElse { e ->
            e.printStackTrace()
            emptyList()
        }
    }

    private fun guardarEjerciciosEnJson(context: Context, ejercicios: List<Ejercicio>) {
        runCatching {
            val gson = Gson()
            val json = gson.toJson(ejercicios)
            val file = File(context.filesDir, "ejercicios.json")
            FileWriter(file).use { writer ->
                writer.write(json)
            }
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}
