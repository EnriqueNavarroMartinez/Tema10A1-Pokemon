package com.example.tema10a1_pokemon

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tema10a1_pokemon.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // Variable para el objeto de View Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PokemonApiService::class.java)

        // Llamada para obtener un Pokémon específico (por ejemplo, Ditto)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getPokemon("ditto").execute()
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    withContext(Dispatchers.Main) {
                        pokemon?.let {
                            updateUI(it)
                        }
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", e.toString())
            }
        }
    }

    // Actualizar la UI con los datos del Pokémon
    private fun updateUI(pokemon: Pokemon) {
        binding.apply {
            textViewId.text = "ID: ${pokemon.id}"
            textViewName.text = "Name: ${pokemon.name}"
            Picasso.get().load(pokemon.sprites.front_default).into(imageViewSprite)
        }
    }
}
