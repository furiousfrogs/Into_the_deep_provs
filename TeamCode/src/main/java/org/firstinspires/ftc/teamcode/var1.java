package org.firstinspires.ftc.teamcode;

public class var1 {


    public static float inWristIntaking = 0.63F; //LIKELY TO CHANGE
    public static float inWristTransfer = 0.9F;
    public static float inWristSpit = 0.9F;

    public static float gateOpen = 0.05F;
    public static float gateClose = 0.37F;

    //Bigger number - intake is lower
    public static float inDown = 0.8F; //LIKELY TO CHANGE
    public static float inTransfer = 0.45F;
    public static float inIdle = 0.8F;
    public static float inSpit = 0.8F;

    //Bigger number - arm is lower
    public static float armOut = 0.85F;
    public static float armTransfer = 0.33F;
    public static float armSpec = 0.0F;
    public static float armSpecScore = 0.44F;

    //Bigger number - claw is open
    public static float clawCloseTight = 0.066F;
    public static float clawClose = 0.63F;
    public static float clawOpen = 0.50F;
    public static float clawOpenWide = 0.3F;
    public static float clawCloseLoose=0.6F;
    //Bigger number - wrist goes out
    public static float wristTransfer = 0.2F;
    public static float wristOut = 0.9F;
    public static float wristSpec = 0.2F;
    public static float wristSpecScore = 0.47F;
    public static float wristInit = 0F;

    //Slide Positions
    public static int slideDeposit = 2300;
    public static int slideTransfer = 60;
    public static int slideSpecPickup = 400;
    public static int slideSpecScore = 960;


    // slide PID coefficients
    public static double kP = 0.005; // Proportional gain
    public static double kI = 0.0;  // Integral gain
    public static double kD = 0.00;  // Derivative gain
    public static double kF = 0.0; //Feedforward gain
    public static double maxIntegral = 800;
    public static double tolerance = 50;



    //pid state variables
    public static double targetPosition = 1000.0; // Desired slide position
    public static double lastError = 0; // Previous error for derivative calculation
    public static double integralSum = 0; // Accumulated integral

    //LED Values
    public static double LEDtest = 0.61; //RED
}