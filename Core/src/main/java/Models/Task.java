package Models;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
public class Task {
    public int taskid;
    public String taskName;
    public String taskDescription;
    public LocalDate taskCreationDate;
    public LocalDate TaskDeadline;
    public int positionInList;
    public int priorityId;
    public int listId;
    public String creatorId;

}
