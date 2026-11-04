package com.example.clarity

data class MeditationTrack(
    val id: Int,
    val title: String,
    val image: Int,
    val time: String,
    val guided: Boolean,
    val track: Int,
    val tags: List<String>
)
