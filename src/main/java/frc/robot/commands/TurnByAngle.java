package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.Command;

public class TurnByAngle extends Command {
    private final DriveSubsystem m_DriveSubsystem;
    private final double m_turnAngle;
    private final double m_startAngle;
    private final double m_endAngle;
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
        m_turnAngle = turnAngle; // + CCW, - CW
        m_startAngle = m_DriveSubsystem.getYaw();
        m_endAngle = m_startAngle + m_turnAngle;
        m_speed = (turnAngle > 0) ? 0.5 : -0.5;
        addRequirements(m_DriveSubsystem);
    }


  @Override
  public void initialize() {
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
    if (m_turnAngle > 0) {
        return m_DriveSubsystem.getYaw() >= m_endAngle;
    }
    else if (m_turnAngle < 0) {
        return m_DriveSubsystem.getYaw() <= m_endAngle;
    }
    else {
        return true; // no turning if angle is 0.
    }
  }

}

