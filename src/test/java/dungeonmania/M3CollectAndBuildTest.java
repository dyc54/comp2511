package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static dungeonmania.TestUtils.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntities.MercenaryEnemy;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class M3CollectAndBuildTest {
    public DungeonResponse playerMoveController(DungeonManiaController dmc, Direction direction, int movetime){
        DungeonResponse res = dmc.tick(direction);
        for (int i = 1; i < movetime; i++) {
            res = dmc.tick(direction);
        }
        return res;
    }

    /* **************************************************TEST PICK UP FUNCTION********************************************* */
    @Test
    @DisplayName("Test the player can pick up SunStone in map")
    public void testPickUpSunStone() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpSunStone",
                "c_collectTests");
        // pick up SunStone
        res = playerMoveController(dmc, Direction.RIGHT, 1);
        assertEquals(1, getInventory(res, "sun_stone").size());

    }


    @Test
    @DisplayName("Test the player can Open door by SunStone and no use key")
    public void testOpenDoorBySunStone() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpSunStone",
                "c_collectTests");
        // pick up Key
        assertEquals(1, getInventory(res, "key").size());
        assertEquals(new Position(1,1), getEntities(res, "player").get(0).getPosition());

        //pick up SunStone
        res = playerMoveController(dmc, Direction.RIGHT, 1);
        assertEquals(1, getInventory(res, "sun_stone").size());
        assertEquals(new Position(2,1), getEntities(res, "player").get(0).getPosition());

        //player Open door and go through the door
        res = playerMoveController(dmc, Direction.RIGHT, 2);
        assertEquals(new Position(3,1), getEntities(res, "door").get(0).getPosition());
        assertEquals(new Position(4,1), getEntities(res, "player").get(0).getPosition());

        assertEquals(1, getInventory(res, "sun_stone").size());
        assertEquals(1, getInventory(res, "key").size());
    }


