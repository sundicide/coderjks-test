package com.coderjks.java;

import org.junit.Test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleTest {

//    public static void main (String[] args) throws InterruptedException {
    @Test(expected = IllegalStateException.class)
    public void testt() throws Exception {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
            }
        };

        Timer timer = new Timer();
        long delay = 0;
        long intervalPeriod = 1 * 1000;

        timer.scheduleAtFixedRate(task, delay, intervalPeriod);

        Thread.sleep(2000);
        timer.cancel();

        timer = null;

        timer = new Timer();
        /**
         * exception 발생!!!
         * new Timer()는 선언을 해줬지만 그래도 아래 에러가 발생한다.
         * java.lang.IllegalStateException: Task already scheduled or cancelled
         *
         * 해결: task도 새로 만들어줘야 한다.
         */
        timer.scheduleAtFixedRate(task, delay, intervalPeriod);
    }
}
