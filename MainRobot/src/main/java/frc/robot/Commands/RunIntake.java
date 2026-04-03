package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.submodule.subsystems.Intake;



public class RunIntake extends Command {

    private final Intake intakesub;
//runIntake constructor

    public RunIntake(Intake intakeSubsystem){
    
     intakesub = intakeSubsystem; 
    }
    
    @Override
    public void execute() {
        // intakesub.setShooter_motorSpeed(0.875);
            intakesub.setIndexer_motorSpeed(-0.8);
            intakesub.setIntake_motorSpeed(-0.8);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // intakesub.setShooter_motorSpeed(0);
        intakesub.setIndexer_motorSpeed(0);
        intakesub.setIntake_motorSpeed(0);
    }


}
