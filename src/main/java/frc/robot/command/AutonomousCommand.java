// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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

  Encoder encodin = new Encoder(1, 2, false, Encoder.EncodingType.k4X);

  private final double targetDistance = 1.0; 
  
  Timer timer = new Timer();

  Timer recuoTimer = new Timer();
  private Drive driveSubsystem;
  public AutonomousCommand(Drive driveSubsystem, BracinSub braceta) {
    this.driveSubsystem = driveSubsystem;
    this.braceta = braceta;


    addRequirements(driveSubsystem,braceta);

    // Configuração do encoder
    double diametroRoda = 0.06; // 6 cm
    double distancePerPulse = (Math.PI * diametroRoda) / 2048;
    encodin.setMinRate(5);
    encodin.setDistancePerPulse(distancePerPulse);
    encodin.reset();
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
   System.out.println("distance " + distance);
   System.out.println("Pulsos " + encodin.getRaw());
   System.out.println("Ticks " + encodin.get());
   System.out.println("distance Direta " + encodin.getDistance());
   System.out.println("Pulsos Diretos " + encodin.getRaw());
   SmartDashboard.putNumber("Encoder Raw", encodin.getRaw());
   
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
