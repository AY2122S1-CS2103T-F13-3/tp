package seedu.address.ui;

import static com.calendarfx.view.DayViewBase.EarlyLateHoursStrategy.HIDE;
import static com.calendarfx.view.DayViewBase.HoursLayoutStrategy.FIXED_HOUR_COUNT;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.WeekPage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.TimeRange;

public class WeekPanel extends UiPart<Region> {

    private static final String FXML = "WeekPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

//    private final WeekPage calendarView;
    private final CalendarView calendarView;
    @FXML
    private ScrollPane weekView;

    /**
     * Creates a {@code WeekPanel} with the given {@code Calendar}.
     */
    public WeekPanel(Calendar calendar) {
        super(FXML);
//        calendarView = new WeekPage();
        calendarView = new CalendarView();
        initialiseCalendar(calendar);
        createTimeThread();
    }

    /**
     * Sets up CalendarFX.
     * Adapted from CalendarFX developer manual.
     * http://dlsc.com/wp-content/html/calendarfx/manual.html#_quick_start
     */
    private void initialiseCalendar(Calendar calendar) {
        calendar.setReadOnly(true);
        CalendarSource calendarSource = new CalendarSource();
        calendarSource.getCalendars().addAll(calendar);

        calendarView.getCalendarSources().addAll(calendarSource);
        calendarView.setStartTime(TimeRange.DAY_START);
        calendarView.setEndTime(TimeRange.DAY_END);
        calendarView.setShowPrintButton(false);
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSourceTrayButton(false);
        calendarView.setShowPageToolBarControls(false);

        initialiseDayPage();
        initialiseWeekPage();
        initialiseMonthPage();
        initialiseYearPage();

        weekView.setContent(calendarView);
    }

    private void initialiseDayPage() {
        calendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
        calendarView.getDayPage().setStartTime(TimeRange.DAY_START);

        calendarView.getDayPage().getDetailedDayView().setVisibleHours(15);
        calendarView.getDayPage().getDetailedDayView().setHoursLayoutStrategy(FIXED_HOUR_COUNT);
        calendarView.getDayPage().getDetailedDayView().getDayView().setDisable(true);
    }

    private void initialiseWeekPage() {
        calendarView.getWeekPage().getDetailedWeekView().setVisibleHours(15);
        calendarView.getWeekPage().getDetailedWeekView().setHoursLayoutStrategy(FIXED_HOUR_COUNT);
        calendarView.getWeekPage().getDetailedWeekView().setEarlyLateHoursStrategy(HIDE);
        calendarView.getWeekPage().getDetailedWeekView().setShowScrollBar(false);

        calendarView.getWeekPage().getDetailedWeekView().getWeekView().setDisable(true);
    }

    private void initialiseMonthPage() {
        calendarView.getMonthPage().getMonthView().setDisable(true);
    }

    private void initialiseYearPage() {
        calendarView.getYearPage().getMonthSheetView().setDisable(true);
    }

    /**
     * Adapted from CalendarFX developer manual.
     * http://dlsc.com/wp-content/html/calendarfx/manual.html#_quick_start
     */
    private void createTimeThread() {
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });
                    try {
                        // update every 60 seconds
                        sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }

    public void goNext() {
        calendarView.getSelectedPage().goForward();
    }

    public void goToday() {
        calendarView.getSelectedPage().goToday();
    }

    public void goBack() {
        calendarView.getSelectedPage().goBack();
    }

    public void showDay() {
        calendarView.showDayPage();
    }

    public void showWeek() {
        calendarView.showWeekPage();
    }

    public void showMonth() {
        calendarView.showMonthPage();
    }

    public void showYear() {
        calendarView.showYearPage();
    }
}
