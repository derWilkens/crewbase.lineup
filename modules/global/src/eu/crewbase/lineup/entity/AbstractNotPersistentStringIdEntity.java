package eu.crewbase.lineup.entity;

/*
 * Copyright (c) 2008-2016 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.impl.AbstractInstance;
import com.haulmont.chile.core.model.utils.InstanceUtils;
import com.haulmont.cuba.core.entity.annotation.SystemLevel;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.sys.CubaEnhanced;
import com.haulmont.cuba.core.sys.CubaEnhancingDisabled;

/**
 * Base class for not persistent entities.
 *
 */
@com.haulmont.chile.core.annotations.MetaClass(name = "sys$AbstractNotPersistentStringIdEntity")
@SystemLevel(propagateToSubclasses = false)
public abstract class AbstractNotPersistentStringIdEntity extends AbstractInstance implements CubaEnhancingDisabled {

	private static final long serialVersionUID = -5568458077041860441L;

	protected String id;

	protected boolean __new = true;

	protected AbstractNotPersistentStringIdEntity() {

	}

	@Override
	public MetaClass getMetaClass() {
		Metadata metadata = AppBeans.get(Metadata.NAME);
		return metadata.getSession().getClassNN(getClass());
	}

	@MetaProperty
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setValue(String property, Object obj, boolean checkEquals) {
		Object oldValue = getValue(property);
		if ((!checkEquals) || (!InstanceUtils.propertyValueEquals(oldValue, obj))) {
			getMethodsCache().invokeSetter(this, property, obj);
			if (!(this instanceof CubaEnhanced)) {
				propertyChanged(property, oldValue, obj);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		AbstractNotPersistentStringIdEntity that = (AbstractNotPersistentStringIdEntity) o;

		return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);
	}

	@Override
	public int hashCode() {
		return (int) (getId() != null ? serialVersionUID : 0);
	}

	@Override
	public String toString() {
		return getClass().getName() + "-" + getId();
	}
}
