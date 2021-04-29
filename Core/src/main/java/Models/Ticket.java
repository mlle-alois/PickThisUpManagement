package Models;

import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class Ticket {
    public int ticketId;
    public String ticketName;
    public String ticketDescription;
    public Date ticketCreationDate;
    public int statusId;
    public int priorityId;
    public String creatorId;
}
