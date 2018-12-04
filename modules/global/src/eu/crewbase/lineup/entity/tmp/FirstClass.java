package eu.crewbase.lineup.entity.tmp;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|firstAttr")
@Table(name = "LINEUP_FIRST_CLASS")
@Entity(name = "lineup$FirstClass")
public class FirstClass extends StandardEntity {
    private static final long serialVersionUID = 1126650395331550354L;

    @Column(name = "FIRST_ATTR")
    protected String firstAttr;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "firstClass")
    protected List<SecondClass> secList;

    public void setSecList(List<SecondClass> secList) {
        this.secList = secList;
    }

    public List<SecondClass> getSecList() {
        return secList;
    }


    public void setFirstAttr(String firstAttr) {
        this.firstAttr = firstAttr;
    }

    public String getFirstAttr() {
        return firstAttr;
    }


}