package com.example.FitCraft

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

class ConexionJSONPersonas {

    @Composable
    fun CargarPersonasDesdeJson(usuarios: SnapshotStateList<Persona>) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            runCatching {
                leerPersonasDesdeJson(context)
            }.onSuccess { personas ->
                usuarios.clear()
                usuarios.addAll(personas)
            }.onFailure { e ->
                e.printStackTrace()
            }
        }
    }

    @Composable
    fun CargarPersonasDesdeJsonPorNombre(
        nombreUsuario: String,
        usuario: MutableState<Persona?>
    ) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            runCatching {
                leerPersonasDesdeJson(context).find { it.nombreUsuario == nombreUsuario }
            }.onSuccess { personaEncontrada ->
                usuario.value = personaEncontrada
            }.onFailure { e ->
                e.printStackTrace()
            }
        }
    }

    fun agregarPersonaAlJson(context: Context, persona: Persona) {
        runCatching {
            val personas = leerPersonasDesdeJson(context).toMutableList()
            personas.add(persona)
            guardarPersonasEnJson(context, personas)
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    fun borrarPersonaDelJson(context: Context, nombreUsuario: String): Boolean {
        return runCatching {
            val personas = leerPersonasDesdeJson(context).toMutableList()
            val personaEliminada = personas.removeIf { it.nombreUsuario == nombreUsuario }
            if (personaEliminada) {
                guardarPersonasEnJson(context, personas)
            }
            personaEliminada
        }.getOrElse { e ->
            e.printStackTrace()
            false
        }
    }

    fun leerPersonasDesdeJson(context: Context): List<Persona> {
        return runCatching {
            val file = File(context.filesDir, "personas.json")

            val json = if (file.exists()) {
                file.readText()
            } else {
                context.assets.open("personas.json").bufferedReader().use { it.readText() }
            }

            val gson = Gson()
            val listType = object : TypeToken<List<Persona>>() {}.type
            gson.fromJson<List<Persona>>(json, listType)

        }.getOrElse { e ->
            e.printStackTrace()
            emptyList()
        }
    }


    private fun guardarPersonasEnJson(context: Context, personas: List<Persona>) {
        runCatching {
            val gson = Gson()
            val json = gson.toJson(personas)
            val file = File(context.filesDir, "personas.json")
            FileWriter(file).use { writer ->
                writer.write(json)
            }
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}

