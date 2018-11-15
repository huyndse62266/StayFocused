package huynd.repository;

import huynd.domain.ActivitiesLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ActivitiesLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivitiesLogRepository extends JpaRepository<ActivitiesLog, Long> {

    @Query("select activities_log from ActivitiesLog activities_log where activities_log.user.login = ?#{principal.username}")
    List<ActivitiesLog> findByUserIsCurrentUser();


    List<ActivitiesLog> findAllByUserId(@Param("id") Long id);

    List<ActivitiesLog> findByActivitiesLogDateBetweenAndUserIdOrderByActivitiesLogDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("id") Long id);

    Optional<ActivitiesLog> findByActivitiesLogDateAndUserId(@Param("date") Date date, @Param("id") Long id);

    @Query("Select SUM(al.activitiesLogPointReceived) from ActivitiesLog al where al.user.id = :id")
    Long sumAtivitiesLogPointReceivedByUserID(@Param("id") Long id);

    @Query("Select SUM(al.activitiesLogAchievedTime) from ActivitiesLog al where al.user.id = :id")
    Long sumActivitiesLogAchievedTimeByUserID(@Param("id") Long id);
}
