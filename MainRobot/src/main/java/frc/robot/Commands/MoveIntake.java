package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

// DO NOT MOVE WHAT IS ABOVE!! >:|

public class MoveIntake extends Command {

    Intake subsystem;

    public MoveIntake(Intake subsystem){

        this.subsystem = subsystem;

    }

    @Override
    public void execute() {
        subsystem.MoveLSolenoid(true);
        subsystem.MoveRSolenoid(true);
    }

    @Override
    public void end(boolean interrupted) {

        subsystem.MoveLSolenoid(false);
        subsystem.MoveRSolenoid(false);

    }


    
}