package org.example;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);
        TaskDAO taskDAO = new TaskDAO();

        while(true)
        {
            Task task = new Task();

            System.out.println("\n");
            System.out.println(" \t \t \t \t Task Operations");
            System.out.println("=======================================================");
            System.out.println("1. Add new task" +
                    "\n2. View all tasks" +
                    "\n3. View task by task ID" +
                    "\n4. Update task" +
                    "\n5. Delete task" +
                    "\n6. Filter by status" +
                    "\n7. Filter by Priority" +
                    "\n8. Filter by status and priority" +
                    "\n9. Exit");

            System.out.print("Enter you choice : ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice)
            {
                case 1 :
                    System.out.print("Enter Title : ");
                    String title = sc.nextLine();
                    task.setTitle(title);

                    System.out.print("Enter Description : ");
                    String description = sc.nextLine();
                    task.setDescription(description);

                    LocalDate dueDate = null;
                    while(dueDate == null) {
                        System.out.print("Enter DueDate (yyyy-MM-dd) : ");
                        String dateInput = sc.nextLine();
                        try {
                            dueDate = LocalDate.parse(dateInput);
                        } catch (DateTimeException e) {
                            System.out.println("Invalid input ! Please enter date like: 2025-06-01");
                        }
                    }
                    task.setDueDate(dueDate);

                    TaskStatus status = null;
                    while(status == null) {
                        System.out.print("Enter task status ( PENDING, ON_PROGRESS, COMPLETED ) : ");
                        String inputStatus = sc.nextLine();
                        try {
                             status = TaskStatus.valueOf(inputStatus.toUpperCase());
                        } catch (Exception e) {
                            System.out.println("Invalid status! Please try again later...");

                        }
                    }
                    task.setStatus(status);

                    PriorityLevel priority = null;
                    while(priority == null) {
                        System.out.print("Enter task priority ( LOW, MEDIUM, HIGH ): ");
                        String inputPriority = sc.nextLine();
                        try {
                             priority = PriorityLevel.valueOf(inputPriority.toUpperCase());
                        } catch (Exception e) {
                            System.out.println("Invalid priority! Please try again later... ");
                        }
                    }
                    task.setPriority(priority);
                    taskDAO.saveTask(task);
                    break;

                case 2:
                    List<Task> allTasks = taskDAO.getAllTasks();
                    if (allTasks == null || allTasks.isEmpty()) {
                        System.out.println("Tasks not found");
                    } else {
                        System.out.printf("%-5s %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                "ID", "Title", "Description", "Due Date", "Created Time", "Status", "Priority");
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------");

                        for (Task t : allTasks) {
                            System.out.printf("%-5d %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                    t.getId(),
                                    t.getTitle(),
                                    t.getDescription().length() > 37 ? t.getDescription().substring(0, 37) + "..." : t.getDescription(),
                                    t.getDueDate(),
                                    t.getCreatedTime(),
                                    t.getStatus(),
                                    t.getPriority());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter ID which task you want : ");
                    int taskId = sc.nextInt();
                    sc.nextLine();

                    Task task1 = taskDAO.getTaskById(taskId);
                    if(task1 == null)
                    {
                        System.out.println("Task not found");
                    }
                    else{
                        System.out.printf("%-5s %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                "ID", "Title", "Description", "Due Date", "Created Time", "Status", "Priority");
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
                        System.out.printf("%-5d %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                task1.getId(),
                                task1.getTitle(),
                                task1.getDescription().length() > 37 ? task1.getDescription().substring(0, 37) + "..." : task1.getDescription(),
                                task1.getDueDate(),
                                task1.getCreatedTime(),
                                task1.getStatus(),
                                task1.getPriority());
                    }
                    break;

                case 4:
                    System.out.print("Enter ID to update task : ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    Task taskToUpdate = taskDAO.getTaskById(updateId);
                    if(taskToUpdate == null)
                    {
                        System.out.println("Task not found");
                    }
                    else {
                        System.out.print("Enter title (Leave blank to keep unchanged) : ");
                        String updateTitle = sc.nextLine();
                        if(!updateTitle.isEmpty())taskToUpdate.setTitle(updateTitle);

                        System.out.print("Enter description (Leave blank to keep unchanged) : ");
                        String updateDesc = sc.nextLine();
                        if(!updateDesc.isEmpty()) taskToUpdate.setDescription(updateDesc);

                        LocalDate updateDate = null;
                            System.out.print("Enter new dueDate (yyyy-MM-dd) ( Leave blank to keep unchanged ) : ");
                            String inputDate = sc.nextLine();
                            if(!inputDate.isEmpty()) {
                                try {
                                    updateDate = LocalDate.parse(inputDate);
                                    taskToUpdate.setDueDate(updateDate);
                                } catch (Exception e) {
                                    System.out.println("Invalid input, check your date format..");
                                }
                        }

                        TaskStatus updateStatus = null;
                        while(updateStatus == null) {
                            System.out.print("Enter new status ( PENDING, ON_PROGRESS, COMPLETED ) : ");
                            String inputStatus = sc.nextLine();
                            try {
                                updateStatus = TaskStatus.valueOf(inputStatus.toUpperCase());
                                taskToUpdate.setStatus(updateStatus);
                            } catch(Exception e)
                            {
                                System.out.println("Invalid status! Please try again...");
                            }
                        }

                        PriorityLevel updatedPriority = null;
                        while(updatedPriority == null)
                        {
                            System.out.print("Enter new priority ( LOW, MEDIUM, HIGH ) : ");
                            String inputPriority = sc.nextLine();

                            try{
                                updatedPriority = PriorityLevel.valueOf(inputPriority.toUpperCase());
                                taskToUpdate.setPriority(updatedPriority);
                            }catch (Exception e)
                            {
                                System.out.println("Invalid priority! Please try again...");
                            }
                        }
                    }
                    taskDAO.updateTask(taskToUpdate);
                    break;

                case 5:
                    System.out.print("Enter ID to delete task : ");
                    int inputId = sc.nextInt();
                    sc.nextLine();

                    Task getId = taskDAO.getTaskById(inputId);
                    if(getId != null) {
                        taskDAO.deleteTask(inputId);
                    }else{
                        System.out.println("Task not found");
                    }
                    break;

                case 6:
                    System.out.print("Enter status for filtering task : ");
                    String inputStatus = sc.nextLine();
                    try {
                        TaskStatus filterStatus = TaskStatus.valueOf(inputStatus.toUpperCase());
                        List<Task> filteredStatusTasks = taskDAO.getTaskByStatus(filterStatus);

                        if (filteredStatusTasks.isEmpty()) {
                            System.out.println("Task not found...");
                        } else {
                            System.out.printf("%-5s %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                    "ID", "Title", "Description", "Due Date", "Created Time", "Status", "Priority");
                            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

                            for (Task getStatusTask : filteredStatusTasks) {
                                System.out.printf("%-5d %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                        getStatusTask.getId(),
                                        getStatusTask.getTitle(),
                                        getStatusTask.getDescription().length() > 37 ? getStatusTask.getDescription().substring(0, 37) + "..." : getStatusTask.getDescription(),
                                        getStatusTask.getDueDate(),
                                        getStatusTask.getCreatedTime(),
                                        getStatusTask.getStatus(),
                                        getStatusTask.getPriority());
                            }

                        }
                    } catch (Exception e) {
                        System.out.println("Invalid status! Please try again...");
                    }
                    break;

                case 7:
                    System.out.print("Enter Priority level for filtering task : ");
                    String inputPriority = sc.nextLine();

                    try{
                        PriorityLevel filterPriority = PriorityLevel.valueOf(inputPriority.toUpperCase());
                        List<Task> filteredPriorityTask = taskDAO.getTaskByPriority(filterPriority);

                        if (filteredPriorityTask.isEmpty())
                        {
                            System.out.println("Task not found...");
                        }else
                        {
                            System.out.printf("%-5s %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                    "ID", "Title", "Description", "Due Date", "Created Time", "Status", "Priority");
                            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

                            for (Task getPriorityTask : filteredPriorityTask) {
                                System.out.printf("%-5d %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                        getPriorityTask.getId(),
                                        getPriorityTask.getTitle(),
                                        getPriorityTask.getDescription().length() > 37 ? getPriorityTask.getDescription().substring(0, 37) + "..." : getPriorityTask.getDescription(),
                                        getPriorityTask.getDueDate(),
                                        getPriorityTask.getCreatedTime(),
                                        getPriorityTask.getStatus(),
                                        getPriorityTask.getPriority());
                            }
                        }

                    }catch(Exception e) {
                        System.out.println("Invalid Priority! Please try again...");
                    }
                    break;

                case 8:
                    System.out.print("Enter status for filtering ( PENDING, ON_PROGRESS, COMPLETED ) : ");
                    String filteringInputStatus = sc.nextLine();
                    System.out.print("Enter priority for filtering ( LOW, MEDIUM, HIGH ) : ");
                    String filteringInputPriority = sc.nextLine();

                    try{
                        TaskStatus filteringStatus =  TaskStatus.valueOf(filteringInputStatus.toUpperCase());
                        PriorityLevel filteringPriority =  PriorityLevel.valueOf(filteringInputPriority.toUpperCase());
                        List<Task> filteredTasksByStatusAndPriority = taskDAO.getTaskByStatusAndPriority(filteringStatus, filteringPriority);

                        if(filteredTasksByStatusAndPriority.isEmpty())
                        {
                            System.out.println("Task not found...");
                        }else {
                            System.out.printf("%-5s %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                    "ID", "Title", "Description", "Due Date", "Created Time", "Status", "Priority");
                            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

                            for (Task getFilteredTask : filteredTasksByStatusAndPriority) {
                                System.out.printf("%-5d %-20s %-40s %-12s %-20s %-15s %-15s\n",
                                        getFilteredTask.getId(),
                                        getFilteredTask.getTitle(),
                                        getFilteredTask.getDescription().length() > 37 ? getFilteredTask.getDescription().substring(0, 37) + "..." : getFilteredTask.getDescription(),
                                        getFilteredTask.getDueDate(),
                                        getFilteredTask.getCreatedTime(),
                                        getFilteredTask.getStatus(),
                                        getFilteredTask.getPriority());
                        }
                        }
                    }catch (Exception e)
                    {
                        System.out.println("Invalid status and priority! Please try again...");
                    }
                    break;

                case 9:
                    return;

                default:
                    System.out.println("Invalid choice ! Please try again...");
                    break;
            }
        }
    }
}
