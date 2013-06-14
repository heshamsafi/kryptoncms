package edu.asu.krypton.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.Menu;


/**
 * @author hesham
 * this is great for prefilling the database (Y) aswell as testing
 */
//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MenuServiceTest{
	
	@Autowired
	private MenuService menuService;

	@Test
	public void insertMenus() throws CustomRuntimeException{
		Menu[] menus = new Menu[]{
				new Menu().setAdmin(true).setName("Scaffold").setUrl("/scaffold").setOrder(1),
				new Menu().setAdmin(true).setName("Article").setUrl("/article/edit").setOrder(2),
				new Menu().setAdmin(true).setName("Themes").setUrl("/themes").setOrder(3),
				new Menu().setAdmin(true).setName("Web Services").setUrl("/webservices/admin").setOrder(4),
				new Menu().setAdmin(true).setName("Navigation").setUrl("/navigation").setOrder(5),
		};
		for(Menu menu:menus){
			menuService.insert(menu);
		}
	}
}
