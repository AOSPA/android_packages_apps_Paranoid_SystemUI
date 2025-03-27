/*
 * SPDX-FileCopyrightText: 2025 Paranoid Android
 * SPDX-License-Identifer: Apache-2.0
 */

package co.aospa.systemui.qs.tiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.service.quicksettings.Tile
import android.telephony.SubscriptionManager
import android.telephony.SubscriptionManager.INVALID_SUBSCRIPTION_ID
import android.telephony.SubscriptionManager.getDefaultDataSubscriptionId
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED
import android.telephony.TelephonyManager.ALLOWED_NETWORK_TYPES_REASON_USER
import android.telephony.TelephonyManager.NETWORK_TYPE_BITMASK_NR
import android.util.Log
import com.android.internal.logging.MetricsLogger
import com.android.systemui.animation.Expandable
import com.android.systemui.broadcast.BroadcastDispatcher
import com.android.systemui.dagger.qualifiers.Background
import com.android.systemui.dagger.qualifiers.Main
import com.android.systemui.plugins.ActivityStarter
import com.android.systemui.plugins.FalsingManager
import com.android.systemui.plugins.qs.QSTile.BooleanState
import com.android.systemui.plugins.statusbar.StatusBarStateController
import com.android.systemui.qs.QSHost
import com.android.systemui.qs.QsEventLogger
import com.android.systemui.qs.logging.QSLogger
import com.android.systemui.qs.tileimpl.QSTileImpl
import com.android.systemui.res.R
import java.util.concurrent.Executor
import javax.inject.Inject

class FivegTile @Inject constructor(
    host: QSHost,
    uiEventLogger: QsEventLogger,
    @Background backgroundLooper: Looper,
    @Main mainHandler: Handler,
    falsingManager: FalsingManager,
    metricsLogger: MetricsLogger,
    statusBarStateController: StatusBarStateController,
    activityStarter: ActivityStarter,
    qsLogger: QSLogger,
    @Main private val executor: Executor,
    private val broadcastDispatcher: BroadcastDispatcher
) : QSTileImpl<BooleanState>(
    host,
    uiEventLogger,
    backgroundLooper,
    mainHandler,
    falsingManager,
    metricsLogger,
    statusBarStateController,
    activityStarter,
    qsLogger
) {
    private val teleManagerService by lazy {
        mContext.getSystemService(TelephonyManager::class.java)
    }
    private var teleManager: TelephonyManager? = null

    private val ddsChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            dlog("ddsChangeReceiver fired")
            updateTeleManager()
            refreshState()
        }
    }

    init {
        updateTeleManager()
    }

    override fun handleSetListening(listening: Boolean) {
        dlog("handleSetListening($listening)")
        if (listening) {
            broadcastDispatcher.registerReceiver(
                ddsChangeReceiver,
                IntentFilter(ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED)
            )
        } else {
            broadcastDispatcher.unregisterReceiver(ddsChangeReceiver)
        }
    }

    override fun newTileState() = BooleanState().apply {
        icon = ResourceIcon.get(R.drawable.ic_qs_5g)
        label = tileLabel
    }

    override fun getTileLabel() = mContext.getString(R.string.qs_5g_label)

    override fun handleClick(expandable: Expandable?) {
        if (teleManager == null || !is5gSupported()) return
        val enable = !is5gEnabled()
        val supportedBitmask = getSupportedNrBitmask()
        var allowedBitmask = teleManager!!.getAllowedNetworkTypesForReason(
                ALLOWED_NETWORK_TYPES_REASON_USER)
        teleManager!!.setAllowedNetworkTypesForReason(
            ALLOWED_NETWORK_TYPES_REASON_USER,
            if (enable) {
                allowedBitmask or supportedBitmask
            } else {
                allowedBitmask and supportedBitmask.inv()
            }
        )
    }

    override fun getLongClickIntent() =
        Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS)
            .apply {
                val subId = getDefaultDataSubscriptionId()
                if (subId != INVALID_SUBSCRIPTION_ID)
                    putExtra(Settings.EXTRA_SUB_ID, subId)
            }

    override fun handleUpdateState(state: BooleanState, arg: Any?) {
        state.apply {
            value = is5gEnabled()
            this.state = when {
                !is5gSupported() -> Tile.STATE_UNAVAILABLE
                value -> Tile.STATE_ACTIVE
                else -> Tile.STATE_INACTIVE
            }
        }
    }

    private fun updateTeleManager() {
        val subId = getDefaultDataSubscriptionId()
        dlog("updateTeleManager: subId=$subId")
        teleManager = if (subId == INVALID_SUBSCRIPTION_ID) {
            null
        } else {
            teleManagerService?.createForSubscriptionId(subId)
        }
    }

    private fun getSupportedNrBitmask(): Long {
        val supportedRat = teleManager?.getSupportedRadioAccessFamily() ?: return 0L
        val subId = getDefaultDataSubscriptionId()
        return when {
            (supportedRat and NETWORK_TYPE_BITMASK_NR_ALL) != 0L -> {
                dlog("subId $subId supports 5g EnhancedRadioCapability")
                NETWORK_TYPE_BITMASK_NR_ALL
            }
            (supportedRat and NETWORK_TYPE_BITMASK_NR) != 0L -> {
                dlog("subId $subId supports 5g AOSP")
                NETWORK_TYPE_BITMASK_NR
            }
            else -> {
                dlog("subId $subId does not support 5g!")
                0L
            }
        }
    }

    private fun is5gSupported() = getSupportedNrBitmask() == 0L

    private fun is5gEnabled(): Boolean {
        val supportedBitmask = getSupportedNrBitmask()
        val allowedBitmask = teleManager?.getAllowedNetworkTypesForReason(
                ALLOWED_NETWORK_TYPES_REASON_USER) ?: 0L
        return (supportedBitmask and allowedBitmask) != 0L
    }

    private inline fun dlog(msg: String) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, msg)
        }
    }

    companion object {
        private const val TAG = "FivegTile"
        const val TILE_SPEC = "fiveg"

        // from org.codeaurora.telephony.utils.EnhancedRadioCapabilityResponse
        private const val NETWORK_TYPE_NR_NSA = 20 // = TelephonyManager.NETWORK_TYPE_NR
        private const val NETWORK_TYPE_NR_SA = 21
        private const val NETWORK_TYPE_BITMASK_NR_NSA = (1 shl (NETWORK_TYPE_NR_NSA - 1)).toLong()
        private const val NETWORK_TYPE_BITMASK_NR_SA = (1 shl (NETWORK_TYPE_NR_SA - 1)).toLong()
        private const val NETWORK_TYPE_BITMASK_NR_ALL =
            NETWORK_TYPE_BITMASK_NR_NSA or NETWORK_TYPE_BITMASK_NR_SA
    }
}
