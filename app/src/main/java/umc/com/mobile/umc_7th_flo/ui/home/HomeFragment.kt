package umc.com.mobile.umc_7th_flo.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import umc.com.mobile.umc_7th_flo.data.Album
import umc.com.mobile.umc_7th_flo.ui.album.AlbumFragment
import umc.com.mobile.umc_7th_flo.ui.album.AlbumRVAdapter
import umc.com.mobile.umc_7th_flo.ui.banner.BannerFragment
import umc.com.mobile.umc_7th_flo.ui.banner.BannerVPAdapter
import umc.com.mobile.umc_7th_flo.MainActivity
import umc.com.mobile.umc_7th_flo.ui.pannel.PannelFragment
import umc.com.mobile.umc_7th_flo.ui.pannel.PannelVPAdapter
import umc.com.mobile.umc_7th_flo.R
import umc.com.mobile.umc_7th_flo.data.SongDatabase
import umc.com.mobile.umc_7th_flo.databinding.FragmentHomeBinding
import java.util.ArrayList
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

//    private var albumDatas = ArrayList<Album>()
    private var albumDatas = ArrayList<Album>()
    private lateinit var songDB : SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeDailyMusicAlbumImg01Iv.setOnClickListener {
//            val bundle = Bundle()
////            bundle.putString("title", binding.titleLilac.text.toString())
////            bundle.putString("singer", binding.singerIu.text.toString())
//
//            val albumFragment = AlbumFragment()
//            albumFragment.arguments = bundle
//
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm , AlbumFragment())
//                .commitAllowingStateLoss()
//        }

        // album 더미 데이터
//        albumDatas.apply {
//            add(Album("Butter", "방탄소년단(BTS)", R.drawable.img_album_exp))
//            add(Album("Lilac", "아이유(IU)", R.drawable.img_album_exp2))
//            add(Album("Next Level", "에스파(aespa)", R.drawable.img_album_exp3))
//            add(Album("Boy with luv", "방탄소년단(BTS)", R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연(Tae Yeon)", R.drawable.img_album_exp6))
//        }
        songDB = SongDatabase.getInstance(requireContext())!!

        // Room 데이터베이스 작업을 백그라운드에서 수행하여 UI 스레드를 차단하지 않도록 합니다.
        CoroutineScope(Dispatchers.IO).launch {
            // 데이터베이스에서 앨범 데이터를 가져옵니다.
            val albums = songDB.albumDao().getAlbums()

            // UI 업데이트는 메인 스레드에서 수행해야 하므로 withContext(Dispatchers.Main)를 사용합니다.
            withContext(Dispatchers.Main) {
                albumDatas.addAll(albums)

                // RecyclerView 어댑터 설정
                val albumRVAdapter = AlbumRVAdapter(albumDatas)
                binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
                binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener {
                    override fun onItemClick(album: Album) {
                        (context as MainActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, AlbumFragment().apply {
                                arguments = Bundle().apply {
                                    val gson = Gson()
                                    val albumJson = gson.toJson(album)
                                    putString("album", albumJson)
                                }
                            })
                            .commitAllowingStateLoss()
                    }
                })

                albumRVAdapter.setMiniPlayerSyncListener(object:
                    AlbumRVAdapter.MiniPlayerSyncListener {
                    override fun onPlayButtonClick(album: Album) {
                        (context as MainActivity).updateMiniPlayer(album)
                    }
                })
            }
        }

//        // banner viewPager
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homeBannerIndicator.setViewPager(binding.homeBannerVp)

        autoSlide(bannerAdapter)

        // pannel viewPager
        val pannelAdapter = PannelVPAdapter(this)
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_first_album_default))
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_first_album_default))

        binding.homePannelBackgroundVp.adapter = pannelAdapter
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)

        return binding.root
    }

    private fun autoSlide(adapter: BannerVPAdapter) {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    val nextItem = binding.homeBannerVp.currentItem + 1
                    if (nextItem < adapter.itemCount) {
                        binding.homeBannerVp.currentItem = nextItem
                    } else {
                        binding.homeBannerVp.currentItem = 0 // 순환
                    }
                }
            }
        }, 3000, 3000)
    }
}