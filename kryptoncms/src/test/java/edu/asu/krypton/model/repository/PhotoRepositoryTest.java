package edu.asu.krypton.model.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.Album;
import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.model.persist.db.Photo;

//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PhotoRepositoryTest {
	@Autowired(required=true)
	private AlbumRepository albumRepository;
	@Autowired(required=true)
	private PhotoRepository photoRepository;
	@Autowired(required=true)
	private CommentRepository commentRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(PhotoRepositoryTest.class);

	@Test
	public void save6Photos() throws CustomRuntimeException{
		Album album;
		Photo photo;
		Comment photoComment;
		
//		photoRepository.getDao().openSession();
		
		for(int i = 1; i <=2; i++){
			album = new Album();
			album.setTitle("album" + i);
			for(int j = 1; j <= 6; j++){
				photo = new Photo();
				photo.setPath(j + ".jpg");
				photo.setAlbum(album.getTitle());
				for(int k = 1; k <= 10; k++){
					photoComment = new Comment();
					photoComment.setContent("Album (" + i + ") Photo (" + j + ") Comment Test (" + k + ")");
//					photoComment.setParent(photo);
					photo.getComments().add(photoComment);
					commentRepository.saveOrUpdate(photoComment);
				}
				album.getPhotos().add(photo);
				photoRepository.saveOrUpdate(photo);
			}
			albumRepository.saveOrUpdate(album);
		}

//		Photo photo1;
//		Photo photo2;
//		Album album1 = new Album();
//		album1.setTitle("album1");
//		logger.debug("Album1 id = " + album1.getId());
//		Album album2 = new Album();
//		album2.setTitle("album2");
//		logger.debug("Album2 id = " + album2.getId());
//		for(int i=1;i<=6;i++){
//			logger.debug("inserting photo #"+i);
//			photo1 = new Photo();
//			photo2 = new Photo();
////			photoComment = new PhotoComment();
////			photoComment.setContent("Photo Comment Test");
////			photoComment.setParent(photo1);
//			photo1.setPath(i + ".jpg");
//			photo1.setAlbum(album1.getTitle());
////			photo1.setParent(album1);
////			photo1.getComments().add(photoComment);
//			photo2.setPath(i + ".jpg");
////			photo2.setParent(album2);
//			album1.getPhotos().add(photo1);
//			album2.getPhotos().add(photo2);
//			photoRepository.saveOrUpdate(photo1);
//			photoRepository.saveOrUpdate(photo2);
//			albumRepository.saveOrUpdate(album1);
//			albumRepository.saveOrUpdate(album2);
////			commentRepository.saveOrUpdate(photoComment);
//		}
//		photoRepository.getDao().killSession(true);
	}

	public AlbumRepository getAlbumRepository() {
		return albumRepository;
	}

	public void setAlbumRepository(AlbumRepository albumRepository) {
		this.albumRepository = albumRepository;
	}

	public PhotoRepository getPhotoRepository() {
		return photoRepository;
	}

	public void setPhotoRepository(PhotoRepository photoRepository) {
		this.photoRepository = photoRepository;
	}

	public CommentRepository getCommentRepository() {
		return commentRepository;
	}

	public void setCommentRepository(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
}
