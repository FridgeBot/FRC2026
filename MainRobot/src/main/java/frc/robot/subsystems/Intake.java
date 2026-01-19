package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase{

    // Referencing motor controller object-
    private final Spark m_motor = new Spark(0);

    public Command runIntake(){
        return startEnd(()-> m_motor.set(1), ()-> m_motor.set(0));

    }









}