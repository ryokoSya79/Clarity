package com.example.clarity

import android.app.Application
import android.media.browse.MediaBrowser
import android.media.session.PlaybackState
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MediaViewModel(application: Application): AndroidViewModel(application) {

    private val _state = MutableStateFlow(DataMedia())
    val state: StateFlow<DataMedia> = _state.asStateFlow()

    private val player = ExoPlayer.Builder(application).build().apply {
        addListener(object : Player.Listener{
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _state.update { it.copy(isPlaying = isPlaying) }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY){
                    _state.update { it.copy(durationMS = duration.coerceAtLeast(0L)) }
                }
            }
        })
    }

    init {
        viewModelScope.launch {
            while(true){
                if(player.isPlaying){
                    _state.update { it.copy(positionMS = player.currentPosition) }
                }
                delay(250)
            }
        }
    }

    fun loadTrack(uri: Uri){
        if (player.mediaItemCount == 0){
            player.setMediaItem(MediaItem.fromUri(uri))
            player.prepare()
        }
    }

    fun tooglePlayer(){
        if(player.isPlaying){
            player.pause()
        }else{
            if(player.playbackState == Player.STATE_ENDED) seekTo(0)
            player.play()
        }
    }

    fun seekTo(positionMS: Long){
        player.seekTo(positionMS)
        _state.update { it.copy(positionMS = positionMS) }
    }

    override fun onCleared(){
        player.release()
        super.onCleared()
    }
}