/**
 * Created by qcluu on 12/7/17.
 */
/*
 * Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import com.google.api.services.calendar.model.*;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Yaniv Inbar
 */
public class View {

    static void header(String name) {
        System.out.println();
        System.out.println("============== " + name + " ==============");
        System.out.println();
    }

    static void display(CalendarList feed) {
        if (feed.getItems() != null) {
            for (CalendarListEntry entry : feed.getItems()) {
                System.out.println();
                System.out.println("-----------------------------------------------");
                display(entry);
            }
        }
    }

    static void display(Events feed) {
        if (feed.getItems() != null) {
            for (Event entry : feed.getItems()) {
                System.out.println();
                System.out.println("-----------------------------------------------");
                display(entry);
            }
        }
    }

    static void display(CalendarListEntry entry) {
        System.out.println("ID: " + entry.getId());
        System.out.println("Summary: " + entry.getSummary());
        if (entry.getDescription() != null) {
            System.out.println("Description: " + entry.getDescription());
        }
    }

    static void display(Calendar entry) {
        System.out.println("ID: " + entry.getId());
        System.out.println("Summary: " + entry.getSummary());
        if (entry.getDescription() != null) {
            System.out.println("Description: " + entry.getDescription());
        }
    }

    static void display(Event event) {
        final String id = event.getId();
        System.out.println("=================== Start of Event " + id);
        String summary = event.getSummary();
        if (summary == null) {
            summary = "";
        }
        String description = event.getDescription();
        if (description == null) {
            description = "";
        }
        if (StringUtils.isEmpty(summary) == false) {
            System.out.println(summary + " - " + description);
        }
        final EventDateTime startTime = event.getStart();
        final long startTimeMs = startTime.getDateTime().getValue();
        if (event.getStart() != null) {
            System.out.println("Start Time: " + event.getStart());
        }
        if (event.getEnd() != null) {
            System.out.println("End Time: " + event.getEnd());
        }
        final EventDateTime endTime = event.getEnd();
        final long endTimeMs = endTime.getDateTime().getValue();

        final long durationMin = (endTimeMs - startTimeMs);
        System.out.println("Duration: " + Utils.MillisToLongDHMS(durationMin));
        System.out.println("=================== End of Event " + id);

    }
}
