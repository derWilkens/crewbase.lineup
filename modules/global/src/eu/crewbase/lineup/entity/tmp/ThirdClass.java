package eu.crewbase.lineup.entity.tmp;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@NamePattern("%s|secondClass")
@Table(name = "LINEUP_THIRD_CLASS")
@Entity(name = "lineup$ThirdClass")
public class ThirdClass extends StandardEntity {
    private static final long serialVersionUID = 5347444765058148541L;

    @Column(name = "THIRD_ATTR")
    protected String thirdAttr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECOND_CLASS_ID")
    protected SecondClass secondClass;

    public void setSecondClass(SecondClass secondClass) {
        this.secondClass = secondClass;
    }

    public SecondClass getSecondClass() {
        return secondClass;
    }


    public void setThirdAttr(String thirdAttr) {
        this.thirdAttr = thirdAttr;
    }

    public String getThirdAttr() {
        return thirdAttr;
    }


}