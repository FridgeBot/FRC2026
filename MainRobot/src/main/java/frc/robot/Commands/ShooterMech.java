package frc.robot.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Intake;

public class ShooterMech extends Command{

private final Intake subSystem;

public ShooterMech(Intake shooterSubsystem){
    subSystem = shooterSubsystem;

}

@Override
public void execute(){
   if(subSystem.Exstended_SolenoidState()){
   
    subSystem.Shooter_motorSpeed(0.75);

    if(Math.abs(subSystem.getShooter_motorSpeed()) > 4000){
        
        subSystem.Indexer_motorSpeed(0.3);
        subSystem.Intake_motorSpeed(-0.3);
        
        

    }
    }

    else{

    subSystem.Shooter_motorSpeed(0);
    subSystem.Indexer_motorSpeed(0);
    subSystem.Intake_motorSpeed(0);

    }



}
@Override
public void end(boolean interrupted){
    subSystem.Shooter_motorSpeed(0);
    subSystem.Indexer_motorSpeed(0);
    subSystem.Intake_motorSpeed(0);
}


    
}
