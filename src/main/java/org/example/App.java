package org.example;

import java.time.DateTimeException;
import java.time.LocalDate;
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
                        System.out.print("Enter task status ( PENDING, IN_PROGRESS, COMPLETED ) : ");
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
                    if(allTasks == null)
                    {
                        System.out.println("Tasks not found");
                    }
                    else {
                            System.out.printf("%-5s %-20s %-40s %-12s %-15s %-15s\n",
                                    "ID", "Title", "Description", "Due Date", "Status", "Priority");
                            System.out.println("-----------------------------------------------------------------------------------------------");

                            for (Task t : allTasks) {
                                System.out.printf("%-5d %-20s %-40s %-12s %-15s %-15s\n",
                                        t.getId(),
                                        t.getTitle(),
                                        t.getDescription().length() > 37 ? t.getDescription().substring(0, 37) + "..." : t.getDescription(),
                                        t.getDueDate(),
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
                        System.out.printf("%-5s %-20s %-40s %-12s %-15s %-15s\n",
                                "ID", "Title", "Description", "Due Date", "Status", "Priority");
                        System.out.println("-----------------------------------------------------------------------------------------------");
                        System.out.printf("%-5d %-20s %-40s %-12s %-15s %-15s\n",
                                task1.getId(),
                                task1.getTitle(),
                                task1.getDescription().length() > 37 ? task1.getDescription().substring(0, 37) + "..." : task1.getDescription(),
                                task1.getDueDate(),
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
                        while(updateDate == null) {
                            System.out.print("Enter new dueDate (yyyy-MM-dd) : ");
                            String inputDate = sc.nextLine();
                            try {
                                updateDate = LocalDate.parse(inputDate);
                                taskToUpdate.setDueDate(updateDate);
                            } catch (Exception e) {
                                System.out.println("Invalid input, check your date format..");
                            }
                        }

                        TaskStatus updateStatus = null;
                        while(updateStatus == null) {
                            System.out.print("Enter new status ( PENDING, IN_PROGRESS, COMPLETED ) : ");
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
                    System.out.print("Enter status for filter task : ");
                    String inputStatus = sc.nextLine();
                    try {
                        TaskStatus filterStatus = TaskStatus.valueOf(inputStatus.toUpperCase());
                        List<Task> filteredTasks = taskDAO.getTaskByStatus(filterStatus);

                        if (filteredTasks == null) {
                            System.out.println("Task not found");
                        } else {
                            System.out.printf("%-5s %-20s %-40s %-12s %-15s %-15s\n",
                                    "ID", "Title", "Description", "Due Date", "Status", "Priority");
                            System.out.println("-----------------------------------------------------------------------------------------------");
                            for (Task getTask : filteredTasks) {
                                System.out.printf("%-5d %-20s %-40s %-12s %-15s %-15s\n",
                                        getTask.getId(),
                                        getTask.getTitle(),
                                        getTask.getDescription().length() > 37 ? getTask.getDescription().substring(0, 37) + "..." : getTask.getDescription(),
                                        getTask.getDueDate(),
                                        getTask.getStatus(),
                                        getTask.getPriority());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid status! Please try again...");
                    }
                    break;

                case 7:
                    break;

                case 8:
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
