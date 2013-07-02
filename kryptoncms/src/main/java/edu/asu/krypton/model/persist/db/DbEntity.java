package edu.asu.krypton.model.persist.db;

import edu.asu.krypton.model.repository.Repository;

public interface DbEntity {
	public void onDelete(Repository<?> repository) throws ClassNotFoundException;
	public void onEdit(Repository<?> repository);
	public void onInsert(Repository<?> repository);
	public void merge(DbEntity newObject);
	public void addOwned(DbEntity owned);
}
