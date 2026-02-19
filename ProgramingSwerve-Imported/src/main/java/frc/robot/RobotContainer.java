// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Commands.RunIntake;
import frc.robot.Commands.ShootWithVelocityControl;
import frc.robot.Commands.ShooterMech;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake;

//import for the sendable chooser system

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.pathplanner.lib.auto.AutoBuilder;

public class RobotContainer {
        private Rotation2d Rotation = Rotation2d.kZero;
        private final Intake intakeSubsystem = new Intake();

    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentricFacingAngle driveAngle = new SwerveRequest.FieldCentricFacingAngle()
            .withHeadingPID(0.2,0.1,0) 
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final SendableChooser<Command> autoChooser;
    

    private final CommandJoystick joystick = new CommandJoystick(0);
    private final CommandXboxController xboxController = new CommandXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {
        configureBindings();

        // Subsystem initialization
        autoChooser = AutoBuilder.buildAutoChooser();
    // Another option that allows you to specify the default auto by its name
    // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

    SmartDashboard.putData("Auto Chooser", autoChooser);




        // Register Named Commands
        NamedCommands.registerCommand("Shoot", new ShooterMech(intakeSubsystem));
        NamedCommands.registerCommand("Intake", new RunIntake(intakeSubsystem));

        // Do all other initialization
        

        // ...
    }





    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() -> 
                // {
                    // Rotation = Rotation.plus(Rotation2d.fromDegrees(joystick.getRawAxis(5)));
                   drive.withVelocityX(-joystick.getRawAxis(1) * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getRawAxis(0) * MaxSpeed) // Drive left with negative X (left)  :joystick.getRawAxis(0) * MaxSpeed
                    .withRotationalRate(0*-joystick.getRawAxis(5) * MaxAngularRate) // Drive counterclockwise with negative X (left)   -joystick.getRawAxis(5)
                    // .withTargetDirection(Rotation);
                // }
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.button(99).whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.button(98).whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getY(), -joystick.getX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.button(11).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));//(Dy 11)
        joystick.button(12).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));//Dy12
        joystick.button(13).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));//(Qt13)
        joystick.button(14).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));//(Qt14)

        // reset the field-centric heading on left bumper press
        joystick.button(10).onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);

        joystick.button(4).whileTrue(new RunIntake(intakeSubsystem));
        joystick.button(3).whileTrue(new ShooterMech(intakeSubsystem));

        xboxController.rightBumper().whileTrue(new ShootWithVelocityControl(intakeSubsystem));
        xboxController.a().and(xboxController.povUp()).whileTrue(intakeSubsystem.sysIdDynamic(Direction.kForward));
        xboxController.a().and(xboxController.povDown()).whileTrue(intakeSubsystem.sysIdDynamic(Direction.kReverse));
        xboxController.a().and(xboxController.povLeft()).whileTrue(intakeSubsystem.sysIdQuasistatic(Direction.kForward));
        xboxController.a().and(xboxController.povRight()).whileTrue(intakeSubsystem.sysIdQuasistatic(Direction.kReverse));

    }

     public Command getAutonomousCommand() {
        return autoChooser.getSelected();
  }
}
