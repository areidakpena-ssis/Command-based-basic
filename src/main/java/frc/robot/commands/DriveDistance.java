package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveDistance extends Command {
    private final DriveSubsystem m_drive;
    private final double m_distance;
    private final double m_speed;

    /**
     * Creates a new DriveDistance command.
     * @param inches - distance to drive in inches
     * @param speed - speed at which robot will drive
     * @param drive - drive subsystem
     */
    public DriveDistance(double inches, double speed, DriveSubsystem drive) {
        m_drive = drive;
        m_distance = inches;
        m_speed = speed;
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        m_drive.resetEncoders();
        m_drive.arcadeDrive(m_speed, 0);
    }

    @Override
    public void execute() {
        m_drive.arcadeDrive(m_speed, 0);
    }

    @Override
    public void end(boolean interrupted) {
        m_drive.arcadeDrive(0,0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(m_drive.getAverageEncoderDistance()) >= m_distance;
    }
}