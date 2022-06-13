package com.aish.apod.data.db

import androidx.room.*

/***
 * dao interface for recent pictures
 */
@Dao
interface RecentPicturesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(recentPictureEntity: RecentPictureEntity)

    @Query("Delete from recent_picture")
    fun delete()

    @Query("Select * from recent_picture")
    fun getRecentPictureData() : RecentPictureEntity?

    @Transaction
    suspend fun deleteAndInsertInTransaction(recentPictureEntity: RecentPictureEntity) {
        delete()
        addData(recentPictureEntity)
    }
}