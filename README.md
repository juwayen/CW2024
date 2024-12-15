# GitHub

Github repository link: https://github.com/juwayen/CW2024

# Compilation Instructions

## Prerequisites
* Install JDK 19 or higher.

## Steps to run the project
1. Open the project folder in IntelliJ IDEA.
2. In the Maven tool window (right panel), click the refresh icon and select `Reload All Maven Projects`.
3. Navigate to `src/main/java/com/example/demo`.
4. Right-click on `Main.java` and select `Run 'Main.main()'`.

## Special settings

### System display scale should be set to 100%:
#### Steps for Windows 11:
1. Open Settings.
1. Navigate to System > Display > Scale & layout > Scale.
2. Set the scale to 100%.

# Implemented and Working Properly

### 2D movement
* All entities can move any direction.

### Better player controls
* The player plane can be moved with WASD as well as the arrow keys.
* The space button can be held down to shoot while moving.

### Enemy formations
* Enemies can spawn in a random formation, V formation, or wall formation.

### More levels
* Added three more levels, each with increasing difficulty, and unique enemy formations.

### Visual changes
* Updated the images to resemble retro plane-shooting games like *1942*.
* Changed the gameplay orientation from horizontal to vertical.

### Health bar
* Replaced the old discrete heart display with a continuous health bar.

### Collectibles
* Added objects that can be collected by the player during a level to gain an advantage, such as the health collectible that restores the player's health.

### Better enemies
* All enemies shoot in the direction of the player.

### New enemy type
* Added an advanced enemy that is stronger than the basic enemy.

### Better boss design
* Separated the boss into three components, the main body, the left wing, and the right wing. Each component is equipped with a turret that shoots at the player. Each component can be destroyed individually. The main body needs to be destroyed to kill this enemy. Destroying a wing deals additional damage to the main body.

### Better bullets
* Bullets have unique damage value.
* Added a single bullet type, shot by the player.
* Added a double bullet type, shot by the player for some time after collecting the power-up collectible.
* Added a basic bullet type, shot by non-boss enemies.
* Added a heavy bullet type, shot by the boss turrets.

### Game screens
* Added a start game screen.
* Added a win game screen.
* Added a lose game screen.
* Added screens for every level that is displayed when it is completed.
* Every screen has animated text.

### Animations
* Added an endlessly scrolling background to give the illusion of movement.
* Added animations for plane propellers.
* Added animations for when the player plane moves left or right.
* Added explosion animations for when a plane is destroyed.
* Added level enter and exit animations for the player plane.

### Audio
* Added looping background music that plays throughout the levels.
* Added a sound effect for when a plane is destroyed.
* Added a sound effect for when a bullet is fired.
* Added a jingle for when all levels are completed.

# Implemented but Not Working Properly

N/A

# Features Not Implemented

### Score system
* **Reason**: Decided against implementing it as it would be more meaningful in the context of a global leaderboard.

# New Java Classes

### Background
* **Purpose**: Represents a visual background component that scrolls vertically to simulate a continuous animated background.
* **Location**: src/main/java/com/example/demo/background/

### Cloneable
* **Purpose**: Interface that defines a contract for creating deep copies of objects. Implementation of the Prototype pattern.
* **Location**: src/main/java/com/example/demo/entity

### BulletConfig
* **Purpose**: Encapsulates configuration details for creating an instance of `Bullet`, such as its initial position and image.
* **Location**: src/main/java/com/example/demo/entity/bullet
    #### BasicBulletConfig
    * **Purpose**: Extends `BulletConfig`. Provides a predefined configuration for a basic bullet type.
    * **Location**: src/main/java/com/example/demo/entity/bullet
    #### DoubleBulletConfig
    * **Purpose**: Extends `BulletConfig`. Provides a predefined configuration for a double bullet type.
    * **Location**: src/main/java/com/example/demo/entity/bullet
    #### HeavyBulletConfig
    * **Purpose**: Extends `BulletConfig`. Provides a predefined configuration for a heavy bullet type.
    * **Location**: src/main/java/com/example/demo/entity/bullet
    #### SingleBulletConfig
    * **Purpose**: Extends `BulletConfig`. Provides a predefined configuration for a single bullet type.
    * **Location**: src/main/java/com/example/demo/entity/bullet

