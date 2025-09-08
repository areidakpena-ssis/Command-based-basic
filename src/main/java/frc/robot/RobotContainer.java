// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.wpilibj.PS4Controller.Button;

import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DefaultDrive;
//import frc.robot.commands.Autos;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.TurnByAngle;
//import frc.robot.commands.SwitchDriveMode;
//import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.PS4Controller;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands; // static factory methods for commands
//import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();

  private final PS4Controller m_driverController =
      new PS4Controller(OperatorConstants.kDriverControllerPort);

  private final Command m_simpleAuto =
    new DriveDistance(
      AutoConstants.kAutoDriveDistanceInches, AutoConstants.kAutoDriveSpeed, m_robotDrive);

  // slew rate limiters to limit acceleration
  //private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(3);
  //private final SlewRateLimiter m_rotRateLimiter = new SlewRateLimiter(3);

  // a chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    // configure default commands
    // set default drive to split-stick arcade drive
    m_robotDrive.setDefaultCommand(
      // DefaultDrive uses either tank drive with left and right Y joysticks,
      // or split-stick arcade with left Y and right X.
      new DefaultDrive(
        m_robotDrive,
        () -> -m_driverController.getLeftY(),
        () -> -m_driverController.getRightY(),
        () -> -m_driverController.getRightX())
    );
    
    // Add commands to the autonomous command chooser
    m_chooser.setDefaultOption("Simple Auto", m_simpleAuto);
  }


  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // switch drive modes when triangle button pressed
    //new JoystickButton(m_driverController, Button.kTriangle.value).onTrue(new SwitchDriveMode(m_robotDrive));
    // or with inline command:
    new JoystickButton(m_driverController, Button.kTriangle.value).onTrue(
      Commands.run( () -> m_robotDrive.switchSelectedDriveMode() )
    );

    new JoystickButton(m_driverController, Button.kL1.value).onTrue(
      new TurnByAngle(90, m_robotDrive)
    );

    new JoystickButton(m_driverController, Button.kR1.value).onTrue(
      new TurnByAngle(-90, m_robotDrive)
    );

    /* 
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    */
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_chooser.getSelected();
  }
}
