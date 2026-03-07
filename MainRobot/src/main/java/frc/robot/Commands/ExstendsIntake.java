package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

// DO NOT MOVE WHAT IS ABOVE!! >:|

public class ExstendsIntake extends Command {

    Intake subsystem;

    public ExstendsIntake(Intake subsystem){

        this.subsystem = subsystem;

    }

    @Override
    public void execute() {
        subsystem.MoveLExstendIntakeSolenoid(true);
        // subsystem.MoveRExstendIntakeSolenoid(true);
    }

    @Override
    public void end(boolean interrupted) {

        subsystem.MoveLExstendIntakeSolenoid(false);
        // subsystem.MoveRExstendIntakeSolenoid(false);

    }


    
}