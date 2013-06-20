package edu.asu.krypton.service;

import static org.junit.Assert.fail;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.model.persist.db.Album;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AlbumServiceTest {
	
	@Autowired
	private AlbumService albumService;

	public AlbumService getAlbumService() {
		return albumService;
	}

	public void setAlbumService(AlbumService albumService) {
		this.albumService = albumService;
	}

	@Test
	public void test() {
		Album album;
		
		albumService.getAlbumRepository().deleteAll();
		
		for(int i = 0; i < 10; i++){
			album = new Album();
			album.setTitle("Album"+i);
			albumService.saveOrUpdate(album);
		}
		
		List<Album> albumList = albumService.getAll();

		Assert.assertEquals(10, albumList.size());
		
		for(int i = 0; i < 10; i++){
			Assert.assertEquals(albumList.get(i).getTitle(), "Album"+i);
		}

		albumService.getAlbumRepository().deleteAll();
		
	}

}
