package edu.asu.krypton.service;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.MenuItem;


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
		MenuItem menuItem = new MenuItem().setAdmin(false).setName("menu1").setUrl("").setOrder(1);
		MenuItem submenu = new MenuItem().setAdmin(false).setName("submenu1").setUrl("").setOrder(1);
		MenuItem subsubmenu = new MenuItem().setAdmin(false).setName("submenu1").setUrl("").setOrder(1);
		submenu.getMenuItems().add(subsubmenu);
		submenu.getMenuItems().add(subsubmenu);
		submenu.getMenuItems().add(subsubmenu);
		subsubmenu.getMenuItems().add(new MenuItem().setAdmin(false).setName("subsubmenu").setUrl("").setOrder(1));
		subsubmenu.getMenuItems().add(new MenuItem().setAdmin(false).setName("subsubmenu").setUrl("").setOrder(2));
		subsubmenu.getMenuItems().add(new MenuItem().setAdmin(false).setName("subsubmenu").setUrl("").setOrder(3));
		menuItem.getMenuItems().add(submenu.setName("submenu1").setOrder(1));
		menuItem.getMenuItems().add(submenu.setName("submenu2").setOrder(2));
		menuItem.getMenuItems().add(submenu.setName("submenu3").setOrder(3));
		menuItem.getMenuItems().add(submenu.setName("submenu4").setOrder(4));
		menuItem.getMenuItems().add(submenu.setName("submenu5").setOrder(5));
		MenuItem[] menus = new MenuItem[]{
				new MenuItem().setAdmin(true).setName("Scaffold").setUrl("/scaffold").setOrder(1),
				new MenuItem().setAdmin(true).setName("Article").setUrl("/article/edit").setOrder(2),
				new MenuItem().setAdmin(true).setName("Themes").setUrl("/themes").setOrder(3),
				new MenuItem().setAdmin(true).setName("Web Services").setUrl("/webservices/admin").setOrder(4),
				new MenuItem().setAdmin(true).setName("Navigation").setUrl("/navigation").setOrder(5),
				
				menuItem,
				new MenuItem(menuItem).setName("menu1").setOrder(2),
				new MenuItem(menuItem).setName("menu2").setOrder(3),
				new MenuItem(menuItem).setName("menu3").setOrder(4),
				new MenuItem(menuItem).setName("menu4").setOrder(5)
		};
		for(MenuItem menu:menus){
			menuService.insert(menu);
		}
	}
	
//	@Test
//	public void getMenus(){
//		List<MenuItem> menuItems = menuService.getItems(true);
//		for(MenuItem menuItem : menuItems)
//			System.out.println(menuItem);
//	}
}
