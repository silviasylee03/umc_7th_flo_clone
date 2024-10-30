package umc.com.mobile.umc_7th_flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.com.mobile.umc_7th_flo.databinding.FragmentLockerSavedsongBinding

class SavedSongFragment: Fragment() {
    lateinit var binding: FragmentLockerSavedsongBinding
    private var songDatas = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        songDatas.apply {
            add(Song("Butter", "방탄소년단(BTS)", R.drawable.img_album_exp))
            add(Song("Lilac", "아이유(IU)", R.drawable.img_album_exp2))
            add(Song("Next Level", "에스파(aespa)", R.drawable.img_album_exp3))
            add(Song("Boy with luv", "방탄소년단(BTS)", R.drawable.img_album_exp4))
            add(Song("BBoom BBoom", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
            add(Song("Weekend", "태연(Tae Yeon)", R.drawable.img_album_exp6))
            add(Song("Butter", "방탄소년단(BTS)", R.drawable.img_album_exp))
            add(Song("Lilac", "아이유(IU)", R.drawable.img_album_exp2))
            add(Song("Next Level", "에스파(aespa)", R.drawable.img_album_exp3))
            add(Song("Boy with luv", "방탄소년단(BTS)", R.drawable.img_album_exp4))
            add(Song("BBoom BBoom", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
            add(Song("Weekend", "태연(Tae Yeon)", R.drawable.img_album_exp6))
            add(Song("Butter", "방탄소년단(BTS)", R.drawable.img_album_exp))
            add(Song("Lilac", "아이유(IU)", R.drawable.img_album_exp2))
            add(Song("Next Level", "에스파(aespa)", R.drawable.img_album_exp3))
            add(Song("Boy with luv", "방탄소년단(BTS)", R.drawable.img_album_exp4))
            add(Song("BBoom BBoom", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
            add(Song("Weekend", "태연(Tae Yeon)", R.drawable.img_album_exp6))
        }

        val songRVAdapter = SavedSongRVAdapter(songDatas)
        binding.lockerSavedSongRecyclerView.adapter = songRVAdapter
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        songRVAdapter.setMyItemClickListener((object : SavedSongRVAdapter.MyItemClickListener {
            override fun onRemoveSong(position: Int) {
//                songDatas.removeAt(position)
//                songRVAdapter.notifyDataSetChanged()
            }
        }))
    }
}