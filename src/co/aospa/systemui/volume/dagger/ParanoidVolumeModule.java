/*
 * Copyright (C) 2020 The Android Open Source Project
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
package co.aospa.systemui.volume.dagger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.media.AudioManager;
import android.os.Looper;

import co.aospa.systemui.volume.ParanoidVolumeUI;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.CoreStartable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.volume.CsdWarningDialog;
import com.android.systemui.volume.VolumeComponent;
import com.android.systemui.volume.VolumeDialogImpl;
import com.android.systemui.volume.VolumePanelDialogReceiver;
import com.android.systemui.volume.VolumeUI;
import com.android.systemui.volume.dagger.AncModule;
import com.android.systemui.volume.dagger.AudioModule;
import com.android.systemui.volume.dagger.CaptioningModule;
import com.android.systemui.volume.dagger.MediaDevicesModule;
import com.android.systemui.volume.dagger.SpatializerModule;
import com.android.systemui.volume.dagger.VolumeModule;
import com.android.systemui.volume.domain.interactor.VolumeDialogInteractor;
import com.android.systemui.volume.domain.interactor.VolumePanelNavigationInteractor;
import com.android.systemui.volume.panel.dagger.VolumePanelComponent;
import com.android.systemui.volume.panel.dagger.factory.VolumePanelComponentFactory;
import com.android.systemui.volume.panel.shared.flag.VolumePanelFlag;
import com.android.systemui.volume.ui.navigation.VolumeNavigator;

import co.aospa.systemui.tristate.dagger.TriStateModule;
import co.aospa.systemui.volume.ParanoidVolumeDialogComponent;
import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;

/**
 * Dagger Module for code in the volume package.
 * Fork of {@link VolumeModule}
 */
@Module(
        includes = {
                AudioModule.class,
                AncModule.class,
                CaptioningModule.class,
                MediaDevicesModule.class,
                SpatializerModule.class,
                TriStateModule.class
        },
        subcomponents = {
                VolumePanelComponent.class
        }
)
public interface ParanoidVolumeModule {

    /**
     * Binds [VolumePanelDialogReceiver]
     */
    @Binds
    @IntoMap
    @ClassKey(VolumePanelDialogReceiver.class)
    BroadcastReceiver bindVolumePanelDialogReceiver(VolumePanelDialogReceiver receiver);

    /** Starts VolumeUI. */
    @Binds
    @IntoMap
    @ClassKey(ParanoidVolumeUI.class)
    CoreStartable bindVolumeUIStartable(ParanoidVolumeUI impl);

    /** Listen to config changes for VolumeUI. */
    @Binds
    @IntoSet
    ConfigurationController.ConfigurationListener bindVolumeUIConfigChanges(ParanoidVolumeUI impl);

    /**  */
    @Binds
    VolumeComponent provideVolumeComponent(ParanoidVolumeDialogComponent volumeDialogComponent);

    /**  */
    @Binds
    VolumePanelComponentFactory bindVolumePanelComponentFactory(VolumePanelComponent.Factory impl);

    /**  */
    @Provides
    static VolumeDialog provideVolumeDialog(
            Context context,
            VolumeDialogController volumeDialogController,
            AccessibilityManagerWrapper accessibilityManagerWrapper,
            DeviceProvisionedController deviceProvisionedController,
            ConfigurationController configurationController,
            MediaOutputDialogManager mediaOutputDialogManager,
            InteractionJankMonitor interactionJankMonitor,
            VolumePanelNavigationInteractor volumePanelNavigationInteractor,
            VolumeNavigator volumeNavigator,
            CsdWarningDialog.Factory csdFactory,
            DevicePostureController devicePostureController,
            VolumePanelFlag volumePanelFlag,
            DumpManager dumpManager,
            Lazy<SecureSettings> secureSettings,
            VibratorHelper vibratorHelper,
            SystemClock systemClock,
            VolumeDialogInteractor interactor) {
        VolumeDialogImpl impl = new VolumeDialogImpl(
                context,
                volumeDialogController,
                accessibilityManagerWrapper,
                deviceProvisionedController,
                configurationController,
                mediaOutputDialogManager,
                interactionJankMonitor,
                volumePanelNavigationInteractor,
                volumeNavigator,
                true, /* should listen for jank */
                csdFactory,
                devicePostureController,
                Looper.getMainLooper(),
                volumePanelFlag,
                dumpManager,
                secureSettings,
                vibratorHelper,
                systemClock,
                interactor);
        impl.setStreamImportant(AudioManager.STREAM_SYSTEM, false);
        impl.setAutomute(true);
        impl.setSilentMode(false);
        return impl;
    }
}
