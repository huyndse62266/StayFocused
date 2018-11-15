package huynd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

/**
 * A ActivitiesLog.
 */
@Entity
@Table(name = "activities_log")
public class ActivitiesLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "activities_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activitiesLogID;

    @Column(name = "activities_log_date")
    private Date activitiesLogDate;

    @Column(name = "activities_log_point_received")
    private Long activitiesLogPointReceived;

    @Column(name = "activities_log_achieved_time")
    private Long activitiesLogAchievedTime;

    @ManyToOne(fetch = FetchType.EAGER,
        cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH
        })
    @JoinColumn(name = "id")
    @JsonIgnoreProperties("activitiesLog")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Long getActivitiesLogID() {
        return activitiesLogID;
    }

    public ActivitiesLog activitiesLogID(Long activitiesLogID) {
        this.activitiesLogID = activitiesLogID;
        return this;
    }

    public void setActivitiesLogID(Long activitiesLogID) {
        this.activitiesLogID = activitiesLogID;
    }

    public Date getActivitiesLogDate() {
        return activitiesLogDate;
    }

    public ActivitiesLog activitiesLogDate(Date activitiesLogDate) {
        this.activitiesLogDate = activitiesLogDate;
        return this;
    }

    public void setActivitiesLogDate(Date activitiesLogDate) {
        this.activitiesLogDate = activitiesLogDate;
    }

    public Long getActivitiesLogPointReceived() {
        return activitiesLogPointReceived;
    }

    public ActivitiesLog activitiesLogPointReceived(Long activitiesLogPointReceived) {
        this.activitiesLogPointReceived = activitiesLogPointReceived;
        return this;
    }

    public void setActivitiesLogPointReceived(Long activitiesLogPointReceived) {
        this.activitiesLogPointReceived = activitiesLogPointReceived;
    }

    public Long getActivitiesLogAchievedTime() {
        return activitiesLogAchievedTime;
    }

    public ActivitiesLog activitiesLogAchievedTime(Long activitiesLog) {
        this.activitiesLogAchievedTime = activitiesLog;
        return this;
    }

    public void setActivitiesLogAchievedTime(Long activitiesLog) {
        this.activitiesLogAchievedTime = activitiesLog;
    }

    public User getUser() {
        return user;
    }

    public ActivitiesLog user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivitiesLog activitiesLog = (ActivitiesLog) o;
        if (activitiesLog.getActivitiesLogID() == null || getActivitiesLogID() == null) {
            return false;
        }
        return Objects.equals(getActivitiesLogID(), activitiesLog.getActivitiesLogID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getActivitiesLogID());
    }

    @Override
    public String toString() {
        return "ActivitiesLog{" +
            "activitiesLogID=" + activitiesLogID +
            ", activitiesLogDate=" + activitiesLogDate +
            ", activitiesLogPointReceived=" + activitiesLogPointReceived +
            ", activitiesLogAchievedTime=" + activitiesLogAchievedTime +
            ", user=" + user +
            '}';
    }
}
