package com.testapp.weatherapp.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface QueryItemDao {
    @Query("Select * from QueryItemTable order by generatedTime desc limit 1")
    fun getLatestQueryItem(): Maybe<QueryItem>

    @Query("Select * from QueryItemTable where queryKey = :searchKey")
    fun getQueryItem(searchKey:String): Maybe<QueryItem>

    //Can be optimised future by segment the data.
    @Query("Select * from QueryItemTable order by generatedTime desc")
    fun getAllQueryItems(): Single<List<QueryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg queryItem: QueryItem): Completable

    @Delete
    fun delete(queryItem: QueryItem): Completable

    @Query("Delete from QueryItemTable where queryKey = :searchKey")
    fun deleteByKey(searchKey:String) :Completable
}