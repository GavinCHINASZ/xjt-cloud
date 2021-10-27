package common.utils;

import entity.Project;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 *
 */
public class ProjectUtils {
	public static String TASK_EXECUTE = "TE";
	public static String TASK_AUDIT = "TA";

	public static String getProjectId(HttpServletRequest request) {
		String projId = request.getParameter("_pid");
		if (projId != null && projId.trim().length() > 0) {
			return projId;
		}

		return null;
	}

	public boolean isProjectManager(HttpServletRequest request) {
		return isProjectMember(request, true);
	}

	public boolean isProjectMember(HttpServletRequest request) {
		return isProjectMember(request, false);
	}

	public boolean isProjectMember(HttpServletRequest request, boolean isPm) {
		String projectId = ProjectUtils.getProjectId((HttpServletRequest) request);
		return isProjectMember(projectId, request, isPm);
	}

	public boolean isProjectManager(String projectId, HttpServletRequest request) {
		return isProjectMember(projectId, request, true);
	}

	public boolean isProjectMember(String projectId, HttpServletRequest request, boolean isPm) {

		/*if (projectId != null) {
			PbBaseProjectService pbBaseProjectService = (PbBaseProjectService) SpringUtils
					.getBean("pbBaseProjectService");
			PbBaseProject project = pbBaseProjectService.get(projectId);
			if (project != null) {
				return isProjectMember(project, request, isPm);
			}
		}*/
		return false;
	}

	public boolean hasProjectRole(Project project, List<String> roles, String userid) {
		/*PbProjectMemberService pbProjectMemberService = (PbProjectMemberService) SpringUtils.getBean("pbProjectMemberService");
		if(project == null){
			return false;
		}
		List<PbProjectMember> teams = pbProjectMemberService.findByProjectIdTeam(project.getId());
		for (PbProjectMember member : teams) {
			List<String> roles1 = new ArrayList<String>();
			roles1.addAll(roles);
			if (member.getProjectRole() != null) {
				Set<Employee> members = member.getMembers();
				for (Employee emp : members) {
					if (emp.getUserid() != null && emp.getUserid().equals(userid)) {
						if (member.getProjectRole().getProjectManager()) {
							return true;
						}
						String permissionStr = member.getProjectRole().getPrjPrivilege();
						if (permissionStr != null) {
							List<String> defines = Arrays.asList(permissionStr.split(","));
							roles1.retainAll(defines);
							if (roles1.size() > 0) {
								return true;
							}
						}
					}
				}
			}
		}*/
		return false;
	}

	public boolean hasProjectRole(Project project, String role, String userid) {
		/*PbProjectMemberService pbProjectMemberService = (PbProjectMemberService) SpringUtils.getBean("pbProjectMemberService");
		if(project == null){
			return false;
		}
		List<PbProjectMember> teams = pbProjectMemberService.findByProjectIdTeam(project.getId());

		for (PbProjectMember member : teams) {
			if (member.getProjectRole() != null
					&& (member.getProjectRole().getId().equals(role) || member.getProjectRole().getProjectManager())) {
				Set<Employee> members = member.getMembers();
				for (Employee emp : members) {
					if (emp.getUserid() != null && emp.getUserid().equals(userid)) {
						return true;
					}
				}
			}
		}*/
		return false;
	}

	public boolean isProjectMember(Project project, HttpServletRequest request, boolean isPm) {
		/*if (project != null) {
			String userid = UserUtils.getCurrentUserId();
			PbProjectMemberService pbProjectMemberService = (PbProjectMemberService) SpringUtils.getBean("pbProjectMemberService");
			List<PbProjectMember> teams = pbProjectMemberService.findByProjectIdTeam(project.getId());
			if (teams != null && teams.size()>0) {
				for (PbProjectMember member : teams) {
					if (isPm) {
						// check is project manager
						if (member.getProjectRole() != null && member.getProjectRole().getProjectManager()) {
							Set<Employee> members = member.getMembers();
							for (Employee emp : members) {
								if (emp.getUserid() != null && emp.getUserid().equals(UserUtils.getCurrentUserId())) {
									return true;
								}
							}
						}
					} else {
						EmployeeService employeeService = (EmployeeService) SpringUtils.getBean("employeeService");
						String propertyPicId = project.getPropertyPicId();
						Employee employee2 = null;
						if (propertyPicId!=null && !"".equals(propertyPicId)) {
							employee2 = employeeService.get(propertyPicId);
						}
						if (employee2 != null && employee2.getUserid() != null && employee2.getUserid().equals(userid)) {
							return true;
						}
						
						String maintenancePicId = project.getMaintenancePicId();
						Employee employee = null;
						if (maintenancePicId!=null && !"".equals(maintenancePicId)) {
							employee = employeeService.get(maintenancePicId);
						}
						if (employee != null && employee.getUserid() != null && employee.getUserid().equals(userid)) {
							return true;
						}
						if (project.getProjCreator() != null && project.getProjCreator().getUserid() != null
								&& project.getProjCreator().getUserid().equals(userid)) {
							return true;
						}
						
						Set<Employee> members = member.getMembers();
						for (Employee emp : members) {
							if (emp.getUserid() != null && emp.getUserid().equals(userid)) {
								return true;
							}
						}
					}
				}
			}
		}*/

		return false;
	}

