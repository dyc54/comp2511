package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class StaticEntityTests {
	@Test
	@DisplayName("Test the player cannot pass a wall")
	public void testWall() {
		DungeonManiaController dmc = new DungeonManiaController();
		DungeonResponse initDungonRes = dmc.newGame("d_spiderTest_basicMovement",
						"c_spiderTest_basicMovement");
		EntityResponse initPlayer = getPlayer(initDungonRes).get();

		// create the expected result
		EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
						new Position(1, 1),
						false);

		// move player upward
		DungeonResponse actualDungonRes = dmc.tick(Direction.UP);
		EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

		// assert after movement
		assertEquals(expectedPlayer, actualPlayer);
	}

	@Test
	@DisplayName("Test the player can simply move a boulder")
	public void testMoveBoulder() {
		DungeonManiaController dmc = new DungeonManiaController();
		DungeonResponse initDungonRes = dmc.newGame("d_boulderTest",
						"c_complexGoalsTest_andAll");
		EntityResponse initPlayer = getPlayer(initDungonRes).get();

		// create the expected result
		EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
						new Position(2, 1),
						false);

		DungeonResponse actualDungonRes;
		actualDungonRes = dmc.tick(Direction.RIGHT);

		EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

		// assert the position of boulder
		Position boulderPosition = getEntities(actualDungonRes, "boulder").get(0).getPosition();
		assertEquals(new Position(3, 1), boulderPosition);

		// assert after movement
		assertEquals(expectedPlayer, actualPlayer);
	}

	@Test
	@DisplayName("Test the player cannot move a boulder if there are obstacle behind it")
	public void testObstacleBehindBoulder() {
		DungeonManiaController dmc = new DungeonManiaController();
		DungeonResponse initDungonRes = dmc.newGame("d_moveBoulderTest",
						"c_moveBoulderTest");
		EntityResponse initPlayer = getPlayer(initDungonRes).get();

		// create the expected result
		EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
						new Position(1, 1),
						false);

		DungeonResponse actualDungonRes;
		actualDungonRes = dmc.tick(Direction.RIGHT);

		EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

		// assert after movement
		assertEquals(expectedPlayer, actualPlayer);
	}

	@Test
	@DisplayName("Test the player can move through a portal")
	public void testMoveThroughPortal() {
		DungeonManiaController dmc = new DungeonManiaController();
		DungeonResponse initDungonRes = dmc.newGame("d_portalTest",
						"c_portalTest");
		EntityResponse initPlayer = getPlayer(initDungonRes).get();

		// create the expected result
		EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
						new Position(1, 3),
						false);

		// move player downward
		DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
		EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

		// assert after movement
		assertEquals(expectedPlayer.getPosition(), actualPlayer.getPosition());
		assertEquals(expectedPlayer, actualPlayer);
	}

	@Test
	@DisplayName("Test the portal cannot use anymore if its twins portal has been destoyed")
	public void testSinglePortalCannotUse() {
		DungeonManiaController dmc = new DungeonManiaController();
		DungeonResponse initDungonRes = dmc.newGame("d_singlePortal",
						"c_portalTest");
		EntityResponse initPlayer = getPlayer(initDungonRes).get();

		// create the expected result
		EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
						new Position(3, 1),
						false);

		// move player downward
		DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
		EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

		// assert after movement
		assertEquals(expectedPlayer.getPosition(), actualPlayer.getPosition());
		assertEquals(expectedPlayer, actualPlayer);
	}

	@Test
	@DisplayName("Test the player can't move through a portal if there is a wall behind it")
	public void testObstacleBehindPortal() {
		DungeonManiaController dmc = new DungeonManiaController();
		DungeonResponse initDungonRes = dmc.newGame("d_portalTest",
						"c_portalTest");
		EntityResponse initPlayer = getPlayer(initDungonRes).get();

		// create the expected result
		EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
						new Position(3, 0),
						false);

		// move player downward
		DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
		EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

		// assert after movement
		assertEquals(expectedPlayer.getPosition(), actualPlayer.getPosition());
		assertEquals(expectedPlayer, actualPlayer);
	}

	@Test
	@DisplayName("Test advanced portal")
	public void testAdvancedPortal() {
		DungeonManiaController dmc = new DungeonManiaController();
		DungeonResponse initDungonRes = dmc.newGame("d_portalTest",
						"c_portalTest");
		EntityResponse initPlayer = getPlayer(initDungonRes).get();

		// create the expected result
		EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
						new Position(1, 4),
						false);

		// move player downward
		DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
		EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

		// assert after movement
		assertEquals(expectedPlayer.getPosition(), actualPlayer.getPosition());
		assertEquals(expectedPlayer, actualPlayer);
	}

	@Test
	@DisplayName("Test zombie toast spwaner")
	public void testZombieSpwaner() {
		DungeonManiaController dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_zombieSpwaner",
						"c_zombieSpwaner");

		DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
		assertEquals(true,
						actualDungonRes.getEntities().stream()
										.anyMatch(e -> e.getType().equals("zombie_toast_spawner")));
		assertEquals(1, getEntities(actualDungonRes, "zombie_toast").size());
		actualDungonRes = dmc.tick(Direction.LEFT);
		assertEquals(2, getEntities(actualDungonRes, "zombie_toast").size());
	}

	@Test
	@DisplayName("Test zombie toast spwaner cannot spawn zombie if there are obstacles at all the cardinally adjacent positions of it")
	public void testZombieSpwanerCannotSpwan() {
		DungeonManiaController dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_zombieSpwanerCannotSpwan",
						"c_zombieSpwanerCannotSpwan");

		DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
		assertEquals(true,
						actualDungonRes.getEntities().stream()
										.anyMatch(e -> e.getType().equals("zombie_toast_spawner")));
		assertEquals(0, getEntities(actualDungonRes, "zombie_toast").size());
	}

	@Test
	@DisplayName("Test player can use a key to open and walk through a door")
	public void useKeyWalkThroughOpenDoor() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor",
						"c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

		// pick up key
		res = dmc.tick(Direction.RIGHT);
		Position pos = getEntities(res, "player").get(0).getPosition();
		assertEquals(1, getInventory(res, "key").size());

		// walk through door and check key is gone
		res = dmc.tick(Direction.RIGHT);
		assertEquals(0, getInventory(res, "key").size());
		assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
	}

	@Test
	@DisplayName("Test player cannot have two keys in bag at the same time")
	public void TestCannotPickUpTwoKey() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_twoKeyTest",
						"c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

		// pick up key
		res = dmc.tick(Direction.DOWN);
		res = dmc.tick(Direction.RIGHT);
		assertEquals(1, getInventory(res, "key").size());

		// walk through door and check key is gone
		res = dmc.tick(Direction.UP);
		assertEquals(1, getInventory(res, "key").size());
		Position pos = getEntities(res, "player").get(0).getPosition();
		assertEquals(new Position(2, 1), pos);
	}

	@Test
	@DisplayName("Test player can use the correct key to open and walk through a related door")
	public void TestTwoKey() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_twoKeyTest",
						"c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

		// pick up key
		res = dmc.tick(Direction.DOWN);
		res = dmc.tick(Direction.RIGHT);
		assertEquals(1, getInventory(res, "key").size());

		// walk through door and check key is gone
		res = dmc.tick(Direction.RIGHT);
		assertEquals(0, getInventory(res, "key").size());
		Position pos = getEntities(res, "player").get(0).getPosition();
		assertEquals(new Position(3, 2), pos);
	}

	@Test
	@DisplayName("Test player cannot open a door with a wrong key")
	public void TestCannotOpenDoorWithWrongKey() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_twoKeyTest",
						"c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

		// pick up the key
		res = dmc.tick(Direction.RIGHT);
		res = dmc.tick(Direction.DOWN);
		assertEquals(1, getInventory(res, "key").size());

		// The player cannot pass
		Position posPlayer = getEntities(res, "player").get(0).getPosition();
		assertEquals(new Position(2, 2), posPlayer);
		assertEquals(1, getInventory(res, "key").size());
	}

	@Test
	@DisplayName("Test player can pass opened door without consuming key")
	public void TestDoorKeyAdvanced() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_twoKeyTest",
						"c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

		// pick up key
		res = dmc.tick(Direction.RIGHT);
		assertEquals(1, getInventory(res, "key").size());

		res = dmc.tick(Direction.RIGHT);
		assertEquals(0, getInventory(res, "key").size());

		res = dmc.tick(Direction.RIGHT);
		assertEquals(0, getInventory(res, "key").size());

		// walk through the opened door
		res = dmc.tick(Direction.LEFT);
		assertEquals(0, getInventory(res, "key").size());
	}

	@Test
	@DisplayName("Test if you can place a bomb")
	public void TestPlaceBomb() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2",
						"c_bombTest_placeBombRadius2");

		// pick up bomb
		res = dmc.tick(Direction.DOWN);
		res = dmc.tick(Direction.RIGHT);
		Position pos = getEntities(res, "player").get(0).getPosition();
		assertEquals(new Position(3, 3), pos);
		assertEquals(1, getInventory(res, "bomb").size());

		// Place the bomb
		String bombId = getInventory(res, "bomb").get(0).getId();
		res = assertDoesNotThrow(() -> dmc.tick(bombId));

		assertEquals(0, getInventory(res, "bomb").size());
		assertEquals(1, getEntities(res, "bomb").size());
	}

	@Test
	@DisplayName("Test you can't pass a placed bomb")
	public void TestCannotPassPlacedBomb() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2",
						"c_bombTest_placeBombRadius2");

		// pick up bomb
		res = dmc.tick(Direction.DOWN);
		res = dmc.tick(Direction.RIGHT);
		Position pos = getEntities(res, "player").get(0).getPosition();
		assertEquals(new Position(3, 3), pos);
		assertEquals(1, getInventory(res, "bomb").size());

		// Place the bomb
		String bombId = getInventory(res, "bomb").get(0).getId();
		res = assertDoesNotThrow(() -> dmc.tick(bombId));

		// Cannot move on this bomb and pick it up again
		res = dmc.tick(Direction.RIGHT);
		res = dmc.tick(Direction.LEFT);
		pos = getEntities(res, "player").get(0).getPosition();
		assertEquals(new Position(4, 3), pos);
		assertEquals(0, getInventory(res, "bomb").size());
	}

	@Test
	@DisplayName("Test BombId does not change")
	public void TestBombId() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2",
						"c_bombTest_placeBombRadius2");

		String idBefore = getEntities(res, "bomb").get(0).getId();
		// pick up bomb
		res = dmc.tick(Direction.DOWN);
		res = dmc.tick(Direction.RIGHT);
		String idPickedUp = getInventory(res, "bomb").get(0).getId();
		assertEquals(idBefore, idPickedUp);

		// Place the bomb
		res = assertDoesNotThrow(() -> dmc.tick(idPickedUp));

		// Check Id
		String idAfter = getEntities(res, "bomb").get(0).getId();
		assertEquals(idBefore, idAfter);

	}

	@Test
	@DisplayName("Test if you can place a bomb correctly when you have two bomb in your bag")
	public void TestPlaceOneOfTwoBombs() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_TwoBombsTest",
						"c_bombTest_placeBombRadius2");

		// pick up bomb
		res = dmc.tick(Direction.DOWN);
		res = dmc.tick(Direction.RIGHT);
		Position pos = getEntities(res, "player").get(0).getPosition();
		assertEquals(new Position(3, 3), pos);
		assertEquals(1, getInventory(res, "bomb").size());

		// The second bomb
		res = dmc.tick(Direction.RIGHT);
		assertEquals(2, getInventory(res, "bomb").size());

		// Place the bomb
		String bombId = getInventory(res, "bomb").get(1).getId();
		res = assertDoesNotThrow(() -> dmc.tick(bombId));

		assertEquals(1, getInventory(res, "bomb").size());
		assertEquals(1, getEntities(res, "bomb").size());
	}

	@Test
	@DisplayName("Test placeing a bomb cardinally adjacent to an inactive switch does not cause the bomb to explode")
	public void TestBombNotExplode() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2",
						"c_bombTest_placeBombRadius2");

		// pick up bomb
		res = dmc.tick(Direction.DOWN);
		res = dmc.tick(Direction.RIGHT);

		assertEquals(1, getInventory(res, "bomb").size());
		res = dmc.tick(Direction.RIGHT);

		// Place the bomb
		String bombId = getInventory(res, "bomb").get(0).getId();
		res = assertDoesNotThrow(() -> dmc.tick(bombId));

		assertEquals(0, getInventory(res, "bomb").size());
		assertEquals(1, getEntities(res, "bomb").size());
		Position posBomb = getEntities(res, "bomb").get(0).getPosition();
		assertEquals(new Position(4, 3), posBomb);

		assertEquals(1, getEntities(res, "player").size());
		// Other entities should also haven't been removed
		assertEquals(1, getEntities(res, "switch").size());
		assertEquals(2, getEntities(res, "wall").size());
		assertEquals(2, getEntities(res, "treasure").size());
		assertEquals(1, getEntities(res, "boulder").size());
		assertEquals(1, getEntities(res, "bomb").size());
	}

	@Test
	@DisplayName("Test bomb can explode")
	public void TestBomb() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2",
						"c_bombTest_placeBombRadius2");

		// pick up bomb
		res = dmc.tick(Direction.DOWN);
		res = dmc.tick(Direction.RIGHT);

		assertEquals(1, getInventory(res, "bomb").size());
		res = dmc.tick(Direction.RIGHT);

		// Place the bomb
		String bombId = getInventory(res, "bomb").get(0).getId();
		res = assertDoesNotThrow(() -> dmc.tick(bombId));

		assertEquals(0, getInventory(res, "bomb").size());
		assertEquals(1, getEntities(res, "bomb").size());
		Position posBomb = getEntities(res, "bomb").get(0).getPosition();
		assertEquals(new Position(4, 3), posBomb);

		// Go back and push the boulder
		res = dmc.tick(Direction.LEFT);
		res = dmc.tick(Direction.LEFT);
		res = dmc.tick(Direction.UP);
		res = dmc.tick(Direction.RIGHT);

		// Check the positions of the player
		Position posPlayer = getEntities(res, "player").get(0).getPosition();
		assertEquals(new Position(3, 2), posPlayer);

		// Now, switch is active, bomb should explode
		// Player should be alive
		assertEquals(1, getEntities(res, "player").size());
		// Other entities should have been removed
		assertEquals(0, getEntities(res, "switch").size());
		assertEquals(0, getEntities(res, "wall").size());
		assertEquals(0, getEntities(res, "treasure").size());
		assertEquals(0, getEntities(res, "boulder").size());
		assertEquals(0, getEntities(res, "bomb").size());
	}

	@Test
	@DisplayName("Test player can pick up multiple entities at the same time")
	public void TestPlayerCanPickUpMultipleEntitiesAtOneTime() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_pickupTwoEntities",
						"c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

		// pick up
		res = dmc.tick(Direction.RIGHT);
		assertEquals(1, getInventory(res, "key").size());

		assertEquals(1, getInventory(res, "bomb").size());
	}
	@Test
	@DisplayName("Test player can pick up multiple entities at the same time")
	public void TestPlayerDoor() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("testdoorWFNO31657999539.7356172",
						"c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

		// pick up
		res = dmc.tick(Direction.RIGHT);
		assertEquals(1, getInventory(res, "key").size());
	}

	@Test
	@DisplayName("Test zombie will stuck on the swamp")
	public void TestSwampWithZombie() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_SwampTileTest_zombie",
						"c_collectTests");

		// zombie spwan and walk on the swamp
		res = dmc.tick(Direction.DOWN);
		assertEquals(new Position(4, 1), getEntities(res, "zombie").get(0).getPosition());

		// stuck on the swamp as movement factor is 2
		res = dmc.tick(Direction.DOWN);
		res = dmc.tick(Direction.DOWN);
		assertEquals(new Position(4, 1), getEntities(res, "zombie").get(0).getPosition());

		// Leave the swamp
		res = dmc.tick(Direction.DOWN);
		assertNotEquals(new Position(4, 1), getEntities(res, "zombie").get(0).getPosition());
	}

	@Test
	@DisplayName("Test mercenary will stuck on the swamp")
	public void TestSwampWithMercenary() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_SwampTileTest_mercenary",
						"c_collectTests");

		// mercenary walk on swamp
		res = dmc.tick(Direction.LEFT);
		assertEquals(new Position(4, 1), getEntities(res, "mercenary").get(0).getPosition());

		// stuck on swamp
		res = dmc.tick(Direction.LEFT);
		res = dmc.tick(Direction.LEFT);
		assertEquals(new Position(4, 1), getEntities(res, "mercenary").get(0).getPosition());

		// Leave the swamp
		res = dmc.tick(Direction.LEFT);
		assertEquals(new Position(3, 1), getEntities(res, "mercenary").get(0).getPosition());
	}

	@Test
	@DisplayName("Test spider will stuck on the swamp")
	public void TestSwampWithSpider() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_SwampTileTest_spider",
						"c_collectTests");

		// spider walk on swamp
		res = dmc.tick(Direction.DOWN);
		assertEquals(new Position(5, 0), getEntities(res, "spider").get(0).getPosition());

		// stuck on swamp
		 res = dmc.tick(Direction.DOWN);
		 res = dmc.tick(Direction.DOWN);
		assertEquals(new Position(5, 0), getEntities(res, "spider").get(0).getPosition());

		// Leave the swamp
		res = dmc.tick(Direction.DOWN);
		assertNotEquals(new Position(5, 0), getEntities(res, "spider").get(0).getPosition());
	}

	@Test
	@DisplayName("Test assassin will stuck on the swamp")
	public void TestSwampWithAssassin() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_SwampTileTest_assassin",
						"c_collectTests");

		// mercenary walk on swamp
		res = dmc.tick(Direction.LEFT);
		assertEquals(new Position(4, 1), getEntities(res, "assassin").get(0).getPosition());

		// stuck on swamp
		res = dmc.tick(Direction.LEFT);
		res = dmc.tick(Direction.LEFT);
		assertEquals(new Position(4, 1), getEntities(res, "assassin").get(0).getPosition());

		// Leave the swamp
		res = dmc.tick(Direction.LEFT);
		assertEquals(new Position(3, 1), getEntities(res, "assassin").get(0).getPosition());
	}

	@Test
	@DisplayName("Test hydra will stuck on the swamp")
	public void TestSwampWithHydra() {
		DungeonManiaController dmc;
		dmc = new DungeonManiaController();
		DungeonResponse res = dmc.newGame("d_SwampTileTest_hydra",
						"c_collectTests");

		// mercenary walk on swamp
		res = dmc.tick(Direction.LEFT);
		assertEquals(new Position(4, 1), getEntities(res, "hydra").get(0).getPosition());

		// stuck on swamp
		res = dmc.tick(Direction.LEFT);
		res = dmc.tick(Direction.LEFT);
		assertEquals(new Position(4, 1), getEntities(res, "hydra").get(0).getPosition());

		// Leave the swamp
		res = dmc.tick(Direction.LEFT);
		assertEquals(new Position(3, 1), getEntities(res, "hydra").get(0).getPosition());
	}

}
