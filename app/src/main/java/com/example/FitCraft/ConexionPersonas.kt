package com.example.FitCraft

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ConexionPersonas {

    private val database = FirebaseDatabase.getInstance()
    private val personasRef = database.getReference("personas")

    @Composable
    fun CargarPersonasDesdeFirebase(usuarios: SnapshotStateList<Persona>) {
        LaunchedEffect(Unit) {
            personasRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listaPersonas = snapshot.children.mapNotNull { it.getValue(Persona::class.java) }
                    usuarios.clear()
                    usuarios.addAll(listaPersonas)
                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException().printStackTrace()
                }
            })
        }
    }

    @Composable
    fun CargarPersonaPorNombreDesdeFirebase(
        nombreUsuario: String,
        usuario: MutableState<Persona?>
    ) {
        LaunchedEffect(Unit) {
            personasRef.orderByChild("nombreUsuario").equalTo(nombreUsuario)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val persona = snapshot.children.firstOrNull()?.getValue(Persona::class.java)
                        usuario.value = persona
                    }

                    override fun onCancelled(error: DatabaseError) {
                        error.toException().printStackTrace()
                    }
                })
        }
    }

    fun agregarPersonaAFirebase(persona: Persona) {
        val nuevaPersonaRef = personasRef.push() // Crea un nodo único
        nuevaPersonaRef.setValue(persona).addOnFailureListener { e ->
            e.printStackTrace()
        }
    }

    fun borrarPersonaDeFirebase(nombreUsuario: String): Boolean {
        var eliminado = false
        personasRef.orderByChild("nombreUsuario").equalTo(nombreUsuario)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { it.ref.removeValue() }
                    eliminado = true
                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException().printStackTrace()
                }
            })
        return eliminado
    }
}
