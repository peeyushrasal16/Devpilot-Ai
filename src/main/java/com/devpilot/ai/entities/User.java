package com.devpilot.ai.entities;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.devpilot.ai.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "user")
	private List<ProjectMember> projectMemberships;

	@OneToMany(mappedBy = "user")
	private List<TaskAssignment> taskAssignments;
	
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "user")
	private List<Notification> notifications;
	
	@OneToMany(mappedBy = "user")
	private List<ActivityLog> activityLogs;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role.name()));
		// Role ke aage "ROLE_" prefix add kar rahe hain taaki Spring Security match kar sake
	    String roleName = this.role.name(); // assuming role is an Enum or String
	    if (!roleName.startsWith("ROLE_")) {
	        roleName = "ROLE_" + roleName;
	    }
	    return Collections.singletonList(new SimpleGrantedAuthority(roleName));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
}
