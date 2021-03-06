package me.hii488.volcanoRush.objects.entities;

import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.EntityHandler;
import me.hii488.misc.Grid;
import me.hii488.misc.Settings;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.registries.EntityRegistry;
import me.hii488.registries.TileRegistry;
import me.hii488.volcanoRush.containers.volcanoes.Volcano;
import me.hii488.volcanoRush.dataTypes.DeathCause;
import me.hii488.volcanoRush.dataTypes.OreType;
import me.hii488.volcanoRush.objects.tiles.DirtTile;
import me.hii488.volcanoRush.objects.tiles.LightTile;

public class FallingDirt extends VREntity{

	public OreType oreType;
	
	public FallingDirt(){
		super();
	}
	public FallingDirt(FallingDirt f){
		super(f);
		this.oreType = f.oreType;
	}
	
	@Override
	public void initVars() {
		this.identifier = "fallingDirt";
		this.textureName = "dirtTile_NONE.png";
	}
	
	public void updateOnTick(){
		super.updateOnTick();
		Grid g = ContainerHandler.getLoadedContainer().grid;
		if(g.getTileAtVector(position.clone().addToLocation(0, Settings.Texture.tileSize)).isCollidable){
			this.destroy();
			DirtTile d = (DirtTile) TileRegistry.getTile("dirtTile");
			d.setOreType(oreType);
			d.damageValue = 3;
			
			BaseTile t = g.getTileAtVector(position);
			if(t instanceof LightTile) d.setLowestLight(((LightTile) t).lowestLight);
			ContainerHandler.getLoadedContainer().grid.setTile(d, Grid.getGridPosAtVector(position));
			
			if(ContainerHandler.getLoadedContainer() instanceof Volcano)
				((Volcano) ContainerHandler.getLoadedContainer()).seismometer.addToCurrentActivity(10);
		}
		else{
			this.position.addToLocation(0, Settings.Texture.tileSize/2);
			if(EntityHandler.getCollidingEntities(this).contains(EntityRegistry.player)) ((VRPlayer) EntityRegistry.player).kill(DeathCause.CRUSH);
		}
	}
	
	public void setOreType(OreType o) {
		oreType = o;
		this.identifier = "fallingDirt" + this.oreType;
		this.textureName = "dirtTile_" + oreType + ".png";
		this.setupTextures("tiles");
	}
	
	@Override
	public float randTickChance() {return 0;}

	@Override
	public void updateOnSec() {}

	@Override
	public void updateOnRandTick() {}

	@Override
	public void onLoad() {}

	@Override
	public void onDestroy() {}

	@Override
	public FallingDirt clone() {
		return new FallingDirt(this);
	}

}
