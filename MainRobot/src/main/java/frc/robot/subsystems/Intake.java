package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase{

    // Referencing motor controller object-
    private final SparkMax m_motor = new SparkMax(16, MotorType.kBrushless);

    public Command runIntake(){
        return startEnd(()-> m_motor.set(0.3), ()-> m_motor.set(0));

    }



    public void runMotorForward(){
     m_motor.set(0.3);
    }


    public void stopMotor(){
        m_motor.set(0);
    }


    public void runMotorReverse(){
        m_motor.set(-0.3);
    }


}