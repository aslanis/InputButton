package ru.ncedu.java.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ncedu.java.domain.NewData;

public interface NewDataRepos extends CrudRepository<NewData, Long> {

}
