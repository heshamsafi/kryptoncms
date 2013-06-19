package edu.asu.krypton.model.repository;

import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.Album;

@Repository
public class AlbumRepository extends edu.asu.krypton.model.repository.Repository<Album>{

	public AlbumRepository(){
		setPersistentClass(Album.class);
	}
}
