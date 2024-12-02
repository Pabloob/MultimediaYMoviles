package com.example.FitCraft

import android.content.Context
import com.google.gson.annotations.SerializedName

data class Persona(
    @SerializedName("idPersona") var idPersona: Int,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("apellidos") var apellidos: String,
    @SerializedName("fechaNacimiento") var fechaNacimiento: String,
    @SerializedName("altura") var altura: Float,
    @SerializedName("peso") var peso: Float,
    @SerializedName("nombreUsuario") var nombreUsuario: String,
    @SerializedName("contrasena") var contrasena: String
) {
    companion object {
        fun crearNuevaPersona(
            context: Context,
            nombre: String,
            apellidos: String,
            fechaNacimiento: String,
            altura: Float,
            peso: Float,
            nombreUsuario: String,
            contrasena: String
        ): Persona {
            val conexionJSONPersonas = ConexionJSONPersonas()
            val personas = conexionJSONPersonas.leerPersonasDesdeJson(context)

            val ultimoId = personas.maxOfOrNull { it.idPersona } ?: 0
            val nuevoId = ultimoId + 1

            return Persona(
                idPersona = nuevoId,
                nombre = nombre,
                apellidos = apellidos,
                fechaNacimiento = fechaNacimiento,
                altura = altura,
                peso = peso,
                nombreUsuario = nombreUsuario,
                contrasena = contrasena
            )
        }
    }
}
