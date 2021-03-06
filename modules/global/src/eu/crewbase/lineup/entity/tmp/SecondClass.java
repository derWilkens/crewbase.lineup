package eu.crewbase.lineup.entity.tmp;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.List;

@NamePattern("%s|secAttr")
@Table(name = "LINEUP_SECOND_CLASS")
@Entity(name = "lineup$SecondClass")
public class SecondClass extends StandardEntity {
    private static final long serialVersionUID = 3629566028576635919L;

    @Column(name = "SEC_ATTR")
    protected String secAttr;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "secondClass")
    protected List<ThirdClass> thirdList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIRST_CLASS_ID")
    protected FirstClass firstClass;

    public void setThirdList(List<ThirdClass> thirdList) {
        this.thirdList = thirdList;
    }

    public List<ThirdClass> getThirdList() {
        return thirdList;
    }


    public void setFirstClass(FirstClass firstClass) {
        this.firstClass = firstClass;
    }

    public FirstClass getFirstClass() {
        return firstClass;
    }


    public void setSecAttr(String secAttr) {
        this.secAttr = secAttr;
    }

    public String getSecAttr() {
        return secAttr;
    }


}