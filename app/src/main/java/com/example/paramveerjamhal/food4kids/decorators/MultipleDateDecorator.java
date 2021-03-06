package com.example.paramveerjamhal.food4kids.decorators;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;

import com.example.paramveerjamhal.food4kids.entities.WeeklyEvent;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Highlight Saturdays and Sundays with a background
 */
public class MultipleDateDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance(Locale.getDefault());
    private final Drawable highlightDrawable;
    private static final int color = Color.parseColor("#009369");
    List<DayOfWeek> dayOfWeeks;

    public MultipleDateDecorator(List<DayOfWeek> dayOfWeeks) {
        highlightDrawable = new ColorDrawable(color);
        this.dayOfWeeks=dayOfWeeks;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);

           DayOfWeek dow= dayOfWeeks.get(0);
           @SuppressLint({"NewApi", "LocalSuppress"})
           int newday=dow.getValue()+1;
        @SuppressLint({"NewApi", "LocalSuppress"})
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay ==newday ;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(highlightDrawable);
    }
}

