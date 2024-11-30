package umc.com.mobile.umc_7th_flo.ui.album

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.com.mobile.umc_7th_flo.ui.detail.DetailFragment
import umc.com.mobile.umc_7th_flo.ui.video.VideoFragment
import umc.com.mobile.umc_7th_flo.ui.song.SongFragment

class AlbumVPAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }
}