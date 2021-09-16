package app.repository;

import app.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISearchRepository extends JpaRepository<SearchEntity,String> {
}
