package umc.com.mobile.umc_7th_flo.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.com.mobile.umc_7th_flo.data.Album
import umc.com.mobile.umc_7th_flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>):
    RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(album: Album)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(albumList[position])
        }

        holder.binding.itemAlbumPlayImgIv.setOnClickListener {
            miniPlayerSyncListener.onPlayButtonClick(albumList[position])
        }
    }

    override fun getItemCount(): Int = albumList.size

    interface MiniPlayerSyncListener {
        fun onPlayButtonClick(album: Album)
    }

    private lateinit var miniPlayerSyncListener: MiniPlayerSyncListener

    fun setMiniPlayerSyncListener(listener: MiniPlayerSyncListener) {
        miniPlayerSyncListener = listener
    }

    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.titleLilac.text = album.title
            binding.singerIu.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }
}