package me.hii488.volcanoRush.objects.entities;

import java.awt.Rectangle;
import java.util.ArrayList;

import me.hii488.controllers.GameController;
import me.hii488.handlers.ContainerHandler;
import me.hii488.handlers.EntityHandler;
import me.hii488.misc.Grid;
import me.hii488.misc.Settings;
import me.hii488.misc.Vector;
import me.hii488.objects.entities.BaseEntity;
import me.hii488.objects.tiles.BaseTile;
import me.hii488.volcanoRush.containers.volcanoes.Volcano;
import me.hii488.volcanoRush.dataTypes.DeathCause;
import me.hii488.volcanoRush.objects.tiles.AirTile;
import me.hii488.volcanoRush.objects.tiles.DirtTile;
import me.hii488.volcanoRush.objects.tiles.RockTile;
import me.hii488.volcanoRush.registers.FluidRegistry;

public class DynamiteEntity extends GravityEntity{

	public int fuse = 90;
	
	public DynamiteEntity() {super();}
	public DynamiteEntity(DynamiteEntity e) {
		super(e);
		this.fuse = 90;
	}
	
	@Override
	public void initVars() {
		this.identifier = "dynamite";
		this.textureName = "dynamite.png";
		this.states = 3;
		this.currentState = 2;
		this.fuse = 90;
		this.acceleration = 1;
		this.maxFallSpeed = 13;
	}
	
	public void updateOnTick() {
		super.updateOnTick();
		fuse--;
		if(fuse == 60 || fuse == 59) currentState = 1;
		if(fuse <= 20) currentState = 0;
		if(fuse <= 0) explode();
	}
	
	public void explode() {
		Grid g = ContainerHandler.getLoadedContainer().grid;
		Vector p = Grid.getGridPosAtVector(position), q;
		BaseTile t;
		
		if(g.getTile(p) instanceof AirTile) { // If it's in water, exit.
			if(((AirTile) g.getTile(p)).fluidContent.get(FluidRegistry.getFluid("water")) != 0) {
				this.destroy();
				return;
			}
		}
		
		int halfLength = (int) (Settings.Texture.tileSize * 1f);
		ArrayList<BaseEntity> entities = EntityHandler.getEntitiesIntersectingWithRect(new Rectangle(position.getX() - halfLength, position.getY() - halfLength, halfLength, halfLength));
		
		for(BaseEntity e : entities) {
			if(e instanceof VRPlayer) ((VRPlayer) e).kill(DeathCause.EXPLOSION);
			else if(e instanceof MineralItem) e.destroy();
		}
		
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				q = p.clone().addToLocation(i, j);
				t = g.getTile(q);
				if(t instanceof RockTile) {
					double d = ((RockTile) t).lowestLight;
					g.setTile("dirtTile", q);
					((DirtTile) g.getTile(q)).setOreType(((RockTile) t).oreType);
					((DirtTile) g.getTile(q)).damageValue += 3;
					((DirtTile) g.getTile(q)).setLowestLight(d);
				}
				
				if(t instanceof DirtTile) {
					((DirtTile) g.getTile(q)).damageValue += 4 + GameController.rand.nextInt(3)-1;
				}
			}
		}
		
		if(ContainerHandler.getLoadedContainer() instanceof Volcano)
			((Volcano) ContainerHandler.getLoadedContainer()).seismometer.addToCurrentActivity(30);
		
		
		this.destroy();
	}
	
	@Override
	public DynamiteEntity clone() {
		return new DynamiteEntity(this);
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


}
