package frc.robot.command;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SubSystem.BracinSub;
import frc.robot.SubSystem.Drive;


public class AutonomousCommand extends Command {
  private double vel;

  private boolean finished,recuando = false;

  BracinSub braceta;

  Timer SensorTime = new Timer();


  private final double targetDistance = 1.0;

  
  Timer timer = new Timer();

  Timer recuoTimer = new Timer();
  private Drive driveSubsystem;
  public AutonomousCommand(Drive driveSubsystem, BracinSub braceta) {
    this.driveSubsystem = driveSubsystem;
    this.braceta = braceta;


    addRequirements(driveSubsystem,braceta);
  }

  public void setSpeedAuto(){
    driveSubsystem.m_leftDrive.set(ControlMode.PercentOutput, vel);
    driveSubsystem.m_leftDrive2.set(ControlMode.PercentOutput, vel);
    driveSubsystem.m_rightDrive.set(ControlMode.PercentOutput, vel);
    driveSubsystem.m_rightDrive2.set(ControlMode.PercentOutput, vel);
  }

  public void mexe() {
    vel = Constants.autonomousLoc;
    setSpeedAuto();
} 

private void stopDrive() {
    vel = 0;
    setSpeedAuto();

    System.out.println("STOP DRIVE CALLED!");
}
  
  @Override
  public void initialize() {
    timer.start();
    driveSubsystem.reqDrive();
    driveSubsystem.resetEncoders();
  }


  public void execute() {
    double distance = driveSubsystem.getDistance();
    if (distance < targetDistance){
      mexe();
    } else { 
      stopDrive();
      finished = true;
  }

  SmartDashboard.putNumber("distancia encoder: ", distance);
  SmartDashboard.putNumber("timer", timer.get());
  SmartDashboard.putNumber("speed", vel);
  SmartDashboard.putBoolean("Auto Finished", finished);
}
  @Override
  public void end(boolean interrupted) {
    stopDrive();
  }

  @Override
  public boolean isFinished() {
    if (timer.get() >= Constants.autonomousTime){
      finished = true;
    }
  SmartDashboard.putBoolean("Auto Finished", finished);
  return finished;
  }
}
