package edu.asu.krypton.service;

import java.io.IOException;
import java.util.List;

import org.atmosphere.cpr.MetaBroadcaster;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.MenuMessage;
import edu.asu.krypton.model.persist.db.MenuItem;
import edu.asu.krypton.model.repository.MenuRepository;

@Service
public class MenuService {

	@Autowired(required=true)
	private MenuRepository menuRepository;
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;
	
	public List<MenuItem> getItems(boolean admin) {
		return menuRepository.getItems(admin);
	}

	public void insert(MenuItem menu) throws CustomRuntimeException{
		menuRepository.saveOrUpdate(menu);
	}

	public void rearrangeMenu(MenuMessage menuMessage) {
		menuRepository.rearrangeMenu(menuMessage);
	}

	public void delete(String operandId) {
		menuRepository.deleteById(operandId);
	}

	public void broadcast(MenuMessage menuMessage) throws JsonGenerationException, JsonMappingException, IOException {
		if(menuMessage.getAction().equals("rearrange")) this.rearrangeMenu(menuMessage);
		if(menuMessage.getAction().equals("delete")) this.delete(menuMessage.getOperandId());
		MetaBroadcaster.getDefault().broadcastTo("/menu/echo", objectMapper.writeValueAsString(menuMessage));		
	}

	public Object getRootItems(boolean admin) {
		return menuRepository.getRootItems(admin);
	}
}
