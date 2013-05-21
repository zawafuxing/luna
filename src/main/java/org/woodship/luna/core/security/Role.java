package org.woodship.luna.core.security;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;
import org.woodship.luna.core.Resource;
import org.woodship.luna.db.IdEntity;

import com.vaadin.data.fieldgroup.Caption;

/**
 * 权限组.
 */
@Entity
@Table(name ="ROLE_", uniqueConstraints = { @UniqueConstraint(columnNames = {"name"})})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity<Role>{
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Caption("角色名称")
	private String name;

	@Caption("内置角色")
	private boolean sysRole;
	
	@Caption("备注")
	private String remark;
	
	@Caption("功能")
	@ManyToMany(fetch=FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Resource> resource = new LinkedHashSet<Resource>();
	
	@ManyToMany(mappedBy="roles",fetch=FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<User> users = new LinkedHashSet<User>();

	public Role(String name) {
		this.name = name;
	}

	public Role(String name, String desc) {
		this.name = name;
		this.remark = desc;
	}

	public Role() {
	}

	@Transient
	public List<String> toPermissionNames() {
		List<String> permissionNameList = new ArrayList<String>();
		for (Resource res : resource) {
			permissionNameList.add(res.getName());
		}
		return permissionNameList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Resource> getResource() {
		return resource;
	}

	public void setResource(Set<Resource> resource) {
		this.resource = resource;
	}

	public boolean isSysRole() {
		return sysRole;
	}

	public void setSysRole(boolean sysRole) {
		this.sysRole = sysRole;
	}


}
