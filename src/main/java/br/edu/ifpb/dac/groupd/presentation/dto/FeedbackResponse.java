package br.edu.ifpb.dac.groupd.presentation.dto;

import java.time.OffsetDateTime;

public class FeedbackResponse {
    private Long id;
    private String name;
    private String contact;
    private String message;
    private OffsetDateTime createdAt;
    private String messageCategory;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public String getMessageCategory() { return messageCategory; }
    public void setMessageCategory(String messageCategory) { this.messageCategory = messageCategory; }
}
