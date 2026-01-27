package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;



public class RunIntake extends Command {

    private final Intake intakesub;
//runIntake constructor

    public RunIntake(Intake intakeSubsystem){
    
     intakesub = intakeSubsystem; 
    }
    @Override
    public void execute() {
        intakesub.m_motorSpeed(0.875);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intakesub.m_motorSpeed(0);
    }


}