### Collectible
* **Purpose**: Abstract class to encapsulate logic and behavior for collectible items in the game.
* **Location**: src/main/java/com/example/demo/entity/collectible
    #### HealthCollectible
    * **Purpose**: Concrete implementation of `Collectible`. Restores the player's health when collected.
    * **Location**: src/main/java/com/example/demo/entity/collectible
    #### PowerupCollectible
    * **Purpose**: Concrete implementation of `Collectible`. Upgrades the player's bullets from `SingleBullet` to `DoubleBullet` for some time when collected.
    * **Location**: src/main/java/com/example/demo/entity/collectible

### BossPlaneWingLeft and BossPlaneWingRight
* **Purpose**: Represents the wings of `BossPlane`, allowing them to be individually targeted and destroyed.
* **Location**: src/main/java/com/example/demo/entity/plane

### PlaneData
* **Purpose**: Encapsulates data for creating an instance of `Plane`, such as its health and image.
* **Location**: src/main/java/com/example/demo/entity/plane
    #### AdvancedEnemyData
    * **Purpose**: Extends `PlaneData`. Provides predefined data for the advanced enemy type.
    * **Location**: src/main/java/com/example/demo/entity/plane
    #### BasicEnemyData
    * **Purpose**: Extends `PlaneData`. Provides predefined data for the basic enemy type.
    * **Location**: src/main/java/com/example/demo/entity/plane
    #### BossData
    * **Purpose**: Extends `PlaneData`. Provides predefined data for `BossPlane`.
    * **Location**: src/main/java/com/example/demo/entity/plane
    #### BossWingLeftData
    * **Purpose**: Extends `PlaneData`. Provides predefined data for `BossPlaneWingLeft`.
    * **Location**: src/main/java/com/example/demo/entity/plane
    #### BossWingRightData
    * **Purpose**: Extends `PlaneData`. Provides predefined data for `BossPlaneWingRight`.
    * **Location**: src/main/java/com/example/demo/entity/plane
    #### PlayerData
    * **Purpose**: Extends `PlaneData`. Provides predefined data for `PlayerPlane`.
    * **Location**: src/main/java/com/example/demo/entity/plane

### EntityState
* **Purpose**: Interface that defines the contract for representing and managing different states of an `Entity`. Implementation of the State pattern.
* **Location**: src/main/java/com/example/demo/factory
    #### BulletDestroyedState
    * **Purpose**: Implements `EntityState`. Represents the state of a `Bullet` when it has been destroyed.
    * **Location**: src/main/java/com/example/demo/factory
    #### BulletMovingState
    * **Purpose**: Implements `EntityState`. Represents the state of a `Bullet` when it is moving.
    * **Location**: src/main/java/com/example/demo/factory
    #### PlaneDestroyedState
    * **Purpose**: Implements `EntityState`. Represents the state of a `Plane` when it has been destroyed.
    * **Location**: src/main/java/com/example/demo/factory
    #### PlaneMovingLeftState
    * **Purpose**: Implements `EntityState`. Represents the state of a `Plane` when it is moving left.
    * **Location**: src/main/java/com/example/demo/factory
    #### PlaneMovingRightState
    * **Purpose**: Implements `EntityState`. Represents the state of a `Plane` when it is moving right.
    * **Location**: src/main/java/com/example/demo/factory
    #### PlaneMovingStraightState
    * **Purpose**: Implements `EntityState`. Represents the state of a `Plane` when it is moving up or down.
    * **Location**: src/main/java/com/example/demo/factory

### EntityStateMachine
* **Purpose**: Manages state transitions of an `Entity` within a state machine framework by coordinating the entry and exit logic of `EntityState` instances.
* **Location**: src/main/java/com/example/demo/factory

### Factory
* **Purpose**: Interface that defines the contract for creating objects. Implementation of the Factory pattern.
* **Location**: src/main/java/com/example/demo/factory

