package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;//importing libraries
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;


@TeleOp(name = "FrogProvsDuoRed", group= "TeleOp")
public class FrogProvsRed extends OpMode {
    RevBlinkinLedDriver.BlinkinPattern pattern;

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private Servo leftIn, rightIn, wrist, outArm, claw, gate, inWrist;
    private DcMotor horSlide, vertSlideL, vertSlideR, intake;

    double lightsTimer;

    boolean transferAction1 = false;
    private RevBlinkinLedDriver lights;

    boolean spitReset = false;
    boolean manualReset = false;
    private ElapsedTime  initTimer = new ElapsedTime();

    double abortTimer, intakeTimer, transferTimer, outtakeTimer, specScoreTimer, specTimer2, specAbortTimer, spitTimer, resetTimer;

    private ElapsedTime globalTimer = new ElapsedTime();

    boolean specMode = false;
    boolean eat = false;
    boolean spit = false;

    boolean botUnfold = false;
    boolean manualSlide = false;
    boolean transferAction = false;
    boolean outtakeAction = false;
    boolean specScore = false;
    boolean specAction = false;
    boolean specAction2 = false;
    boolean specAbort = false;

    boolean transfering = false;
    private BHI260IMU imu;
    private ColorRangeSensor intakeColour;
    private ColorRangeSensor transferColour;
    private TouchSensor hortouch;
    private TouchSensor vertouch;
    boolean resethor = false;
    Gamepad currentGamepad1;
    Gamepad previousGamepad1;
    Gamepad currentGamepad2;
    Gamepad previousGamepad2;
    public enum armState {
        armTransfering,
        armOuttaking,
        armSpec,
        armSpecScore,
        armIdle;
    }
    armState currentArmState = armState.armIdle;
    public enum intakeState {
        intakeIdle,
        intaking,
        intakeTransfering,
        intakeAbort;
    }
    intakeState currentIntakeState = intakeState.intakeIdle;
    public enum transferState {
        gateOpen,
        spin,
        idle;
    }
    transferState currentTransferState = transferState.gateOpen;
    @Override
    public void init() {

        globalTimer.reset();

        lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");


        intakeColour = hardwareMap.get(ColorRangeSensor.class, "intakecolour");
        transferColour=hardwareMap.get(ColorRangeSensor.class,"transfercolour");

        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = hardwareMap.get(DcMotor.class, "back_right");
        backRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        inWrist = hardwareMap.get(Servo.class, "inwrist");
        inWrist.setDirection(Servo.Direction.FORWARD);
        inWrist.setPosition(var1.inWristTransfer);
        outArm = hardwareMap.get(Servo.class, "outarm");
        outArm.setDirection(Servo.Direction.FORWARD);
// outArm.setPosition(var.armTransfer);
        wrist = hardwareMap.get(Servo.class, "wrist");
        wrist.setDirection(Servo.Direction.FORWARD);
// wrist.setPosition(var.wristInit);
        gate = hardwareMap.get(Servo.class, "gate");
        gate.setDirection(Servo.Direction.FORWARD);
        gate.setPosition(var1.gateClose);
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setDirection(Servo.Direction.FORWARD);
// claw.setPosition(var.clawOpen);

        leftIn = hardwareMap.get(Servo.class, "leftin");
        leftIn.setDirection(Servo.Direction.REVERSE);
        leftIn.setPosition(var1.inIdle);

        rightIn = hardwareMap.get(Servo.class, "rightin");
        rightIn.setDirection(Servo.Direction.FORWARD);
        rightIn.setPosition(var1.inIdle);
        horSlide = hardwareMap.get(DcMotor.class, "horslide");
        horSlide.setDirection(DcMotor.Direction.REVERSE);
        horSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vertSlideL = hardwareMap.get(DcMotor.class, "leftvertical");
        vertSlideL.setDirection(DcMotor.Direction.FORWARD);
        vertSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertSlideL.setTargetPosition(0);
        vertSlideL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vertSlideL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vertSlideR = hardwareMap.get(DcMotor.class, "rightvertical");
        vertSlideR.setDirection(DcMotor.Direction.REVERSE);
        vertSlideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertSlideR.setTargetPosition(0);
        vertSlideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vertSlideR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotor.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        currentGamepad2 = new Gamepad();
        previousGamepad2 = new Gamepad();
        currentGamepad1 = new Gamepad();
        previousGamepad1 = new Gamepad();
        hortouch = hardwareMap.get(TouchSensor.class, "hortouch");
        vertouch = hardwareMap.get(TouchSensor.class, "vertouch");

        pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;

    }
    public void drive() {
// double turnvar = Math.max(1, 1 + (horSlide.getCurrentPosition() / 1000.0));
        boolean slow = gamepad1.options;
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = (gamepad1.right_trigger - gamepad1.left_trigger) ;
        double slowvar = 2.0; // Slow mode divisor
        double speedFactor = slow ? 1 / slowvar : 1;
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 0.8);
// Denominator is the largest motor power (absolute value) or 1
// This ensures all the powers maintain the same ratio,
// but only if at least one is out of the range [-1, 1]
        double frontLeftPower = (y + x + rx) / denominator * speedFactor * 0.8;
        double backLeftPower = (y - x + rx) / denominator * speedFactor * 0.8;
        double frontRightPower = (y - x - rx) / denominator * speedFactor * 0.8;
        double backRightPower = (y + x - rx) / denominator * speedFactor * 0.8;
// Normalize motor powers
        double maxPower = Math.max(Math.abs(frontLeftPower),
                Math.max(Math.abs(frontRightPower),
                        Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }
        if (vertSlideL.getCurrentPosition() > 2000) {
            frontLeftPower = frontLeftPower/1.2;
            frontRightPower = frontRightPower/1.2;
            backLeftPower = backLeftPower/1.2;
            backRightPower = backRightPower/1.2;
        }
// Set motor powers
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }

