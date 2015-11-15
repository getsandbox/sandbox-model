package com.sandbox.common.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

/**
 * Created by nickhoughton on 14/09/2014.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActivityMessage {

    @JsonIgnore
    @Id
    String id;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    long createdTimestamp;

    @Column(name = "message", nullable = false)
    String message;

    @Column(name = "message_type", nullable = false)
    String messageType;

    @Column(name = "sandbox_id", nullable = false)
    String sandboxId;

    public ActivityMessage() {

    }

    public ActivityMessage(InstanceTransaction txn, ObjectMapper mapper, String sandboxId) {
        this.messageType = "request";
        this.sandboxId = sandboxId;
        try {
            this.message = mapper.writeValueAsString(txn);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public ActivityMessage(String message, String sandboxId) {
        this.message = message;
        this.messageType = "log";
        this.sandboxId = sandboxId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getMessage() {
        return "log".equals(messageType) ? message : null;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSandboxId() {
        return sandboxId;
    }

    public void setSandboxId(String sandboxId) {
        this.sandboxId = sandboxId;
    }

    @JsonRawValue
    public String getMessageObject() {
        return "request".equals(messageType) ? message : null;
    }

    @PrePersist
    protected void onCreate() {
        this.createdTimestamp = System.currentTimeMillis();
    }

}
