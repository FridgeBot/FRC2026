package frc.robot.Commands;

import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class ShootWithVelocityControl extends Command {
    private Intake intake;
    private double currentRPM = 0;

    private SimpleMotorFeedforward feedforwardCalculator = new SimpleMotorFeedforward(0, 0, 0);
    private double feedForwardValue = 0;

    private PIDController pidCalculator = new PIDController(0, 0, 0);
    private double pidValue = 0;


    private double targetRPM = 0;
    private double ks = 0;
    private double kv = 0;
    private double ka = 0;
    private double p = 0;
    private double i = 0;
    private double d = 0;
    private double marginOfError = 100;

    public ShootWithVelocityControl(Intake intakeSystem) {
        intake = intakeSystem;
        addRequirements(intake);
        Preferences.initDouble("Target RPM", targetRPM);
        Preferences.initDouble("ks", ks);
        Preferences.initDouble("kv", kv);
        Preferences.initDouble("ka", ka);
        Preferences.initDouble("p", p);
        Preferences.initDouble("i", i);
        Preferences.initDouble("d", d);
        Preferences.initDouble("Margin of Error", marginOfError);
    }

    @Override
    public void initialize() {
        targetRPM = Preferences.getDouble("Target RPM", targetRPM);
        ks = Preferences.getDouble("ks", ks);
        kv = Preferences.getDouble("kv", kv);
        ka = Preferences.getDouble("ka", ka);
        p = Preferences.getDouble("p", p);
        i = Preferences.getDouble("i", i);
        d = Preferences.getDouble("d", d);
        marginOfError = Preferences.getDouble("Margin of Error", marginOfError);

        feedforwardCalculator.setKs(ks);
        feedforwardCalculator.setKv(kv);
        feedforwardCalculator.setKa(ka);

        pidCalculator.setPID(p, i, d);
    }

    @Override
    public void execute() {
        currentRPM = intake.getM_motorSpeed();
        feedForwardValue = feedforwardCalculator.calculate(targetRPM);
        pidValue = pidCalculator.calculate(currentRPM, targetRPM);

        intake.m_motorVoltage(Volts.of(feedForwardValue + pidValue));

        if(Math.abs(intake.getM_motorSpeed() - targetRPM) <= marginOfError && targetRPM > 0){
            intake.s_motorSpeed(-0.85);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.m_motorVoltage(Volts.zero());
        intake.s_motorSpeed(0);
    }
    
}
