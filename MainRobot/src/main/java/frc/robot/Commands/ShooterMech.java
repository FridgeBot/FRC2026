package frc.robot.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class ShooterMech extends Command{

private final Intake subSystem;

public ShooterMech(Intake shooterSubsystem){
    subSystem = shooterSubsystem;

}

@Override
public void execute(){
    subSystem.Shooter_motorSpeed(0.5);

    if(subSystem.getShooter_motorSpeed() > 2000){
        
        subSystem.Indexer_motorSpeed(-0.5);
        subSystem.Intake_motorSpeed(0.5);
        

    }
}

@Override
public void end(boolean interrupted){
    subSystem.Shooter_motorSpeed(0);
    subSystem.Indexer_motorSpeed(0);
    subSystem.Intake_motorSpeed(0);
}


    
}
