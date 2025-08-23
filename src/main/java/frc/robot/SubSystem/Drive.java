package frc.robot.SubSystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

  public Encoder encodin = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
  
  // Motor controllers
 public final VictorSPX m_leftDrive = new VictorSPX(Constants.LMot);
 public final VictorSPX m_rightDrive = new VictorSPX(Constants.RMot);
 public final VictorSPX m_rightDrive2 = new VictorSPX(Constants.RMot2);
 public final VictorSPX m_leftDrive2 = new VictorSPX(Constants.LMot2);
 


public Drive() {
  reqDrive();
  }

  @Override
  public void periodic() {
    getDistance();
    System.out.println("Distance: " + encodin.getDistance());
    System.out.println("Pulsos " + encodin.getRaw());
  }

  public void getDistance(){
   encodin.getDistance();
  } 

  public void reqDrive(){


      double diametroRoda = 0.06; // 6 cm
      double distancePerPulse = (diametroRoda);
      encodin.setMinRate(5);
      encodin.setDistancePerPulse(distancePerPulse);
    

  m_rightDrive.setInverted(true);
  m_rightDrive2.setInverted(true);
  m_leftDrive.setInverted(false);
  m_leftDrive2.setInverted(false);

  m_leftDrive.configNeutralDeadband(Constants.deadZone);
  m_leftDrive2.configNeutralDeadband(Constants.deadZone);
  m_rightDrive.configNeutralDeadband(Constants.deadZone);
  m_rightDrive2.configNeutralDeadband(Constants.deadZone);
 
  m_rightDrive.setNeutralMode(NeutralMode.Brake);
  m_rightDrive2.setNeutralMode(NeutralMode.Brake);
  m_leftDrive.setNeutralMode(NeutralMode.Brake);
  m_leftDrive2.setNeutralMode(NeutralMode.Brake);

  }
}
