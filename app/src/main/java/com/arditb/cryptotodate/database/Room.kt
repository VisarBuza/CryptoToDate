package com.arditb.cryptotodate.database

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CryptoDao {
    @Query("select * from databasecrypto")
    fun getCryptoCurrencies(): LiveData<List<DatabaseCrypto>>

    @Query("select * from databasecrypto where name like :name")
    fun getFilteredCryptos(name: String): LiveData<List<DatabaseCrypto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( cryptoCurrencies: List<DatabaseCrypto>)

    @Insert
    fun insert(crypto: DatabaseCrypto)

    @Query("select * from databasecrypto")
    fun getCryptos(): List<DatabaseCrypto>
}

@Database(entities = [DatabaseCrypto::class], version = 1)
abstract class CryptosDatabase: RoomDatabase() {
    abstract val cryptoDao: CryptoDao
}

private lateinit var INSTANCE: CryptosDatabase

fun getDatabase(context: Context): CryptosDatabase {
    synchronized(CryptosDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, CryptosDatabase::class.java, "cryptos")
                .build()
        }
    }
    return INSTANCE
}