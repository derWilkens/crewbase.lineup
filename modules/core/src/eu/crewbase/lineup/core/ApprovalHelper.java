package eu.crewbase.lineup.core;

import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;

import eu.crewbase.lineup.entity.cap.Certificate;

@Component("cap_ApprovalHelper")
public class ApprovalHelper {
    @Inject
    private Persistence persistence;

    public void updateState(UUID entityId, String state) {
        try (Transaction tx = persistence.getTransaction()) {
            Certificate certificate = persistence.getEntityManager().find(Certificate.class, entityId);
            if (certificate != null) {
            	certificate.setState(state);
            }
            tx.commit();
        }
    }
}
