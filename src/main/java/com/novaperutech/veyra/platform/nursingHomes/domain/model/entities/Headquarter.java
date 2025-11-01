package com.novaperutech.veyra.platform.nursingHomes.domain.model.entities;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.Address;
import com.novaperutech.veyra.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Represents a headquarter entity within a nursing home.
 * <p>
 * A headquarter is a physical location or branch associated with a nursing home.
 * Each headquarter has a title and a physical address, and belongs to a specific
 * nursing home. This entity extends {@link AuditableModel} to track creation and
 * modification timestamps.
 * </p>
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see NursingHome
 * @see Address
 * @see AuditableModel
 */
@Entity
@Getter
@NoArgsConstructor

public class Headquarter extends AuditableModel {
    /**
     * The nursing home to which this headquarter belongs.
     * <p>
     * This is a required field that establishes a many-to-one relationship
     * with the {@link NursingHome} aggregate.
     * </p>
     */
@NotNull
@ManyToOne
@JoinColumn(name = "nursing_home_id",nullable = false)
private NursingHome nursingHome;
    /**
     * The title or name of the headquarter.
     * <p>
     * This field represents the identifying name for this headquarter
     * (e.g., "Main Branch", "North Location").
     * </p>
     */
@NotNull
@Column(name = "headquarters_title")
private String title ;
    /**
     * The physical address of the headquarter.
     * <p>
     * This embedded value object contains detailed location information
     * for the headquarter.
     * </p>
     */
@Embedded
private Address address;
    /**
     * Constructs a new Headquarter with the specified attributes.
     *
     * @param nursingHome the nursing home that owns this headquarter, must not be null
     * @param title       the title or name of the headquarter, must not be null
     * @param address     the physical address of the headquarter
     */
    public Headquarter(NursingHome nursingHome, String title , Address address
    ){
      this.nursingHome= nursingHome;
        this.title= title;
        this.address= address;
    }
    /**
     * Updates the title of this headquarter.
     * <p>
     * If the provided new title is null, the current title remains unchanged.
     * Otherwise, the new title is trimmed of leading and trailing whitespace
     * before being set.
     * </p>
     *
     * @param newTitle the new title to set, or null to keep the current title
     */
    public void updateTitle(String newTitle) {
        this.title = newTitle == null ? this.title : newTitle.trim();
    }
    /**
     * Updates the address of this headquarter.
     * <p>
     * Replaces the current address with the provided new address.
     * </p>
     *
     * @param newAddress the new address to set for this headquarter
     */

    public void updateAddress(Address newAddress) {
        this.address = newAddress;
    }
}
