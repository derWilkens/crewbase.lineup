package eu.crewbase.lineup.listener;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;

@Component("LINEUP_UserEntityListener")
public class UserEntityListener implements BeforeInsertEntityListener<User> {

	@Inject
	private Persistence persistence;

	@Inject
	private Metadata metadata;

	@Override
	public void onBeforeInsert(User entity, EntityManager entityManager) {
		System.out.println("saas_UserEntityListener.onBeforeInsert");

		TypedQuery<Role> query = persistence.getEntityManager()
				.createQuery("select r from sec$Role r where r.defaultRole = true", Role.class);
		List<Role> defaultRoles = query.getResultList();

		for (Role defaultRole : defaultRoles) {
			
			boolean notAssigned = entity.getUserRoles().stream().map(UserRole::getRole)
					.noneMatch(role -> role.equals(defaultRole));
			if (notAssigned) {
				UserRole userRole = metadata.create(UserRole.class);
				userRole.setRole(defaultRole);
				userRole.setUser(entity);
				entity.getUserRoles().add(userRole);
				persistence.getEntityManager().persist(userRole);
			}
		}
		UserSessionSource session = AppBeans.get(UserSessionSource.class);
		entity.setGroup(session.getUserSession().getUser().getGroup());
	}

}