package frc.robot.Commands;
import static edu.wpi.first.units.Units.Volt;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.VoltageUnit;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.submodule.subsystems.Intake;
import frc.robot.submodule.subsystems.Intake.MotorState;

public class ShooterMech extends Command{

private final Intake subSystem;

public ShooterMech(Intake shooterSubsystem){
    subSystem = shooterSubsystem;

    // addRequirements(subSystem);
}

@Override
public void execute(){
   if(subSystem.Exstended_SolenoidState()){
   
    subSystem.setShooter_motorVoltage(Voltage.ofBaseUnits(-7,Volt));
    subSystem.setIntake_motorSpeed(MotorState.forward);

    if(Math.abs(subSystem.getShooter_motorSpeed()) > 3350){
        
        subSystem.setIndexer_motorSpeed(MotorState.forward);
        subSystem.setFeedShooter_motorSpeed(MotorState.forward);
        
        

    }
    }

    else{

    subSystem.setShooter_motorSpeed(MotorState.off);
    subSystem.setIndexer_motorSpeed(MotorState.off);
    subSystem.setFeedShooter_motorSpeed(MotorState.off);
    subSystem.setIntake_motorSpeed(MotorState.off);

    }



}
@Override
public void end(boolean interrupted){
    subSystem.setShooter_motorSpeed(MotorState.off);
    subSystem.setIndexer_motorSpeed(MotorState.off);
    subSystem.setFeedShooter_motorSpeed(MotorState.off);
}


    
}