/* **************************************************TEST BUILD FUNCTION********************************************* */
    @Test
    @DisplayName("Test the player can Building Midnight Armour")
    public void testBuildingMidnightArmourSuccess() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingMidnightArmour1",
                    "c_collectTests");
            // pick up SunStone
            assertEquals(1, getInventory(res, "sun_stone").size());

            //pick up sword 
            res = playerMoveController(dmc, Direction.RIGHT, 1);
            assertEquals(1, getInventory(res, "sword").size());
            assertEquals(0, getInventory(res, "midnight_armour").size());
            
            //player build Midnight Armour
            res = dmc.build("midnight_armour");
            assertEquals(1, getInventory(res, "midnight_armour").size());
            assertEquals(0, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "sword").size());

            List<String> buildables = new ArrayList<>();
            // buildables.add("midnight_armour");
            assertEquals(buildables, res.getBuildables());
           
        });
    }


    @Test
    @DisplayName("Test the player Building Midnight Armour fail because have zombie")
    public void testBuildingMidnightArmourFailZombie() {
        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingMidnightArmour2",
                    "c_collectTests");
            // pick up SunStone
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(new Position(5,1), getEntities(res, "zombie_toast").get(0).getPosition());
            //pick up sword 
            res = playerMoveController(dmc, Direction.RIGHT, 1);
            assertEquals(1, getInventory(res, "sword").size());
            
            //player build Midnight Armour
            res = dmc.build("midnight_armour");          
        });

    }

    @Test
    @DisplayName("Test the player Building Midnight Armour fail because no enough sword")
    public void testBuildingMidnightArmourFailSword() {
        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingMidnightArmour3",
                    "c_collectTests");
            // pick up SunStone
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "sword").size());
            
            //player build Midnight Armour
            res = dmc.build("midnight_armour");          
        });
    }

    @Test
    @DisplayName("Test the player can Building Sceptre no replace (1 wood + 1 key + 1 sun_stone)")
    public void testBuildingSceptreSuccess1() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreSuccess1",
                    "c_collectTests");
            // pick up
            assertEquals(0, getInventory(res, "sceptre").size()); 
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(1, getInventory(res, "wood").size());
            assertEquals(1, getInventory(res, "key").size());
            
            //player build sceptre
            res = dmc.build("sceptre");
            assertEquals(1, getInventory(res, "sceptre").size());
            assertEquals(0, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "wood").size());
            assertEquals(0, getInventory(res, "key").size());

            List<String> buildables = new ArrayList<>();
            // buildables.add("sceptre");
            assertEquals(buildables, res.getBuildables());
           
        });
    }


    @Test
    @DisplayName("Test the player can Building Sceptre no replace (1 wood + 1 treasure + 1 sun_stone)")
    public void testBuildingSceptreSuccess2() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreSuccess2",
                    "c_collectTests");
            // pick up
            assertEquals(0, getInventory(res, "sceptre").size()); 
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(1, getInventory(res, "wood").size());
            assertEquals(1, getInventory(res, "treasure").size());
            
            //player build sceptre
            res = dmc.build("sceptre");
            assertEquals(1, getInventory(res, "sceptre").size());
            assertEquals(0, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "wood").size());
            assertEquals(0, getInventory(res, "treasure").size());

            List<String> buildables = new ArrayList<>();
            // buildables.add("sceptre");
            assertEquals(buildables, res.getBuildables());
           
        });
    }


    @Test
    @DisplayName("Test the player can Building Sceptre no replace (2 arrows + 1 key + 1 sun_stone)")
    public void testBuildingSceptreSuccess3() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreSuccess3",
                    "c_collectTests");
            // pick up
            assertEquals(0, getInventory(res, "sceptre").size()); 
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(2, getInventory(res, "arrow").size());
            assertEquals(1, getInventory(res, "key").size());
            
            //player build sceptre
            res = dmc.build("sceptre");
            assertEquals(1, getInventory(res, "sceptre").size());
            assertEquals(0, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "arrow").size());
            assertEquals(0, getInventory(res, "key").size());

            List<String> buildables = new ArrayList<>();
            // buildables.add("sceptre");
            assertEquals(buildables, res.getBuildables());
           
        });
    }

    @Test
    @DisplayName("Test the player can Building Sceptre no replace (2 arrows + 1 treasure + 1 sun_stone)")
    public void testBuildingSceptreSuccess4() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreSuccess4",
                    "c_collectTests");
            // pick up
            assertEquals(0, getInventory(res, "sceptre").size()); 
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(2, getInventory(res, "arrow").size());
            assertEquals(1, getInventory(res, "treasure").size());
            
            //player build sceptre
            res = dmc.build("sceptre");
            assertEquals(1, getInventory(res, "sceptre").size());
            assertEquals(0, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "arrow").size());
            assertEquals(0, getInventory(res, "treasure").size());

            List<String> buildables = new ArrayList<>();
            // buildables.add("sceptre");
            assertEquals(buildables, res.getBuildables());
           
        });
    }

    @Test
    @DisplayName("Test the player can Building Sceptre replace (1 wood + 1 sun_stone + 1 sun_stone)")
    public void testBuildingSceptreSuccess5() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreSuccess5",
                    "c_collectTests");
            // pick up
            assertEquals(0, getInventory(res, "sceptre").size()); 
            assertEquals(2, getInventory(res, "sun_stone").size());
            assertEquals(1, getInventory(res, "wood").size());
            
            //player build sceptre
            res = dmc.build("sceptre");
            assertEquals(1, getInventory(res, "sceptre").size());
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "wood").size());

            List<String> buildables = new ArrayList<>();
            // buildables.add("sceptre");
            assertEquals(buildables, res.getBuildables());
           
        });
    }

    @Test
    @DisplayName("Test the player can Building Sceptre replace (2 arrows + 1 sun_stone + 1 sun_stone)")
    public void testBuildingSceptreSuccess6() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreSuccess6",
                    "c_collectTests");
            // pick up
            assertEquals(0, getInventory(res, "sceptre").size()); 
            assertEquals(2, getInventory(res, "sun_stone").size());
            assertEquals(2, getInventory(res, "arrow").size());
            
            //player build sceptre
            res = dmc.build("sceptre");
            assertEquals(1, getInventory(res, "sceptre").size());
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "arrow").size());

            List<String> buildables = new ArrayList<>();
            // buildables.add("sceptre");
            assertEquals(buildables, res.getBuildables());
           
        });
    }


    @Test
    @DisplayName("Test the player can Building Sceptre Faile (1 arrows + 1 key + 1 sun_stone)")
    public void testBuildingSceptreFaile1() {

        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreFaile1",
                    "c_collectTests");
            // pick up 
            assertEquals(0, getInventory(res, "sceptre").size());
            assertEquals(1, getInventory(res, "arrow").size());
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(1, getInventory(res, "key").size());
            
            //player build sceptre
            res = dmc.build("sceptre");       
        });
    }


    @Test
    @DisplayName("Test the player can Building Sceptre Faile (2 arrows  + 1 sun_stone)")
    public void testBuildingSceptreFaile2() {
        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreFaile2",
                    "c_collectTests");
            // pick up 
            assertEquals(0, getInventory(res, "sceptre").size());
            assertEquals(2, getInventory(res, "arrow").size());
            assertEquals(1, getInventory(res, "sun_stone").size());
            
            //player build sceptre
            res = dmc.build("sceptre");       
        });
    }


    @Test
    @DisplayName("Test the player can Building Sceptre Faile (1 wood  + 1 sun_stone)")
    public void testBuildingSceptreFaile3() {

        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreFaile3",
                    "c_collectTests");
            // pick up 
            assertEquals(0, getInventory(res, "sceptre").size());
            assertEquals(1, getInventory(res, "wood").size());
            assertEquals(1, getInventory(res, "sun_stone").size());
            
            //player build sceptre
            res = dmc.build("sceptre");       
        });
    }

    @Test
    @DisplayName("Test the player can Building Sceptre Faile (1 wood  + 1 key + 1 treasure)")
    public void testBuildingSceptreFaile4() {

        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreFaile4",
                    "c_collectTests");
            // pick up 
            assertEquals(0, getInventory(res, "sceptre").size());
            assertEquals(1, getInventory(res, "wood").size());
            assertEquals(1, getInventory(res, "treasure").size());
            assertEquals(1, getInventory(res, "key").size());
            
            //player build sceptre
            res = dmc.build("sceptre");       
        });
    }

    @Test
    @DisplayName("Test the player can Building Sceptre Faile (2 arrows + 1 key + 1 treasure)")
    public void testBuildingSceptreFaile5() {

        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreFaile5",
                    "c_collectTests");
            // pick up 
            assertEquals(0, getInventory(res, "sceptre").size());
            assertEquals(2, getInventory(res, "arrow").size());
            assertEquals(1, getInventory(res, "treasure").size());
            assertEquals(1, getInventory(res, "key").size());
            
            //player build sceptre
            res = dmc.build("sceptre");       
        });
    }

    /* **************************************************TEST Usage********************************************* */
    @Test
    @DisplayName("Test the player can mindcontrol the mercenary")
    public void testSceptreCanMindControl() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_sceptreUsage",
                    "c_collectTests");
            
            //player build sceptre
            res = dmc.build("sceptre");
            assertEquals(1, getInventory(res, "sceptre").size());
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "arrow").size());

            //Use the sceptre
            String sceptreId = getInventory(res, "sceptre").get(0).getId();
            res = assertDoesNotThrow(() -> dmc.tick(sceptreId));
            assertEquals(new Position(2,1), getEntities(res, "mercenary").get(0).getPosition());

            //Check if the mercenary is controlled
            Position playerPosition = getEntities(res, "player").get(0).getPosition();
            res = dmc.tick(Direction.RIGHT);
            assertEquals(new Position(1,1), getEntities(res, "mercenary").get(0).getPosition());


            playerPosition = getEntities(res, "player").get(0).getPosition();
            res = dmc.tick(Direction.DOWN);
            assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());
            assertEquals(new Position(2,1), getEntities(res, "mercenary").get(0).getPosition());

            playerPosition = getEntities(res, "player").get(0).getPosition();
            res = dmc.tick(Direction.DOWN);
            assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());
            
        });
    }

    @Test
    @DisplayName("Test the mercenary will be free after the sceptre duration")
    public void testMercenaryFreeAfterSceptreDuration() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_sceptreUsage",
                    "c_collectTests");
            
            //player build sceptre
            res = dmc.build("sceptre");
            assertEquals(1, getInventory(res, "sceptre").size());
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "arrow").size());

            //Use the sceptre
            String sceptreId = getInventory(res, "sceptre").get(0).getId();
            res = assertDoesNotThrow(() -> dmc.tick(sceptreId));

            //Check if the mercenary is controlled
            Position playerPosition = getEntities(res, "player").get(0).getPosition();
            res = dmc.tick(Direction.RIGHT);
            assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());

            playerPosition = getEntities(res, "player").get(0).getPosition();
            res = dmc.tick(Direction.RIGHT);
            assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());

            playerPosition = getEntities(res, "player").get(0).getPosition();
            res = dmc.tick(Direction.RIGHT);
            assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());
            // Now the mercenary should be free, and the player will kill the mercenary
            System.out.println("+++++++++++++++++++++++++");
            assertEquals(new Position(3,1), getEntities(res, "mercenary").get(0).getPosition());
            assertEquals(new Position(4,1), getEntities(res, "player").get(0).getPosition());
            res = dmc.tick(Direction.LEFT);
            // assertEquals(new Position(4,1), getEntities(res, "mercenary").get(0).getPosition());
            // assertEquals(new Position(3,1), getEntities(res, "player").get(0).getPosition());
            // res = dmc.tick(Direction.RIGHT);
            // assertEquals(new Position(5,1), getEntities(res, "mercenary").get(0).getPosition());
            // assertEquals(new Position(4,1), getEntities(res, "player").get(0).getPosition());
            assertEquals(0, getEntities(res, "mercenary").size());

        });
    }

    @Test
    @DisplayName("Test sceptre can mindcontrol mutiple mercenaries")
    public void testSceptreMindControlMultipleMercenaries() {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_multipleMercenaries",
                    "c_collectTests");
            
            //player build sceptre
            res = dmc.build("sceptre");
            assertEquals(1, getInventory(res, "sceptre").size());
            assertEquals(1, getInventory(res, "sun_stone").size());
            assertEquals(0, getInventory(res, "arrow").size());

            //Use the sceptre
            String sceptreId = getInventory(res, "sceptre").get(0).getId();
            res = assertDoesNotThrow(() -> dmc.tick(sceptreId));

            //Check if all the mercenaries are controlled
            Position playerPosition = getEntities(res, "player").get(0).getPosition();
            res = dmc.tick(Direction.RIGHT);
            assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());
            assertEquals(new Position(2, 2), getEntities(res, "mercenary").get(1).getPosition());

            playerPosition = getEntities(res, "player").get(0).getPosition();
            res = dmc.tick(Direction.RIGHT);
            assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());
            assertEquals(new Position(3, 2), getEntities(res, "mercenary").get(1).getPosition());

            playerPosition = getEntities(res, "player").get(0).getPosition();
            res = dmc.tick(Direction.RIGHT);
            assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());
            assertEquals(new Position(4, 2), getEntities(res, "mercenary").get(1).getPosition());
        });
    }

    


}
