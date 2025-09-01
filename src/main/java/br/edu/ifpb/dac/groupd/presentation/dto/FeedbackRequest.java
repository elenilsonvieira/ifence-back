package br.edu.ifpb.dac.groupd.presentation.dto;

import javax.validation.constraints.NotBlank;

public class FeedbackRequest {
    private String name;
    private String contact;
    @NotBlank
    private String message;
    private String messageCategory;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getMessageCategory() { return messageCategory; }
    public void setMessageCategory(String messageCategory) { this.messageCategory = messageCategory; }
}
