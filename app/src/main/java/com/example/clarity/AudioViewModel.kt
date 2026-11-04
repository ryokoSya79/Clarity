package com.example.clarity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AudioViewModel: ViewModel() {
    private val repository = AudioReposotory()

    private val _tracks = MutableStateFlow<List<MeditationTrack>>(emptyList())
    val tracks: StateFlow<List<MeditationTrack>> = _tracks.asStateFlow()

    private val _genre = MutableStateFlow<List<DataGenre>>(emptyList())
    val genre: StateFlow<List<DataGenre>> = _genre.asStateFlow()

    private val _filter_tracks_all_jazz = MutableStateFlow<List<MeditationTrack>>(emptyList())
    val filter_tracks_all_jazz: StateFlow<List<MeditationTrack>> = _filter_tracks_all_jazz.asStateFlow()

    private val _filter_tracks_all_deep_sleep = MutableStateFlow<List<MeditationTrack>>(emptyList())
    val filter_tracks_all_deep_sleep: StateFlow<List<MeditationTrack>> = _filter_tracks_all_deep_sleep.asStateFlow()

    private val _filter_tracks_all_lofi = MutableStateFlow<List<MeditationTrack>>(emptyList())
    val filter_tracks_all_lofi: StateFlow<List<MeditationTrack>> = _filter_tracks_all_lofi.asStateFlow()

    private val _filter_tracks_all_nature = MutableStateFlow<List<MeditationTrack>>(emptyList())
    val filter_tracks_all_nature: StateFlow<List<MeditationTrack>> = _filter_tracks_all_nature.asStateFlow()

    private val _searchTrack = MutableStateFlow("")
    val searchTrack = _searchTrack.asStateFlow()

    private val _tracks_all_tags = MutableStateFlow<Set<String>>(setOf("All"))
    val tracks_all_tags = _tracks_all_tags.asStateFlow()

    private val _filter_tracks_all_tags = MutableStateFlow<List<MeditationTrack>>(emptyList())
    val filter_tracks_all_tags: StateFlow<List<MeditationTrack>> = _filter_tracks_all_tags.asStateFlow()


    val filter_tracks_jazz = MutableStateFlow(AudioReposotory().getTrack().filter { it.id >= 1 && it.id <= 10 })
    val filter_tracks_deep_sleep = MutableStateFlow(AudioReposotory().getTrack().filter { it.id >= 11 && it.id <= 20})
    val filter_tracks_lofi = MutableStateFlow(AudioReposotory().getTrack().filter { it.id >= 21 && it.id <= 30})
    val filter_tracks_nature = MutableStateFlow(AudioReposotory().getTrack().filter { it.id >= 31 && it.id <= 40})


    val search_all_tracks = _searchTrack
        .combine(tracks){query, tracks ->
            if(query.isBlank()){
                tracks
            } else{
                tracks.filter { track ->
                    track.title.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = tracks.value
        )
    val filter_search_tracks_deep_sleep = _searchTrack
        .combine(filter_tracks_deep_sleep){query, tracks ->
            if(query.isBlank()){
                tracks
            } else{
                tracks.filter { track ->
                    track.title.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = filter_tracks_deep_sleep.value
        )

    val filter_search_tracks_jazz = _searchTrack
        .combine(filter_tracks_jazz){query, tracks ->
            if(query.isBlank()){
                tracks
            } else{
                tracks.filter { track ->
                    track.title.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = filter_tracks_jazz.value
        )

    val filter_search_tracks_lofi = _searchTrack
        .combine(filter_tracks_lofi){query, tracks ->
            if(query.isBlank()){
                tracks
            } else{
                tracks.filter { track ->
                    track.title.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = filter_tracks_lofi.value
        )

    val filter_search_tracks_nature = _searchTrack
        .combine(filter_tracks_nature){query, tracks ->
            if(query.isBlank()){
                tracks
            } else{
                tracks.filter { track ->
                    track.title.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = filter_tracks_nature.value
        )


    init{
        loadDataTracks()
    }

    fun loadDataTracks(){
        _tracks.value = repository.getTrack()
        _genre.value = repository.getGenre()
        _filter_tracks_all_jazz.value = filter_tracks_jazz.value
        _filter_tracks_all_deep_sleep.value = filter_tracks_deep_sleep.value
        _filter_tracks_all_lofi.value = filter_tracks_lofi.value
        _filter_tracks_all_nature.value = filter_tracks_nature.value
    }

    fun updateSelectTags(list: Set<String>){
        _tracks_all_tags.value = list
    }

    fun loadDataTracksTags(){
        val selected = _tracks_all_tags.value
        _tracks.value = if ("All" in selected) {
            repository.getTrack()
        } else {
            repository.getTrack().filter { track ->
                track.tags.any { tag -> tag in selected }
            }
        }
    }

    fun onSearchTracks(newQuery: String){
        _searchTrack.value = newQuery
    }



}