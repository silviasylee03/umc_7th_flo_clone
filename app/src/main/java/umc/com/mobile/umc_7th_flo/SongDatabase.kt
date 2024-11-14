package umc.com.mobile.umc_7th_flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class, Album::class, Like::class], version = 1)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao

    companion object {
//        @Volatile
//        private var INSTANCE: SongDatabase? = null
//
//        fun getDatabase(context: Context) : SongDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext, SongDatabase::class.java, "song_db"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"//다른 데이터 베이스랑 이름겹치면 꼬임

                    ).fallbackToDestructiveMigration()  // 마이그레이션 없이 기존 데이터를 삭제하고 새로 생성
                     .build()
                }
            }

            return instance
        }
    }
}