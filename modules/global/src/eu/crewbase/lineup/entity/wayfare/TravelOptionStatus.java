package eu.crewbase.lineup.entity.wayfare;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TravelOptionStatus implements EnumClass<Integer> {

    Init(10),
    Sent(20),
    Requested(30),
    Approved(40),
    Rejected(50),
    Executed(60),
    Closed(70);

    private Integer id;

    TravelOptionStatus(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TravelOptionStatus fromId(Integer id) {
        for (TravelOptionStatus at : TravelOptionStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}