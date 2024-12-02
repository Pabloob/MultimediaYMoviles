package com.example.FitCraft

import com.google.gson.annotations.SerializedName

data class Rutina(
    @SerializedName("idRutina") val idRutina: Int,
    @SerializedName("idPersona") val idPersona: Int,
    @SerializedName("nombreRutina") val nombreRutina: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("dia") val dia: List<String>,
    @SerializedName("horaInicio") val horaInicio: String,
    @SerializedName("horaFin") val horaFin: String,
    @SerializedName("ejercicios") val ejercicios: List<Ejercicio>
)



