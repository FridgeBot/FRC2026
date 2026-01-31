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
    subSystem.m_motorSpeed(0.875);

    if(subSystem.getM_motorSpeed() > 4000){
        
        subSystem.s_motorSpeed(-0.5);

    }
}

@Override
public void end(boolean interrupted){
    subSystem.m_motorSpeed(0);
    subSystem.s_motorSpeed(0);
}


    
}
