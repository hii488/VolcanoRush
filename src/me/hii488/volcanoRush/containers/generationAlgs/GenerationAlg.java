package me.hii488.volcanoRush.containers.generationAlgs;

import me.hii488.misc.Grid;
import me.hii488.misc.Vector;
import me.hii488.volcanoRush.dataTypes.OreType;

public abstract class GenerationAlg {
	
	public abstract void populate(Grid g);
	public abstract void populate(Grid g, long seed);
	public abstract OreType getOreType(int x, int y);
	
	public OreType getOreType(Vector v){
		return getOreType(v.getX(), v.getY());
	}
}
