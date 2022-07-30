# Signal Object test
## Spider
- For movement, the spider can move clockwise direction 'circling' their spawn spot.
- Testing the spider should change the moving direction when they meet the boulder.
- Test spider spawn by counting the number of spider after X ticks

## Zombie Toast
- Zombie toast have random moving direction so the test should limit the moving path to make the limited condition the same as player
  
## Mercenary
- The mercenary will move to player when their are hostile, so test should judge whether the mercenary close to player and have the same movement restraction as the player
- When mercenary become the allies, they will following the player. Therefore, the test should check whether the mercenary in following state.
- Testing the mercenary can be bribed using the amount number treasure. By asserting number of treasures before and after to aprove the mercenary bribed.
- Testing the mercenary is restricted by wall.
- After bribed mercenary, the mercenary should follow the player.
- write a test which includes bribe and the mercenary movement to make sure the mercenary can interact with the player normally
- test the exception for bribing mercenary

## Zombie Toast Spwaner
- create zombie after X ticks by counting the number of zombie toast
- the player can interact with zombie toast spwaner and distory this using the weapon
- test distory the zombie toast spwaner fail situation
  
## Player
- Players can move
  
## Wall
The player cannot pass the wall

## Door and key
The player can open the right lock with the right key. 
The wrong key cannot open the door. 
The player can pass through the opened door. 
Players cannot have two keys in their backpack at the same time

## Boulders
The player can push a single boulder, which moves in the same direction as the player. 
If there is another obstacle behind the boulder, it cannot be pushed.

## switch
When the boulder is on the switch, the switch is on, when the boulder is moved, the switch is off.

## Teleport Gate
Players and mercenaries can teleport to the specified location. 
Players can interact with other entities normally after teleporting.

## Zombie Generator
Zombies can be generated randomly around the area at a given generation rate, and not generated if there are obstacles all around.

## Bombs
Players can place bombs. When a bomb exists around a switch and the switch is on, the bomb explodes to eliminate entities within a given range.

## goal
Sub-goals can still be changed to unrealized after they are achieved, or goals can be achieved, achieving the enemies goal requires destroying the zombie generator
- The player can move up, left, right and down. The player can interact with other entities successfully


## Bomb
- The player can put the bomb. When a bomb is cardinally adjacent to a switch, if the trigger of the switch is true, remove all the entities according to the given radius.


## Wall
- Player, zombie and mercenary cannot walk through a wall.

## Exit
- Player will achieve the goal when he is on the exit.

## Portal
- Player and mercenary can teleport to the target portal and the player can interact with the other entities after the teleport.

## ZombieSpwaner
- The zombie Spwaner can spwan zombie at the cardinally adjacent positions, if all the four positions contain a obstacle, don't spwan.


## Collectable entities
- Player can pickup all the collectable entitie and add them to the bag list successfully. (but a bag cannot contains two key)

## Buildable entities
- Player can build Bow and shield with the correct materials.

# muti object test
## Battle
- Enemies can be defeated
- The enemy defeats the player
- Fighting with enemies in cases without weapons and potions
- Use potions to fight with enemies
- Movement of enemies after battling with them after using the potion
- Edge condition of the potion: defeated by the enemy in the first round
- Use weapons to defeat enemies
- Bribing mercenaries to defeat the enemy

## Goals
- Completion of individual goals
- Complete OR as a connected target
- Complete AND as a connected target
- Completing the complex goal
- Some goals are completed and then become uncompleted
- After a subgoal have been achieved, it can still be disachieved. i.e. boulder, exit. OR goal can be achieved. zombie spwaner needs to be destoryed to achieve enemies goal.



## Assassin
- Assassin chase the player
- player bribes assassin will fail in ceratin rate
- When player uses invisibility potion, the assassin can see the player in certain radius and continue move to player
- If out of recon radios, the assassin should random movement

## Hydra
- Having a certain rate to increase the health when battle with player
- Having the same movement strategy with zombie


## Persistence 
- Can read and write files
- Can list all games
- Player inventory can be saved and load.
- Battle can be saved
- Randomly generated spiders can also be read correctly
- Test item durability
- direction of enemy movement
  
## Time Travel
- The older-self appear when player is time travelling
- New players can fight against old players
- The older-self can correctly appear and disappear where they should be.
  
## Dungeon Builder 
- There is a player at the starting location
- There is an exit at the end location
- the size of the border
- Generation of minimap, e.g. <0,0> -> <1,1>
- If the width is even, there must be a way to go from the beginning to the end
  
## Logic Switches
- Logical entities can be activated correctly
- Entities can be activated by wires
- Entities in the M2 section cannot be activated by wires
  
## Integration testing
- Game after Time travelling can be saved and read correctly after time transfer
- Randomly generated maps can be saved
- Logical entities can be saved
- Activate logical entity after time transfer

## Pathfinding
-  Test mercenary moves towards the player following the shortest path
-  Test mercenary moves towards the player using portals
-  Test mercenary moves towards the player using swamp tiles

## Building Midnight Armour
- Building Midnight Armour (No Zombies)
- Building Midnight Armour (with zombies)
- Building Midnight Armour Fail (insufficient material missing sword)
- Building Midnight Armour Fail (insufficient material missing sun_stone)

## Building Sceptre
- Use 1 wood + 1 key + 1 sun_stone Building Sceptre
- Use 1 wood + 1 treasure + 1 sun_stone Building Sceptre
- Use 2 arrows + 1 key + 1 sun_stone Building Sceptre
- Use 2 arrows + 1 treasure + 1 sun_stone Building Sceptre
- Use 1 wood + 1 sun_stone + 1 sun_stone,sun_stone keeps one
- Use 2 arrows + 1 sun_stone + 1 sun_stone,sun_stone keeps one
- building Sceptre fail (insufficient materials missing wood, arrows)
- Building Sceptre fail (insufficient material missing sun_stone)
  
## Sceptre
- Can be used to mindcontrol mercenary and assassin
- Free allies after the duration of sceptre
- retain after used

## midnight_armour
- Adding right amount of damage and defence to player

## Swamp
- Can trap enemies
- Enemies can leave after a given movement_factor
- Won't trap player

## SunStone
- Can be used to open the door (has higher priority than key)
- Can be count as treasure when calculating treasure goal

