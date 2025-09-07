package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SwitchDriveMode extends InstantCommand {
    public SwitchDriveMode(DriveSubsystem drive) {
        super(drive::switchSelectedDriveMode, drive);
    }
}
