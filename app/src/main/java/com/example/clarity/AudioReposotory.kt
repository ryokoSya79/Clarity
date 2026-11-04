package com.example.clarity

class AudioReposotory {
    fun getTrack(): List<MeditationTrack>{
        return listOf(
            MeditationTrack(
                id = 1,
                title = "Sunrise",
                image = R.drawable.track_jazz_1,
                track = R.raw.track_jazz_1,
                guided = false,
                time = "01:13 min",
                tags = listOf("Energy", "Relaxation", "Creativity")
            ),
            MeditationTrack(
                id = 2,
                title = "Liberty Ave",
                image = R.drawable.track_jazz_2,
                track = R.raw.track_jazz_2,
                guided = false,
                time = "02:38 min",
                tags = listOf("Relaxation", "Relief")
            ),
            MeditationTrack(
                id = 3,
                title = "Morning",
                image = R.drawable.track_jazz_3,
                track = R.raw.track_jazz_3,
                guided = false,
                time = "02:29 min",
                tags = listOf("Focus", "Relief", "Creativity")
            ),
            MeditationTrack(
                id = 4,
                title = "Day Jazz",
                image = R.drawable.track_jazz_4,
                track = R.raw.track_jazz_4,
                guided = false,
                time = "02:14 min",
                tags = listOf("Relaxation", "Focus", "Peace")
            ),
            MeditationTrack(
                id = 5,
                title = "Parade",
                image = R.drawable.track_jazz_5,
                track = R.raw.track_jazz_5,
                guided = false,
                time = "02:18 min",
                tags = listOf("Energy", "Relaxation")
            ),
            MeditationTrack(
                id = 6,
                title = "Blue Sky",
                image = R.drawable.track_jazz_6,
                track = R.raw.track_jazz_6,
                guided = false,
                time = "02:18 min",
                tags = listOf("Energy", "Relaxation", )
            ),
            MeditationTrack(
                id = 7,
                title = "Picnic",
                image = R.drawable.track_jazz_7,
                track = R.raw.track_jazz_7,
                guided = false,
                time = "02:59 min",
                tags = listOf("Focus", "Peace", "Love")
            ),
            MeditationTrack(
                id = 8,
                title = "Fireworks",
                image = R.drawable.track_jazz_8,
                track = R.raw.track_jazz_8,
                guided = false,
                time = "02:31 min",
                tags = listOf("Energy", "Relaxation", "Love")
            ),
            MeditationTrack(
                id = 9,
                title = "Celebration",
                image = R.drawable.track_jazz_9,
                track = R.raw.track_jazz_9,
                guided = false,
                time = "02:39 min",
                tags = listOf("Energy", "Peace", "Creativity")
            ),
            MeditationTrack(
                id = 10,
                title = "Midnight",
                image = R.drawable.track_jazz_10,
                track = R.raw.track_jazz_10,
                guided = false,
                time = "02:15 min",
                tags = listOf("Energy", "Peace", "Focus")
            ),
            MeditationTrack(
                id = 11,
                title = "Relax",
                image = R.drawable.track_deep_sleep_11,
                track = R.raw.track_deep_sleep_11,
                guided = false,
                time = "06:16 min",
                tags = listOf("Sleep", "Peace", "Healing")
            ),
            MeditationTrack(
                id = 12,
                title = "Polaris",
                image = R.drawable.track_deep_sleep_12,
                track = R.raw.track_deep_sleep_12,
                guided = false,
                time = "04:16 min",
                tags = listOf("Sleep", "Relaxation", "Healing")
            ),
            MeditationTrack(
                id = 13,
                title = "At Look",
                image = R.drawable.track_deep_sleep_13,
                track = R.raw.track_deep_sleep_13,
                guided = false,
                time = "05:42 min",
                tags = listOf("Sleep", "Focus", "Healing")
            ),
            MeditationTrack(
                id = 14,
                title = "Calm music",
                image = R.drawable.track_deep_sleep_14,
                track = R.raw.track_deep_sleep_14,
                guided = false,
                time = "04:53 min",
                tags = listOf("Sleep", "Relaxation", "Healing")
            ),
            MeditationTrack(
                id = 15,
                title = "J-songs",
                image = R.drawable.track_deep_sleep_15,
                track = R.raw.track_deep_sleep_15,
                guided = false,
                time = "04:08 min",
                tags = listOf("Sleep", "Relief", "Healing")
            ),
            MeditationTrack(
                id = 16,
                title = "Ambient",
                image = R.drawable.track_deep_sleep_16,
                track = R.raw.track_deep_sleep_16,
                guided = false,
                time = "04:16 min",
                tags = listOf("Sleep", "Relaxation", "Healing")
            ),
            MeditationTrack(
                id = 17,
                title = "Sound rain",
                image = R.drawable.track_deep_sleep_17,
                track = R.raw.track_deep_sleep_17,
                guided = false,
                time = "04:46 min",
                tags = listOf("Sleep", "Peace", "Healing")
            ),
            MeditationTrack(
                id = 18,
                title = "Close eyes",
                image = R.drawable.track_deep_sleep_18,
                track = R.raw.track_deep_sleep_18,
                guided = false,
                time = "04:13 min",
                tags = listOf("Sleep", "Relaxation", "Healing")
            ),
            MeditationTrack(
                id = 19,
                title = "Background",
                image = R.drawable.track_deep_sleep_19,
                track = R.raw.track_deep_sleep_19,
                guided = false,
                time = "05:16 min",
                tags = listOf("Sleep", "Love", "Healing")
            ),

            MeditationTrack(
                id = 20,
                title = "Deep sleep",
                image = R.drawable.track_deep_sleep_20,
                track = R.raw.track_deep_sleep_20,
                guided = false,
                time = "04:39 min",
                tags = listOf("Sleep", "Focus", "Healing")
            ),

            MeditationTrack(
                id = 21,
                title = "J Lo-Fi",
                image = R.drawable.track_lofi_21,
                track = R.raw.track_lofi_21,
                guided = false,
                time = "05:18 min",
                tags = listOf("Sleep", "Love", "Healing")
            ),
            MeditationTrack(
                id = 22,
                title = "Resonance",
                image = R.drawable.track_lofi_22,
                track = R.raw.track_lofi_22,
                guided = false,
                time = "03:33 min",
                tags = listOf("Sleep", "Peace", "Healing")
            ),
            MeditationTrack(
                id = 23,
                title = "Lakehouse",
                image = R.drawable.track_lofi_23,
                track = R.raw.track_lofi_23,
                guided = false,
                time = "03:14 min",
                tags = listOf("Sleep", "Relaxation", "Relief")
            ),
            MeditationTrack(
                id = 24,
                title = "Flying sled",
                image = R.drawable.track_lofi_24,
                track = R.raw.track_lofi_24,
                guided = false,
                time = "04:39 min",
                tags = listOf("Focus", "Relaxation")
            ),
            MeditationTrack(
                id = 25,
                title = "My muse",
                image = R.drawable.track_lofi_25,
                track = R.raw.track_lofi_25,
                guided = false,
                time = "03:15 min",
                tags = listOf("Focus", "Breathing")
            ),
            MeditationTrack(
                id = 26,
                title = "Smowman",
                image = R.drawable.track_lofi_26,
                track = R.raw.track_lofi_26,
                guided = false,
                time = "03:15 min",
                tags = listOf("Focus")
            ),
            MeditationTrack(
                id = 27,
                title = "Powfu x LoFi",
                image = R.drawable.track_lofi_27,
                track = R.raw.track_lofi_27,
                guided = false,
                time = "02:25 min",
                tags = listOf("Focus", "Breathing", "Healing", "Energy")
            ),
            MeditationTrack(
                id = 28,
                title = "Breeze",
                image = R.drawable.track_lofi_28,
                track = R.raw.track_lofi_28,
                guided = false,
                time = "02:12 min",
                tags = listOf("Focus", "Relaxation", "Healing", "Creativity")
            ),
            MeditationTrack(
                id = 29,
                title = "Flowers",
                image = R.drawable.track_lofi_29,
                track = R.raw.track_lofi_29,
                guided = false,
                time = "03:08 min",
                tags = listOf("Focus", "Peace", "Creativity")
            ),
            MeditationTrack(
                id = 30,
                title = "Travaler",
                image = R.drawable.track_lofi_30,
                track = R.raw.track_lofi_30,
                guided = false,
                time = "05:46 min",
                tags = listOf("Focus", "Relaxation", "Healing")
            ),
            MeditationTrack(
                id = 31,
                title = "Rain",
                image = R.drawable.track_nature_31,
                track = R.raw.track_nature_31,
                guided = false,
                time = "10:00 min",
                tags = listOf("Nature", "Relaxation", "Healing", "Sleep", "Breathing")
            ),
            MeditationTrack(
                id = 32,
                title = "Bonfire",
                image = R.drawable.track_nature_32,
                track = R.raw.track_nature_32,
                guided = false,
                time = "10:00 min",
                tags = listOf("Nature", "Relaxation", "Healing", "Breathing")
            ),
            MeditationTrack(
                id = 33,
                title = "Forest",
                image = R.drawable.track_nature_33,
                track = R.raw.track_nature_33,
                guided = false,
                time = "09:49 min",
                tags = listOf("Nature", "Relaxation", "Healing", "Breathing", "Sleep")
            ),
            MeditationTrack(
                id = 34,
                title = "Beach",
                image = R.drawable.track_nature_34,
                track = R.raw.track_nature_34,
                guided = false,
                time = "09:50 min",
                tags = listOf("Nature", "Focus", "Healing", "Breathing")
            ),
            MeditationTrack(
                id = 35,
                title = "Win",
                image = R.drawable.track_nature_35,
                track = R.raw.track_nature_35,
                guided = false,
                time = "09:49 min",
                tags = listOf("Nature", "Relaxation", "Healing", "Breathing", "Focus")
            )
        )
    }

    fun getGenre(): List<DataGenre>{
        return listOf(
            DataGenre(
                title_genre = "Deep Sleep",
                average_time = "4 min",
                image_genre = R.drawable.image_genre_deep_sleep,
                id = 1
            ),
            DataGenre(
                title_genre = "Jazz",
                average_time = "2 min",
                image_genre = R.drawable.image_genre_jazz,
                id = 2
            ),
            DataGenre(
                title_genre = "Lo-Fi",
                average_time = "3 min",
                image_genre = R.drawable.image_genre_lofi,
                id = 3
            ),
            DataGenre(
                title_genre = "Sound of Nature",
                average_time = "10 min",
                image_genre = R.drawable.image_genre_sound_nature,
                id = 4
            )
        )
    }
}