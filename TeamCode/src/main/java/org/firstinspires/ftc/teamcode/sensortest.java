package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;//importing libraries
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name = "sensor test", group= "TeleOp")
public class sensortest extends OpMode {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private Servo leftIn, rightIn, wrist, outArm, claw, gate, inWrist;
    private DcMotor horSlide, vertSlideL, vertSlideR, intake;

    boolean resetLights = false;


    private RevBlinkinLedDriver lights;

    boolean specMode = false;
    boolean eat = false;
    boolean spit = false;
    double colour = 1; //1 is dark green, 2 is blue/red, 3 is yellow, 4 is light green.
    boolean timeReset = false;
    boolean intakeAbort = false;
    boolean botUnfold = false;
    boolean lightsReset = false;
    private ElapsedTime lightsTimer = new ElapsedTime();
    boolean manualSlide = false;
    private ElapsedTime abortTimer = new ElapsedTime();
    boolean intakeAction = false;
    private ElapsedTime intakeTimer = new ElapsedTime();
    boolean transferAction = false;
    private ElapsedTime transferTimer = new ElapsedTime();
    boolean outtakeAction = false;
    private ElapsedTime outtakeTimer = new ElapsedTime();
    boolean specScore = false;
    private ElapsedTime specScoreTimer = new ElapsedTime();
    boolean specAction = false;
    private ElapsedTime specTimer = new ElapsedTime();
    boolean specAction2 = false;
    private ElapsedTime specTimer2 = new ElapsedTime();
    boolean specAbort = false;
    private ElapsedTime specAbortTimer = new ElapsedTime();
    private ElapsedTime initTimer = new ElapsedTime();
    boolean transfering = false;
    private BHI260IMU imu;
    private ColorRangeSensor intakeColour;
    private TouchSensor hortouch;
    private TouchSensor vertouch;
    boolean resethor = false;
    Gamepad currentGamepad1;
    Gamepad previousGamepad1;
    Gamepad currentGamepad2;
    Gamepad previousGamepad2;

    private ColorRangeSensor intakeDistance;

    boolean iseeblue = false;
    boolean iseered = false;
    boolean iseeyellow = false;

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

        lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");


        intakeColour = hardwareMap.get(ColorRangeSensor.class, "intakeColour");


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
        inWrist = hardwareMap.get(Servo.class, "intakewrist");
        inWrist.setDirection(Servo.Direction.FORWARD);
        inWrist.setPosition(var.inWristTransfer);
        outArm = hardwareMap.get(Servo.class, "outarm");
        outArm.setDirection(Servo.Direction.FORWARD);
// outArm.setPosition(var.armTransfer);
        wrist = hardwareMap.get(Servo.class, "wrist");
        wrist.setDirection(Servo.Direction.FORWARD);
// wrist.setPosition(var.wristInit);
        gate = hardwareMap.get(Servo.class, "gate");
        gate.setDirection(Servo.Direction.FORWARD);
        gate.setPosition(var.gateClose);
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setDirection(Servo.Direction.FORWARD);
// claw.setPosition(var.clawOpen);
        leftIn = hardwareMap.get(Servo.class, "leftin");
        leftIn.setDirection(Servo.Direction.FORWARD);
        leftIn.setPosition(var.inIdle);
        rightIn = hardwareMap.get(Servo.class, "rightin");
        rightIn.setDirection(Servo.Direction.FORWARD);
        rightIn.setPosition(var.inIdle);
        horSlide = hardwareMap.get(DcMotor.class, "righthor");
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
// coloursensor = hardwareMap.get(ColorRangeSensor.class, "coloursensor");
    }
    public void manualTake() {
        double distanceMM = intakeDistance.getDistance(DistanceUnit.MM);
//colour sensor
        NormalizedRGBA intakeColours = intakeColour.getNormalizedColors();
        double intakeRed = intakeColours.red;
        double intakeGreen = intakeColours.green;
        double intakeBlue = intakeColours.blue;
//spec sample mode
        if (currentGamepad2.square && !previousGamepad2.square && !specMode) {
            colour = 2;
            specMode = true;
        } else if (currentGamepad2.square && !previousGamepad2.square && specMode) {
            colour = 3;
            specMode = false;
        } else if (!specMode && lightsReset) {
            colour = 3;
        } else if (specMode && lightsReset) {
            colour = 2;
        }
if (intakeColour.getDistance(DistanceUnit.CM) < 1) {
    if (intakeBlue > intakeGreen && intakeBlue > intakeRed) {
        iseeblue = true;
    } else {
        iseeblue = false;
    }
    if (intakeRed > intakeGreen && intakeBlue < intakeRed) {
        iseered = true;
    } else {
        iseered = false;
    }
    if (intakeRed > intakeBlue && intakeGreen > intakeBlue && Math.abs(intakeRed - intakeGreen) < 0.5) {
        iseeyellow = true;
    } else {
        iseeyellow = false;
    }
}

    }


    public void Telemetry() {


if (iseeyellow) {
    telemetry.addLine("iseeyellow");
}
        if (iseeblue) {
            telemetry.addLine("iseeblue");
        }
        if (iseered) {
            telemetry.addLine("iseered");
        }

    }
    @Override
    public void loop () {

        manualTake();
        Telemetry();

    }
}