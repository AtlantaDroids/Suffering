package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Pain extends LinearOpMode {

    private DcMotor[] motors = new DcMotor[4];


    private void applyConfig(DcMotor[] motors) {
        for(DcMotor motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {


        motors[0] = hardwareMap.get(DcMotor.class, "frontLeft");
        motors[1] = hardwareMap.get(DcMotor.class, "frontRight");
        motors[2] = hardwareMap.get(DcMotor.class, "backLeft");
        motors[3] = hardwareMap.get(DcMotor.class, "backRight");

        motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[3].setDirection(DcMotorSimple.Direction.REVERSE);

        applyConfig(motors);


        while(opModeIsActive()) {
            double y = -this.gamepad1.left_stick_y;
            double x = -this.gamepad1.right_stick_x;

            if (this.gamepad1.a) {
                // do the thing
            }
        }

    }
}
