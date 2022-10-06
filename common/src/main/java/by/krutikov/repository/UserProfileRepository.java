package by.krutikov.repository;

import by.krutikov.domain.UserProfile;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    @Query(
            value = "select up.id, " +
                    "up.media_id, " +
                    "up.is_visible, " +
                    "up.instrument_id, " +
                    "up.experience_id, " +
                    "up.date_modified, " +
                    "up.date_created, " +
                    "up.cell_phone_number, " +
                    "up.description, " +
                    "up.account_id, " +
                    "up.location, " +
                    "up.displayed_name, " +
                    "round(cast(st_distancesphere(location, :userLocation) as numeric), 2) as distance " +
                    "from bandhub.user_profiles up order by distance",
            nativeQuery = true
    )
    List<UserProfile> findAllProfilesOrderedByDistance(@Param("userLocation") Point userLocation);
}