    public void manualTake() {
        previousGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);



        if (!botUnfold) {
            vertSlideL.setPower(1);
            vertSlideR.setPower(1);
            vertSlideL.setTargetPosition(500);
            vertSlideR.setTargetPosition(500);
            initTimer.reset();
            claw.setPosition(var1.clawOpen);
            leftIn.setPosition(var1.inDown);
            rightIn.setPosition(var1.inDown);
            inWrist.setPosition(var1.inWristIntaking);

            while (initTimer.seconds() < 0.3) {
            }

            claw.setPosition(var1.clawClose);
            outArm.setPosition(var1.armTransfer);
            wrist.setPosition(var1.wristTransfer);
            vertSlideL.setPower(1);
            vertSlideR.setPower(1);
            vertSlideL.setTargetPosition(var1.slideTransfer);
            vertSlideR.setTargetPosition(var1.slideTransfer);
            currentArmState = armState.armTransfering;
            initTimer.reset();

            while (initTimer.seconds() < 0.3) {
            }

            leftIn.setPosition(var1.inTransfer);
            rightIn.setPosition(var1.inTransfer);
            inWrist.setPosition(var1.inWristTransfer);
            claw.setPosition(var1.clawOpen);
            botUnfold = true;
        } else if (botUnfold) { //all the shit below
            if (currentGamepad2.options && !previousGamepad2.options && !manualSlide) {
                manualSlide = true;
            } else if (currentGamepad2.options && !previousGamepad2.options && manualSlide) {
                vertSlideL.setTargetPosition(500);
                vertSlideR.setTargetPosition(500);
                claw.setPosition(var1.clawOpen);
                outArm.setPosition(var1.armTransfer);
                wrist.setPosition(var1.wristTransfer);
                manualReset = true;
                resetTimer = globalTimer.seconds() + 0.5;

            }
            if (manualReset && globalTimer.seconds() > resetTimer) {
                vertSlideL.setTargetPosition(var1.slideTransfer);
                vertSlideR.setTargetPosition(var1.slideTransfer);
                manualReset = false;
                manualSlide = false;
                currentArmState = armState.armTransfering;
            }
            NormalizedRGBA transferColours = transferColour.getNormalizedColors();
            double transferRed = transferColours.red;
            double transferGreen = transferColours.green;
            double transferBlue = transferColours.blue;
            double waitTransferDisplayer=Float.MAX_VALUE;
            if(transferRed>0.3 ||transferGreen>0.3 ||transferBlue>0.3){
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                waitTransferDisplayer=globalTimer.seconds()+2;
            }
            if(globalTimer.seconds()>waitTransferDisplayer){
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
                waitTransferDisplayer=Double.MAX_VALUE;
            }
            if (!manualSlide) {
                vertSlideL.setPower(1);
                vertSlideL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                vertSlideR.setPower(1);
                vertSlideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                switch (currentArmState) {
                    case armIdle:
                        if (currentGamepad2.circle && !previousGamepad2.circle) {
                            claw.setPosition(var1.clawOpen);
                            outArm.setPosition(var1.armTransfer);
                            wrist.setPosition(var1.wristTransfer);
                            vertSlideL.setTargetPosition(var1.slideTransfer);
                            vertSlideR.setTargetPosition(var1.slideTransfer);
                            currentArmState = armState.armTransfering;
                        } else if (currentGamepad2.triangle && !previousGamepad2.triangle) {
                            claw.setPosition(var1.clawClose);
                            vertSlideL.setTargetPosition(1000);
                            vertSlideR.setTargetPosition(1000);
                            specAction = true;
                        }
                        break;
                    case armTransfering:
                        if (currentGamepad2.circle && !previousGamepad2.circle) {
                            claw.setPosition(var1.clawClose);
                            transferAction = true;
                            transferTimer = globalTimer.seconds() + 0.2;
                        }
                        if (currentGamepad2.triangle && !previousGamepad2.triangle) {
                            claw.setPosition(var1.clawClose);
                            vertSlideL.setTargetPosition(1000);
                            vertSlideR.setTargetPosition(1000);//TODO
                            specAction = true;
                        }
                        break;
                    case armOuttaking:
                        if (currentGamepad2.circle && !previousGamepad2.circle) {
                            claw.setPosition(var1.clawOpen);
                            outtakeAction = true;
                            outtakeTimer = globalTimer.seconds() + 0.2;
                        } else if (currentGamepad2.triangle && !previousGamepad2.triangle) { //unfinshed
                            claw.setPosition(var1.clawClose);
                            vertSlideL.setTargetPosition(1000);
                            vertSlideR.setTargetPosition(1000);//TODO
                            specAction = true;
                        }
                        if (outtakeAction && globalTimer.seconds() > outtakeTimer) {
                            outArm.setPosition(var1.armTransfer);
                            wrist.setPosition(var1.wristTransfer);
                            vertSlideL.setPower(1);
                            vertSlideR.setPower(1);
                            vertSlideL.setTargetPosition(var1.slideTransfer);
                            vertSlideR.setTargetPosition(var1.slideTransfer);
                            currentArmState = armState.armTransfering;
                            outtakeAction = false;
                        }
                        break;
                    case armSpec:
                        if (currentGamepad2.triangle && !previousGamepad2.triangle) {
                            claw.setPosition(var1.clawClose);
                            vertSlideL.setTargetPosition(1000);
                            vertSlideR.setTargetPosition(1000);//TODO
                            specScoreTimer = globalTimer.seconds() + 0.3;
                            specScore = true;
                        }

                        if (specScore && globalTimer.seconds() > specScoreTimer) {
                            outArm.setPosition(var1.armSpecScore);
                            wrist.setPosition(var1.wristSpecScore);
                            currentArmState = armState.armSpecScore;
                            specScore = false;
                        }

                        break;
                    case armSpecScore:
                        if (currentGamepad2.right_bumper && !previousGamepad2.right_bumper) {
                            outArm.setPosition(var1.armSpec);
                            wrist.setPosition(var1.wristSpec);
                            specAbort = true;
                            specAbortTimer = globalTimer.seconds() + 0.3;
                        }

                        if (specAbort && globalTimer.seconds() > specAbortTimer) {
                            claw.setPosition(var1.clawOpenWide);
                            currentArmState = armState.armSpec;
                        }


                        if (currentGamepad2.triangle && !previousGamepad2.triangle) {
                            claw.setPosition(var1.clawOpen);
                            currentArmState = armState.armIdle;
                        } break;
                } //end of arm switch


                if (transferAction && globalTimer.seconds() > transferTimer) {
                    vertSlideL.setTargetPosition(2150);
                    vertSlideR.setTargetPosition(2150);//TODO
                    transferAction1 = true;
                    transferAction = false;
                }
                if (transferAction1 && vertSlideL.getCurrentPosition() > 1500) {
                    outArm.setPosition(var1.armOut);
                    wrist.setPosition(var1.wristOut);
                    transferAction1 = false;
                    currentArmState = armState.armOuttaking;
                }

                if (specAction && vertSlideL.getCurrentPosition() > 500) {
                    outArm.setPosition(var1.armSpec);
                    wrist.setPosition(var1.wristSpec);
                    specTimer2 = globalTimer.seconds() + 0.5;
                    specAction2 = true;
                    specAction = false;
                }
                if (specAction2 && globalTimer.seconds() > specTimer2) {
                    claw.setPosition(var1.clawOpenWide);
                    currentArmState = armState.armSpec;
                    specAction2 = false;
                }

            } // end of manual slide
        }
        if (manualSlide) {
            vertSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            vertSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            if (vertouch.isPressed() && vertSlideL.getCurrentPosition() != 0 &&
                    vertSlideR.getCurrentPosition() != 0) {
                vertSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                vertSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                vertSlideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                vertSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            vertSlideL.setPower(-gamepad2.right_stick_y);
            vertSlideR.setPower(-gamepad2.right_stick_y);
            if (gamepad2.right_bumper) {
                outArm.setPosition(var1.armOut);
                wrist.setPosition(var1.wristOut);
            }
        }

        switch (currentIntakeState) {
            case intakeIdle:
                intake.setPower(0); // Ensure motors are stopped in idle
                if (currentGamepad2.cross && !previousGamepad2.cross) { // Start intake
                    gate.setPosition(var1.gateClose);
                    leftIn.setPosition(var1.inDown);
                    rightIn.setPosition(var1.inDown);
                    inWrist.setPosition(var1.inWristIntaking); // Set wrist to intaking position
                    currentIntakeState = intakeState.intaking; // Move to intaking state
                }
                break;
            case intaking:


                if (!spit && !spitReset) {
                    leftIn.setPosition(var1.inDown);
                    rightIn.setPosition(var1.inDown);
                    inWrist.setPosition(var1.inWristIntaking);
                    gate.setPosition(var1.gateClose);
                } else if (spit) {
                    spitReset = true;
                    leftIn.setPosition(var1.inSpit);
                    rightIn.setPosition(var1.inSpit);
                    inWrist.setPosition(var1.inWristSpit);
                    gate.setPosition(var1.gateOpen);
                    intake.setPower(1);
                    spitTimer = globalTimer.seconds() + 0.5;
                }
                if (globalTimer.seconds() > spitTimer && spitReset) {
                    spitReset = false;
                }

// Control intake power with the left bumper
                if (gamepad2.left_bumper) {
                    leftIn.setPosition(var1.inSpit);
                    rightIn.setPosition(var1.inSpit);
                    inWrist.setPosition(var1.inWristSpit);
                    gate.setPosition(var1.gateOpen);
                    intake.setPower(1); // Reverse intake
                } else {
                    leftIn.setPosition(var1.inDown);
                    rightIn.setPosition(var1.inDown);
                    inWrist.setPosition(var1.inWristIntaking);
                    gate.setPosition(var1.gateClose);
                    intake.setPower(1);
                    // Normal intake
                }
                if ((currentGamepad2.cross && !previousGamepad2.cross) || (eat) && !spit) { // Start transfer
                    leftIn.setPosition(var1.inTransfer);
                    rightIn.setPosition(var1.inTransfer);
                    inWrist.setPosition(var1.inWristTransfer); // Set wrist to transfer position
// Reset abort timer
                    resethor = true;
                    intake.setPower(0.5);
                    eat = false;
                    currentTransferState = transferState.gateOpen;
                    currentIntakeState = intakeState.intakeTransfering;

                }
                break;

            case intakeTransfering:
                // Initialize transfer state if not already set
                // Handle transfer state logic
                switch (currentTransferState) {
                    case gateOpen:
                        resethor = true;
                        transfering = true;
                        gate.setPosition(var1.gateOpen);
                        if (hortouch.isPressed()) { // Wait for touch sensor
                            intakeTimer = globalTimer.seconds() + 0.5;
                            currentTransferState = transferState.spin; // Move to spin state
                        }
                        break;
                    case spin:

                        intake.setPower(1); // Spin intake
                        if (globalTimer.seconds() > intakeTimer) {
                            currentTransferState = transferState.idle; // Move to idle state
                        }
                        break;
                    case idle:
                        intake.setPower(0); // Stop motors
                        transfering = false;
                        currentIntakeState = intakeState.intakeIdle;
                        break;
                }
                break;
        }
// Reset vertical slides if touch sensor is pressed
    }

