package com.devpilot.ai.aiservices;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.devpilot.ai.entities.User;
import com.devpilot.ai.enums.BugPriority;
import com.devpilot.ai.enums.BugStatus;
import com.devpilot.ai.enums.ProjectStatus;
import com.devpilot.ai.enums.TaskPriority;
import com.devpilot.ai.enums.TaskStatus;
import com.devpilot.ai.repositories.BugRepository;
import com.devpilot.ai.repositories.ProjectMemberRepository;
import com.devpilot.ai.repositories.ProjectRepository;
import com.devpilot.ai.repositories.TaskAssignmentRepository;
import com.devpilot.ai.repositories.TaskRepository;
import com.devpilot.ai.repositories.UserRepository;

@Service
public class AiService {
	private final ChatClient chatClient;
	
	 private final ProjectRepository projectRepository;
	 private final BugRepository bugRepository;
	 private final UserRepository userRepository;
	 private final TaskAssignmentRepository taskAssignmentRepository;
	 private final ProjectMemberRepository projectMemberRepository;
	 private final AiKnowledgeService aiKnowledgeService;
	
	private final TaskRepository taskRepository;
	
	public AiService(ChatClient.Builder builder,
			TaskRepository taskRepository,
			ProjectRepository projectRepository,
            BugRepository bugRepository,
            UserRepository userRepository,
            TaskAssignmentRepository taskAssignmentRepository,
            ProjectMemberRepository projectMemberRepository,
            AiKnowledgeService aiKnowledgeService) {
		
		this.chatClient=builder.build();
		this.taskRepository = taskRepository;
		
		 this.projectRepository = projectRepository;
	        this.bugRepository = bugRepository;
	        this.userRepository = userRepository;
	        this.taskAssignmentRepository = taskAssignmentRepository;
	        this.projectMemberRepository = projectMemberRepository;
	        this.aiKnowledgeService = aiKnowledgeService;

	}
	
	public String askAi(String question) {
		
		//TASK INFROMATION
		long totalTasks = taskRepository.count();
		
		long todoTasks =
                taskRepository.countByStatus(TaskStatus.TODO);

        long inProgressTasks =
                taskRepository.countByStatus(TaskStatus.IN_PROGRESS);

        long inReviewTasks =
                taskRepository.countByStatus(TaskStatus.IN_REVIEW);

        long completedTasks =
                taskRepository.countByStatus(TaskStatus.COMPLETED);

        long lowPriorityTasks =
                taskRepository.countByPriority(TaskPriority.LOW);

        long mediumPriorityTasks =
                taskRepository.countByPriority(TaskPriority.MEDIUM);

        long highPriorityTasks =
                taskRepository.countByPriority(TaskPriority.HIGH);

        long criticalPriorityTasks =
                taskRepository.countByPriority(TaskPriority.CRITICAL);
        
        //PROJECT INFROMATION
        
        long totalProjects = projectRepository.count();

        long planningProjects =
                projectRepository.countByStatus(ProjectStatus.PLANNING);

        long inProgressProjects =
                projectRepository.countByStatus(ProjectStatus.IN_PROGRESS);

        long completedProjects =
                projectRepository.countByStatus(ProjectStatus.COMPLETED);

        long onHoldProjects =
                projectRepository.countByStatus(ProjectStatus.ON_HOLD);
		
		
        //BUG INFROMATION 
        
        long totalBugs = bugRepository.count();

        long openBugs =
                bugRepository.countByStatus(BugStatus.OPEN);

        long inProgressBugs =
                bugRepository.countByStatus(BugStatus.IN_PROGRESS);

        long resolvedBugs =
                bugRepository.countByStatus(BugStatus.RESOLVED);

        long closedBugs =
                bugRepository.countByStatus(BugStatus.CLOSED);

        long reopenedBugs =
                bugRepository.countByStatus(BugStatus.REOPENED);

        long lowPriorityBugs =
                bugRepository.countByPriority(BugPriority.LOW);

        long mediumPriorityBugs =
                bugRepository.countByPriority(BugPriority.MEDIUM);

        long highPriorityBugs =
                bugRepository.countByPriority(BugPriority.HIGH);

        long criticalPriorityBugs =
                bugRepository.countByPriority(BugPriority.CRITICAL);
        
        //USER INFORMATION
        
        long totalUsers = userRepository.count();

        long totalDevelopers = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() != null
                        && user.getRole().name().equals("DEVELOPER"))
                .count();

