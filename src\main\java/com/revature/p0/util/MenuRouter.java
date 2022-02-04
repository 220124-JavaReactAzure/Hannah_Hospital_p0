package com.revature.p0.util;
import com.revature.p0.menus.*;
import com.revature.p0.util.collections.*;


public class MenuRouter<T> {
	private final ArrayList<Menu> menus;
	
	public MenuRouter(){
		menus = new ArrayList<Menu>();
	}
	
	public void addMenu(Menu menu) {
		menus.add(menu);
		
	}
	
	public void transfer(String menuRoute) {
		for(int i = 0; i< menus.size(); i++) {
			Menu presentMenu = menus.get(i);
			if(presentMenu.getMenuRoute().equals(menuRoute)){
				presentMenu.renderMenu();
			}
		}
	}
	
	
	
	

}
