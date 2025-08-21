package frc.robot.SubSystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Encodin extends SubsystemBase {
    DutyCycleEncoder EncoderFD = new DutyCycleEncoder(0);
    double diametroRoda = 0.06; 
    double distancePerPulse;

    public Encodin() {

        distancePerPulse = (Math.PI * diametroRoda ) / 8192;
    }

    public double getDistance() {   
        double dutyCycle = EncoderFD.get(); // Obtém o duty cycle (0 a 1)
        return dutyCycle * distancePerPulse;
    }

    public void reset() {
     
    }

    @Override
    public void periodic() {
        // Exibe no SmartDashboard
        SmartDashboard.putNumber("EncoderFD Duty Cycle", EncoderFD.get());
        SmartDashboard.putNumber("Distância", getDistance());
        System.out.println("Distância: " + getDistance());
    }
}