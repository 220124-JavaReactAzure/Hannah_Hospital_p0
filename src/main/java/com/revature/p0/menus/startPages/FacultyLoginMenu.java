package com.revature.p0.menus.startPages;

import java.io.BufferedReader;
import java.io.IOException;

import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.menus.Menu;
import com.revature.p0.services.FacultyService;
import com.revature.p0.util.MenuRouter;

public class FacultyLoginMenu extends Menu {

	private final FacultyService facultyService;

	public FacultyLoginMenu(BufferedReader bufferedReader, MenuRouter menuRouter, FacultyService facultyService) {
		super("FacultyLogin", "/FacultyLoginMenu", bufferedReader, menuRouter);
		this.facultyService = facultyService;

	}

	@Override
	public void renderMenu() {
		try {
			System.out.println("Please enter your credentials for you account.");
			System.out.print("Username: ");
			String username = bufferedReader.readLine();
			System.out.print("Password: ");
			String password = bufferedReader.readLine();
			boolean result = facultyService.validateUser(username, password);

			if (result) {
				facultyService.authenticateFaculty(username, password);
				menuRouter.transfer("/FacultyDashboardMenu");
			} else {
				System.out.println(
						"You are not an authenticated faculty member. Routing you back to the Welcome Menu...");
				menuRouter.transfer("/welcome");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}