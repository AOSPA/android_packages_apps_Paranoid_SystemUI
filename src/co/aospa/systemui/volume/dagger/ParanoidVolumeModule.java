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

import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.volume.VolumeComponent;
import com.android.systemui.volume.VolumePanelDialogReceiver;
import com.android.systemui.volume.dagger.AncModule;
import com.android.systemui.volume.dagger.AudioModule;
import com.android.systemui.volume.dagger.AudioSharingModule;
import com.android.systemui.volume.dagger.CaptioningModule;
import com.android.systemui.volume.dagger.MediaDevicesModule;
import com.android.systemui.volume.dagger.SpatializerModule;
import com.android.systemui.volume.dialog.VolumeDialogPlugin;
import com.android.systemui.volume.dialog.dagger.VolumeDialogPluginComponent;
import com.android.systemui.volume.dialog.dagger.factory.VolumeDialogPluginComponentFactory;
import com.android.systemui.volume.panel.dagger.VolumePanelComponent;
import com.android.systemui.volume.panel.dagger.factory.VolumePanelComponentFactory;

import co.aospa.systemui.tristate.dagger.TriStateModule;
import co.aospa.systemui.volume.ParanoidVolumeDialogComponent;
import co.aospa.systemui.volume.ParanoidVolumeUI;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;

/** Dagger Module for code in the volume package. */
@Module(
        includes = {
                AudioModule.class,
                AudioSharingModule.class,
                AncModule.class,
                CaptioningModule.class,
                MediaDevicesModule.class,
                SpatializerModule.class,
                TriStateModule.class
        },
        subcomponents = {
                VolumePanelComponent.class,
                VolumeDialogPluginComponent.class,
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

    @Binds
    VolumeDialogPluginComponentFactory bindVolumeDialogPluginComponentFactory(
            VolumeDialogPluginComponent.Factory impl);

    @Binds
    VolumeDialog bindVolumeDialog(VolumeDialogPlugin impl);
}
