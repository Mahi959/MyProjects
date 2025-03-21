package com.rbc.util;

import org.checkerframework.checker.units.qual.C;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static boolean isDateLessThanToday(String targetDate, String dateFormat){
        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate targetLocalDate = LocalDate.parse(targetDate, formatter);
        LocalDate todayLocalDate = LocalDate.parse(today.format(DateTimeFormatter.ofPattern(dateFormat)),formatter);

        try {
            if (targetLocalDate.isBefore(todayLocalDate)){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String getCurrentYear(){
        return String.valueOf(LocalDate.now().getYear());
    }

    public static String addDayInDate(String dateStr, String dateFormat, int day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        LocalDate date = LocalDate.parse(dateStr, formatter);
        LocalDate newDate = date.plusDays(day);

        String newDateStr = newDate.format(formatter);
        return newDateStr;
    }

    public static boolean isDateLessThanDate(String dateToCheck, String dateCompareWith, String dateFormat){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        boolean matchResutl = false;
        try {
            LocalDate dateToCheckDate = LocalDate.parse(dateToCheck, formatter);
            LocalDate dateCompareWithDate = LocalDate.parse(dateCompareWith, formatter);
            if(dateToCheckDate.isBefore(dateCompareWithDate)){
                matchResutl = true;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use "+ dateFormat);
        }
        return matchResutl;
    }

    public static List<String> getAllDatesBetweenDates(String firstDateString, String secondDateString, String dateFormat) throws ParseException {
        List<String> datesList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        try{
            Date firstDate = sdf.parse(firstDateString);
            Date secondDate = sdf.parse(secondDateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(firstDate);

            while (calendar.getTime().before(secondDate) || calendar.getTime().equals(secondDate)) {
                Date result = calendar.getTime();
                datesList.add(sdf.format(result));
                calendar.add(Calendar.DATE, 1);
            }
            return datesList;
        } catch (Exception e){
            System.out.println("Exception occurred : "+ e);
            return datesList;
        }
    }

    public static List<String> getFilterDatesBetweenTwoDatesFromList(List<String> datesList, String firstDateString, String secondDateString, String dateFormat) throws ParseException {

        List<String> filteredDatesList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date firstDate = sdf.parse(firstDateString);
        Date secondDate =sdf.parse(secondDateString);

        for(String dateStr : datesList){
            Date date = sdf.parse(dateStr);
            if(date.after(firstDate) && date.before(secondDate)){
                filteredDatesList.add(dateStr);
            }
        }
        return filteredDatesList;
    }

}