### FormationFactory
* **Purpose**: Abstract `Factory` for creating `EnemyPlane` objects in specific formations by managing positions and constraints.
* **Location**: src/main/java/com/example/demo/factory
    #### RandomFormationFactory
    * **Purpose**: Concrete implementation of `FormationFactory`. Generates `EnemyPlane` objects with random positions.
    * **Location**: src/main/java/com/example/demo/factory
    #### VFormationFactory
    * **Purpose**: Concrete implementation of `FormationFactory`. Generates `EnemyPlane` objects in a V formation.
    * **Location**: src/main/java/com/example/demo/factory
    #### WallFormationFactory
    * **Purpose**: Concrete implementation of `FormationFactory`. Generates `EnemyPlane` objects in a straight-line formation.
    * **Location**: src/main/java/com/example/demo/factory

### LevelThree, LevelFour, and LevelFive
* **Purpose**: Additional levels.
* **Location**: src/main/java/com/example/demo/level

### GameScreen
* **Purpose**: Displays a centered label with animated text and detects user input to signal screen transitions.
* **Location**: src/main/java/com/example/demo/screen
    #### StartScreen
    * **Purpose**: Extends `GameScreen`. The screen displayed at the beginning of the game.
    * **Location**: src/main/java/com/example/demo/screen
    #### WinScreen
    * **Purpose**: Extends `GameScreen`. The screen displayed when all levels are completed.
    * **Location**: src/main/java/com/example/demo/screen
    #### GameOverScreen
    * **Purpose**: Extends `GameScreen`. The screen displayed when the game is lost.
    * **Location**: src/main/java/com/example/demo/screen
    #### LevelOneEndScreen, LevelTwoEndScreen, LevelThreeEndScreen, LevelFourEndScreen, and LevelFiveEndScreen
    * **Purpose**: Extends `GameScreen`. The screen displayed after completing the respective level.
    * **Location**: src/main/java/com/example/demo/screen

### Collidable
* **Purpose**: Interface that defines the contract for objects that can participate in collision detection and handling.
* **Location**: src/main/java/com/example/demo/service

### Updatable
* **Purpose**: Interface that defines the contract for objects that require periodic updates, allowing them to implement custom logic for handling updates.
* **Location**: src/main/java/com/example/demo/service

### ServiceLocator
* **Purpose**: Centralizes access to game services for simplified management. Implementation of the Service Locator pattern.
* **Location**: src/main/java/com/example/demo/service
    #### AudioService
    * **Purpose**: Manages audio playback, including background music and sound effects.
    * **Location**: src/main/java/com/example/demo/service
    #### CollisionService
    * **Purpose**: Handles collision detection and response for entities.
    * **Location**: src/main/java/com/example/demo/service
    #### InputService
    * **Purpose**: Processes user inputs and maps them to in-game actions.
    * **Location**: src/main/java/com/example/demo/service
    #### SceneService
    * **Purpose**: Manages the game scene and organizes its visual layers.
    * **Location**: src/main/java/com/example/demo/service
    #### UpdateService
    * **Purpose**: Provides a game loop mechanism for periodic updates.
    * **Location**: src/main/java/com/example/demo/service

### UserInterface
* **Purpose**: Manages and displays the in-game UI components, such as the player's health bar.
* **Location**: src/main/java/com/example/demo/ui

### AudioUtils
* **Purpose**: Provides audio-related operations such as loading a `MediaPlayer` object with a sound from the sound file name.
* **Location**: src/main/java/com/example/demo/util/

### ImageUtils
* **Purpose**: Provides image-related operations such as getting an `Image` object from the image file name.
* **Location**: src/main/java/com/example/demo/util/

### Signal
* **Purpose**: Signal-slot mechanism. Implementation of the Observer pattern.
* **Location**: src/main/java/com/example/demo/util/

### Vector
* **Purpose**: Provides common 2D vector operations for easier position calculations.
* **Location**: src/main/java/com/example/demo/util/

# Modified Java Classes

## Removed Classes

### ActiveActorDestructible and Destructible
* **Reason**: Added a `removeFromScene()` method to the base `ActiveActor` class to replace the `destroy()` method in `Destructible`. Moved the `takeDamage()` method from `Destructible` to `FighterPlane`.

