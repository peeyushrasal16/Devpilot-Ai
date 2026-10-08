package com.devpilot.ai.aiservices;

import org.springframework.stereotype.Service;

@Service
public class AiKnowledgeService {
	
	public String getKnowledge() {

        return """
                DEV PILOT APPLICATION KNOWLEDGE

                DevPilot is a project management application.

                User roles:
                1. ADMIN
                2. PROJECT_MANAGER
                3. DEVELOPER

                Main features:
                - Dashboard
                - Projects
                - Tasks
                - Bugs
                - Documentation
                - Project Members
                - Task Assignments
                - Notifications
                - Activity Logs

                GENERAL TASK GUIDANCE:
                To create a task, the user should open the project/task management
                area, select the required project, choose the option to create or
                add a task, enter the task information, select priority/status if
                available, assign the task when required, and save it.

                GENERAL PROJECT GUIDANCE:
                A project manager or admin can use the Projects area to create
                and manage projects. Project information includes project name,
                description, status, start date and end date.

                GENERAL BUG GUIDANCE:
                Users can use the Bugs area to report and manage bugs.
                A bug contains information such as title/description, project,
                status and priority.

                GENERAL DOCUMENTATION GUIDANCE:
                Documentation can be created for a project and can contain
                API, GUIDE, README, TECHNICAL or GENERAL documentation.

                TASK STATUS:
                TODO
                IN_PROGRESS
                IN_REVIEW
                COMPLETED

                TASK PRIORITY:
                LOW
                MEDIUM
                HIGH
                CRITICAL

                PROJECT STATUS:
                PLANNING
                IN_PROGRESS
                COMPLETED
                ON_HOLD

                BUG STATUS:
                OPEN
                IN_PROGRESS
                RESOLVED
                CLOSED
                REOPENED

                BUG PRIORITY:
                LOW
                MEDIUM
                HIGH
                CRITICAL

                When answering "how to" questions, explain the steps clearly
                and use DevPilot terminology instead of mentioning other
                applications such as Trello, Jira, Asana or Notion.
                """;
	}

}
