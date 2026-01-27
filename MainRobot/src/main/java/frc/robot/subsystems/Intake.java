package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// 8ft and 2.44 meters with the power given to the motors

public class Intake extends SubsystemBase{

    // Referencing motor controller object-
    private final SparkMax m_motor = new SparkMax(16, MotorType.kBrushless);
    private final SparkMax s_motor = new SparkMax(17, MotorType.kBrushless);
    //This command will run the intake of the robot. Then set to 0 speed when false.
    public Command runIntake(){
        return startEnd(()-> s_motor.set(-1), ()-> s_motor.set(0));

    }

    //methods that will run the motors.
    //Curerently shooter can shoot successfully from 8ft and 2.44 meters.

    public void runMotorForward(){
     m_motor.set(0.875);
    }


    public void stopMotor(){
        m_motor.set(0);
    }


    public void runMotorReverse(){
        m_motor.set(-0.875);
    }

    public void runIndexerForward(){
        s_motor.set(0.5);
    }

    public void stopIndexer(){
        s_motor.set(0);
    }

    public void runIndexerReverse(){
        s_motor.set(-0.5);
    }

}