### BossProjectile, EnemyProjectile, and UserProjectile
* **Reason**: Replaced with `BulletConfig` system, which centralizes projectile logic, decouples bullets from planes, and enables easy customization and addition of projectile types.

### GameOverImage
* **Reason**: Added a `GameOverScreen` class to replace this.

### LevelView
* **Reason**: Added a `UserInterface` class to replace this.

### LevelViewLevelTwo and ShieldImage
* **Reason**: Decided to enhance the boss design by replacing the shield mechanic.

### WinImage
* **Reason**: Added a `WinScreen` class to replace this.

## Updated Classes

### ActiveActor
* **Change**: Renamed to `Entity`.  
  **Reason**: The new name is more concise and descriptive of its purpose.
* **Change**: Introduced services via `ServiceLocator`.  
  **Reason**: Decouples the class from direct implementation details, increasing modularity and making it easier to test and extend.
* **Change**: Added `Signal`s for communication, such as the `removed` signal.  
  **Reason**: Facilitates event-driven communication, allowing decoupled interactions between entities and the game system.
* **Change**: Constructor now accepts an `Image` and a `Vector` for position instead of file paths and coordinates.  
  **Reason**: Decouples the class from resource management and ensures better flexibility for testing and runtime initialization.
* **Change**: Added methods like `addToScene()` and `removeFromScene()`.  
  **Reason**: Encapsulates scene-related logic, ensuring single-responsibility and improving lifecycle management of entities.
* **Change**: Implemented the `Updatable` interface.  
  **Reason**: Provides a way to implement custom update logic.
* **Change**: Implemented the `Collidable` interface with methods `getHitbox()` and `onCollision()`.  
  **Reason**: Provides a standardized way to handle collisions, aligning with game mechanics and making it extensible.
* **Change**: Movement is now handled via `Vector` objects, and the `MILLISECOND_DELAY` is used for scaling.  
  **Reason**: Simplifies mathematical operations and ensures consistent movement calculations across the game.
* **Change**: Added `connectSignals()` and `clearSignalsConnections()` for managing signal lifecycles.  
  **Reason**: Prevents memory leaks and ensures proper cleanup when entities are removed or reset.

### Boss
* **Change**: Renamed to `BossPlane`.  
  **Reason**: Follows the pattern of ending in "Plane" for classes extending `Plane`.
* **Change**: Constructor now accepts a `BossData` object for initialization instead of hardcoding specific properties.  
  **Reason**: Centralizes initialization data in `BossData`, enhancing flexibility and reusability for creating different plane configurations.
* **Change**: Introduced `BossPlaneWingLeft` and `BossPlaneWingRight` as separate components.  
  **Reason**: Decomposes the `BossPlane` into modular components, allowing independent behavior and management of its parts.
* **Change**: Connected the `destroyed` `Signal` of wings so that the `BossPlane` takes damage when a wing is destroyed.  
  **Reason**: Introduces a modular damage system where parts of the `BossPlane` can influence its overall health, enabling richer game mechanics.

### Controller
* **Change**: Renamed to `GameService`.  
  **Reason**: Follows the pattern of ending in "Service" for service classes.
* **Change**: Introduced services via `ServiceLocator`.  
  **Reason**: Decouples the class from direct implementation details, increasing modularity and making it easier to test and extend.
* **Change**: Replaced reflection-based level handling with predefined a list of levels.  
  **Reason**: Improves type safety and maintainability.
* **Change**: Added `UserInterface` and `PlayerPlane` initialization  
  **Reason**: Centralizes UI and player state management.
* **Change**: Removed `Observer` implementation.  
  **Reason**: `Observer` is now deprecated. Uses `Signal`s to implement the Observer pattern instead.
* **Change**: Added player and level `Signal`s (e.g., `enteredLevel`, `exitedLevel`, `levelWon`).  
  **Reason**: Enables event-driven architecture for game state transitions.

### EnemyPlane
* **Change**: Replaced hardcoded constants (e.g., velocities, offsets, fire rate) with values from `PlaneData`.
  **Reason**: Centralizes configuration, improving maintainability and flexibility for different enemy types.
