package Models;

import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
public class Task {
    public int taskId;
    public String taskName;
    public String taskDescription;
    public Date taskCreationDate;
    public Date taskDeadline;
    public int positionInList;
    public int statusId;
    public int priorityId;
    public int listId;
    public String creatorId;

}
