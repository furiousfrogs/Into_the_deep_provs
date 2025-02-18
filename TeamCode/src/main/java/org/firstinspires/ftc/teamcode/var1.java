package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;


@Config
public class var1 {

    public static float inWristIntakingAuto = 0.53F;
    public static float inWristIntaking = 0.55F; //LIKELY TO CHANGE
    public static float inWristTransfer = 0.65F;
    public static float inWristSpit = 0.8F;

    public static float inWristSpitAuto = 0.8F;

    public static float gateOpen = 0.4F;
    public static float gateClose = 0F;

    //Bigger number - intake is lower
    public static float inDownAuto = 0.79F;
    public static float inDown = 0.84F; //LIKELY TO CHANGE
    public static float inTransfer = 0.45F;
    public static float inIdle = 0.6F;
    public static float inSpit = 0.8F;


    //Bigger number - arm is lower
    public static float armOut = 0.98F;
    public static float armTransfer = 0.35F;
    public static float armSpec = 0.09F;
    public static float armSpecScore = 0.57F;
    public static float armInit = 0.4F;
    public static float armTransferOld = 0.46F;
    //Bigger number - claw is open
    public static float clawCloseTight = 0.71F;
    public static float clawClose = 0.65F;
    public static float clawOpen = 0.52F;
    public static float clawOpenWide = 0.32F;
    public static float clawCloseLoose=0.64F;
    //Bigger number - wrist goes out
    public static float wristTransfer = 0.34F;
    public static float wristOut = 0.9F;
    public static float wristSpec = 0.2F;
    public static float wristSpecScore = 0.47F;
    public static float wristInit = 0.54F;
    public static float wristTransferOld = 0.2F;

    //Slide Positions
    public static int slideDeposit = 2300;
    public static int slideTransfer = 130;
    public static int slideSpecPickup = 400;
    public static int slideSpecScore = 920;


    // slide PID coefficients
    public static double kP = 0.005; // Proportional gain
    public static double kI = 0.0;  // Integral gain
    public static double kD = 0.00;  // Derivative gain
    public static double kF = 0.0; //Feedforward gain
    public static double maxIntegral = 800;
    public static double tolerance = 50;

    public static float spec1PickupHeading = 249F;
    public static float spec2PickupHeading = 228.5F;
    public static float spec3PickupHeading = 228F;


    public static int specPickupx1 = -45;
    public static int specPickupx2 = -47;
    public static float specPickupx3 = -52.6F;

    public static float sampdrop1heading = 225F;
    public static float sampdrop2heading = 225F;
    public static float sampdrop3heading = 225F;

    public static float samppickup1heading = 272F;
    public static float samppickup2heading = 280F;
    public static float samppickup3heading = 315F;

    public static float sampdropx1 = 57F;
    public static float sampdropx2 = 50F;
    public static float sampdropx3 = 55F;

    public static float sampdropy1 = 57F;
    public static float sampdropy2 = 55F;
    public static float sampdropy3 = 55F;

    public static float samppickx1 = 45F;
    public static float samppickx2 = 60.5F;






    //pid state variables
    public static double targetPosition = 1000.0; // Desired slide position
    public static double lastError = 0; // Previous error for derivative calculation
    public static double integralSum = 0; // Accumulated integral

    //LED Values
    public static double LEDtest = 0.61; //RED
}