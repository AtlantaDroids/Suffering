package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Pain extends LinearOpMode {

    private final int ELEVATOR_MAX = 3100;
    private final int ELEVATOR_HALF = ELEVATOR_MAX / 2;
    private final int PIXEL_TICKS = 457;

    private DcMotor elevatorMotor;
    private DcMotor[] driveMotors = new DcMotor[4];

    private void applyConfig(DcMotor[] motors) {
        for(DcMotor motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        /* Get Drive Motors */
        driveMotors[0] = hardwareMap.get(DcMotor.class, "frontLeft");
        driveMotors[1] = hardwareMap.get(DcMotor.class, "frontRight");
        driveMotors[2] = hardwareMap.get(DcMotor.class, "backLeft");
        driveMotors[3] = hardwareMap.get(DcMotor.class, "backRight");

        /* Get Elevator Motors */
        elevatorMotor = hardwareMap.get(DcMotor.class, "ellevator");
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /* Set Drive & Elevator Motor Direction */
        driveMotors[0].setDirection(DcMotorSimple.Direction.REVERSE);
        driveMotors[2].setDirection(DcMotorSimple.Direction.REVERSE);

        applyConfig(driveMotors);

        int target = 0;
        boolean lastStateUp = true;
        boolean lastStateDown = true;

        waitForStart();

        /* Run During Active OpMode */
        while(opModeIsActive()) {

            /* Define Controls */
            double rt = -this.gamepad1.right_trigger;
            double lt = -this.gamepad1.left_trigger;
            double y = -this.gamepad1.left_stick_y;
            double x = -this.gamepad1.left_stick_x;
            double r = -this.gamepad1.right_stick_x;

            /* Set Drive Motors Power */
            driveMotors[0].setPower(y-x-r);
            driveMotors[1].setPower(y+x+r);
            driveMotors[2].setPower(y+x-r);
            driveMotors[3].setPower(y-x+r);

            /* Move Arm Up One Pixel Length /w Up/Down Hat */
            if (gamepad1.dpad_up == true && lastStateUp == false) {
                target += PIXEL_TICKS;
                lastStateUp = true;
            } else {
                lastStateUp = gamepad1.dpad_up;
            }
            if (gamepad1.dpad_down == true && lastStateDown == false) {
                target -= PIXEL_TICKS;
                lastStateDown= true;
            } else {
                lastStateDown = gamepad1.dpad_down;
            }
            
            telemetry.addData("target", target);
            
            int pos = elevatorMotor.getCurrentPosition();
            if (gamepad1.b == true) {
                elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            } else {
                elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            /* Move Elevator Halfway When Press 'A' */
            if (gamepad1.a == true) {
                int error = target - pos;
                double kP = 0.0014;
                double kF = 0;
                if (error > 0) {
                    kF = 0.07;
                }
                elevatorMotor.setPower(error * kP + kF);
            } else {
                double elevatorCmd = -(rt-lt);
                if (pos >= ELEVATOR_MAX && elevatorCmd > 0) {
                    elevatorMotor.setPower(0);
                } else {
                    elevatorMotor.setPower(elevatorCmd);
                }
                telemetry.addData("Elevator power", elevatorCmd);
            }

            telemetry.addData("Elevator Pos", pos);

//            double elevatorCmd = -(rt-lt);
//            if (pos >= ELEVATOR_MAX && elevatorCmd > 0) {
//                elevatorMotor.setPower(0);
//            } else {
//                elevatorMotor.setPower(elevatorCmd);
//            }

            /* Update Telemetry */
            telemetry.update();
        }

    }
}
