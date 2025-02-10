package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;//importing libraries
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;


@TeleOp(name = "lightes tests", group= "TeleOp")
public class lightstest extends OpMode {
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
    private NormalizedColorSensor intakeColour;
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

    private final static int LED_PERIOD = 30;
    Deadline ledCycleDeadline;
    RevBlinkinLedDriver.BlinkinPattern pattern;
    @Override
    public void init() {
        if (lights == null) {
            telemetry.addLine("Error: Lights not found in configuration.");
            telemetry.update();
        } else {
            telemetry.addLine("Lights Initialized.");
            telemetry.update();
        }
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");
        currentGamepad2 = new Gamepad();
        previousGamepad2 = new Gamepad();
        currentGamepad1 = new Gamepad();
        previousGamepad1 = new Gamepad();

        ledCycleDeadline = new Deadline(LED_PERIOD, TimeUnit.SECONDS);
    }

    public void manualTake() {
        previousGamepad1.copy(currentGamepad1);
        // Update current state with the latest gamepad data
        currentGamepad1.copy(gamepad1);

        // Store previous state
        previousGamepad2.copy(currentGamepad2);
        // Update current state with the latest gamepad data
        currentGamepad2.copy(gamepad2);
//colour sensor
//spec sample mode

        if (currentGamepad2.square && !previousGamepad2.square) {
            pattern = RevBlinkinLedDriver.BlinkinPattern.BLUE_GREEN;
                lights();
        } else if (currentGamepad2.triangle && !previousGamepad2.triangle) {
            pattern = RevBlinkinLedDriver.BlinkinPattern.BLUE;
            lights();
        } else if (currentGamepad2.circle && !previousGamepad2.circle) {
            pattern = RevBlinkinLedDriver.BlinkinPattern.YELLOW;
            lights();
        } else if (currentGamepad2.cross && !previousGamepad2.cross) {
            pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;
            lights();

        }



    }



    // Reset horizontal slide logic

    public void lights() {
        lights.setPattern(pattern);
    }
    @Override
    public void loop () {
        manualTake();
                telemetry.addData("Lights Initialized", lights != null);
        telemetry.addData("Square Pressed", currentGamepad2.square);
        telemetry.addData("Triangle Pressed", currentGamepad2.triangle);
        telemetry.addData("Circle Pressed", currentGamepad2.circle);
        telemetry.addData("Cross Pressed", currentGamepad2.cross);
        telemetry.update();
    }
}