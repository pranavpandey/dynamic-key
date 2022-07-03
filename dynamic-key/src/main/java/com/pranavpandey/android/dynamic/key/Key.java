/*
 * Copyright 2022 Pranav Pandey
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

package com.pranavpandey.android.dynamic.key;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Constant values for the key status.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Key {

    /**
     * Constant for the key prefix.
     */
    String BASE = "adk_key_";

    /**
     * Key constant for the status.
     */
    String STATUS = "status";

    /**
     * Key constant for the activated state.
     */
    String ACTIVATED = "activated";

    /**
     * Key constant for the installed state.
     */
    String INSTALLED = "installed";

    /**
     * An interface to hold key status.
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface Status {

        /**
         * Constant for the unknown key status.
         */
        int UNKNOWN = -1;

        /**
         * Constant for the buy key status.
         */
        int BUY = 0;

        /**
         * Constant for the activated key status.
         */
        int ACTIVATED = 1;

        /**
         * Constant for the not found key status.
         */
        int NOT_FOUND = 2;

        /**
         * Constant for the available key status.
         */
        int AVAILABLE = 3;

        /**
         * Constant for the removed key status.
         */
        int REMOVED = 4;
    }

    /**
     * An interface to hold value constants.
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface Value {

        /**
         * Default value for the key status.
         */
        int STATUS = Key.Status.BUY;

        /**
         * Default value for the activated state.
         */
        boolean ACTIVATED = false;

        /**
         * Default value for the installed state.
         */
        boolean INSTALLED = false;
    }

    /**
     * Constant values for the key intent.
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface Intent {

        /**
         * Intent action constant for the dynamic key.
         */
        String ACTION = "com.pranavpandey.android.dynamic.key.intent.action";

        /**
         * Intent action constant to activate the key.
         */
        String ACTION_ACTIVATE = ACTION + ".ACTIVATE";

        /**
         * Intent action constant for the {@link App#PACKAGE_ROTATION} key.
         */
        String ACTION_ROTATION = ACTION + ".ROTATION";

        /**
         * Intent action constant for the {@link App#PACKAGE_CALENDAR} key.
         */
        String ACTION_CALENDAR = ACTION + ".CALENDAR";

        /**
         * Intent action constant for the {@link App#PACKAGE_THEME} key.
         */
        String ACTION_THEME = ACTION + ".THEME";

        /**
         * Intent extra constant for the dynamic key.
         */
        String EXTRA = "extra_dynamic_key";
    }

    /**
     * Constant values for the app package.
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface App {

        /**
         * Package for the rotation app.
         */
        String PACKAGE_ROTATION = "com.pranavpandey.rotation";

        /**
         * Package for the calendar app.
         */
        String PACKAGE_CALENDAR = "com.pranavpandey.calendar";

        /**
         * Package for the theme app.
         */
        String PACKAGE_THEME = "com.pranavpandey.theme";
    }
}
