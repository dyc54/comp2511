package dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import dungeonmania.Inventories.Inventory;
import dungeonmania.helpers.LogicCondition;
import dungeonmania.helpers.LogicContent;

public class BuildableRecipeTree implements LogicContent<BuildableComponent> {
    private BuildableRecipematerial material;
    private BuildableRecipeTree materials;
    private final LogicCondition<BuildableComponent> condition;
    public BuildableRecipeTree(String logic, String type, int amount) {
        material = new BuildableRecipematerial(type, amount);
        materials = null;
        condition = new LogicCondition<>(logic);
    }
    public BuildableRecipeTree(String logic) {
        material = null;
        materials = null;
        condition = new LogicCondition<>(logic);
    }
    public BuildableRecipeTree add(BuildableComponent item) {
        if (material == null) {
            material = new BuildableRecipematerial(item.getItemType(), item.getItemAmount());
        } else if (materials == null){
            materials = new BuildableRecipeTree(condition.getLogic(), item.getItemType(), item.getItemAmount());
        } else {
            materials.add(new BuildableRecipematerial(item.getItemType(), item.getItemAmount()));
        }
        return this;
    }
    public void remove(Inventory inventory) {
        if (material == null) {
            return;
        }
        if (materials != null) {
            materials.remove(inventory);
        }
        material.remove(inventory);
    }
    public BuildableRecipeTree mapForAll(Consumer<BuildableComponent> func) {
        if (material != null) {
            func.accept(material);
            // materials.stream().forEach(treenode -> treenode.mapForAll(func));
        }
        if (materials != null) {
            materials.mapForAll(func);
        }
        return this;
    }
    // public int size() {
    //     if (material == 0) {
    //         return 0;
    //     }
    //     return 1 + materials.size();
    // }
    public boolean isSatisfied() {
        // System.out.println(String.format("%s %s %s", material == null, material.toString(), materials == null));
        // // boolean next = materials != null && materials.isSatisfied();
        // if (material == null || materials == null) {
        //     return false;
        // }
        if (material != null) {
            return material.isSatisfied();
            
        }
        // return condition.isTrue(this);
        return true;
    }
    @Override
    public BuildableComponent getContent() {
        return null;
    }
    
    @Override
    public boolean isTrue() {
        return isSatisfied();
    }
    @Override
    public LogicContent<BuildableComponent> getSubContentA() {
        // TODO Auto-generated method stub
        return this;
    }
    @Override
    public LogicContent<BuildableComponent> getSubContentB() {
        // TODO Auto-generated method stub
        return materials;
    }
    @Override
    public String toString() {
        return String.format("%s %s", material.toString(), materials.toString());
    }
    
}
