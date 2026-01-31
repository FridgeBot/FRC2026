package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// 8ft and 2.44 meters with the power given to the motors

public class Intake extends SubsystemBase{

    // Referencing motor controller object-
    private final SparkMax m_motor = new SparkMax(16, MotorType.kBrushless);
    private final SparkMax s_motor = new SparkMax(17, MotorType.kBrushless);
    private final RelativeEncoder m_encoder = m_motor.getEncoder();
    private final RelativeEncoder s_Encoder = s_motor.getEncoder();
    //This command will run the intake of the robot. Then set to 0 speed when false.
    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("RPM", getM_motorSpeed());
    }
    
    

    //methods that will run the motors.
    //Curerently shooter can shoot successfully from 8ft and 2.44 meters.

    public double getM_motorSpeed(){
        return m_encoder.getVelocity();
    }

    public double getS_motorSpeed(){
        return s_Encoder.getVelocity();
    }

   public void m_motorSpeed(double speed){
    m_motor.set(speed);
   }

   public void s_motorSpeed(double speed){
    s_motor.set(speed);
   }
}