        long totalProjectManagers = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() != null
                        && user.getRole().name().equals("PROJECT_MANAGER"))
                .count();

        long totalAdmins = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() != null
                        && user.getRole().name().equals("ADMIN"))
                .count();

        //CURRENT USER 
        
        String currentUserInformation =
                getCurrentUserInformation();
        
        //DATABASE INFROMATION
        
        String databaseInformation = """

                TASK INFORMATION

                Total tasks: %d
                TODO tasks: %d
                In-progress tasks: %d
                In-review tasks: %d
                Completed tasks: %d

                Task Priority:
                LOW: %d
                MEDIUM: %d
                HIGH: %d
                CRITICAL: %d


                PROJECT INFORMATION

                Total projects: %d
                Planning projects: %d
                In-progress projects: %d
                Completed projects: %d
                On-hold projects: %d


                BUG INFORMATION

                Total bugs: %d
                Open bugs: %d
                In-progress bugs: %d
                Resolved bugs: %d
                Closed bugs: %d
                Reopened bugs: %d

                Bug Priority:
                LOW: %d
                MEDIUM: %d
                HIGH: %d
                CRITICAL: %d


                USER INFORMATION

                Total users: %d
                Total developers: %d
                Total project managers: %d
                Total admins: %d


                CURRENT USER INFORMATION

                %s

                """.formatted(
                totalTasks,
                todoTasks,
                inProgressTasks,
                inReviewTasks,
                completedTasks,

                lowPriorityTasks,
                mediumPriorityTasks,
                highPriorityTasks,
                criticalPriorityTasks,

                totalProjects,
                planningProjects,
                inProgressProjects,
                completedProjects,
                onHoldProjects,

                totalBugs,
                openBugs,
                inProgressBugs,
                resolvedBugs,
                closedBugs,
                reopenedBugs,

                lowPriorityBugs,
                mediumPriorityBugs,
                highPriorityBugs,
                criticalPriorityBugs,

                totalUsers,
                totalDevelopers,
                totalProjectManagers,
                totalAdmins,

                currentUserInformation
        );
        
        //AI PROMPTS
        
        String prompt = """

                You are DevPilot AI, an intelligent assistant
                inside the DevPilot project management application.

                DevPilot has three main roles:

                1. ADMIN
                2. PROJECT_MANAGER
                3. DEVELOPER


                Your responsibilities are:

                - Answer questions about projects.
                - Answer questions about tasks.
                - Answer questions about bugs.
                - Answer questions about users and developers.
                - Answer questions about the current user's work.
                - Explain how to use DevPilot features.
                - Help users understand and solve programming bugs.
                - Provide clear step-by-step guidance.


                IMPORTANT RULES:

                1. When the user asks about database information,
                   use the provided database information.

                2. Never invent database numbers.

                3. When the user asks "how many", "total", "count",
                   "how many completed", "how many pending", etc.,
                   use the database information.

                4. When the user asks how to use DevPilot,
                   use the DevPilot application knowledge.

                5. Do not mention Trello, Jira, Asana or Notion
                   unless the user specifically asks for comparison.

                6. Consider the current user's role when answering.

                7. If the user asks about a programming error,
                   explain the possible cause and give a solution.

                8. If the database does not contain enough information,
                   clearly say that the required information is not
                   available.

                9. Do not make up projects, tasks, bugs or users.

                10. Keep the answer clear and easy to understand.
                
                11. Do not use Markdown formatting. Do not use **, *, #, backticks, or other Markdown symbols. Return plain text only.


                =========================
                DEV PILOT KNOWLEDGE
                =========================

                %s


                =========================
                DATABASE INFORMATION
                =========================

                %s


                =========================
                USER QUESTION
                =========================

                %s

                """
                .formatted(
                        aiKnowledgeService.getKnowledge(),
                        databaseInformation,
                        question
                );

        
        //CALLING API jo pura bana hai 
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
	
	//CURRENT USER INFROMATION 
	
	private String getCurrentUserInformation() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            return "User is not authenticated.";
        }

        Object principal =
                authentication.getPrincipal();

        if (principal instanceof User user) {

            long assignedTasks =
                    taskAssignmentRepository
                            .countByUser_Id(user.getId());

            long projectMemberships =
                    projectMemberRepository
                            .countByUser_Id(user.getId());

            return """
                    User ID: %d
                    User Name: %s
                    User Email: %s
                    User Role: %s
                    Assigned Tasks: %d
                    Project Memberships: %d
                    """
                    .formatted(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole(),
                            assignedTasks,
                            projectMemberships
                    );
        }

        return "Current user information is unavailable.";
    }
	}


