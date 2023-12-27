/*
 * Copyright (C) 2023 The Android Open Source Project
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

package co.aospa.systemui

import android.content.Context
import com.android.systemui.SystemUIAppComponentFactoryBase

/**
 * Starts up SystemUI using [ParanoidSystemUIInitializer].
 *
 * The [SystemUIAppComponentFactoryBase] is required for proper SystemUI functionality.
 *
 * @see SystemUIAppComponentFactoryBase
 */
class ParanoidSystemUIAppComponentFactory : SystemUIAppComponentFactoryBase()  {
    override fun createSystemUIInitializer(context: Context) = ParanoidSystemUIInitializer(context)
}
