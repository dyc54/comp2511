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

## Key
- Only one key can be picked up

## Door

## Wall
The player cannot pass the wall

## Exit
The player walks to the exit to reach the goal

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

## Bomb

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
