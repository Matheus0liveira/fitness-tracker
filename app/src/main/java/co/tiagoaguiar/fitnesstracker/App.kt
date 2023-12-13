package co.tiagoaguiar.fitnesstracker

import android.app.Application
import android.content.res.Configuration
import androidx.room.Database
import co.tiagoaguiar.fitnesstracker.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getDatabase(this)
    }
}