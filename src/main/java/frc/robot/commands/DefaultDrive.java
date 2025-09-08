// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Constants.DriveMode;

import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.DoubleSupplier;

//import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;


/**
 * A command to drive the robot with joystick input (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes - actual code should inline a command this simple with {@link
 * edu.wpi.first.wpilibj2.command.RunCommand}.
 */
public class DefaultDrive extends Command {
  private final DriveSubsystem m_driveSubsystem;
  private final DoubleSupplier m_leftY;
  private final DoubleSupplier m_rightY;
  private final DoubleSupplier m_rightX;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param leftY left joystick Y value (used by both drive modes)
   * @param rightY right joystick Y value (for tank drive)
   * @param rightX right joystick X value (for split-stick arcade)
   */
  public DefaultDrive(DriveSubsystem subsystem, 
                      DoubleSupplier leftY,
                      DoubleSupplier rightY,
                      DoubleSupplier rightX) {
    m_driveSubsystem = subsystem;
    m_leftY = leftY;
    m_rightY = rightY;
    m_rightX = rightX;
    addRequirements(m_driveSubsystem);
  }

  /** 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
  */

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // the following article explains the sign conventions used for joystick and 
    // robot rotation, and why minus signs are required
    // link: https://docs.wpilib.org/en/latest/docs/software/basic-programming/coordinate-system.html  
    if (m_driveSubsystem.getSelectedDriveMode() == DriveMode.TANK) {
        m_driveSubsystem.tankDrive(-m_leftY.getAsDouble(), -m_rightY.getAsDouble());
    } else {
        m_driveSubsystem.arcadeDrive(-m_leftY.getAsDouble(), -m_rightX.getAsDouble());
    }
  }
 
/**
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  */

} // end DefaultDrive