
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class CalendarAPIQuickstart {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME =
        "Google Calendar API Java Quickstart";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        //    "/Users/qcluu/calendar-java-quickstart");
        System.getProperty("user.home"), ".credentials/calendar-java-quickstart");
    //    System.getProperty("user.home"), "/calendar-java-quickstart");

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     * <p/>
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    /*
    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR_READONLY);
    */
    private static final java.util.Collection<String> SCOPES =
        CalendarScopes.all();

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize1() throws IOException {
        URL url = CalendarAPIQuickstart.class.getResource("/resources");
        System.out.println(url);

        // Load client secrets.
        InputStream in =
            CalendarAPIQuickstart.class.getResourceAsStream("/client_secret_zd.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
            new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
            "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    private static Credential authorize4() throws Exception {
        GoogleCredential credential = new GoogleCredential.Builder()
            .setTransport(HTTP_TRANSPORT)
            .setJsonFactory(JSON_FACTORY)
            .setServiceAccountId("quan@zoomdata.com")
            .setServiceAccountPrivateKeyFromP12File(new File("/Users/qcluu/git/google-calendar-poc/calendar api project-482aa8bfee9f.p12"))
            .setServiceAccountScopes(SCOPES)
            .setServiceAccountUser("quan@zoomdata.com")
            .build();

        return credential;
    }

    /**
     * Authorizes the installed application to access user's protected data.
     */
    private static Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
            new InputStreamReader(CalendarAPIQuickstart.class.getResourceAsStream("/client_secret.json")));
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
            Collections.singleton(CalendarScopes.CALENDAR))
            .setDataStoreFactory(DATA_STORE_FACTORY)
            .build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private static Event newEvent() {
        Event event = new Event();
        event.setSummary("New Event");
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 3600000);
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }


    /**
     * Build and return an authorized Calendar client service.
     *
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
    getCalendarService() throws Exception {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    public static void main(String[] args) throws Exception {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
            getCalendarService();


        CalendarList feed = service.calendarList().list().execute();
        View.display(feed);

        List<CalendarListEntry> list = feed.getItems();
        for (final CalendarListEntry item : list) {
            System.out.println(item.get("id=") + ":" + item);

        }

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        String user = "primary";
        user = "quancluu@gmail.com";


        // Retrieve the calendar
        com.google.api.services.calendar.model.Calendar calendar =
            service.calendars().get(user).execute();

        System.out.println(calendar.getId() + ": ============= Beginning of Calendar Summary ============= ");
        View.display(calendar);
        System.out.println(calendar.getId() + ": ============= End of Calendar Summary ============= ");

      //  System.out.println(calendar.getSummary());

        Events events = service.events().list(user)
            .setMaxResults(10)
            .setTimeMin(now)
            .setOrderBy("startTime") // or "updated": Order by last modification time (ascending).
            .setSingleEvents(true)
                //          .setShowDeleted(true)
            .execute();
        List<Event> items = events.getItems();

        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events for " + user);
            for (Event event : items) {

                View.display(event);

                /*
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                String summary = event.getSummary();
                if (StringUtils.isEmpty(summary)) {
                    summary = "Unknown";
                }

                System.out.printf(event.getId() + ": %s (%s)\n", summary, start);
                */

            }
        }

        System.out.println("All Done!!!");
    }

}

