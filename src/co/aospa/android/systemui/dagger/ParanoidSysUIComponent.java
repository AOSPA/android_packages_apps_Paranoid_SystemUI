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
package co.aospa.android.systemui.dagger;

import com.android.systemui.dagger.DefaultBroadcastReceiverBinder;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dagger.SysUISingleton;
import com.android.systemui.dagger.SystemUICoreStartableModule;
import com.android.systemui.dagger.SystemUIModule;
import com.android.systemui.keyguard.dagger.KeyguardModule;
import com.android.systemui.recents.RecentsModule;
import com.android.systemui.statusbar.dagger.CentralSurfacesModule;

import dagger.Subcomponent;

@SysUISingleton
@Subcomponent(modules = {
        CentralSurfacesModule.class,
        DefaultBroadcastReceiverBinder.class,
        DependencyProvider.class,
        KeyguardModule.class,
        RecentsModule.class,
        SystemUICoreStartableModule.class,
        SystemUIModule.class,
        ParanoidSystemUIModule.class})
public interface ParanoidSysUIComponent extends SysUIComponent {
    @SysUISingleton
    @Subcomponent.Builder
    interface Builder extends SysUIComponent.Builder {
        ParanoidSysUIComponent build();
    }
}
