/*
 * Copyright 2016 Paul Gestwicki
 *
 * This file is part of The Social Startup Game
 *
 * The Social Startup Game is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Social Startup Game is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with The Social Startup Game.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.bsu.cybersec.java;

import edu.bsu.cybersec.core.GameConfig;
import edu.bsu.cybersec.core.ui.PlatformSpecificDateFormatter;
import playn.core.Log;
import playn.java.JavaPlatform;
import playn.scene.Pointer;
import react.Value;

import static com.google.common.base.Preconditions.checkNotNull;

public final class JavaGameConfig extends GameConfig.Default {

    private final Java7DateFormatter formatter = new Java7DateFormatter();
    Value<Boolean> skipIntro = Value.create(super.skipIntro());
    Value<Boolean> skipWelcome = Value.create(super.skipWelcome());
    Value<Boolean> useNarrativeEvents = Value.create(super.useNarrativeEvents());
    Value<Boolean> muteMusic = Value.create(super.muteMusic());
    Value<Boolean> showConsent = Value.create(super.showConsentForm());

    private JavaPlatform platform;

    @Override
    public PlatformSpecificDateFormatter dateFormatter() {
        return formatter;
    }

    @Override
    public boolean skipIntro() {
        return skipIntro.get();
    }

    @Override
    public boolean skipWelcome() {
        return skipWelcome.get();
    }

    @Override
    public boolean useNarrativeEvents() {
        return useNarrativeEvents.get();
    }

    @Override
    public boolean muteMusic() {
        return muteMusic.get();
    }

    @Override
    public boolean showConsentForm() {
        return showConsent.get();
    }

    @Override
    public void enableGameplayLogging() {
        checkNotNull(platform, "Platform must be specified first.");
        platform.log().setCollector(new EchoCollector());
    }

    public void setPlatform(JavaPlatform platform) {
        this.platform = platform;
    }

    private class EchoCollector implements Log.Collector {
        @Override
        public void logged(Log.Level level, String msg, Throwable e) {
            if (level.equals(Log.Level.INFO)) {
                System.out.println("--> " + msg);
            }
        }
    }

    @Override
    public boolean isEmployeeExpansionTrigger(Pointer.Interaction iact) {
        return !iact.event.isTouch;
    }
}
