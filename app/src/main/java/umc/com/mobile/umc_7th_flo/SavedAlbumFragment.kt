package umc.com.mobile.umc_7th_flo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import umc.com.mobile.umc_7th_flo.databinding.FragmentLockerSavedalbumBinding
import android.view.LayoutInflater as LayoutInflater1

class SavedAlbumFragment: Fragment() {
    lateinit var binding: FragmentLockerSavedalbumBinding
    lateinit var albumDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater1,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSavedalbumBinding.inflate(inflater, container, false)

        albumDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedSongRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val albumRVAdapter = SavedAlbumRVAdapter()
        binding.lockerSavedSongRecyclerView.adapter = albumRVAdapter

        // 데이터베이스 작업을 코루틴으로 처리
        CoroutineScope(Dispatchers.IO).launch {
            val likedAlbums = albumDB.albumDao().getLikedAlbums(getJwt()) // RoomDatabase 호출

            // UI 작업은 Dispatchers.Main에서 처리
            CoroutineScope(Dispatchers.Main).launch {
                albumRVAdapter.addAlbums(likedAlbums as ArrayList)
            }
        }

        albumRVAdapter.setMyItemClickListener(object : SavedAlbumRVAdapter.MyItemClickListener {
            override fun onRemoveSong(songId: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    albumDB.albumDao().getLikedAlbums(getJwt())
                }
            }
        })
    }

    private fun getJwt() : Int {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)
        val jwt = spf!!.getInt("jwt", 0)
        Log.d("MAIN_ACT/GET_JWT", "jwt_token: $jwt")

        return jwt
    }
}