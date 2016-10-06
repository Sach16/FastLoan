package com.whatsloan.dsa.uiactivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customviews.CalenderSqureButton;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AttendanceMember;
import com.whatsloan.dsa.model.AttendanceMemberData;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 10/4/16.
 */
public class DSAViewAttendanceHistoryCalendar extends DSABaseActivity {
    private static final String tag = "MyCalendarActivity";

    private float x1, x2;
    private int m_cRowId;
    static final int MIN_DISTANCE = 100;
    private boolean SWIPED = false;
    ArrayList<Integer> m_cPosn;
    ArrayList<Integer> m_cPreviousPos ;
    private Calendar mCalendar;


    @Nullable
    @Bind(R.id.ATENDANCE_LAY)
    LinearLayout m_cSwipeLay;

    @Nullable
    @Bind(R.id.currentMonth)
    TextView currentMonth;

    @Nullable
    @Bind(R.id.prevMonth)
    ImageView prevMonth;

    @Nullable
    @Bind(R.id.nextMonth)
    ImageView nextMonth;

    @Nullable
    @Bind(R.id.calendar)
    GridView calendarView;

    @Nullable
    @Bind(R.id.PRESENT_TXT)
    TextView presentText;

    @Nullable
    @Bind(R.id.LEAVE_TXT)
    TextView leaveText;


    private GridCellAdapter adapter;
    private Calendar m_cObjCalendar;
    private HashMap<String, String> m_cBeforeJs;
    private HashMap<String, String> m_cAbsents;
    private HashMap<String, String> m_cToday;
    private HashMap<String, String> m_cHolidays;

    private final int[] m_cDaysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private int m_cMonth;
    private int m_cYear;
    private int m_cToday_Date;
    private int m_cMonth_Now;
    private int m_cYear_Now;

    private final String[] m_cDaysForWeeks = new String[] { "Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday" };

    private final String[] m_cMonths = { "Jan", "Feb", "Mar",
            "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec" };


    @SuppressWarnings("unused")
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    ArrayList<String> m_cDayList;
    //    ArrayList<String> m_cMrngList, m_cNoonList, m_cEvengList;
    String m_cSelectedDate;
    String m_cTitle;
    int m_cPresent;
    int m_cAbsent;

    private AttendanceMember m_cAttendanceMember;
    public CalenderSqureButton m_cSelectedView;

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.view_attendance_history_calendar);
        ButterKnife.bind(this);

        // Slider Initilization
        m_cAttendanceMember = (AttendanceMember) getIntent().getParcelableExtra("ATTENDANCE_MEMBER");
        m_cTitle = getIntent().getStringExtra("ATTEN_NAME");
        m_cPresent = getIntent().getIntExtra("ATTEN_PRESENT", -1);
        m_cAbsent = getIntent().getIntExtra("ATTEN_ABSENT", -1);

        setTitle(m_cTitle, false, true, true, false);

        m_cObjCalendar = Calendar.getInstance(Locale.getDefault());
