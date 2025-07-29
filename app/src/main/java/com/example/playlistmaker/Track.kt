import android.os.Parcel
import android.os.Parcelable

data class Track(
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя
    var trackTimeMillis: Long, // Продолжительность трека
    val artworkUrl100: String, // Ссылка на изображение обложки
    val trackId: String,
    val collectionName: String?,
    val releaseDate: String?,
    val primaryGenreName: String,
    val country: String
)