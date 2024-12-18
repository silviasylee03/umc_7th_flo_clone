package umc.com.mobile.umc_7th_flo.ui.locker

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.com.mobile.umc_7th_flo.ui.savedalbum.SavedAlbumFragment
import umc.com.mobile.umc_7th_flo.ui.savedsong.SavedSongFragment

class LockerVPAdapter (fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int  = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SavedSongFragment()
            else -> SavedAlbumFragment()
        }
    }
}