package eu.crewbase.lineup.entity.dto;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@NamePattern("%s|siteName")
@MetaClass(name = "lineup$SiteItem")
public class SiteItem extends BaseUuidEntity {
    private static final long serialVersionUID = 2094335111974906805L;

    @MetaProperty
    protected String siteName;

    @MetaProperty
    protected String color;

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }


}