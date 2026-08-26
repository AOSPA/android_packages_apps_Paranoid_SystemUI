/*
 * Copyright (C) 2023 Paranoid Android
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.aospa.systemui.qs.tileimpl

import co.aospa.systemui.qs.tiles.AlwaysOnDisplayTile
import co.aospa.systemui.qs.tiles.CaffeineTile
import co.aospa.systemui.qs.tiles.DataSwitchTile
import co.aospa.systemui.qs.tiles.DcDimmingTile
import co.aospa.systemui.qs.tiles.HeadsUpTile
import co.aospa.systemui.qs.tiles.PowerShareTile
import co.aospa.systemui.qs.tiles.SoundTile
import co.aospa.systemui.qs.tiles.UsbTetherTile

import com.android.systemui.R
import com.android.systemui.qs.QsEventLogger
import com.android.systemui.qs.pipeline.shared.TileSpec
import com.android.systemui.qs.shared.model.TileCategory
import com.android.systemui.qs.tileimpl.QSTileImpl
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ParanoidQSModule {

    /** Inject AlwaysOnDisplayTile into tileMap in QSModule */
    @Binds
    @IntoMap
    @StringKey(AlwaysOnDisplayTile.TILE_SPEC)
    fun bindAlwaysOnDisplayTile(alwaysOnDisplayTile: AlwaysOnDisplayTile): QSTileImpl<*>

    /** Inject CaffeineTile into tileMap in QSModule */
    @Binds
    @IntoMap
    @StringKey(CaffeineTile.TILE_SPEC)
    fun bindCaffeineTile(caffeineTile: CaffeineTile): QSTileImpl<*>

    /** Inject DataSwitchTile into tileMap in QSModule */
    @Binds
    @IntoMap
    @StringKey(DataSwitchTile.TILE_SPEC)
    fun bindDataSwitchTile(dataSwitchTile: DataSwitchTile): QSTileImpl<*>

    /** Inject DcDimmingTile into tileMap in QSModule */
    @Binds
    @IntoMap
    @StringKey(DcDimmingTile.TILE_SPEC)
    fun bindDcDimmingTile(dcDimmingTile: DcDimmingTile): QSTileImpl<*>

    /** Inject HeadsUpTile into tileMap in QSModule */
    @Binds
    @IntoMap
    @StringKey(HeadsUpTile.TILE_SPEC)
    fun bindHeadsUpTile(headsUpTile: HeadsUpTile): QSTileImpl<*>

    /** Inject PowerShareTile into tileMap in QSModule */
    @Binds
    @IntoMap
    @StringKey(PowerShareTile.TILE_SPEC)
    fun bindPowerShareTile(powerShareTile: PowerShareTile): QSTileImpl<*>

    /** Inject SoundTile into tileMap in QSModule */
    @Binds
    @IntoMap
    @StringKey(SoundTile.TILE_SPEC)
    fun bindSoundTile(soundTile: SoundTile): QSTileImpl<*>

    /** Inject UsbTetherTile into tileMap in QSModule */
    @Binds
    @IntoMap
    @StringKey(UsbTetherTile.TILE_SPEC)
    fun bindUsbTetherTile(usbTetherTile: UsbTetherTile): QSTileImpl<*>

    companion object {
        const val AOD_TILE_SPEC = "aod"
        const val CAFFEINE_TILE_SPEC = "caffeine"
        const val DATASWITCH_TILE_SPEC = "dataswitch"
        const val DCDIMMING_TILE_SPEC = "dc_dimming"
        const val HEADS_UP_TILE_SPEC = "heads_up"
        const val NFC_TILE_SPEC = "nfc"
        const val POWERSHARE_TILE_SPEC = "powershare"
        const val SOUND_TILE_SPEC = "sound"
        const val USB_TETHER_TILE_SPEC = "usb_tether"

        @Provides
        @IntoMap
        @StringKey(AOD_TILE_SPEC)
        fun provideAodTileConfig(uiEventLogger: QsEventLogger): QSTileConfig =
            QSTileConfig(
                tileSpec = TileSpec.create(AOD_TILE_SPEC),
                uiConfig =
                    QSTileUIConfig.Resource(
                        iconRes = R.drawable.ic_qs_aod,
                        labelRes = R.string.quick_settings_aod_label
                    ),
                instanceId = uiEventLogger.getNewInstanceId(),
                category = TileCategory.DISPLAY,
            )

        @Provides
        @IntoMap
        @StringKey(CAFFEINE_TILE_SPEC)
        fun provideCaffeineTileConfig(uiEventLogger: QsEventLogger): QSTileConfig =
            QSTileConfig(
                tileSpec = TileSpec.create(CAFFEINE_TILE_SPEC),
                uiConfig =
                    QSTileUIConfig.Resource(
                        iconRes = R.drawable.ic_qs_caffeine,
                        labelRes = R.string.quick_settings_caffeine_label
                    ),
                instanceId = uiEventLogger.getNewInstanceId(),
                category = TileCategory.DISPLAY,
            )

        @Provides
        @IntoMap
        @StringKey(DATASWITCH_TILE_SPEC)
        fun provideDataSwitchTileConfig(uiEventLogger: QsEventLogger): QSTileConfig =
            QSTileConfig(
                tileSpec = TileSpec.create(DATASWITCH_TILE_SPEC),
                uiConfig =
                    QSTileUIConfig.Resource(
                        iconRes = R.drawable.ic_qs_data_switch_1,
                        labelRes = R.string.qs_data_switch_label
                    ),
                instanceId = uiEventLogger.getNewInstanceId(),
                category = TileCategory.CONNECTIVITY,
            )

        @Provides
        @IntoMap
        @StringKey(DCDIMMING_TILE_SPEC)
        fun provideDcDimmingTileConfig(uiEventLogger: QsEventLogger): QSTileConfig =
            QSTileConfig(
                tileSpec = TileSpec.create(DCDIMMING_TILE_SPEC),
                uiConfig =
                    QSTileUIConfig.Resource(
                        iconRes = R.drawable.ic_dc_dimming_tile,
                        labelRes = R.string.quick_settings_dc_dimming_label
                    ),
                instanceId = uiEventLogger.getNewInstanceId(),
                category = TileCategory.DISPLAY,
            )

        @Provides
        @IntoMap
        @StringKey(HEADS_UP_TILE_SPEC)
        fun provideHeadsUpTileConfig(uiEventLogger: QsEventLogger): QSTileConfig =
            QSTileConfig(
                tileSpec = TileSpec.create(HEADS_UP_TILE_SPEC),
                uiConfig =
                    QSTileUIConfig.Resource(
                        iconRes = R.drawable.ic_qs_heads_up,
                        labelRes = R.string.quick_settings_heads_up_label
                    ),
                instanceId = uiEventLogger.getNewInstanceId(),
                category = TileCategory.ACCESSIBILITY,
            )

        @Provides
        @IntoMap
        @StringKey(NFC_TILE_SPEC)
        fun provideNfcTileConfig(uiEventLogger: QsEventLogger): QSTileConfig =
            QSTileConfig(
                tileSpec = TileSpec.create(NFC_TILE_SPEC),
                uiConfig =
                    QSTileUIConfig.Resource(
                        iconRes = com.android.systemui.res.R.drawable.ic_qs_nfc,
                        labelRes = com.android.systemui.res.R.string.quick_settings_nfc_label
                    ),
                instanceId = uiEventLogger.getNewInstanceId(),
                category = TileCategory.CONNECTIVITY,
            )

        @Provides
        @IntoMap
        @StringKey(POWERSHARE_TILE_SPEC)
        fun providePowershareTileConfig(uiEventLogger: QsEventLogger): QSTileConfig =
            QSTileConfig(
                tileSpec = TileSpec.create(POWERSHARE_TILE_SPEC),
                uiConfig =
                    QSTileUIConfig.Resource(
                        iconRes = R.drawable.ic_qs_powershare,
                        labelRes = R.string.quick_settings_powershare_label
                    ),
                instanceId = uiEventLogger.getNewInstanceId(),
                category = TileCategory.UTILITIES,
            )

        @Provides
        @IntoMap
        @StringKey(SOUND_TILE_SPEC)
        fun provideSoundTileConfig(uiEventLogger: QsEventLogger): QSTileConfig =
            QSTileConfig(
                tileSpec = TileSpec.create(SOUND_TILE_SPEC),
                uiConfig =
                    QSTileUIConfig.Resource(
                        iconRes = R.drawable.ic_qs_ringer_audible,
                        labelRes = R.string.quick_settings_sound_label
                    ),
                instanceId = uiEventLogger.getNewInstanceId(),
                category = TileCategory.UTILITIES,
            )

        @Provides
        @IntoMap
        @StringKey(USB_TETHER_TILE_SPEC)
        fun provideUsbTetherTileConfig(uiEventLogger: QsEventLogger): QSTileConfig =
            QSTileConfig(
                tileSpec = TileSpec.create(USB_TETHER_TILE_SPEC),
                uiConfig =
                    QSTileUIConfig.Resource(
                        iconRes = R.drawable.ic_qs_usb_tether,
                        labelRes = R.string.quick_settings_usb_tether_label
                    ),
                instanceId = uiEventLogger.getNewInstanceId(),
                category = TileCategory.CONNECTIVITY,
            )
    }
}
