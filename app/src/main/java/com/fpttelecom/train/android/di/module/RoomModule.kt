package com.fpttelecom.train.android.di.module

//@Module
//@InstallIn(SingletonComponent::class)
//object RoomModule {
//
//    @Provides
//    @Singleton
//    fun provideRoomAuthenDatabase(app: Application): AuthenDatabase {
//        return Room.databaseBuilder(
//            app,
//            AuthenDatabase::class.java,
//            AuthenDatabase.DATABASE_NAME
//        ).build()
//    }
//
//    @Provides
//    @Singleton
//    fun authenRepository(db: AuthenDatabase): AuthenRepository {
//        return AuthenRepository(db.authenDAO)
//    }
//}