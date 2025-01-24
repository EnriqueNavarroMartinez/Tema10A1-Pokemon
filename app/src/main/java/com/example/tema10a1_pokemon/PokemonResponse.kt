package com.example.tema10a1_pokemon

data class PokemonResponse(
    val count: Int,
    val pokemon: List<Pokemon>
)

data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: Sprites
)

data class Sprites(
    val front_default: String
)
