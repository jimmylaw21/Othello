package components;

import controller.Robot;
import view.GameFrame;

public class Clock implements Runnable {
    public static int clockTime = 0;
    public Thread clockThread = new Thread();
    public boolean isClockEnd = false;

    @Override
    public void run() {

        while (!isClockEnd) {
            try {
                clockTime++;
                System.out.println(clockTime);
                clockThread.sleep(1000);
                //狂暴状态判断
                if (Clock.clockTime >= 15) {
                    Robot.randomAbility2++;
                }
                GameFrame.controller.updateStatues();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
