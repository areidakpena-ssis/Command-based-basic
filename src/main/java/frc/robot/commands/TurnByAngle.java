package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.Command;

public class TurnByAngle extends Command {
    private final DriveSubsystem m_DriveSubsystem;
    private final double m_deltaYaw; // change in yaw; negative is ccw - opposite of robot coord system
    private final double m_speed;


    /**
     * Create a new TurnByAngle
     * 
     * @param angle The angle in degrees (+ CCW) which to turn. Note that the 
     * pigeon IMU's Yaw angles overflow at +/- 23040 degrees, which has not 
     * been accounted for in this code. 
     * https://ctre.download/files/user-manual/Pigeon%20IMU%20User%27s%20Guide.pdf
     */
    public TurnByAngle(double turnAngle, DriveSubsystem drive) {
        m_DriveSubsystem = drive;
        m_deltaYaw = -turnAngle; // yaw angle sign opposite to robot z-axis
        // positive turn angle indicates ccw rotation
        // positive speed indicates positive turning direction around z-axis
        m_speed = (turnAngle > 0) ? 0.5 : -0.5;
        addRequirements(m_DriveSubsystem);
    }


  @Override
  public void initialize() {
      m_DriveSubsystem.setYaw(0.0); //zero the yaw for consistency
  }

  @Override
  public void execute() {
    // later can improve this with PID
    m_DriveSubsystem.arcadeDrive(0, m_speed);
  }

  @Override
  public void end(boolean interrupted) {
    m_DriveSubsystem.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(m_DriveSubsystem.getYaw()) >= Math.abs(m_deltaYaw); // keep it simple
  }
}

