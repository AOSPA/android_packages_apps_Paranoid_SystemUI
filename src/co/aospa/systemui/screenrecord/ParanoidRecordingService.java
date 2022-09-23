/*
 * Copyright (C) 2022 StatiXOS
 * SPDX-License-Identifer: Apache-2.0
 */

package co.aospa.systemui.screenrecord;

import android.app.NotificationManager;

import com.android.internal.logging.UiEventLogger;

import com.android.systemui.dagger.qualifiers.LongRunning;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingService;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ParanoidRecordingService extends RecordingService {

    @Inject
    public StatixRecordingService(RecordingController controller, @LongRunning Executor executor,
            UiEventLogger uiEventLogger, NotificationManager notificationManager,
            UserContextProvider userContextTracker, KeyguardDismissUtil keyguardDismissUtil) {
        super(controller, executor, uiEventLogger, notificationManager, userContextTracker, keyguardDismissUtil);
    }

}
