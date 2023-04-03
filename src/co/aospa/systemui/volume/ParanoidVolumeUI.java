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

package co.aospa.systemui.volume;

import android.content.Context;

import com.android.systemui.dagger.SysUISingleton;
import com.android.systemui.volume.VolumeDialogComponent;
import com.android.systemui.volume.VolumeUI;

import javax.inject.Inject;

/**
 * TODO: Remove once {@link VolumeUI#VolumeUI} doesn't depend on {@link VolumeDialogComponent} anymore
 */
@SysUISingleton
public class ParanoidVolumeUI extends VolumeUI {

    @Inject
    public ParanoidVolumeUI(Context context,
            ParanoidVolumeDialogComponent volumeDialogComponent) {
        super(context, volumeDialogComponent);
    }
}
