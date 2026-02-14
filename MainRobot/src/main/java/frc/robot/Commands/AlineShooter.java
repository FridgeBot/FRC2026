package frc.robot.Commands;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;

import java.util.concurrent.locks.Lock;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;


public class AlineShooter extends Command {
    
    private double MaxSpeed;
    private double MaxAngularRate;

    private final Pose2d tarPose2d;
    private final CommandJoystick DriveStick;
    private final CommandSwerveDrivetrain drivetrain;


    public AlineShooter(Pose2d targetPosition, CommandSwerveDrivetrain driveTrain,CommandJoystick joystick, double maxSpeed, double maxAngularRate){
        tarPose2d = targetPosition;
        DriveStick = joystick;
        MaxSpeed = maxSpeed;
        MaxAngularRate = maxAngularRate;
        drivetrain = driveTrain;
    }

    SwerveRequest.FieldCentric Lock = new SwerveRequest.FieldCentric()
    .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
    .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    
        @Override
        public void execute() {
            // TODO Auto-generated method stub
            super.execute();
        

            drivetrain.setControl(
            Lock.withVelocityX(DriveStick.getRawAxis(1) * MaxSpeed)       
                .withVelocityY(DriveStick.getRawAxis(0) * MaxSpeed)
                    .withRotationalRate(0) 
        );
        }

}
