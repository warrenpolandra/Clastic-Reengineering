package com.clastic.data.di

import com.clastic.data.repository.ArticleRepositoryImpl
import com.clastic.data.repository.DropPointRepositoryImpl
import com.clastic.data.repository.MissionRepositoryImpl
import com.clastic.data.repository.PlasticKnowledgeRepositoryImpl
import com.clastic.data.repository.PlasticTransactionRepositoryImpl
import com.clastic.data.repository.UserRepositoryImpl
import com.clastic.domain.repository.ArticleRepository
import com.clastic.domain.repository.DropPointRepository
import com.clastic.domain.repository.MissionRepository
import com.clastic.domain.repository.PlasticKnowledgeRepository
import com.clastic.domain.repository.PlasticTransactionRepository
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

    @Binds
    @Singleton
    abstract fun bindsMissionRepository(
        missionRepositoryImpl: MissionRepositoryImpl
    ): MissionRepository

    @Binds
    @Singleton
    abstract fun bindsPlasticKnowledgeRepository(
        plasticKnowledgeRepositoryImpl: PlasticKnowledgeRepositoryImpl
    ): PlasticKnowledgeRepository

    @Binds
    @Singleton
    abstract fun bindsDropPointRepository(
        dropPointRepositoryImpl: DropPointRepositoryImpl
    ): DropPointRepository

    @Binds
    @Singleton
    abstract fun bindsPlasticTransactionRepository(
        plasticTransactionRepositoryImpl: PlasticTransactionRepositoryImpl
    ): PlasticTransactionRepository
}