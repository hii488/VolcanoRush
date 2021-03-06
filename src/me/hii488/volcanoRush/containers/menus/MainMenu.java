package me.hii488.volcanoRush.containers.menus;

import java.awt.Color;
import java.awt.event.MouseEvent;

import me.hii488.controllers.GameController;
import me.hii488.graphics.GUI.GUI;
import me.hii488.graphics.GUI.GUILabel;
import me.hii488.handlers.ContainerHandler;
import me.hii488.objects.containers.BaseContainer;
import me.hii488.registries.EntityRegistry;
import me.hii488.volcanoRush.objects.entities.VRPlayer;

public class MainMenu extends BaseContainer {
	
	public MainMenu(){
		grid.setupGrid(10, 10);
		System.out.println(grid.getTile(0, 0).identifier);
		this.identifier = "mainmenu";
		
		GUI main = new GUI();
		GUILabel startButton = (GUILabel) new GUILabel(){
			@Override
			public void onClick(MouseEvent e){
				if(e.getButton() != MouseEvent.BUTTON1) return;
				ContainerHandler.loadNewContainer("shop");			
			}
		}.setFill(false).setTextColor(Color.white).setOutlineColor(Color.white).setText("Start Game").setDimensions(100, 40).setPosition(GameController.getWindow().width/2-50, GameController.getWindow().height/2-20);
		
		main.addElement(startButton);
		guis.add(main);
		
	}
	
	@Override
	public void onLoad(){
		super.onLoad();
		this.showEntities(false);
		((VRPlayer) EntityRegistry.player).movementAllowed = false;
	}
	
}
