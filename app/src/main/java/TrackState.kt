import com.example.playlistmaker.Track

object TrackState {
    var trackHistory:ArrayDeque<Track> = ArrayDeque<Track>()
    var currentTrack: Track = Track("","",2,"","","","","","")
}