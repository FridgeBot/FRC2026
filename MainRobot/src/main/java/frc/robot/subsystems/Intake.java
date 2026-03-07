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
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Commands.ExstendsIntake;

// 8ft and 2.44 meters with the power given to the motors

public class Intake extends SubsystemBase{

    // Referencing motor controller object-
    private final SparkMax ShooterMotor = new SparkMax(4, MotorType.kBrushless);
    private final SparkMax Indexer = new SparkMax(16, MotorType.kBrushless);
    private final SparkMax Intake = new SparkMax(14, MotorType.kBrushless);
    private final RelativeEncoder Shooter_encoder = ShooterMotor.getEncoder();
    private final RelativeEncoder Indexer_Encoder = Indexer.getEncoder();
    private final RelativeEncoder Intak_Encoder = Intake.getEncoder();
    private final Solenoid LExstendIntake_solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    private final Solenoid RExstendIntake_solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    private final Solenoid LDropIntake_Solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
    private final Solenoid RDropIntake_Solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 3);

    private final Compressor Compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    
    //This command will run the intake of the robot. Then set to 0 speed when false.
    
    


    @Override
    public void periodic() {
        SmartDashboard.putNumber("Current RPM", getShooter_motorSpeed());
    }
    
    



    //methods that will run the motors.
    //Curerently shooter can shoot successfully from 8ft and 2.44 meters.

    public double getShooter_motorSpeed(){
        return Shooter_encoder.getVelocity();
    }

    public double getS_motorSpeed(){
        return Intak_Encoder.getVelocity();
    }

    public void Shooter_motorSpeed(double speed){
        ShooterMotor.set(-speed);
    }

    public void Indexer_motorSpeed(double speed){
        Indexer.set(-speed);
    }

    public void MoveLExstendIntakeSolenoid(boolean on){
        
        LExstendIntake_solenoid.set(on);
    }

    public void MoveRExstendIntakeSolenoid(boolean on){

        RExstendIntake_solenoid.set(on);
    }

     public void MoveRDropIntakeSolenoid(boolean on){

        RDropIntake_Solenoid.set(on);
    }

     public void MoveLDropIntakeSolenoid(boolean on){

        LDropIntake_Solenoid.set(on);
    }

    public void Shooter_motorVoltage(Voltage voltage){
        ShooterMotor.setVoltage(voltage.times(-1));
    }

    public void Intake_motorSpeed(double speed){
        Intake.set(speed);
    }

    public double Intake_getSpeed(){
     return Intak_Encoder.getVelocity();   
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
              this::Shooter_motorVoltage,
              // Tell SysId how to record a frame of data for each motor on the mechanism being
              // characterized.
              log -> {
                // Record a frame for the shooter motor.
                log.motor("shooter-wheel")
                    .voltage(
                        m_appliedVoltage.mut_replace(ShooterMotor.getAppliedOutput() * ShooterMotor.getBusVoltage(), Volts))
                    .angularPosition(m_angle.mut_replace(Shooter_encoder.getPosition(), Rotations))
                    .angularVelocity(
                        m_velocity.mut_replace(Shooter_encoder.getVelocity(), RotationsPerSecond));
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