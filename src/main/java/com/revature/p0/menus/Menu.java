package com.revature.p0.menus;

import java.io.BufferedReader;
import java.io.IOException;

import com.revature.p0.menus.*;
import com.revature.p0.util.MenuRouter;

public abstract class Menu {

	private String menuName;
	private String menuRoute;
	protected BufferedReader bufferedReader;
	protected  MenuRouter menuRouter;

	public Menu(String menuName, String menuRoute, BufferedReader bufferedReader, MenuRouter menuRouter) {
		super();
		this.menuName = menuName;
		this.menuRoute = menuRoute;
		this.bufferedReader = bufferedReader;
		this.menuRouter = menuRouter;
	}
	
	public abstract void renderMenu();
	
	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuRoute() {
		return menuRoute;
	}

	public void setMenuRoute(String menuRoute) {
		this.menuRoute = menuRoute;
	}

}
