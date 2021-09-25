public class BasicBot {
    
    private double x_position;
    private double y_position;
    private double linear_speed;
    private double drive_angle; // in degrees (0 = "north", 90 = east, ...)

    private boolean is_enabled;
    private String robot_name;

    private long last_time_drive_method_was_called;

    //constructor
    public BasicBot(String the_robot_name){ 

        this.robot_name = the_robot_name;

        // set the start position
        this.x_position = 0;
        this.y_position = 0;

        // set speed and direction
        this.linear_speed = 0;
        this.drive_angle = 0.0;

        // disable the robot
        this.is_enabled = false;

        // let's zero the timer based on when the constructor was called
        this.last_time_drive_method_was_called = System.currentTimeMillis();

    }

    public BasicBot(String the_robot_name, double x_start, double y_start, double drive_angle_start){ 
 
        this.robot_name = the_robot_name;

        // set the start position
        this.x_position = x_start;
        this.y_position = y_start;

        // set speed and direction
        this.linear_speed = 0;
        this.drive_angle = drive_angle_start;

        // disable the robot
        this.is_enabled = false;

        // let's zero the timer based on when the constructor was called
        this.last_time_drive_method_was_called = System.currentTimeMillis();
       
    }

    public void enable(){ 
        this.is_enabled = true;
    }

    public void disable(){ 
        this.is_enabled = false;
    }

    public void updateDrivePosition(double new_speed, double new_drive_angle ){ 
        // use the old x and y position and the old time the funcion was called
        // use trig to work out where we are now.
        long current_time = System.currentTimeMillis();

        double new_x = this.x_position + Math.sin(Math.toRadians(this.drive_angle)) * (this.linear_speed * ((current_time - this.last_time_drive_method_was_called) / 1000.0)); // need to use sin and cosin based on old drive values
        double new_y = this.y_position + Math.cos(Math.toRadians(this.drive_angle)) * (this.linear_speed * ((current_time - this.last_time_drive_method_was_called) / 1000.0)); // need to use sin and cosin based on old drive values

        // Then update everything
        this.last_time_drive_method_was_called = current_time;
        this.x_position = new_x;
        this.y_position = new_y;
        this.setSpeed(new_speed);
        this.setDriveAngle(new_drive_angle);

    }

    public String getPrintableRobotPosition(){ 
        return "" + this.robot_name + " (x: " + this.x_position + " y: " + this.y_position + "). ";
    }

    private void setSpeed(double new_speed){ 
        if(this.is_enabled == true) {
            this.linear_speed = new_speed;
        } else {
            this.linear_speed = 0;
        }
    }

    private void setDriveAngle(double new_drive_angle){ 
        this.drive_angle = new_drive_angle;
    }
}
