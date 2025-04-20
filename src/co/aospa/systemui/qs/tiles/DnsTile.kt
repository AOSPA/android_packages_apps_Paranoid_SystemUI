/*
 * SPDX-FileCopyrightText: 2025 Paranoid Android
 * SPDX-License-Identifier: Apache-2.0
 */

package co.aospa.systemui.qs.tiles

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.service.quicksettings.Tile
import android.util.Log
import com.android.internal.logging.MetricsLogger
import com.android.systemui.animation.Expandable
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
import javax.inject.Inject

class DnsTile @Inject constructor(
    host: QSHost,
    uiEventLogger: QsEventLogger,
    @Background backgroundLooper: Looper,
    @Main mainHandler: Handler,
    falsingManager: FalsingManager,
    metricsLogger: MetricsLogger,
    statusBarStateController: StatusBarStateController,
    activityStarter: ActivityStarter,
    qsLogger: QSLogger
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
    private val TAG = "DnsTile"
    private var currentModeIndex = 0

    override fun newTileState() = BooleanState().apply {
        icon = ResourceIcon.get(R.drawable.ic_qs_dns)
        label = tileLabel
    }

    override fun getTileLabel(): CharSequence = mContext.getString(R.string.qs_dns_label)

    override fun isAvailable() = true

    override fun handleClick(expandable: Expandable?) {
        currentModeIndex = (currentModeIndex + 1) % MODES.size
        val newMode = MODES[currentModeIndex]

        val success = setPrivateDnsMode(newMode)
        if (success) {
            refreshState()
        }
    }

    override fun handleUpdateState(state: BooleanState, arg: Any?) {
        val mode = getCurrentPrivateDnsMode()

        state.label = tileLabel
        state.secondaryLabel = when (mode) {
            "opportunistic" -> mContext.getString(R.string.qs_dns_mode_auto)
            "hostname" -> mContext.getString(R.string.qs_dns_mode_custom)
            else -> mContext.getString(R.string.qs_dns_mode_off)
        }
        state.state = when (mode) {
            "off" -> Tile.STATE_INACTIVE
            else -> Tile.STATE_ACTIVE
        }
    }

    override fun getLongClickIntent(): Intent =
        Intent(Settings.ACTION_WIRELESS_SETTINGS)

    private fun setPrivateDnsMode(mode: String): Boolean {
        return try {
            Settings.Global.putString(mContext.contentResolver, "private_dns_mode", mode)
            true
        } catch (e: Exception) {
            dlog("Failed to set DNS mode: ${e.message}")
            false
        }
    }

    private fun getCurrentPrivateDnsMode(): String {
        return try {
            Settings.Global.getString(mContext.contentResolver, "private_dns_mode") ?: "off"
        } catch (e: Exception) {
            dlog("Failed to get DNS mode: ${e.message}")
            "off"
        }
    }

    private fun dlog(msg: String) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, msg)
        }
    }

    companion object {
        const val TILE_SPEC = "dns"
        private val MODES = listOf("off", "opportunistic", "hostname")
    }
}
