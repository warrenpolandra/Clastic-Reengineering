package com.clastic.transaction.detail

import androidx.annotation.Keep
import com.clastic.model.Mission
import com.clastic.model.MissionTransaction

@Keep
data class MissionTransactionDetailState(
    val mission: Mission,
    val missionTransaction: MissionTransaction
)