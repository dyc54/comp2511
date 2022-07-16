## Spider
- For movement, the spider can move clockwise direction 'circling' their spawn spot.
- Testing the spider should change the moving direction when they meet the boulder.


## Zombie Toast
- Zombie toast have random moving direction so the test should limit the moving path to make the limited condition the same as player
- 

## Mercenary
- The mercenary will move to player when their are hostile, so test should judge whether the mercenary close to player and have the same movement restraction as the player
- When mercenary become the allies, they will following the player. Therefore, the test should check whether the mercenary in following state.


## Player
- The player can move up, left, right and down. The player can interact with other entities successfully

## Key && Door
- The player can use the correct key to open the related door. The wrong key cannot open the door. The bag of the player cannot contain two keys.

## Bomb
- The player can put the bomb. When a bomb is cardinally adjacent to a switch, if the trigger of the switch is true, remove all the entities according to the given radius.

## Wall
- Player, zombie and mercenary cannot walk through a wall.

## Exit
- Player will achieve the goal when he is on the exit.

## Boulder && FloorSwitch
- The player can push a boulder, the direction of the bould should be same as the player. If there is an obstacle behind the boulder, cannot move. When a boudler is on a switch, the trigger of the switch should be true, otherwise false.

## Portal
- Player and mercenary can teleport to the target portal and the player can interact with the other entities after the teleport.

## ZombieSpwaner
- The zombie Spwaner can spwan zombie at the cardinally adjacent positions, if all the four positions contain a obstacle, don't spwan.

## Goal
- After a subgoal have been achieved, it can still be disachieved. i.e. boulder, exit. OR goal can be achieved. zombie spwaner needs to be destoryed to achieve enemies goal.

## Collectable entities
- Player can pickup all the collectable entitie and add them to the bag list successfully. (but a bag cannot contains two key)

## Buildable entities
- Player can build Bow and shield with the correct materials.
