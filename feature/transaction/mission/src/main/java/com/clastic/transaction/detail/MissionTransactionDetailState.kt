package com.clastic.transaction.detail

import androidx.annotation.Keep
import com.clastic.model.Mission
import com.clastic.model.MissionTransaction
import com.clastic.model.User

@Keep
data class MissionTransactionDetailState(
    val mission: Mission,
    val missionTransaction: MissionTransaction
)