package co.aospa.android.systemui.smartspace

import com.android.systemui.dagger.SysUISingleton
import com.android.systemui.flags.FeatureFlags
import com.android.systemui.flags.Flags

import co.aospa.android.systemui.smartspace.KeyguardMediaViewController
import co.aospa.android.systemui.smartspace.KeyguardZenAlarmViewController

import javax.inject.Inject

@SysUISingleton
class KeyguardSmartspaceController @Inject constructor(
    private val featureFlags: FeatureFlags,
    private val zenController: KeyguardZenAlarmViewController,
    private val mediaController: KeyguardMediaViewController,
) {
    init {
        if (featureFlags.isEnabled(Flags.SMARTSPACE)) {
            mediaController.init()
            zenController.init()
        }
    }
}
