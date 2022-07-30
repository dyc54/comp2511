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

