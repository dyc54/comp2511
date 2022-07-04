package dungeonmania;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.response.models.*;

public class Game {
    
    //用于返回DungeonResponse
    private String dungeonId;
    private String dungeonName;
    private List<EntityResponse> entities;
    private List<ItemResponse> inventory;
    private List<BattleResponse> battles;
    private List<String> buildables;
    private String goals;
    private List<AnimationQueue> animations;

    //用于维护地图上实体的状态，改变移动实体的位置，更新背包
    private DungeonMap dungeonMap;
    private List<Entity> entitiesList;
    private List<Entity> inventoryList;
    /* private List<Battle> battlesList; */
    

    public Game (String path, Config dungeonConfig) throws IOException{
        String content = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject json =  new JSONObject(content);
        //根据joson 文件创建实例，将实例添加到map 和 entitiesList
        JSONArray entities = json.getJSONArray("entities");
        for (int i = 0; i < entities.length(); i++) {
            JSONObject entity = entities.getJSONObject(i);
            entitiesList.add(EntityController.newEntity(entity, dungeonConfig));
            dungeonMap.addEntity(EntityController.newEntity(entity, dungeonConfig));
        }

    }


    public void setInventoryList(List<Entity> inventoryList) {
        this.inventoryList = inventoryList;
    }


    // 更新返回Response
    private void setEntities() {
        List<EntityResponse> entities = new ArrayList<>();
        for(Entity entitie : entitiesList){
            entities.add(entitie.getEntityResponse());
        }
        this.entities = entities;
    }

    private void setBattles() {
        
    }

    private void setBuildables() {
        
    }

    private void setAnimations() {

    }

    // 返回 DungeonResponse
    public DungeonResponse getDungeonResponse() {
        return new DungeonResponse(dungeonId, dungeonName,entities, inventory, battles, buildables, goals, animations);
    }
}
