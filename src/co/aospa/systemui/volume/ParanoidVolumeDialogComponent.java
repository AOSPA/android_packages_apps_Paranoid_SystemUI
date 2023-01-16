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

package co.aospa.systemui.volume;

import android.content.Context;

import com.android.systemui.dagger.SysUISingleton;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.volume.VolumeDialogComponent;
import com.android.systemui.volume.VolumeDialogControllerImpl;

import javax.inject.Inject;

@SysUISingleton
public class ParanoidVolumeDialogComponent extends VolumeDialogComponent {

    @Inject
    public ParanoidVolumeDialogComponent(Context context,
            KeyguardViewMediator keyguardViewMediator,
            ActivityStarter activityStarter,
            VolumeDialogControllerImpl volumeDialogController,
            DemoModeController demoModeController,
            PluginDependencyProvider pluginDependencyProvider,
            ExtensionController extensionController,
            TunerService tunerService,
            VolumeDialog volumeDialog) {
        super(context, keyguardViewMediator, activityStarter, volumeDialogController,
                demoModeController, pluginDependencyProvider, extensionController, tunerService,
                volumeDialog);
    }
}
