package com.novaperutech.veyra.platform.nursing.domain.model.entities;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.RoomOccupancy;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.RoomStatus;
import com.novaperutech.veyra.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Room entity.
 * <p>
 * This entity represents a room in a nursing home.
 * A room can be assigned to residents and has a capacity and occupancy.
 * </p>
 * @see NursingHome
 * @see Resident
 * @since 1.0
 */
@Getter
@Entity
public class Room extends AuditableModel {

    @ManyToOne
    @JoinColumn(name = "nursing_home_id")
    @NotNull
    private NursingHome nursingHome;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @NotNull
    @Column(nullable = false)
    private String roomNumber;

    @NotNull
    @Column(nullable = false)
    private String type;

    @Embedded
    @NotNull
    private RoomOccupancy roomOccupancy;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private RoomStatus roomStatus;

    /**
     * Default constructor.
     */
    public Room() {
        super();
    }

    /**
     * Constructor with nursing home, capacity, and type.
     * @param nursingHome the nursing home
     * @param capacity the capacity of the room
     * @param type the type of the room
     */
    public Room(NursingHome nursingHome, Integer capacity, String type,String roomNumber) {
        this();
        this.nursingHome = nursingHome;
        this.type = type;
        this.roomOccupancy = new RoomOccupancy(capacity, 0);
        this.roomNumber=roomNumber;
        this.roomStatus = RoomStatus.AVAILABLE;
    }

    /**
     * Assign a resident to the room.
     * @param resident the resident to assign
     * @throws IllegalStateException if room is not available or at full capacity
     */
    public void assignResident(Resident resident) {
        if (this.roomStatus != RoomStatus.AVAILABLE) {
            throw new IllegalStateException("Room is not available");
        }
        if (this.roomOccupancy.isFull()) {
            throw new IllegalStateException("Room is at full capacity");
        }

        this.resident = resident;
        this.roomOccupancy = this.roomOccupancy.incrementOccupiedSlots(1);

        if (this.roomOccupancy.isFull()) {
            this.roomStatus = RoomStatus.OCCUPIED;
        }
    }

    /**
     * Change the resident assigned to this room.
     * @param newResident the new resident
     * @throws IllegalStateException if no resident is currently assigned
     */
    public void changeResident(Resident newResident) {
        if (this.resident == null) {
            throw new IllegalStateException("No resident currently assigned to this room");
        }
        this.resident = newResident;
    }

    /**
     * Remove the resident from the room.
     * @throws IllegalStateException if no resident is currently assigned
     */
    public void removeResident() {
        if (this.resident == null) {
            throw new IllegalStateException("No resident currently assigned to this room");
        }

        this.resident = null;
        this.roomOccupancy = this.roomOccupancy.release(1);

        if (!this.roomOccupancy.isFull()) {
            this.roomStatus = RoomStatus.AVAILABLE;
        }
    }

    /**
     * Check if the room is available for assignment.
     * @return true if available, false otherwise
     */
    public boolean isAvailable() {
        return this.roomStatus == RoomStatus.AVAILABLE && !this.roomOccupancy.isFull();
    }

}