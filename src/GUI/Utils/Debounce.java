package GUI.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class Debounce {
    private Runnable task;
    private long delay;
    private Timer timer;

    /**
     * 
     * @param task Function to execute
     * @param delay
     */
    public Debounce(Runnable task, long delay) {
        this.task = task;
        this.delay = delay;
    }
    
    public void execute() {
        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }
}
