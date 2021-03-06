/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.gigaspaces.log;

/**
 * A matcher that will be executed only on the first log file.
 *
 * @author kimchy
 */

public class FirstFileLogEntryMatcher extends LogEntryMatcherFilter {

    private static final long serialVersionUID = 1;

    public FirstFileLogEntryMatcher() {
        this(new AllLogEntryMatcher());
    }

    public FirstFileLogEntryMatcher(LogEntryMatcher matcher) {
        super(matcher);
    }

    @Override
    protected boolean filterJustLogs() {
        return false;
    }

    protected Operation filter(LogEntry entry) {
        if (entry.isFileMarker()) {
            if (entry.getPosition() > 0) {
                return Operation.IGNORE;
            }
        }
        return Operation.CONTINUE;
    }
}