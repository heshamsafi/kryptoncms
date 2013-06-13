package edu.asu.krypton.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.MenuMessage;
import edu.asu.krypton.model.persist.db.Menu;
import edu.asu.krypton.model.repository.MenuRepository;

@Service
public class MenuService {

	@Autowired 
	private MenuRepository menuRepository;
	
	public List<Menu> getItems(boolean admin) {
		return menuRepository.getItems(admin);
	}

	public void insert(Menu menu) throws CustomRuntimeException{
		menuRepository.saveOrUpdate(menu);
	}

	public void rearrangeMenu(MenuMessage menuMessage) {
		menuRepository.rearrangeMenu(menuMessage);
	}

	public void delete(ObjectId operandId) {
		menuRepository.deleteById(operandId);
	}
}
