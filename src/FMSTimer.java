import java.util.concurrent.TimeUnit;

public class FMSTimer {

    private enum gameStates {matchSetup,countdown,matchStart,auto,teleop,endgame,endOfMatch}

    public static void main(String[] args) throws Exception {

        BasicBot fawkes = new BasicBot("Fawkes");
        BasicBot chungus = new BasicBot("Chungus", 10, 10, 45 ); 
        BasicBot carl = new BasicBot("CARRRRRRRL", 100, 10, 90);

        long matchStartTime;
        
        // 2 minutes and 30 seconds (*1000)
        long matchLength = (60+60+30) * 1000 ; 
        long currentMatchTime = 0;
        gameStates currentMatchState = gameStates.matchSetup;

        // let's start a countdown
        currentMatchState = gameStates.countdown;
        for(int countdown = 3; countdown >0; countdown--){
            System.out.println(""+ countdown +"...");
            TimeUnit.SECONDS.sleep(1);
        }

        fawkes.enable();
        chungus.enable();
        carl.enable();


        // start the match
        currentMatchState = gameStates.matchStart;
        matchStartTime = System.currentTimeMillis();

        while( System.currentTimeMillis() - matchStartTime < matchLength ){
            currentMatchTime = (System.currentTimeMillis() - matchStartTime) / 1000;

            // auto - 0 to 15 seconds
            if(currentMatchTime == 0){
                currentMatchState = gameStates.auto;
            }

            // teleop - 15 - onward
            if(currentMatchTime == 15){
                currentMatchState = gameStates.teleop;
            }
            // endgame - last 15 seconds
            if(currentMatchTime == 135){
                currentMatchState = gameStates.endgame;
            }

            System.out.println("" + currentMatchState + ": " + currentMatchTime);

            fawkes.updateDrivePosition(20, 0);
            chungus.updateDrivePosition(0, 35);
            carl.updateDrivePosition(0.5, 45);

            System.out.println(fawkes.getPrintableRobotPosition());
            System.out.println(chungus.getPrintableRobotPosition());
            System.out.println(carl.getPrintableRobotPosition());


            TimeUnit.SECONDS.sleep(1);
        }

        fawkes.disable();
        chungus.disable();
        carl.disable();
        
        currentMatchState = gameStates.endOfMatch;
        currentMatchTime = (System.currentTimeMillis() - matchStartTime) / 1000;
        System.out.println("" + currentMatchState + ": " + currentMatchTime);
       
    }
}
