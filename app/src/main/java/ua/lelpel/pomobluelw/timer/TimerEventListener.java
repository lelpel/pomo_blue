package ua.lelpel.pomobluelw.timer;

/**
 * Created by bruce on 02.02.2017.
 */

public interface TimerEventListener {
    void onTimerTick(int seconds);
    void onModeChanged(boolean isActive);
}
