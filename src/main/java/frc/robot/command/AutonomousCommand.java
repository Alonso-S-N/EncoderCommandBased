// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SubSystem.BracinSub;
import frc.robot.SubSystem.Drive;
import frc.robot.SubSystem.Encodin; 

public class AutonomousCommand extends Command {
  private double vel;

  private boolean finished,recuando = false;

  BracinSub braceta;

  Timer SensorTime = new Timer();

  private Encodin encodin;

  private final double targetDistance = 1.0; 
  
  Timer timer = new Timer();

  Timer recuoTimer = new Timer();
  private Drive driveSubsystem;
  public AutonomousCommand(Drive driveSubsystem, BracinSub braceta,Encodin encodin) {
    this.driveSubsystem = driveSubsystem;
    this.braceta = braceta;
    this.encodin = encodin;

    addRequirements(driveSubsystem,braceta, encodin);
  }

  public void mexe() {
    vel = Constants.autonomousLoc;

    driveSubsystem.m_leftDrive.set(ControlMode.PercentOutput, vel);
    driveSubsystem.m_leftDrive2.set(ControlMode.PercentOutput, vel);
    driveSubsystem.m_rightDrive.set(ControlMode.PercentOutput, vel);
    driveSubsystem.m_rightDrive2.set(ControlMode.PercentOutput, vel);
} 

private void stopDrive() {
    vel = 0;
    driveSubsystem.m_leftDrive.set(ControlMode.PercentOutput, 0);
    driveSubsystem.m_leftDrive2.set(ControlMode.PercentOutput, 0);
    driveSubsystem.m_rightDrive.set(ControlMode.PercentOutput, 0);
    driveSubsystem.m_rightDrive2.set(ControlMode.PercentOutput, 0);
    SensorTime.reset();

    System.out.println("STOP DRIVE CALLED!");
}
  
  @Override
  public void initialize() {
    timer.start();
  }


  public void execute() {
    double distance = encodin.getDistance();
    if (distance < targetDistance){
      mexe();
    } else { 
      stopDrive();
      finished = true;
  }
   SmartDashboard.putNumber("Encoder Distance", distance);
   
}
  @Override
  public void end(boolean interrupted) {
    stopDrive();
  }

  @Override
  public boolean isFinished() {
  SmartDashboard.putBoolean("Auto Finished", finished);
  return finished;
  }
}