    public void edibility() {
        NormalizedRGBA intakeColours = intakeColour.getNormalizedColors();
        double intakeRed = intakeColours.red;
        double intakeGreen = intakeColours.green;
        double intakeBlue = intakeColours.blue;
        double waitIn=Float.MAX_VALUE;
        if (currentIntakeState == intakeState.intaking) {
            if (intakeBlue > intakeGreen && intakeBlue > intakeRed) { //see blue
                spit = true;
                pattern = RevBlinkinLedDriver.BlinkinPattern.BLUE;
            } else {
                spit = false;
                pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;
            }
            if (intakeRed > intakeGreen && intakeBlue < intakeRed) { //see red

                eat = true;
                pattern = RevBlinkinLedDriver.BlinkinPattern.RED;
            } else {

                eat = false;
                pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;
            }
        }
        if(pattern!=pattern.previous()){
            lights.setPattern(pattern);
            waitIn=globalTimer.seconds()+3.0;
        }
        if(globalTimer.seconds()>waitIn){
            spit=false;
            eat=false;
            waitIn=Float.MAX_VALUE;
        }
    }

    // Reset horizontal slide logic
    public void resethor() {
        if (!hortouch.isPressed() && resethor) {
            horSlide.setPower(-1); // Move slide backwards
        } else if (hortouch.isPressed() && resethor) {
            horSlide.setPower(0); // Stop slide
            horSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            horSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            resethor = false; // Reset the flag
        } else if (!resethor && !transfering) {
            horSlide.setPower(gamepad2.right_stick_x); // Allow manual control
        }
    }
    public void Telemetry() {
        telemetry.addData("vert", vertSlideL.getCurrentPosition());
        if (vertouch.isPressed()) {
            telemetry.addLine("touch joe");
        }
        telemetry.addData("arm state", currentArmState);
        telemetry.addData("intake state", currentIntakeState);
        telemetry.addData("transferstate", currentTransferState);
        telemetry.addData("vertslide mode", vertSlideR.getMode());
        telemetry.addData("transfer sensor",transferColour);
        if (specMode) {
            telemetry.addLine("specmode");
        } else {
            telemetry.addLine(" ");
        }
        if (eat) {
            telemetry.addLine("eat");
        } else {
            telemetry.addLine(" ");
        }
        if(manualSlide) {
            telemetry.addLine("manualslide");
        }
        telemetry.addData("hor power", horSlide.getPower());
    }
    @Override
    public void loop () {
        edibility();
        drive();
        manualTake();
        Telemetry();
        resethor();

    }
}