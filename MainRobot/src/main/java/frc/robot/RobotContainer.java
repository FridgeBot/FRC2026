// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.lang.ModuleLayer.Controller;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Commands.AlineShooter;
import frc.robot.Commands.IntakeWithVelocityControl;
import frc.robot.Commands.Rotate;
import frc.robot.Commands.ShootWithVelocityControl;
import frc.robot.Commands.ShooterMech;
import frc.robot.submodule.generated.TunerConstants;
import frc.robot.submodule.subCommands.ExstendIntake;
import frc.robot.submodule.subCommands.RunIntake;
import frc.robot.submodule.subsystems.CommandSwerveDrivetrain;
import frc.robot.submodule.subsystems.Intake;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
//import for the sendable chooser system
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;



public class RobotContainer {

    //New Intake object called using the intake class.

    private final Intake intakeSubsystem = new Intake();



    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandJoystick joystick = new CommandJoystick(0);

    private final CommandXboxController Operator = new CommandXboxController(1);

     private final SendableChooser<Command> autoChooser;
    


    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();



   

    public RobotContainer() {
        configureBindings();

          // Build an auto chooser. This will use Commands.none() as the default option.

        NamedCommands.registerCommand("ShooterMech", new ShooterMech(intakeSubsystem));
        NamedCommands.registerCommand("RunIntake", new RunIntake(intakeSubsystem));
        NamedCommands.registerCommand("Intake Out", intakeSubsystem.IntakePrep());

    //Does say there is an issue however build is successful.
    //If any further analysis further comes for the issue, I "fixed" the issue using an import on lines 30. - CS
    autoChooser = AutoBuilder.buildAutoChooser();
    // Another option that allows you to specify the default auto by its name
    // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

    SmartDashboard.putData("Auto Chooser", autoChooser);


  
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getRawAxis(1) * MaxSpeed) // Drive forward with negative Y (forward)
                        
                .withVelocityY(-joystick.getRawAxis(0) * MaxSpeed) // Drive left with negative X (left)        joystick.getRawAxis(0) * MaxSpeed
                    .withRotationalRate(-joystick.getRawAxis(5) * MaxAngularRate) // Drive counterclockwise with negative X (left)      -joystick.getRawAxis(5) * MaxAngularRate
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


        // Reset the field-centric heading on left bumper press.
        joystick.button(10).onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    
        //When button 2 is pressed, run the intake--

        joystick.button(15).whileTrue(new RunIntake(intakeSubsystem));
        joystick.button(2).whileTrue(new ShooterMech(intakeSubsystem));
        joystick.button(5).toggleOnTrue(new Rotate(drivetrain, joystick, MaxSpeed, MaxAngularRate));

        // Operator.y().toggleOnTrue(new ExstendIntake(intakeSubsystem));
        Operator.rightBumper().whileTrue(intakeSubsystem.OutAndDrop());
        Operator.leftBumper().whileTrue(intakeSubsystem.UpAndIn());
        Operator.x().toggleOnTrue(intakeSubsystem.IntakeOnly());
        Operator.b().whileTrue(intakeSubsystem.intakeOut());
        // Operator.start().toggleOnTrue(new IntakeWithVelocityControl(intakeSubsystem));
        Operator.start().whileTrue(intakeSubsystem.TESTMOTOR());
        Operator.a().whileTrue(intakeSubsystem.FULL_INTAKE());
        }


    public void HandleDisable(){
        intakeSubsystem.DisableSolenoids();
    }
//get info from the dashboard and sets that data to the auton init function ----

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
  }


  
}