//        m_cMonth = m_cObjCalendar.get(Calendar.MONTH);
//        m_cYear = m_cObjCalendar.get(Calendar.YEAR);

        StringBuffer lMoth = new StringBuffer();
        String lMonth = null;
        if(getIntent().getIntExtra("ATTEN_MONTH", -1) < 10)
            lMonth = lMoth.append("0").append(getIntent().getIntExtra("ATTEN_MONTH", -1)).toString();

        m_cMonth = getIntent().getIntExtra("ATTEN_MONTH", -1) - 1;
        m_cYear = getIntent().getIntExtra("ATTEN_YEAR", -1);

        m_cBeforeJs = new HashMap<>();
        if(null != m_cAttendanceMember && m_cAttendanceMember.getData().size() > 0) {
            int firstAttendance = m_cAttendanceMember.getData().get(0).getDay();
            for (int i = 1; i < firstAttendance; i++){
                StringBuffer ldayBuf = new StringBuffer();
                String ldayy = i < 10 ? ldayBuf.append("0").append(i).toString() :
                        ""+i;
                m_cBeforeJs.put(m_cYear + "-" + lMonth + "-" + ldayy, m_cYear + "-" + lMonth + "-" + ldayy);
            }
        }

        m_cAbsents = new HashMap<>();
        if(null != m_cAttendanceMember && m_cAttendanceMember.getData().size() > 0) {
            for (AttendanceMemberData lAttenData : m_cAttendanceMember.getData()) {
                if (lAttenData.getIsPresent() == DSAMacros.ABSENT) {

                    StringBuffer ldayBuf = new StringBuffer();
                    String ldayy = lAttenData.getDay() < 10 ? ldayBuf.append("0").append(lAttenData.getDay()).toString() :
                            lAttenData.getDay().toString();
                    
                    m_cAbsents.put(m_cYear + "-" + lMonth + "-" + ldayy, m_cYear + "-" + lMonth + "-" + ldayy);
                }
            }
        }
        m_cToday = new HashMap<>();
        if(null != m_cAttendanceMember && m_cAttendanceMember.getData().size() > 0) {
            for (AttendanceMemberData lAttenData : m_cAttendanceMember.getData()) {
                if (lAttenData.getIsPresent() == DSAMacros.TODAY) {

                    StringBuffer ldayBuf = new StringBuffer();
                    String ldayy = lAttenData.getDay() < 10 ? ldayBuf.append("0").append(lAttenData.getDay()).toString() :
                            lAttenData.getDay().toString();

                    m_cToday.put(m_cYear + "-" + lMonth + "-" + ldayy, m_cYear + "-" + lMonth + "-" + ldayy);
                }
            }
        }
        m_cHolidays = new HashMap<>();
        if(null != m_cAttendanceMember && m_cAttendanceMember.getData().size() > 0) {
            for (AttendanceMemberData lAttenData : m_cAttendanceMember.getData()) {
                if (lAttenData.getIsPresent() == DSAMacros.HOLIDAY) {

                    StringBuffer ldayBuf = new StringBuffer();
                    String ldayy = lAttenData.getDay() < 10 ? ldayBuf.append("0").append(lAttenData.getDay()).toString() :
                            lAttenData.getDay().toString();

                    m_cHolidays.put(m_cYear + "-" + lMonth + "-" + ldayy, m_cYear + "-" + lMonth + "-" + ldayy);
                }
            }
        }

        init();
        initlizationCalender(m_cMonth, m_cYear);
    }

    private void Todaysis() {
        m_cObjCalendar = Calendar.getInstance(Locale.getDefault());
        m_cToday_Date = m_cObjCalendar.get(Calendar.DAY_OF_MONTH);
        m_cMonth_Now = m_cObjCalendar.get(Calendar.MONTH);
        m_cYear_Now = m_cObjCalendar.get(Calendar.YEAR);
    }

    private void init() {
        //Initialize CustomCalendarView from layout
        m_cSwipeLay = (LinearLayout) findViewById(R.id.ATENDANCE_LAY);
        m_cSwipeLay.setOnClickListener(this);
        if(m_cPresent > 1 || m_cPresent == 0){
            presentText.setText(m_cPresent+" days");
        }else {
            presentText.setText(m_cPresent+" day");
        }
        if(m_cAbsent > 1 || m_cAbsent == 0){
            leaveText.setText(m_cAbsent+" days");
        }else {
            leaveText.setText(m_cAbsent+" day");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        m_cSelectedDate = df.format(new Date());
    }

    private void updateUIbyDate() {
        m_cDayList = new ArrayList<>();
        updateGrid();
    }


    private void updateGrid() {
        if(null != m_cDayList && m_cDayList.size() > 0) {
            double size = m_cDayList.size()/4.9;
            final float scale = this.getResources().getDisplayMetrics().density;
            int pixels = (int) (79 * scale + 0.5f);
            double dp = pixels*(size + 0.5f);
            if(m_cDayList.size() <= 5) {
                dp = pixels*(size + 1);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.prevMonth, R.id.nextMonth})
    public void onClick(View v) {
        super.onClick(v);
        Intent lIntent;
        String[] lSelectedDate;
        switch (v.getId()) {
            case R.id.prevMonth:
                //TODO uncomment to block prev month from current month
                /*Calendar lCal = Calendar.getInstance();
                int lCurrentMonth = lCal.get(Calendar.MONTH);
                int lCurrentYear = lCal.get(Calendar.YEAR);
                boolean lisAction = false;
                if(m_cYear > lCurrentYear) {
                    lisAction = true;
                } else if(m_cYear == lCurrentYear && m_cMonth > lCurrentMonth) {
                    lisAction = true;
                }
                if(lisAction) {*/
                    if (m_cMonth <= 0) {
                        m_cMonth = 11;
                        m_cYear--;
                    } else {
                        m_cMonth--;
                    }
                    setGridCellAdapterToDate(m_cMonth, m_cYear);
//                    m_cMrngGridSlots.setVisibility(View.GONE);
//                }
                break;
            case R.id.nextMonth:

                if (m_cMonth >= 11) {
                    m_cMonth = 0;
                    m_cYear++;
                } else {
                    m_cMonth++;
                }
                setGridCellAdapterToDate(m_cMonth, m_cYear);
//                m_cMrngGridSlots.setVisibility(View.GONE);
                break;
        }
    }

    private void initlizationCalender(int pmonth, int pyear) {
        m_cObjCalendar.set(pyear, pmonth, 1);
        currentMonth.setText(DateFormat.format(dateTemplate, m_cObjCalendar.getTime()));

        // Initialised
        adapter = new GridCellAdapter(this, m_cMonth, m_cYear);
        calendarView.setAdapter(adapter);
    }


    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(this, month, year);
        m_cObjCalendar.set(year, month, 1);
        currentMonth.setText(DateFormat.format(dateTemplate, m_cObjCalendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    // Inner Class
    public class GridCellAdapter extends BaseAdapter implements View.OnClickListener {

        private static final String tag = "GridCellAdapter";
        private final Context m_cContext;
        private final List<EachDate> m_cObjList;
        private static final int DAY_OFFSET = 1;

        private int m_cDaysInMonth;
        //		private int m_cSelectedDay;
        private TextView m_cGridText;
        private RelativeLayout m_cGridCell;
        private final SimpleDateFormat m_cDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

        public GridCellAdapter(Context context, int month, int year) {
            super();
            this.m_cContext = context;
            this.m_cObjList = new ArrayList<EachDate>();

            // Print Month
            printMonth(month, year);
        }

        @Override
        public int getCount() {
            return m_cObjList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return m_cObjList.get(arg0);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            LayoutInflater inflater = (LayoutInflater)m_cContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.calendar_gridcell, parent, false);

            // Get a reference to the Day gridcell
            m_cGridText = (TextView) row.findViewById(R.id.CALENDAR_DAY_GRIDTEXT);
            m_cGridCell = (RelativeLayout) row.findViewById(R.id.CALENDAR_DAY_GRIDCELL);
//			LayoutParams lParams = new LayoutParams(lwidth, lwidth);
//			m_cGridText.setLayoutParams(lParams);
//            m_cGridText.setOnClickListener(this);

            // ACCOUNT FOR SPACING

//			String[] day_color = m_cObjList.get(position).m_cDate.split("-");
            EachDate lEachDay = m_cObjList.get(position);
            String[] day = lEachDay.m_cDate.split("-");
//			m_cdayScreenDay = day[0];
//			M_cdayscreenMonth = day[1];
//			m_cdayscreenYear = day[2];

            // Set the Day GridCell
            m_cGridText.setText(day[0]);
            String lDate = m_cObjList.get(position).m_cDate;
            m_cGridText.setTag(lEachDay);
            m_cGridCell.setTag(lEachDay);

            if (lEachDay.m_cColor.equals("GREY")) {
                m_cGridText.setTextColor(Color.LTGRAY);
//                m_cGridText.setEnabled(false);
//                m_cGridText.setClickable(false);
//                m_cGridText.setFocusable(false);

//				m_cGridText.setVisibility(View.INVISIBLE);
//                m_cGridText.setFocusableInTouchMode(false);

            }
//            if (lEachDay.m_cColor.equals("WHITE")) {
//                m_cGridText.setTextColor(Color.BLACK);
//            }
//            if (lEachDay.m_cColor.equals("BLUE")) {
//                m_cGridText.setTextColor(Color.RED);
//            }
//            if (lEachDay.m_cColor.equals("YELLOW")) {
//                m_cGridText.setTextColor(Color.GREEN);
//            }

            if (lEachDay.m_cColor.equals("WHITE")) {
                m_cGridText.setTextColor(Color.BLACK);
                m_cGridText.setBackgroundColor(Color.WHITE);
            }
            if (lEachDay.m_cColor.equals("BLUE")) {
                m_cGridText.setTextColor(Color.parseColor("#05CFB5"));
                m_cGridCell.setBackgroundResource(R.drawable.circle_thin);
            }
            if (lEachDay.m_cColor.equals("YELLOW")) {
                m_cGridText.setTextColor(Color.RED);
                m_cGridText.setBackgroundColor(Color.WHITE);
                m_cGridText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.red_dot);
            }
            if (lEachDay.m_cColor.equals("RED")) {
                m_cGridText.setTextColor(Color.BLACK);
                m_cGridText.setBackgroundColor(Color.RED);
            }
            if (lEachDay.m_cColor.equals("BOLD")) {
                m_cGridText.setTextColor(Color.BLACK);
                m_cGridText.setTypeface(Typeface.DEFAULT_BOLD);
                m_cGridText.setBackgroundColor(Color.WHITE);
            }
            if (lEachDay.m_cColor.equals("GOLD")) {
                m_cGridText.setTextColor(Color.MAGENTA);
            }
            if (lEachDay.m_cColor.equals("LTGREY")) {
                m_cGridText.setTextColor(Color.LTGRAY);
            }

            return row;
        }

        public EachDate getGridItem(int pPos) {
            return m_cObjList.get(pPos);
        }
        public void setGridItem(int pPos, EachDate lItem) {
            m_cObjList.set(pPos, lItem);
        }


        @Override
        public void onClick(View v) {
            EachDate lTag = (EachDate) v.getTag();
            if(lTag.m_cColor.equals("YELLOW")) {
                m_cSelectedDate = lTag.m_cFormatDate;

                if (null != m_cSelectedView) {
                    EachDate lEachDay = (EachDate) m_cSelectedView.getTag();
                    if (lEachDay.m_cColor.equals("GREY")) {
                        m_cSelectedView.setTextColor(Color.LTGRAY);
                        /*m_cSelectedView.setEnabled(false);
                        m_cSelectedView.setClickable(false);
                        m_cSelectedView.setFocusable(false);
                        m_cSelectedView.setFocusableInTouchMode(false);*/
                    }
                    if (lEachDay.m_cColor.equals("WHITE")) {
                        m_cSelectedView.setTextColor(Color.LTGRAY);
                    }
                    if (lEachDay.m_cColor.equals("BLUE")) {
                        m_cSelectedView.setTextColor(Color.LTGRAY);
                    }
                    if (lEachDay.m_cColor.equals("YELLOW")) {
                        m_cSelectedView.setTextColor(Color.BLACK);
                    }
//                    m_cSelectedView.setBackgroundResource(R.drawable.cal_tile_selector);
                }
                m_cSelectedView = (CalenderSqureButton) v;
                m_cSelectedView.setTextColor(Color.WHITE);
//                m_cSelectedView.setBackgroundResource(R.drawable.selectdatebg);
//                m_cMrngGridSlots.setVisibility(View.VISIBLE);
                updateUIbyDate();
            } else {
                int xOffset = Integer.parseInt(((TextView)v).getText().toString());
                int yOffset = Integer.parseInt(((TextView)v).getText().toString());
//                EURemediesMacros.showCustomAlert(m_cContext, xOffset, yOffset);
//                Toast.makeText(m_cContext, "Appointment not available..", Toast.LENGTH_SHORT).show();
            }
        }

        private void printMonth(int mm, int yy) {
            // The number of days to leave blank at
            // the start of this month.
            int lTrailingSpaces = 0;
            int lDaysInPrevMonth = 0;
            int lPrevMonth = 0;
            int lPrevYear = 0;
            int lNextMonth = 0;
            int lNextYear = 0;

            int lCurrentMonth = mm;
            m_cDaysInMonth = getNumberOfDaysOfMonth(lCurrentMonth);

            // Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
            GregorianCalendar cal = new GregorianCalendar(yy, lCurrentMonth, 1);

            if (lCurrentMonth == 11) {
                lPrevMonth = lCurrentMonth - 1;
                lDaysInPrevMonth = getNumberOfDaysOfMonth(lPrevMonth);
                lNextMonth = 0;
                lPrevYear = yy;
                lNextYear = yy + 1;
            } else if (lCurrentMonth == 0) {
                lPrevMonth = 11;
                lPrevYear = yy - 1;
                lNextYear = yy;
                lDaysInPrevMonth = getNumberOfDaysOfMonth(lPrevMonth);
                lNextMonth = 1;
            } else {
                lPrevMonth = lCurrentMonth - 1;
                lNextMonth = lCurrentMonth + 1;
                lNextYear = yy;
                lPrevYear = yy;
                lDaysInPrevMonth = getNumberOfDaysOfMonth(lPrevMonth);
            }

            // Compute how much to leave before before the first day of the
            // month.
            // getDay() returns 0 for Sunday.
            int lCurrentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            lTrailingSpaces = lCurrentWeekDay;

            if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1) {
                ++m_cDaysInMonth;
            }

            // Trailing Month days
            EachDate lDay = null;
            for (int i = 0; i < lTrailingSpaces; i++) {
                lDay = new EachDate();
                int lMonth = lPrevMonth+1;
                String lMonthSt = null;
                if(lMonth <= 9) {
                    lMonthSt = "0"+lMonth;
                }else {
                    lMonthSt = ""+lMonth;
                }
                lDay.m_cFormatDate = String.format("%d-%s-%d", lPrevYear, lMonthSt, (lDaysInPrevMonth- lTrailingSpaces + DAY_OFFSET)+ i);
                String lDate = String.valueOf((lDaysInPrevMonth- lTrailingSpaces + DAY_OFFSET)+ i)+ "-"+ getMonthAsString(lPrevMonth)+ "-"+ lPrevYear;
                lDay.m_cDate = 	lDate;
                lDay.m_cColor = "GREY" ;
                m_cObjList.add(lDay);
				/*m_cObjList.add(String.valueOf((lDaysInPrevMonth
						- lTrailingSpaces + DAY_OFFSET)
						+ i)
						+ "-GREY"
						+ "-"
						+ getMonthAsString(lPrevMonth)
						+ "-"
						+ lPrevYear);*/
            }
            Todaysis();
            for (int i = 1; i <= m_cDaysInMonth; i++) {
                lDay = new EachDate();
                int lMonth = lCurrentMonth+1;
                String lMonthSt = null;
                if(lMonth <= 9) {
                    lMonthSt = "0"+lMonth;
                }else {
                    lMonthSt = ""+lMonth;
                }
                String lday = null;
                if(i <= 9) {
                    lday = "0"+i;
                } else {
                    lday = ""+i;
                }
                lDay.m_cFormatDate = String.format("%d-%s-%s", yy, lMonthSt, lday);
                String lDate = String.valueOf(i) + "-"+ getMonthAsString(lCurrentMonth) + "-" + yy;

                lDay.m_cDate = 	lDate;
                if (i == m_cToday_Date && m_cMonth_Now == m_cMonth	&& m_cYear_Now == m_cYear) {
                    lDay.m_cColor = "BLUE" ;
                } else {
                    lDay.m_cColor = "WHITE" ;
                }

                if(null != m_cAbsents && m_cAbsents.containsKey(lDay.m_cFormatDate)) {
                    lDay.m_cColor = "YELLOW" ;
                }
                if(null != m_cToday && m_cToday.containsKey(lDay.m_cFormatDate)) {
                    lDay.m_cColor = "BOLD" ;
                }
                if(null != m_cHolidays && m_cHolidays.containsKey(lDay.m_cFormatDate)) {
                    lDay.m_cColor = "GOLD" ;
                }
                if(null != m_cBeforeJs && m_cBeforeJs.containsKey(lDay.m_cFormatDate)) {
                    lDay.m_cColor = "LTGREY" ;
                }
                m_cObjList.add(lDay);
            }

            for (int i = 0; i < m_cObjList.size() % 7; i++) {
                lDay = new EachDate();
                int lMonth = lNextMonth+1;
                String lMonthSt = null;
                if(lMonth <= 9) {
                    lMonthSt = "0"+lMonth;
                }else {
                    lMonthSt = ""+lMonth;
                }
                String lDate = String.valueOf(i + 1) + "-" + getMonthAsString(lNextMonth) + "-" + lNextYear;
                lDay.m_cFormatDate = String.format("%d-%s-%d", lNextYear, lMonthSt, i + 1);
                lDay.m_cDate = 	lDate;
                lDay.m_cColor = "GREY" ;
                m_cObjList.add(lDay);
            }

        }

        private String getMonthAsString(int i) {
            return m_cMonths[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return m_cDaysOfMonth[i];
        }
    }

    public class EachDate {
        public String m_cDate;
        public String m_cColor;
        public String m_cFormatDate;
    }


}