* **Change**: Introduced `Vector` objects for positions and directions, replacing manual calculations.  
  **Reason**: Simplifies movement and directional calculations, making the code more readable and consistent.
* **Change**: Added `finalPosition` and `direction` to manage enemy movement towards a target location.  
  **Reason**: Enhances flexibility by allowing each `EnemyPlane` to move towards a unique destination.
* **Change**: Integrated `BulletConfig` for firing mechanics.  
  **Reason**: Supports customization of bullet behavior, such as direction, offset, and shooter, improving extensibility.
* **Change**: Added `PlayerPlane` reference to calculate shooting direction towards the player.  
  **Reason**: Implements targeted shooting, increasing gameplay challenge.
* **Change**: Added `getRandomTime()` to generate random time intervals for firing bullets.  
  **Reason**: Improves variety in enemy behavior while also keeping it somewhat predictable.
* **Change**: Refactored `fireProjectile()` to `fire()` using `Bullet` and `BulletConfig`.  
  **Reason**: Simplifies bullet creation and integrates with the broader game framework for bullet handling.
* **Change**: Marked `EnemyPlane` as non-friendly by overriding `isFriendly()`.  
  **Reason**: Distinguishes `EnemyPlane` from friendly planes, enabling appropriate game logic interactions.

### FighterPlane
* **Change**: Renamed to `Plane`.  
  **Reason**: More concise.
* **Change**: Added state management using `EntityStateMachine` and states like `PlaneMovingStraightState`, and `PlaneDestroyedState`.  
  **Reason**: Introduces a clean and scalable architecture for handling dynamic behaviors based on the plane's state.
* **Change**: Integrated animations using `Timeline` objects.  
  **Reason**: Enhances visual representation and gameplay by enabling smooth animations.
* **Change**: Added dependency on `AudioService` to play sounds for firing and destruction events.  
  **Reason**: Improves the player's experience with auditory feedback for actions.
* **Change**: Introduced `Signal destroyed` for emitting destruction events.  
  **Reason**: Facilitates event-driven communication for handling plane destruction, improving decoupling.
* **Change**: Refactored health management to use getter and setter methods (`getHealth()`, `setHealth(int health)`).  
  **Reason**: Provides a standardized interface for interacting with the plane's health.
* **Change**: Added `takeDamage(int damageAmount)` method with sound effects and destruction logic.  
  **Reason**: Makes damage handling more flexible and integrates it with the new state and animation systems.
* **Change**: Added abstract methods `canFire()` and `fire()` for child classes to define specific firing logic.  
  **Reason**: Increases flexibility and enforces a contract for firing behavior in subclasses.
* **Change**: Added `isFriendly()` as an abstract method.  
  **Reason**: Establishes a clear distinction between friendly and enemy planes, enabling tailored interactions.
* **Change**: Replaced direct position adjustments with `Vector` operations.  
  **Reason**: Simplifies and standardizes movement and position calculations.

### HeartDisplay
* **Change**: Renamed to `HealthDisplay`.  
  **Reason**: Changed display to a health bar.
* **Change**: Removed methods related to heart management.  
  **Reason**: These are no longer relevant in the new health bar design.
* **Change**: Added a `setHealth(int health)` method.  
  **Reason**: Setter method for the current health.
* **Change**: Added a `reset()` method.  
  **Reason**: Supports resetting the health bar, used when starting a new level.
* **Change**: Added an `updateHealthBar()` method.  
  **Reason**: Dynamically resizes the health bar based on the current health percentage.

### LevelParent
* **Change**: Renamed to `Level`.  
  **Reason**: More concise.
* **Change**: Removed inheritance from `Observable`.  
  **Reason**: `Observable` is now deprecated. Uses `Signal`s to implement the Observer pattern instead.
* **Change**: Removed use of a `Timeline` and implemented the `Updatable` interface.  
  **Reason**: Provides a better way to implement custom update logic.
* **Change**: Removed direct handling of collisions, projectile management, and destruction logic.  
  **Reason**: These responsibilities are managed by other classes (e.g., `EnemyPlane`, `Collectible`), following a separation of concerns for better maintainability.
* **Change**: Added `spawnCollectibles()` method.  
  **Reason**: Used to randomly spawn `Collectible`s for the player.
