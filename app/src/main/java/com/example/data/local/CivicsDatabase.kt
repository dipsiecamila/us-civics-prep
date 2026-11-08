package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [QuestionProgressEntity::class, IntroQuestionProgressEntity::class, ExamAttemptEntity::class],
    version = 2,
    exportSchema = false
)
abstract class CivicsDatabase : RoomDatabase() {
    abstract fun civicsDao(): CivicsDao

    companion object {
        @Volatile
        private var INSTANCE: CivicsDatabase? = null

        fun getDatabase(context: Context): CivicsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CivicsDatabase::class.java,
                    "civics_prep_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
