package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.units.measure.MutAngle;
import edu.wpi.first.units.measure.MutAngularVelocity;
import edu.wpi.first.units.measure.MutVoltage;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

// 8ft and 2.44 meters with the power given to the motors

public class Intake extends SubsystemBase{

    // Referencing motor controller object-
    private final SparkMax m_motor = new SparkMax(4, MotorType.kBrushless);
    private final SparkMax s_motor = new SparkMax(16, MotorType.kBrushless);
    private final RelativeEncoder m_encoder = m_motor.getEncoder();
    private final RelativeEncoder s_Encoder = s_motor.getEncoder();
    private final Solenoid L_solenoid = new Solenoid(PneumaticsModuleType.REVPH, 0);
    private final Solenoid R_solenoid = new Solenoid(PneumaticsModuleType.REVPH, 1);
    //This command will run the intake of the robot. Then set to 0 speed when false.
    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Current RPM", getM_motorSpeed());
    }
    
    

    //methods that will run the motors.
    //Curerently shooter can shoot successfully from 8ft and 2.44 meters.

    public double getM_motorSpeed(){
        return m_encoder.getVelocity();
    }

    public double getS_motorSpeed(){
        return s_Encoder.getVelocity();
    }

    public void m_motorSpeed(double speed){
        m_motor.set(-speed);
    }

    public void s_motorSpeed(double speed){
        s_motor.set(-speed);
    }

    public void MoveLSolenoid(boolean on){
        
        L_solenoid.set(on);
    }

    public void MoveRSolenoid(boolean on){

        R_solenoid.set(on);
    }

    public void m_motorVoltage(Voltage voltage){
        m_motor.setVoltage(voltage.times(-1));
    }


      // Mutable holder for unit-safe voltage values, persisted to avoid reallocation.
  private final MutVoltage m_appliedVoltage = Volts.mutable(0);
  // Mutable holder for unit-safe linear distance values, persisted to avoid reallocation.
  private final MutAngle m_angle = Radians.mutable(0);
  // Mutable holder for unit-safe linear velocity values, persisted to avoid reallocation.
  private final MutAngularVelocity m_velocity = RadiansPerSecond.mutable(0);

      // Create a new SysId routine for characterizing the shooter.
  private final SysIdRoutine m_sysIdRoutine =
      new SysIdRoutine(
          // Empty config defaults to 1 volt/second ramp rate and 7 volt step voltage.
          new SysIdRoutine.Config(),
          new SysIdRoutine.Mechanism(
              // Tell SysId how to plumb the driving voltage to the motor(s).
              this::m_motorVoltage,
              // Tell SysId how to record a frame of data for each motor on the mechanism being
              // characterized.
              log -> {
                // Record a frame for the shooter motor.
                log.motor("shooter-wheel")
                    .voltage(
                        m_appliedVoltage.mut_replace(m_motor.getAppliedOutput() * m_motor.getBusVoltage(), Volts))
                    .angularPosition(m_angle.mut_replace(m_encoder.getPosition(), Rotations))
                    .angularVelocity(
                        m_velocity.mut_replace(m_encoder.getVelocity(), RotationsPerSecond));
              },
              // Tell SysId to make generated commands require this subsystem, suffix test state in
              // WPILog with this subsystem's name ("shooter")
              this));

    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_sysIdRoutine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
        return m_sysIdRoutine.dynamic(direction);
    }
}