// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigenvalue_DDRM;

//import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.simulation.EncoderSim;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.Constants.DriveConstants; // our constants
import frc.robot.Constants.DriveMode;

// in this program we can select from two drive modes,
// using standard left-and-right joystick controls for each
/** 
enum DriveMode {
  TANK, 
  SPLIT_ARCADE
}
  */

public class DriveSubsystem extends SubsystemBase {

  // motors on left side and right side
  private final WPI_TalonSRX m_leftLeader = new WPI_TalonSRX(DriveConstants.kLeftMotor1Port);
  private final WPI_VictorSPX m_leftFollower = new WPI_VictorSPX(DriveConstants.kLeftMotor2Port);
  private final WPI_TalonSRX m_rightLeader = new WPI_TalonSRX(DriveConstants.kRightMotor1Port);
  private final WPI_TalonSRX m_rightFollower = new WPI_TalonSRX(DriveConstants.kRightMotor2Port);

  private final PigeonIMU m_pidgey = new PigeonIMU(m_rightFollower);


  private final DifferentialDrive m_drive =
    new DifferentialDrive(m_leftLeader::set, m_rightLeader::set);
    // might need to invert motor before passing


  private DriveMode m_selectedDriveMode = DriveMode.TANK; // default drive control mode

  // left side drive encoder
  private final Encoder m_leftEncoder = 
    new Encoder(
        DriveConstants.kLeftEncoderPorts[0],
        DriveConstants.kLeftEncoderPorts[1],
        DriveConstants.kLeftEncoderReversed);


  private final Encoder m_rightEncoder = 
    new Encoder(
        DriveConstants.kRightEncoderPorts[0],
        DriveConstants.kRightEncoderPorts[1],
        DriveConstants.kRightEncoderReversed);

    
  /** Creates a new ExampleSubsystem. */
  public DriveSubsystem() {
    SendableRegistry.addChild(m_drive, m_leftLeader);
    SendableRegistry.addChild(m_drive, m_rightLeader);

    m_leftFollower.follow(m_leftLeader);
    m_leftLeader.setInverted(true);
    m_leftFollower.setInverted(InvertType.FollowMaster);

    m_rightFollower.follow(m_rightLeader);
    m_rightLeader.setInverted(false);
    m_rightFollower.setInverted(InvertType.FollowMaster);
  }


  /**
   * Drive the robot using arcade controls
   * 
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  /**
   * Drive the robot using tank drive controls
   * 
   */
  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_drive.tankDrive(leftSpeed, rightSpeed);
  }

  /** Resets the drive encoders to currently read a position of 0 */
  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  /**
   * Get the average distance of the TWO encoders.
   * 
   * @return average distance of TWO encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
  }

  /**
   * Sets the maximum output of the drive. Useful for scaling the drive
   * to drive more slowly. 
   * 
   * @param maxOutput the maximum output as a decimal from 0.0 to 1.0
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /**
   * Make selected drive mode accessible to outside
   */
  public DriveMode getSelectedDriveMode() {
    return m_selectedDriveMode;
  }
 
  /**
   * Switch the selected drive mode.
   */
  public void switchSelectedDriveMode() {
    m_selectedDriveMode = (m_selectedDriveMode == DriveMode.TANK) ? DriveMode.SPLIT_ARCADE : DriveMode.TANK;
  }

  /** Get Yaw from Pigeon IMU
   * 
   */
  public double getYaw() {
    return m_pidgey.getYaw();
  }

  /*
    alter some comments
    @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);
    // Publish encoder distances to telemetry.
    builder.addDoubleProperty("leftDistance", m_leftEncoder::getDistance, null);
    builder.addDoubleProperty("rightDistance", m_rightEncoder::getDistance, null);
  }
  */
}
