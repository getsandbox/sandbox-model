package com.sandbox.runtime.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Created by nickhoughton on 14/09/2014.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonPropertyOrder({"id","createdTimestamp","sandboxId"})
public class ActivityMessage {

    @JsonIgnore
    @Id
    String id;

    @ApiModelProperty(value = "Epoch time in milliseconds when the message was created")
    @Column(name = "CREATED_DATE_TIME", nullable = false)
    long createdTimestamp = System.currentTimeMillis();

    @ApiModelProperty(value = "The details of the message when type is 'log'")
    @Column(name = "message", nullable = false)
    String message;

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    ActivityMessageTypeEnum messageType;

    @ApiModelProperty(value = "The ID of the sandbox that generated this message")
    @Column(name = "sandbox_id", nullable = false)
    String sandboxId;

    public ActivityMessage() {

    }

    public ActivityMessage(String sandboxId, ActivityMessageTypeEnum type, String message) {
        this.sandboxId = sandboxId;
        this.messageType = type;
        this.message = message;
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
        return messageType==ActivityMessageTypeEnum.log ? message : null;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ActivityMessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(ActivityMessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    public String getSandboxId() {
        return sandboxId;
    }

    public void setSandboxId(String sandboxId) {
        this.sandboxId = sandboxId;
    }

    @JsonRawValue
    @ApiModelProperty(hidden = true, name = "messageObjectString")
    public String getMessageObject() {
        return messageType == ActivityMessageTypeEnum.request ? message : null;
    }

    //Hack to make swagger pick up the actual type, as we store as JSON and want to use JsonRawValue, getter is a String
    //will replace the above method in Swagger, will be ignored in JSON because returns null.
    @ApiModelProperty(hidden = false, name = "messageObject", value = "The details of the message when type is 'request'")
    public RuntimeTransaction getInstanceTransaction() {
        return null;
    }

}
