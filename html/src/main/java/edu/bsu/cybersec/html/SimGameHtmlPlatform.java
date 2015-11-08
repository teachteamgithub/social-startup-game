/*
 * Copyright 2015 Paul Gestwicki
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

package edu.bsu.cybersec.html;

import edu.bsu.cybersec.core.SimGamePlatform;
import edu.bsu.cybersec.core.ui.PlatformSpecificDateFormatter;
import playn.html.HtmlPlatform;

public class SimGameHtmlPlatform extends HtmlPlatform implements SimGamePlatform {

    private final GWTDateFormatter formatter = new GWTDateFormatter();

    public SimGameHtmlPlatform(Config config) {
        super(config);
    }

    @Override
    public PlatformSpecificDateFormatter dateFormatter() {
        return formatter;
    }

    @Override
    public boolean skipIntro() {
        return false;
    }
}
