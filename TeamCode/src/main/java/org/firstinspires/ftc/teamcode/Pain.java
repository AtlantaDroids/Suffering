package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Pain extends LinearOpMode {

    private DcMotor[] elevatorMotors = new DcMotor[1];
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
        elevatorMotors[0] = hardwareMap.get(DcMotor.class, "ellevator");


        /* Set Drive & Elevator Motor Direction */
        driveMotors[0].setDirection(DcMotorSimple.Direction.REVERSE);
        driveMotors[2].setDirection(DcMotorSimple.Direction.REVERSE);


//


        applyConfig(driveMotors);
        applyConfig(elevatorMotors);

        waitForStart();
        /* Run During Active OpMode */
        while(opModeIsActive()) {
            double rt = -this.gamepad1.right_trigger;
            double lt = -this.gamepad1.left_trigger;
            double y = -this.gamepad1.left_stick_y;
            double x = -this.gamepad1.right_stick_x;
            double r = -this.gamepad1.left_stick_x;

            for(DcMotor motor : elevatorMotors) {
                motor.setPower(rt-lt);
            }

            /* Set Drive Motors Power */
            driveMotors[0].setPower(y-x-r);
            driveMotors[1].setPower(y+x+r);
            driveMotors[2].setPower(y+x-r);
            driveMotors[3].setPower(y-x+r);

            int i = 0;

            if (gamepad1.a == true) {
                i++;
            }




        }

    }
}
