package app.repository;

import app.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITripRepository extends JpaRepository<TripEntity,String> {
}
