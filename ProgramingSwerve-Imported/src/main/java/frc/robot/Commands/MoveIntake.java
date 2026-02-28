package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

// DO NOT MOVE WHAT IS ABOVE!! >:|

public class MoveIntake extends Command {

    boolean forward;
    Intake subsystem;

    public MoveIntake(boolean forward ,Intake subsystem){

        this.forward = forward;
        this.subsystem = subsystem;

    }

    @Override
    public void execute() {
        if(forward == true){
         subsystem.intake_mover(0.25);
        }
        else{
          subsystem.intake_mover(-0.25);
        }
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.intake_mover(0);
    }


    
}