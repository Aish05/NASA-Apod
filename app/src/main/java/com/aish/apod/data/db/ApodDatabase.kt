package com.aish.apod.data.db


import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Database and list of all entities and dao
 */
@Database(
    version = 1,
    exportSchema = false,
    entities = [
        FavoritesEntity::class,
        RecentPictureEntity::class
    ]
)
abstract class ApodDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavoritesDao

    abstract fun recentPictureDao(): RecentPicturesDao
}
