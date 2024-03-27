package com.clastic.data.di

import com.clastic.data.repository.ArticleRepositoryImpl
import com.clastic.data.repository.UserRepositoryImpl
import com.clastic.domain.repository.ArticleRepository
import com.clastic.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindsArticleRepository(
        articleRepositoryImpl: ArticleRepositoryImpl
    ): ArticleRepository
}