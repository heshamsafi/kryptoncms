package edu.asu.krypton.model.repository;

import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.Role;

@Repository
public class RoleRepository extends edu.asu.krypton.model.repository.Repository<Role>{
	//private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	public RoleRepository(){
		setPersistentClass(Role.class);
	}
}
