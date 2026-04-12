package frc.robot.Commands;

import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.submodule.subsystems.Intake;
import frc.robot.submodule.subsystems.Intake.MotorState;

public class IntakeWithVelocityControl extends Command {
    private Intake intake;
    private double currentRPM = 0;

    private SimpleMotorFeedforward feedforwardCalculator = new SimpleMotorFeedforward(0, 0, 0);
    private double feedForwardValue = 0;

    private PIDController pidCalculator = new PIDController(0, 0, 0);
    private double pidValue = 0;


    private double targetRPM = 0;//3000
    private double ks = 0;//0.15
    private double kv = 0;//0.002007
    private double ka = 0;//0
    private double p = 0;//0.005
    private double i = 0;//0
    private double d = 0;//0.0007
    private double marginOfError = 100;

/* Ks 0.125 just a hair higher stops at some points
 * 
 * 
 * 
*/


    public IntakeWithVelocityControl(Intake intakeSystem) {
        intake = intakeSystem;
        addRequirements(intake);
        Preferences.initDouble("Intake Target RPM", targetRPM);
        Preferences.initDouble("Intake ks", ks);
        Preferences.initDouble("Intake kv", kv);
        Preferences.initDouble("Intake ka", ka);
        Preferences.initDouble("Intake p", p);
        Preferences.initDouble("Intake i", i);
        Preferences.initDouble("Intake d", d);
        Preferences.initDouble("Intake Margin of Error", marginOfError);
    }

    @Override
    public void initialize() {
        targetRPM = Preferences.getDouble("Target RPM", targetRPM);
        ks = Preferences.getDouble("Intake ks", ks);
        kv = Preferences.getDouble("Intake kv", kv);
        ka = Preferences.getDouble("Intake ka", ka);
        p = Preferences.getDouble("Intake p", p);
        i = Preferences.getDouble("Intake i", i);
        d = Preferences.getDouble("Intake d", d);
        marginOfError = Preferences.getDouble("Margin of Error", marginOfError);

        feedforwardCalculator.setKs(ks);
        feedforwardCalculator.setKv(kv);
        feedforwardCalculator.setKa(ka);

        pidCalculator.setPID(p, i, d);
    }

    @Override
    public void execute() {
        currentRPM = intake.getIntake_Speed();
        feedForwardValue = feedforwardCalculator.calculate(targetRPM);
        pidValue = pidCalculator.calculate(currentRPM, targetRPM);

        intake.SetIntake_Voltage(feedForwardValue + pidValue);

        
        SmartDashboard.putNumber("Intake Speed", targetRPM);
    }

    @Override
    public void end(boolean interrupted) {
        intake.setIntake_motorSpeed(MotorState.off);
    }
    
}
