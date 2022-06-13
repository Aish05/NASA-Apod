package com.aish.apod.data.db

import androidx.room.*

/**
 * dao interface for favorites
 */
@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(favorites: FavoritesEntity?)

    @Query("select * from favorites")
    fun getFavorites(): List<FavoritesEntity?>

    @Query("SELECT COUNT(*) FROM favorites WHERE date=:date")
    fun isFavorite(date: String): Int

    @Delete
    fun delete(favorites: FavoritesEntity?)

    @Transaction
    suspend fun insertAndDeleteInTransaction(pictureData: FavoritesEntity) {
        delete(pictureData)
        addData(pictureData)
    }
}