* **Change**: Introduced `FormationFactory` for spawning enemy units.  
  **Reason**: The spawning of enemies is now handled through the Factory pattern, enabling easier management and scalability.

### LevelOne and LevelTwo
* **Change**: Removed `BACKGROUND_IMAGE_NAME` constant.  
  **Reason**: There is a constant `Background` throughout the game.
* **Change**: Removed `NEXT_LEVEL` constant.  
  **Reason**: `GameService` hold a list of all levels in order.
* **Change**: Removed `PLAYER_INITIAL_HEALTH` constant.  
  **Reason**: The `PlayerPlane` instance resets it health before every level.
* **Change**: Removed the `checkIfGameOver()` method.  
  **Reason**: The `Level` class is responsible for this.
* **Change**: Removed the `initializeFriendlyUnits()` method.  
  **Reason**: There is only one instance of `PlayerPlane` throughout the levels.
* **Change**: Removed the `instantiateLevelView()` method.  
  **Reason**: The `UserInterface` class is responsible for interface elements.
* **Change**: Removed `userHasReachedKillTarget()` method.  
  **Reason**: The `Level` class is responsible for this.
* **Change**: Added `initializeFactories()` method implementation.  
  **Reason**: Spawns enemy units using various `FormationFactory` objects.

### Projectile
* **Change**: Renamed to `Bullet`.  
  **Reason**: More concise.
* **Change**: Constructor now accepts a `BulletConfig` object.  
  **Reason**: Encapsulates properties and supports additional features like faction alignment, movement direction, and damage.
* **Change**: Replaced the `takeDamage()` method with the `onCollision(Collidable collidable)` method from super class.
* **Reason**: Implements custom logic when colliding with a `Plane` object.
* **Change**: Added methods for out of bounds checks.  
  **Reason**: Responsible for removing itself from the scene if it is out of bounds, preventing memory leaks.
* **Change**: Added an `EntityStateMachine` and states (`BulletMovingState`, `BulletDestroyedState`)  
  **Reason**: To incorporate a state machine for managing bullet behavior dynamically.

### UserPlane
* **Change**: Renamed to `PlayerPlane`.  
  **Reason**: Aligns with naming conventions.
* **Change**: Replaced primitive constants with `Vector` objects for positions and movement bounds.  
  **Reason**: Simplifies math operations and enhances readability, while ensuring consistency with the rest of the game.
* **Change**: Added `Signal`s for events like `healthUpdated`, `enteredLevel`, and `exitedLevel`.  
  **Reason**: Enables event-driven architecture, improving decoupling and facilitating easier event handling in the game loop.
* **Change**: Introduced `InputService` via `ServiceLocator`.  
  **Reason**: Decouples the class from direct implementation details and allows handling of movement and firing actions.
* **Change**: Implemented `powerup()` and `updatePowerup()` methods for temporary upgrades.  
  **Reason**: Adds gameplay variety by allowing the player to temporarily use enhanced bullets.
* **Change**: Added `resetHealth()` and `onSceneReset()` methods.  
  **Reason**: Centralizes state reset logic, improving code clarity and reducing duplication.
* **Change**: Added `playEnterTransition()` and `playExitTransition()` methods for animations.  
  **Reason**: Improves player experience by providing visual feedback during level transitions.
* **Change**: Updated `updatePosition()` to use `Vector.clamped()` for bounded movement based on `MIN_POS` and `MAX_POS`.  
  **Reason**: Simplifies boundary logic and ensures movement stays within the designated area.

# Unexpected Problems

### Window Scaling
The Java application window was affected by the Windows system scaling settings, causing the window to become too large to fit on the screen properly. I searched online for solutions and found one that seemed to work for others, but unfortunately, it didn’t work in my case. As a workaround, I documented it as a special setting requirement.

### JavaFX Media
The game experienced noticeable lag when playing audio for the first time. After researching online, I discovered that this was a common issue with JavaFX Media. I attempted to pre-load the audio files to address the problem, but this approach proved ineffective. To work around the issue, I added background music that starts playing as soon as the game launches, ensuring there is no lag during gameplay.
