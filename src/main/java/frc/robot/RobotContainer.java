package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.SubSystem.Drive;
import frc.robot.SubSystem.Encodin;
import frc.robot.SubSystem.BracinSub;
import frc.robot.command.AutonomousCommand;
import frc.robot.command.Loc;
import frc.robot.command.PidCommand;

public class RobotContainer {

  // Subsystems
  private final Drive driveSubsystem = new Drive();

  // Input
  public final Joystick joyDeliciu = new Joystick(Constants.joy);

  // Commands
  private final Loc locCommand;
  
  private final AutonomousCommand auto;

  private final PidCommand Pdiddy;
  
  private final BracinSub baby = new BracinSub();

  private final Encodin encodin = new Encodin();



  public RobotContainer() {

    CommandScheduler.getInstance().registerSubsystem(encodin);

    Pdiddy = new PidCommand(baby,joyDeliciu);

    CommandScheduler.getInstance().registerSubsystem(driveSubsystem);

    // Initialize Loc command with drive subsystem and joystick
    locCommand = new Loc(driveSubsystem,joyDeliciu);

    auto = new AutonomousCommand(driveSubsystem,baby,encodin);

    // Set default command
    driveSubsystem.setDefaultCommand(locCommand);

    baby.setDefaultCommand(Pdiddy);

  }

  public Command getAutonomousCommand(){
      return auto;
  
  }

  public Encodin getEncodin() {
    return encodin;
  }

  public Command getBracinCommand(){
    return Pdiddy;
  }

}
