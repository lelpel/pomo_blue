package ua.lelpel.pomobluelw.timer;

import android.os.Handler;

/**
 * Created by bruce on 20.02.2017.
 */

public class PomodoroTimer {
    private static final int DEFAULT_ACTIVE_PERIOD = 15;
    private static final int DEFAULT_BREAK_PERIOD = 10;

    private int activityPeriod = DEFAULT_ACTIVE_PERIOD;
    private int breakPeriod = DEFAULT_BREAK_PERIOD;

    //отсчет
    private int ticks = activityPeriod;
    private int currentMaxTicks = activityPeriod;

    public boolean isRunning() {
        return running;
    }

    private boolean running;
    private boolean wasRunning = false;

    private boolean isActive = true;

    public boolean isActive() {
        return isActive;
    }

    private TimerEventListener listener;

    public void setListener(TimerEventListener listener) {
        this.listener = listener;
    }

    public void run() {
        final Handler handler = new Handler();

        //Handler працює з класами, що реалізують
        //інтерфейс Runnable, тому створюємо
        //необхідний анонімний клас
        handler.post(new Runnable() {
            @Override
            public void run() {
                //перевіряємо, чи працює таймер
                if (running) {
                    //зменшуємо число тіків до кінця періоду
                    ticks--;
                }

                //за допомогою слухача виконуємо певні
                //дії в Activity, для того, щоб відобразити
                //результат
                listener.onTimerTick(ticks);

                //якщо період завершився
                if (ticks == 0) {
                    //змінюємо режим
                    invertMode();
                    listener.onModeChanged(isActive);
                }

                //виконуємо цей же код через 1000 мс
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public void invertMode() {
        if (isActive) {
            isActive = false;
            ticks = breakPeriod;
            currentMaxTicks = breakPeriod;
        } else {
            isActive = true;
            ticks = activityPeriod;
            currentMaxTicks = activityPeriod;
        }
    }

    public void startBreak() {
        running = true;
        isActive = false;
        ticks = breakPeriod;
        currentMaxTicks = breakPeriod;
    }

    public void startActive() {
        running = true;
        isActive = true;
        ticks = activityPeriod;
        currentMaxTicks = activityPeriod;
    }


    public void pause() {
        running = false;
        wasRunning = true;
    }

    public boolean wasRunning() {
        return wasRunning;
    }

    public void reset() {
        pause();
        ticks = 0;
    }

    public int getCurrentMaxTicks() {
        return currentMaxTicks;
    }

    public void startCountdown() {
        running = true;
    }

    public void setActivityPeriod(int activityPeriod) {
        this.activityPeriod = activityPeriod;
    }

    public void setBreakPeriod(int breakPeriod) {
        this.breakPeriod = breakPeriod;
    }
}
