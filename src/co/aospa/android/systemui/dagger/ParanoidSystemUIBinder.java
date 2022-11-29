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

import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.dagger.KeyguardModule;
import com.android.systemui.recents.RecentsModule;

import co.aospa.android.systemui.ParanoidServices;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(includes = {RecentsModule.class, KeyguardModule.class})
public abstract class ParanoidSystemUIBinder {

    /**
     * Inject into ParanoidServices.
     */
    @Binds
    @IntoMap
    @ClassKey(ParanoidServices.class)
    public abstract CoreStartable bindParanoidServices(ParanoidServices sysui);

}