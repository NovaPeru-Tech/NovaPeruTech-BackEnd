// language: java
package com.novaperutech.veyra.platform.activities.domain.model.valueobjects;

import java.util.Objects;

public class Area {

    private final String areaCode;
    private final String name;

    public Area(String areaCode, String name) {
        this.areaCode = areaCode;
        this.name = name;
    }

    // Getter esperado por tu código
    public String getAreaCode() {
        return areaCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        Area area = (Area) o;
        return Objects.equals(areaCode, area.areaCode) &&
                Objects.equals(name, area.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCode, name);
    }
}
