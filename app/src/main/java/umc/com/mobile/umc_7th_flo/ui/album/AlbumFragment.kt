package umc.com.mobile.umc_7th_flo.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import umc.com.mobile.umc_7th_flo.data.Album
import umc.com.mobile.umc_7th_flo.data.Like
import umc.com.mobile.umc_7th_flo.MainActivity
import umc.com.mobile.umc_7th_flo.R
import umc.com.mobile.umc_7th_flo.data.SongDatabase
import umc.com.mobile.umc_7th_flo.databinding.FragmentAlbumBinding
import umc.com.mobile.umc_7th_flo.ui.home.HomeFragment

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var isLiked: Boolean = false

    private var gson: Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

//        binding.albumMusicTitleTv.text = arguments?.getString("title")
//        binding.albumSingerNameTv.text = arguments?.getString("singer")

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)

        lifecycleScope.launch {
            val isLiked = isLikedAlbum(album.id)
            if (isLiked) {
                // 좋아요가 눌려진 상태
                println("Album is liked.")
            } else {
                // 좋아요가 눌려지지 않은 상태
                println("Album is not liked.")
            }
        }
        setInit(album)
        setOnClickListener(album)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

//        binding.albumLikeIv.setOnClickListener {
//            if(isLiked) {
//                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
//                disLikeAlbum(userId, album.id)
//            } else {
//                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
//                likeAlbum(userId, album.id)
//            }
//
//            isLiked = !isLiked
//        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()

        if(isLiked) {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt() : Int {
        val spf = requireActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("jwt", 0)
    }

    private suspend fun likeAlbum(userId: Int, albumId: Int) {
        withContext(Dispatchers.IO) {
            val songDB = SongDatabase.getInstance(requireActivity())!!
            val like = Like(userId, albumId)
            songDB.albumDao().likeAlbum(like)
        }
    }

    private suspend fun isLikedAlbum(albumId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val songDB = SongDatabase.getInstance(requireActivity())!!
            val userId = getJwt()
            val likeId: Int? = songDB.albumDao().isLikedAlbum(userId, albumId)
            likeId != null
        }
    }

    private suspend fun disLikeAlbum(albumId: Int) {
        withContext(Dispatchers.IO) {
            val songDB = SongDatabase.getInstance(requireActivity())!!
            val userId = getJwt()
            songDB.albumDao().disLikedAlbum(userId, albumId)
        }
    }

    private fun setOnClickListener(album : Album) {
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            lifecycleScope.launch {
                if (isLiked) {
                    binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                    disLikeAlbum(album.id)
                } else {
                    binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                    likeAlbum(userId, album.id)
                }
                isLiked = !isLiked
            }
        }

    }
}