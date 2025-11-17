package com.novaperutech.veyra.platform.nursing.domain.model.aggregates;

import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.BusinessProfileId;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.Rooms;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class NursingHome extends AuditableAbstractAggregateRoot<NursingHome> {
    @Embedded
    private BusinessProfileId businessProfileId;
    public NursingHome(){}
    public NursingHome(BusinessProfileId businessProfileId) {
        this.businessProfileId = businessProfileId;
    }
    @Embedded
    private Rooms rooms;
    /**
     * Add a room to the nursing home.
     * @param capacity the capacity of the room
     * @param type the type of the room
     */
    public void addRoom(Integer capacity, String type) {
        this.rooms.addRoom(this, capacity, type);
    }
    /**
     * Assign a resident to a room.
     * @param roomNumber the room number
     * @param resident the resident to assign
     */
    public void assignResidentToRoom(String roomNumber, Resident resident) {
        var room = this.rooms.getRoomByRoomNumber(roomNumber);
        room.assignResident(resident);
    }
    /**
     * Change resident in a room.
     * @param roomNumber the room number
     * @param newResident the new resident
     */
    public void changeResidentInRoom(String roomNumber, Resident newResident) {
        var room = this.rooms.getRoomByRoomNumber(roomNumber);
        room.changeResident(newResident);
    }
}