	public List<String> getProjectRoles(Project project) {
		List<String> roles = new ArrayList<String>();
		if (project == null) {
			return roles;
		}

		/*String userid = UserUtils.getCurrentUserId();
		PbProjectMemberService pbProjectMemberService = (PbProjectMemberService) SpringUtils.getBean("pbProjectMemberService");

		List<PbProjectMember> teams = pbProjectMemberService.findByProjectIdTeam(project.getId());
		for (PbProjectMember member : teams) {
			// check is project manager
			if (member.getProjectRole() != null) {

				Set<Employee> members = member.getMembers();
				for (Employee emp : members) {
					if (emp.getUserid() != null && emp.getUserid().equals(userid)) {
						if (member.getProjectRole().getProjectManager()) {
							roles.add("_pm");
							return roles;
						} else {
							String privs = member.getProjectRole().getPrjPrivilege();
							if (StringUtils.isNotBlank(privs)) {
								roles.addAll(Arrays.asList(privs.split(",")));
							}
							roles.add("_pa");
						}
					}
				}
			}

		}*/

		return roles;
	}

	public List<String> findProjectPartyIds(Project project, String userId) {
		List<String> parties = new ArrayList<String>();
		if (project == null) {
			return parties;
		}

		/*PbProjectMemberService pbProjectMemberService = (PbProjectMemberService) SpringUtils.getBean("pbProjectMemberService");

		List<PbProjectMember> teams = pbProjectMemberService.findByProjectIdTeam(project.getId());
		for (PbProjectMember member : teams) {
			Set<Employee> members = member.getMembers();
			for (Employee emp : members) {
				if (emp.getUserid() != null && emp.getUserid().equals(userId)) {
					parties.add(emp.getId());
					if (member.getProjectRole() != null) {
						parties.add(member.getProjectRole().getId());
					}
				}
			}

		}*/

		return parties;
	}

	/*public boolean isTaskRole(PbBaseTask task, String roleId, int location, LandkingUser user,List<PbProjectRole> roleList) {
		*//*if (UserUtils.isCloudAdmin()) {
			return true;
		}
		List<String> userParties = new ArrayList<String>();
		userParties.addAll(user.getOrganizations());
		userParties.addAll(user.getRoles());
		userParties.add(user.getId());

		PbBaseProject project = task.getProject();
		if (roleList.size() > 0 && roleList.size() > location) {
			PbProjectRole party = roleList.get(location);
			PbProjectMemberService pbProjectMemberService = (PbProjectMemberService) SpringUtils
					.getBean("pbProjectMemberService");

			List<PbProjectMember> teams = pbProjectMemberService.findByProjectIdTeam(project.getId());
			for (PbProjectMember member : teams) {
				PbProjectRole projectRole2 = member.getProjectRole();
				if (projectRole2 != null && projectRole2.getId().equals(party.getId())) {
					Set<Employee> members = member.getMembers();
					for (Employee emp : members) {
						if (emp.getUserid() != null && emp.getUserid().equals(user.getId())) {
							return true;
						}
					}
				}
				if (projectRole2 != null && projectRole2.getProjectManager()) {
					Set<Employee> members = member.getMembers();
					for (Employee emp : members) {
						if (emp.getUserid() != null && emp.getUserid().equals(user.getId())) {
							return true;
						}
					}
				}

			}
		}*//*

		return false;
	}*/

