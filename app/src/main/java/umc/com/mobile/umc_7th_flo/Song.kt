package umc.com.mobile.umc_7th_flo

data class Song(
        var title: String = "",
        var singer: String = "",
        var coverImg: Int? = null,
        var second: Int = 0,
        var playTime: Int = 0,
        var isPlaying: Boolean = false,
        var music: String = ""
)
