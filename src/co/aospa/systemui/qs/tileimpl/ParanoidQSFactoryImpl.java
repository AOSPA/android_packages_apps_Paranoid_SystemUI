/*
 * Copyright (C) 2022 Paranoid Android
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
package co.aospa.systemui.qs.tileimpl;

import androidx.annotation.Nullable;

import com.android.systemui.dagger.SysUISingleton;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSFactoryImpl;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.AirplaneModeTile;
import com.android.systemui.qs.tiles.AlarmTile;
import com.android.systemui.qs.tiles.BatterySaverTile;
import com.android.systemui.qs.tiles.BluetoothTile;
import com.android.systemui.qs.tiles.CameraToggleTile;
import com.android.systemui.qs.tiles.CastTile;
import com.android.systemui.qs.tiles.CellularTile;
import com.android.systemui.qs.tiles.ColorCorrectionTile;
import com.android.systemui.qs.tiles.ColorInversionTile;
import com.android.systemui.qs.tiles.DataSaverTile;
import com.android.systemui.qs.tiles.DeviceControlsTile;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.qs.tiles.DreamTile;
import com.android.systemui.qs.tiles.FlashlightTile;
import com.android.systemui.qs.tiles.HotspotTile;
import com.android.systemui.qs.tiles.InternetTile;
import com.android.systemui.qs.tiles.LocationTile;
import com.android.systemui.qs.tiles.MicrophoneToggleTile;
import com.android.systemui.qs.tiles.NfcTile;
import com.android.systemui.qs.tiles.NightDisplayTile;
import com.android.systemui.qs.tiles.OneHandedModeTile;
import com.android.systemui.qs.tiles.QRCodeScannerTile;
import com.android.systemui.qs.tiles.QuickAccessWalletTile;
import com.android.systemui.qs.tiles.ReduceBrightColorsTile;
import com.android.systemui.qs.tiles.RotationLockTile;
import com.android.systemui.qs.tiles.ScreenRecordTile;
import com.android.systemui.qs.tiles.UiModeNightTile;
import com.android.systemui.qs.tiles.WifiTile;
import com.android.systemui.qs.tiles.WorkModeTile;
import com.android.systemui.util.leak.GarbageMonitor;

import javax.inject.Inject;
import javax.inject.Provider;

import co.aospa.systemui.qs.tiles.CaffeineTile;
import co.aospa.systemui.qs.tiles.DataSwitchTile;
import co.aospa.systemui.qs.tiles.DcDimmingTile;
import co.aospa.systemui.qs.tiles.HeadsUpTile;
import co.aospa.systemui.qs.tiles.UsbTetherTile;
import dagger.Lazy;

@SysUISingleton
public class ParanoidQSFactoryImpl extends QSFactoryImpl {

    private final Provider<CaffeineTile> mCaffeineTileProvider;
    private final Provider<DataSwitchTile> mDataSwitchTileProvider;
    private final Provider<HeadsUpTile> mHeadsUpTileProvider;
    private final Provider<DcDimmingTile> mDcDimmingTileProvider;
    private final Provider<UsbTetherTile> mUsbTetherTileProvider;

    @Inject
    public ParanoidQSFactoryImpl(Lazy<QSHost> qsHostLazy,
            Provider<CustomTile.Builder> customTileBuilderProvider,
            Provider<WifiTile> wifiTileProvider,
            Provider<InternetTile> internetTileProvider,
            Provider<BluetoothTile> bluetoothTileProvider,
            Provider<CellularTile> cellularTileProvider,
            Provider<DndTile> dndTileProvider,
            Provider<ColorInversionTile> colorInversionTileProvider,
            Provider<AirplaneModeTile> airplaneModeTileProvider,
            Provider<WorkModeTile> workModeTileProvider,
            Provider<RotationLockTile> rotationLockTileProvider,
            Provider<FlashlightTile> flashlightTileProvider,
            Provider<LocationTile> locationTileProvider,
            Provider<CastTile> castTileProvider,
            Provider<HotspotTile> hotspotTileProvider,
            Provider<BatterySaverTile> batterySaverTileProvider,
            Provider<DataSaverTile> dataSaverTileProvider,
            Provider<NightDisplayTile> nightDisplayTileProvider,
            Provider<NfcTile> nfcTileProvider,
            Provider<GarbageMonitor.MemoryTile> memoryTileProvider,
            Provider<UiModeNightTile> uiModeNightTileProvider,
            Provider<ScreenRecordTile> screenRecordTileProvider,
            Provider<ReduceBrightColorsTile> reduceBrightColorsTileProvider,
            Provider<CameraToggleTile> cameraToggleTileProvider,
            Provider<MicrophoneToggleTile> microphoneToggleTileProvider,
            Provider<DeviceControlsTile> deviceControlsTileProvider,
            Provider<AlarmTile> alarmTileProvider,
            Provider<QuickAccessWalletTile> quickAccessWalletTileProvider,
            Provider<QRCodeScannerTile> qrCodeScannerTileProvider,
            Provider<OneHandedModeTile> oneHandedModeTileProvider,
            Provider<ColorCorrectionTile> colorCorrectionTileProvider,
            Provider<DreamTile> dreamTileProvider,
            Provider<CaffeineTile> caffeineTileProvider,
            Provider<DataSwitchTile> dataSwitchTileProvider,
            Provider<HeadsUpTile> headsUpTileProvider,
            Provider<DcDimmingTile> dcDimTileProvider,
            Provider<UsbTetherTile> usbTetherTileProvider) {
        super(qsHostLazy, customTileBuilderProvider, wifiTileProvider, internetTileProvider,
                bluetoothTileProvider, cellularTileProvider, dndTileProvider,
                colorInversionTileProvider, airplaneModeTileProvider, workModeTileProvider,
                rotationLockTileProvider, flashlightTileProvider, locationTileProvider,
                castTileProvider, hotspotTileProvider, batterySaverTileProvider,
                dataSaverTileProvider, nightDisplayTileProvider, nfcTileProvider,
                memoryTileProvider, uiModeNightTileProvider, screenRecordTileProvider,
                reduceBrightColorsTileProvider, cameraToggleTileProvider,
                microphoneToggleTileProvider, deviceControlsTileProvider, alarmTileProvider,
                quickAccessWalletTileProvider, qrCodeScannerTileProvider, oneHandedModeTileProvider,
                colorCorrectionTileProvider, dreamTileProvider);
        mCaffeineTileProvider = caffeineTileProvider;
        mDataSwitchTileProvider = dataSwitchTileProvider;
        mHeadsUpTileProvider = headsUpTileProvider;
        mDcDimmingTileProvider = dcDimTileProvider;
        mUsbTetherTileProvider = usbTetherTileProvider;
    }

    @Nullable
    @Override
    protected QSTileImpl createTileInternal(String tileSpec) {
        switch (tileSpec) {
            case "caffeine":
                return mCaffeineTileProvider.get();
            case "dataswitch":
                return mDataSwitchTileProvider.get();
            case "heads_up":
                return mHeadsUpTileProvider.get();
            case "dc_dimming":
                return mDcDimmingTileProvider.get();
            case "usb_tether":
                return mUsbTetherTileProvider.get();
            default:
                return super.createTileInternal(tileSpec);
        }
    }
}
