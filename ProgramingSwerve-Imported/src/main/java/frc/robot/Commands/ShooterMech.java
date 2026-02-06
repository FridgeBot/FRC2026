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
    //This will control the shooter motor line 17**
    subSystem.m_motorSpeed(0.6);
//When the condition of the shooter motor reaches a 
//certain limit, then this motor will be activated. 
    if(subSystem.getM_motorSpeed() > 3200){
        
        subSystem.s_motorSpeed(-0.85);

    }
}

@Override
public void end(boolean interrupted){
    subSystem.m_motorSpeed(0);
    subSystem.s_motorSpeed(0);
}


    
}
