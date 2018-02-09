package me.hii488.volcanoRush.containers.volcanoes;

import java.awt.Color;
import java.awt.event.MouseEvent;

import me.hii488.controllers.GameController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.handlers.ContainerHandler;
import me.hii488.misc.Settings;
import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.additionalTickers.LightHandler;
import me.hii488.volcanoRush.containers.generationAlgs.GenerationAlg;
import me.hii488.volcanoRush.dataTypes.Seismometer;
import me.hii488.volcanoRush.objects.entities.VRPlayer;
import me.hii488.volcanoRush.registers.ItemRegistry;

public abstract class Volcano extends BaseContainer{
	public GenerationAlg mineralSpawner;
	public Seismometer seismometer;
	
	public Volcano(){
		super();
		seismometer = new Seismometer();
		
		GUI pauseMenu = new GUI().setIdentifier("pauseMenu");

		GUILabel continueButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				GameController.isPaused = false;
				pauseMenu.hideAll();
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Continue")
				.setDimensions(70, 30).setPosition(GameController.getWindow().width/2-35, 300);
		
		GUILabel menuButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				ContainerHandler.loadNewContainer("mainmenu");
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("To Menu")
				.setDimensions(70, 30).setPosition(GameController.getWindow().width/2-35, 400);
		
		GUILabel backgroundShade = (GUILabel) new GUILabel().setFill(true).setOutlineColor(new Color(0f,0f,0f,0.5f))
				.setPosition(0, 0).setDimensions(GameController.getWindow().width, GameController.getWindow().height);
		
		pauseMenu.addElement(backgroundShade);
		pauseMenu.addElement(continueButton);
		pauseMenu.addElement(menuButton);
		pauseMenu.hideAll();
		
		guis.add(pauseMenu);
		
	}
	
	public void onLoad(){
		super.onLoad();
		((VRPlayer) EntityRegistry.player).movementAllowed = true;
		EntityRegistry.player.position.setLocation(grid.dimensions.getX()/2 * Settings.Texture.tileSize, 60);
		ItemRegistry.doEquips();
		mineralSpawner.populate(grid);
		LightHandler.setInitialLight();
		seismometer.setCurrentActivity(0);
	}
	
	public void updateOnSec(){
		if(seismometer.isEnabled() && seismometer.getCurrentActivity() > seismometer.getMaxActivity()) erupt();
	}
	
	public void erupt(){
		
	}
	
}