	/*public boolean isTaskExcecuter(PbBaseTask task, HttpServletRequest context, LandkingUser user,List<Employee> empList) {
		if (UserUtils.isCloudAdmin()) {
			return true;
		}
		List<String> userParties = new ArrayList<String>();
		userParties.addAll(user.getOrganizations());
		userParties.addAll(user.getRoles());
		userParties.add(user.getId());
		PbBaseProject project = task.getProject();
		for (Employee party : empList) {
			String id = party.getId();
			if (userParties.contains(id)) {
				return true;
			}
			if (party instanceof Employee) {
				String userid = ((Employee) party).getUserid();
				if (userid != null && userid.equals(UserUtils.getCurrentUserId())) {
					return true;
				}
			}
		}
		PartyBaseEntity pe = task.getTaskLeader();
		if (pe != null && userParties.contains(pe.getId())) {
			return true;
		}
		PbProjectRole projectRole = task.getAssigneeRole();
		if (projectRole != null) {
			String id = projectRole.getId();
			PbProjectMemberService pbProjectMemberService = (PbProjectMemberService) SpringUtils
					.getBean("pbProjectMemberService");

			List<PbProjectMember> teams = pbProjectMemberService.findByProjectIdTeam(project.getId());
			for (PbProjectMember member : teams) {
				PbProjectRole projectRole2 = member.getProjectRole();
				if (projectRole2 != null && projectRole2.getId().equals(id)) {
					Set<Employee> members = member.getMembers();
					for (Employee emp : members) {
						if (emp.getUserid() != null && emp.getUserid().equals(user.getId())) {
							return true;
						}
					}
				}
				if (projectRole2 != null && projectRole2.getProjectManager()) {
					Set<Employee> members = member.getMembers();
					for (Employee emp : members) {
						if (emp.getUserid() != null && emp.getUserid().equals(user.getId())) {
							return true;
						}
					}
				}

			}
		}
		return false;
	}*/

	// 是否具有提交任务审核的权限
	/*public boolean hasTaskPermission(PbBaseTask task, String permission,List<Employee> empList, List<PbProjectRole> roleList) {
		if (UserUtils.isSuperAdmin() || UserUtils.isAdmin() || UserUtils.isCloudAdmin()) {
			return true;
		}

		if (permission == null) {
			permission = TASK_EXECUTE;
		}
		// 如果是审批权限,而任务不在待审状态
		if (permission.equals(ProjectUtils.TASK_AUDIT)) {
			if (!task.getTaskStatus().equals(PbBaseTask.TASK_STATUS_AUDITING)) {
				return false;
			}
		}

		PartyBaseEntity leader = task.getTaskLeader();
		List<String> userParties = UserUtils.getCurrentUserParties();
		if (leader != null && userParties.contains(leader.getId())) {
			return true;
		}
		if (permission.equals(ProjectUtils.TASK_EXECUTE)) {
			PartyBaseEntityService partyBaseEntityService = (PartyBaseEntityService) SpringUtils
					.getBean("partyBaseEntityService");
			for (PartyBaseEntity member : empList) {
				if (userParties.contains(member.getId())) {
					return true;
				}
			}
		}
		if (permission.equals(ProjectUtils.TASK_AUDIT)) {
			PbBaseProject project = task.getProject();
			PbProjectMemberService pbProjectMemberService = (PbProjectMemberService) SpringUtils
					.getBean("pbProjectMemberService");

			List<PbProjectMember> teams = pbProjectMemberService.findByProjectIdTeam(project.getId());

			for (PbProjectRole auditor : roleList) {
				for (PbProjectMember member : teams) {
					if (member.getProjectRole() != null && member.getProjectRole().getId().equals(auditor.getId())) {
						Set<Employee> members = member.getMembers();
						for (Employee emp : members) {
							if (emp.getUserid() != null && emp.getUserid().equals(UserUtils.getCurrentUserId())) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}*/

	public Set<String> findUserByProjectAndRole(Project pro, List<String> roles) {
		Set<String> userIds = new TreeSet<String>();

		/*PbProjectMemberService pbProjectMemberService = (PbProjectMemberService) SpringUtils.getBean("pbProjectMemberService");
		List<PbProjectMember> teams = pbProjectMemberService.findByProjectIdTeam(pro.getId());
		for (PbProjectMember member : teams) {
			List<String> roles1 = new ArrayList<String>();
			roles1.addAll(roles);
			if (member.getProjectRole() != null) {
				Set<Employee> members = member.getMembers();
				for (Employee emp : members) {
					if (emp.getUserid() != null) {
						if (member.getProjectRole().getProjectManager()) {
							userIds.add(emp.getUserid());
						}
						String permissionStr = member.getProjectRole().getPrjPrivilege();
						if (permissionStr != null) {
							List<String> defines = Arrays.asList(permissionStr.split(","));
							roles1.retainAll(defines);
							if (roles1.size() > 0) {
								userIds.add(emp.getUserid());
							}
						}

					}
				}
			}
		}*/
		return userIds;
	}

}
