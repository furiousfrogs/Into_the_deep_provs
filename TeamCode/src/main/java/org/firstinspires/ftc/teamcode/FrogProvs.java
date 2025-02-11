//package org.firstinspires.ftc.teamcode;
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
//
//import com.qualcomm.hardware.bosch.BHI260IMU;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;//importing libraries
//import com.qualcomm.robotcore.hardware.ColorRangeSensor;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DistanceSensor;
//import com.qualcomm.robotcore.hardware.Gamepad;
//import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
//import com.qualcomm.robotcore.hardware.NormalizedRGBA;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.TouchSensor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
//
//
//@TeleOp(name = "FrogProvies", group= "TeleOp")
//public class FrogProvs extends OpMode {
//    private DcMotor frontLeft, frontRight, backLeft, backRight;
//    private Servo leftIn, rightIn, wrist, outArm, claw, gate, inWrist;
//    private DcMotor horSlide, vertSlideL, vertSlideR, intake;
//
//    boolean resetLights = false;
//
//    boolean transferAction1 = false;
//    private RevBlinkinLedDriver lights;
//
//    boolean specMode = false;
//    boolean eat = false;
//    boolean spit = false;
//    double colour = 1; //1 is dark green, 2 is blue/red, 3 is yellow, 4 is light green.
//    boolean timeReset = false;
//    boolean intakeAbort = false;
//    boolean botUnfold = false;
//    boolean lightsReset = false;
//    private ElapsedTime lightsTimer = new ElapsedTime();
//    boolean manualSlide = false;
//    private ElapsedTime abortTimer = new ElapsedTime();
//    boolean intakeAction = false;
//    private ElapsedTime intakeTimer = new ElapsedTime();
//    boolean transferAction = false;
//    private ElapsedTime transferTimer = new ElapsedTime();
//    boolean outtakeAction = false;
//    private ElapsedTime outtakeTimer = new ElapsedTime();
//    boolean specScore = false;
//    private ElapsedTime specScoreTimer = new ElapsedTime();
//    boolean specAction = false;
//    private ElapsedTime specTimer = new ElapsedTime();
//    boolean specAction2 = false;
//    private ElapsedTime specTimer2 = new ElapsedTime();
//    boolean specAbort = false;
//    private ElapsedTime specAbortTimer = new ElapsedTime();
//    private ElapsedTime initTimer = new ElapsedTime();
//    boolean transfering = false;
//    private BHI260IMU imu;
//    private ColorRangeSensor intakeColour;
//    private TouchSensor hortouch;
//    private TouchSensor vertouch;
//    boolean resethor = false;
//    Gamepad currentGamepad1;
//    Gamepad previousGamepad1;
//    Gamepad currentGamepad2;
//    Gamepad previousGamepad2;
//    public enum armState {
//        armTransfering,
//        armOuttaking,
//        armSpec,
//        armSpecScore,
//        armIdle;
//    }
//    armState currentArmState = armState.armIdle;
//    public enum intakeState {
//        intakeIdle,
//        intaking,
//        intakeTransfering,
//        intakeAbort;
//    }
//    intakeState currentIntakeState = intakeState.intakeIdle;
//    public enum transferState {
//        gateOpen,
//        spin,
//        idle;
//    }
//    transferState currentTransferState = transferState.gateOpen;
//    @Override
//    public void init() {
//
//        lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");
//
//
//        intakeColour = hardwareMap.get(ColorRangeSensor.class, "intakeColour");
//
//        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
//        frontLeft.setDirection(DcMotor.Direction.REVERSE);
//        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        frontRight = hardwareMap.get(DcMotor.class, "front_right");
//        frontRight.setDirection(DcMotor.Direction.FORWARD);
//        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backLeft = hardwareMap.get(DcMotor.class, "back_left");
//        backLeft.setDirection(DcMotor.Direction.REVERSE);
//        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backRight = hardwareMap.get(DcMotor.class, "back_right");
//        backRight.setDirection(DcMotor.Direction.FORWARD);
//        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        inWrist = hardwareMap.get(Servo.class, "inWrist");
//        inWrist.setDirection(Servo.Direction.FORWARD);
//        inWrist.setPosition(var.inWristTransfer);
//        outArm = hardwareMap.get(Servo.class, "outarm");
//        outArm.setDirection(Servo.Direction.FORWARD);
//// outArm.setPosition(var.armTransfer);
//        wrist = hardwareMap.get(Servo.class, "wrist");
//        wrist.setDirection(Servo.Direction.FORWARD);
//// wrist.setPosition(var.wristInit);
//        gate = hardwareMap.get(Servo.class, "gate");
//        gate.setDirection(Servo.Direction.FORWARD);
//        gate.setPosition(var.gateClose);
//        claw = hardwareMap.get(Servo.class, "claw");
//        claw.setDirection(Servo.Direction.FORWARD);
//// claw.setPosition(var.clawOpen);
//        leftIn = hardwareMap.get(Servo.class, "leftin");
//        leftIn.setDirection(Servo.Direction.FORWARD);
//        leftIn.setPosition(var.inIdle);
//        rightIn = hardwareMap.get(Servo.class, "rightin");
//        rightIn.setDirection(Servo.Direction.FORWARD);
//        rightIn.setPosition(var.inIdle);
//        horSlide = hardwareMap.get(DcMotor.class, "righthor");
//        horSlide.setDirection(DcMotor.Direction.REVERSE);
//        horSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        horSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        horSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        vertSlideL = hardwareMap.get(DcMotor.class, "leftvertical");
//        vertSlideL.setDirection(DcMotor.Direction.FORWARD);
//        vertSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        vertSlideL.setTargetPosition(0);
//        vertSlideL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        vertSlideL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        vertSlideR = hardwareMap.get(DcMotor.class, "rightvertical");
//        vertSlideR.setDirection(DcMotor.Direction.REVERSE);
//        vertSlideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        vertSlideR.setTargetPosition(0);
//        vertSlideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        vertSlideR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        intake = hardwareMap.get(DcMotor.class, "intake");
//        intake.setDirection(DcMotor.Direction.FORWARD);
//        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        currentGamepad2 = new Gamepad();
//        previousGamepad2 = new Gamepad();
//        currentGamepad1 = new Gamepad();
//        previousGamepad1 = new Gamepad();
//        hortouch = hardwareMap.get(TouchSensor.class, "hortouch");
//        vertouch = hardwareMap.get(TouchSensor.class, "vertouch");
//
//    }
//    public void drive() {
//        double turnvar = Math.max(1, 1 + (horSlide.getCurrentPosition() / 1000.0));
//        boolean slow = gamepad1.options;
//        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
//        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
//        double rx = (gamepad1.right_trigger - gamepad1.left_trigger) / turnvar;
//        double slowvar = 2.0; // Slow mode divisor
//        double speedFactor = slow ? 1 / slowvar : 1;
//        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 0.8);
//// Denominator is the largest motor power (absolute value) or 1
//// This ensures all the powers maintain the same ratio,
//// but only if at least one is out of the range [-1, 1]
//        double frontLeftPower = (y + x + rx) / denominator * speedFactor * 0.8;
//        double backLeftPower = (y - x + rx) / denominator * speedFactor * 0.8;
//        double frontRightPower = (y - x - rx) / denominator * speedFactor * 0.8;
//        double backRightPower = (y + x - rx) / denominator * speedFactor * 0.8;
//// Normalize motor powers
//        double maxPower = Math.max(Math.abs(frontLeftPower),
//                Math.max(Math.abs(frontRightPower),
//                        Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
//        if (maxPower > 1.0) {
//            frontLeftPower /= maxPower;
//            frontRightPower /= maxPower;
//            backLeftPower /= maxPower;
//            backRightPower /= maxPower;
//        }
//        if (vertSlideL.getCurrentPosition() > 2000) {
//            frontLeftPower = frontLeftPower/2;
//            frontRightPower = frontRightPower/2;
//            backLeftPower = backLeftPower/2;
//            backRightPower = backRightPower/2;
//        }
//// Set motor powers
//        frontLeft.setPower(frontLeftPower);
//        frontRight.setPower(frontRightPower);
//        backLeft.setPower(backLeftPower);
//        backRight.setPower(backRightPower);
//    }
//    public void manualTake() {
//        previousGamepad1.copy(currentGamepad1);
//        // Update current state with the latest gamepad data
//        currentGamepad1.copy(gamepad1);
//
//        // Store previous state
//        previousGamepad2.copy(currentGamepad2);
//        // Update current state with the latest gamepad data
//        currentGamepad2.copy(gamepad2);
////colour sensor
//        NormalizedRGBA intakeColours = intakeColour.getNormalizedColors();
//        double intakeRed = intakeColours.red;
//        double intakeGreen = intakeColours.green;
//        double intakeBlue = intakeColours.blue;
////spec sample mode
//        if (currentGamepad2.square && !previousGamepad2.square && !specMode) {
//            colour = 2;
//            specMode = true;
//        } else if (currentGamepad2.square && !previousGamepad2.square && specMode) {
//            colour = 3;
//            specMode = false;
//        } else if (!specMode && lightsReset) {
//            colour = 3;
//        } else if (specMode && lightsReset) {
//            colour =2;
//        }
//
//
//        if (specMode && !eat && !spit) {
//            if (intakeBlue > intakeGreen && intakeBlue > intakeRed) {
//                colour = 4;
//                eat = true;
//            } else if (intakeRed > intakeGreen && intakeRed > intakeBlue) {
//                spit = true;
//            } else if (!specMode && !eat) {
//                if (intakeRed > intakeBlue && intakeGreen > intakeBlue) {
//                    colour = 4;
//                    eat = true;
//                } else if (intakeBlue > intakeGreen && intakeBlue > intakeRed) {
//                    colour = 4;
//                    eat = true;
//                } else if (intakeRed > intakeGreen && intakeRed > intakeBlue) {
//                    spit = true;
//                }
//            } //manual controls
//        }
//
//
//
//        if (!botUnfold) {
//            vertSlideL.setPower(1);
//            vertSlideR.setPower(1);
//            vertSlideL.setTargetPosition(500);
//            vertSlideR.setTargetPosition(500);
//            initTimer.reset();
//            claw.setPosition(var.clawOpen);
//            leftIn.setPosition(var.inDown);
//            rightIn.setPosition(var.inDown);
//            inWrist.setPosition(var.inWristIntaking);
//
//            while (initTimer.seconds() < 0.3) {
//            }
//
//            claw.setPosition(var.clawClose);
//            outArm.setPosition(var.armTransfer);
//            wrist.setPosition(var.wristTransfer);
//            vertSlideL.setPower(1);
//            vertSlideR.setPower(1);
//            vertSlideL.setTargetPosition(250);
//            vertSlideR.setTargetPosition(250);
//            currentArmState = armState.armTransfering;
//            initTimer.reset();
//
//            while (initTimer.seconds() < 0.3) {
//            }
//
//            leftIn.setPosition(var.inTransfer);
//            rightIn.setPosition(var.inTransfer);
//            inWrist.setPosition(var.inWristTransfer);
//            claw.setPosition(var.clawOpen);
//            botUnfold = true;
//        } else if (botUnfold) { //all the shit below
//            if (currentGamepad2.options && !previousGamepad2.options && !manualSlide) {
//                manualSlide = true;
//            } else if (currentGamepad2.options && !previousGamepad2.options && manualSlide) {
//                claw.setPosition(var.clawOpen);
//                outArm.setPosition(var.armTransfer);
//                wrist.setPosition(var.wristTransfer);
//                vertSlideL.setTargetPosition(250);
//                vertSlideR.setTargetPosition(250);
//                currentArmState = armState.armTransfering;
//                manualSlide = false;
//            }
//            previousGamepad1.copy(currentGamepad1);
//            currentGamepad1.copy(gamepad1);
//            previousGamepad2.copy(currentGamepad2);
//            currentGamepad2.copy(gamepad2);
//            if (!manualSlide) {
//                vertSlideL.setPower(1);
//                vertSlideL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                vertSlideR.setPower(1);
//                vertSlideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                switch (currentArmState) {
//                    case armIdle:
//                        if (currentGamepad2.circle && !previousGamepad2.circle) {
//                            claw.setPosition(var.clawOpen);
//                            outArm.setPosition(var.armTransfer);
//                            wrist.setPosition(var.wristTransfer);
//                            vertSlideL.setTargetPosition(250);
//                            vertSlideR.setTargetPosition(250);
//                            currentArmState = armState.armTransfering;
//                        } else if (currentGamepad2.triangle && !previousGamepad2.triangle) {
//                            claw.setPosition(var.clawClose);
//                            vertSlideL.setTargetPosition(1000);
//                            vertSlideR.setTargetPosition(1000);
//                            specAction = true;
//                        }
//                        break;
//                    case armTransfering:
//                        if (currentGamepad2.circle && !previousGamepad2.circle) {
//                            claw.setPosition(var.clawClose);
//                            transferAction = true;
//                            transferTimer.reset();
//                        }
//                        if (currentGamepad2.triangle && !previousGamepad2.triangle) {
//                            claw.setPosition(var.clawClose);
//                            vertSlideL.setTargetPosition(1000);
//                            vertSlideR.setTargetPosition(1000);//TODO
//                            specAction = true;
//                        }
//                        break;
//                    case armOuttaking:
//                        if (currentGamepad2.circle && !previousGamepad2.circle) {
//                            claw.setPosition(var.clawOpen);
//                            outtakeAction = true;
//                            outtakeTimer.reset();
//                        } else if (currentGamepad2.triangle && !previousGamepad2.triangle) { //unfinshed
//                            claw.setPosition(var.clawClose);
//                            vertSlideL.setTargetPosition(1000);
//                            vertSlideR.setTargetPosition(1000);//TODO
//                            specAction = true;
//                        }
//                        break;
//                    case armSpec:
//                        if (currentGamepad2.triangle && !previousGamepad2.triangle) {
//                            claw.setPosition(var.clawClose);
//                            vertSlideL.setTargetPosition(1000);
//                            vertSlideR.setTargetPosition(1000);//TODO
//                            specScoreTimer.reset();
//                            specScore = true;
//
//                        }
//
//                        break;
//                    case armSpecScore:
//                        if (currentGamepad2.right_bumper && !previousGamepad2.right_bumper) {
//                            outArm.setPosition(var.armSpec);
//                            wrist.setPosition(var.wristSpec);
//
//                            specAbort = true;
//                            specAbortTimer.reset();
//                        }
//                        if (currentGamepad2.triangle && !previousGamepad2.triangle) {
//                            claw.setPosition(var.clawOpen);
//                            currentArmState = armState.armIdle;
//                        }
//                        break;
//                }
//
//                if (specAbort && specAbortTimer.seconds() > 0.2) {
//                    claw.setPosition(var.clawOpenWide);
//                    currentArmState = armState.armSpec;
//                }
//                if (transferAction && transferTimer.seconds() > 0.5) {
//                    vertSlideL.setTargetPosition(2150);
//                    vertSlideR.setTargetPosition(2150);//TODO
//                    transferAction1 = true;
//                    transferAction = false;
//
//                }
//                if (transferAction1 && vertSlideL.getCurrentPosition() > 1500) {
//                    outArm.setPosition(var.armOut);
//                    wrist.setPosition(var.wristOut);
//                    transferAction1 = false;
//                    currentArmState = armState.armOuttaking;
//                }
//
//            }
//            if (outtakeAction && outtakeTimer.seconds() > 0.5) {
//                outArm.setPosition(var.armTransfer);
//                wrist.setPosition(var.wristTransfer);
//                vertSlideL.setPower(1);
//                vertSlideR.setPower(1);
//                vertSlideL.setTargetPosition(250);
//                vertSlideR.setTargetPosition(250);
//                currentArmState = armState.armTransfering;
//                outtakeAction = false;
//            }
//            if (specScore && specScoreTimer.seconds() > 0.5) {
//                outArm.setPosition(var.armSpecScore);
//                wrist.setPosition(var.wristSpecScore);
//                currentArmState = armState.armSpecScore;
//                specScore = false;
//            }
//            if (specAction && vertSlideL.getCurrentPosition() > 500) {
//                outArm.setPosition(var.armSpec);
//                wrist.setPosition(var.wristSpec);
//                specAction2 = true;
//                specAction = false;
//                specTimer2.reset();
//            }
//            if (specAction2 && specTimer2.seconds() > 0.7) {
//                claw.setPosition(var.clawOpenWide);
//                currentArmState = armState.armSpec;
//                specAction2 = false;
//            }
//
//        } else if (manualSlide) {
//            vertSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            vertSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            if (vertouch.isPressed() && vertSlideL.getCurrentPosition() != 0 &&
//                    vertSlideR.getCurrentPosition() != 0) {
//                vertSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                vertSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                vertSlideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                vertSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            }
//            vertSlideL.setPower(-gamepad2.right_stick_y);
//            vertSlideR.setPower(-gamepad2.right_stick_y);
//            if (gamepad2.right_bumper) {
//                outArm.setPosition(var.armOut);
//                wrist.setPosition(var.wristOut);
//            }
//        }
//        switch (currentIntakeState) {
//            case intakeIdle:
//                intake.setPower(0); // Ensure motors are stopped in idle
//                if (currentGamepad2.cross && !previousGamepad2.cross) { // Start intake
//                    gate.setPosition(var.gateClose);
//                    leftIn.setPosition(var.inDown);
//                    rightIn.setPosition(var.inDown);
//                    inWrist.setPosition(var.inWristIntaking); // Set wrist to intaking position
//                    currentIntakeState = intakeState.intaking; // Move to intaking state
//                }
//                break;
//            case intaking:
//
//                if (intakeRed > intakeGreen && intakeBlue < intakeRed)
//                if (!spit) {
//                    leftIn.setPosition(var.inDown);
//                    rightIn.setPosition(var.inDown);
//                    inWrist.setPosition(var.inWristIntaking);
//                } else if (spit) {
//                    inWrist.setPosition(var.inWristSpit);
//                    intake.setPower(-1);
//                }
//// Control intake power with the left bumper
//                if (gamepad2.left_bumper) {
//                    intake.setPower(-1); // Reverse intake
//                } else {
//                    intake.setPower(1); // Normal intake
//                }
//                if ((currentGamepad2.cross && !previousGamepad2.cross) || (eat)) { // Start transfer
//                    leftIn.setPosition(var.inTransfer);
//                    rightIn.setPosition(var.inTransfer);
//                    inWrist.setPosition(var.inWristTransfer); // Set wrist to transfer position
//// Reset abort timer
//                    resethor = true;
//                    intake.setPower(0);
//                    eat = false;
//                    currentTransferState = transferState.gateOpen;
//                    currentIntakeState = intakeState.intakeTransfering;
//                    timeReset = false;
//                }
//                break;
//
//            case intakeTransfering:
//                // Initialize transfer state if not already set
//                // Handle transfer state logic
//                switch (currentTransferState) {
//                    case gateOpen:
//                        transfering = true;
//                        if (hortouch.isPressed()) { // Wait for touch sensor
//                            gate.setPosition(var.gateOpen);
//                            intakeTimer.reset();
//                            currentTransferState = transferState.spin; // Move to spin state
//                        }
//                        break;
//                    case spin:
//                        if (!timeReset) {
//                            intakeTimer.reset();
//                            timeReset = true;
//                        }
//                        intake.setPower(0.8); // Spin intake
//                        if (intakeTimer.seconds() > 0.5) {
//                            currentTransferState = transferState.idle; // Move to idle state
//                        }
//                        break;
//                    case idle:
//                        intake.setPower(0); // Stop motors
//                        transfering = false;
//                        currentIntakeState = intakeState.intakeIdle;
//                        break;
//                }
//                break;
//        }
//// Reset vertical slides if touch sensor is pressed
//    }
//    public void lights() {
//        if (colour == 1) {
//            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_GREEN);
//
//        }
//        if (colour == 2) {
//            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
//
//        }
//        if (colour == 3) {
//            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
//
//        }
//        if (colour == 4) {
//            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
//
//            if (!lightsReset) {
//                lightsTimer.reset();
//                lightsReset = true;
//            }
//            if (lightsReset && lightsTimer.seconds() > 0.3) {
//                lightsReset = false;
//                resetLights = true;
//            }
//
//        }
//    }
//    // Reset horizontal slide logic
//    public void resethor() {
//        if (!hortouch.isPressed() && resethor) {
//            horSlide.setPower(-1); // Move slide backwards
//        } else if (hortouch.isPressed() && resethor) {
//            horSlide.setPower(0); // Stop slide
//            horSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            horSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            resethor = false; // Reset the flag
//        } else if (!resethor && !transfering) {
//            horSlide.setPower(gamepad2.right_stick_x); // Allow manual control
//        }
//    }
//    public void Telemetry() {
//        telemetry.addData("vert", vertSlideL.getCurrentPosition());
//        if (vertouch.isPressed()) {
//            telemetry.addLine("touch joe");
//        }
//        telemetry.addData("arm state", currentArmState);
//        telemetry.addData("intake state", currentIntakeState);
//        telemetry.addData("transferstate", currentTransferState);
//        telemetry.addData("colour sensor", intakeColour);
//        telemetry.addData("red", intakeColour.getNormalizedColors());
//        if (specMode) {
//            telemetry.addLine("specmode");
//        } else {
//            telemetry.addLine(" ");
//        }
//        if (eat) {
//            telemetry.addLine("eat");
//        } else {
//            telemetry.addLine(" ");
//        }
//        if(manualSlide) {
//            telemetry.addLine("manualslide");
//        }
//        telemetry.addData("hor power", horSlide.getPower());
//    }
//    @Override
//    public void loop () {
//        drive();
//        manualTake();
//        Telemetry();
//        resethor();
//        lights();
//    }
//}