package com.example.FitCraft

import com.google.gson.annotations.SerializedName

data class Ejercicio(
    @SerializedName("nombreEjercicio") val nombreEjercicio: String,
    @SerializedName("repeticiones") val repeticiones: Int,
    @SerializedName("series") val series: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("tipoEjercicio") val tipoEjercicio: String
)