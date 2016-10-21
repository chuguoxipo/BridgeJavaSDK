package org.sagebionetworks.bridge.sdk.models.upload;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import org.sagebionetworks.bridge.sdk.exceptions.InvalidEntityException;
import org.sagebionetworks.bridge.sdk.models.healthData.HealthDataRecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/** This class represents upload validation status and messages. */
@JsonDeserialize(builder = UploadValidationStatus.Builder.class)
public final class UploadValidationStatus {
    private final String id;
    private final List<String> messageList;
    private final UploadStatus status;
    private final HealthDataRecord record;

    /** Private constructor. All construction should go through the builder. */
    private UploadValidationStatus(String id, List<String> messageList, UploadStatus status, HealthDataRecord record) {
        this.id = id;
        this.messageList = messageList;
        this.status = status;
        this.record = record;
    }

    /** Unique upload ID, as generated by the request upload API. Always non-null and non-empty.
     * @return id 
     */
    public String getId() {
        return id;
    }

    /**
     * <p>
     * List of validation messages, generally contains error messages. Since a single upload file may fail validation
     * in multiple ways, Bridge server will attempt to return all messages to the user. For example, the upload file
     * might be unencrypted, uncompressed, and it might not fit any of the expected schemas for the study.
     * </p>
     * <p>
     * This field is always non-null, but it may be empty. The list is immutable and contains non-null, non-empty
     * strings.
     * </p>
     * @return messageList
     */
    public List<String> getMessageList() {
        return messageList;
    }

    /**
     * Represents upload status, such as requested, validation in progress, validation failed, or succeeded. Always
     * non-null.
     * @return uploadStatus
     */
    public UploadStatus getStatus() {
        return status;
    }

    public HealthDataRecord getRecord() { return this.record; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(id);
        result = prime * result + Objects.hashCode(messageList);
        result = prime * result + Objects.hashCode(status);
        result = prime * result + Objects.hashCode(record);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UploadValidationStatus other = (UploadValidationStatus) obj;
        return Objects.equals(id, other.id) && Objects.equals(messageList, other.messageList)
                && Objects.equals(status, other.status) && Objects.equals(record, other.record);
    }

    @Override
    public String toString() {
        return String.format("UploadValidationStatus[id=%s, status=%s, messageList=[\"%s\"]], healthRecord=%s", id, status.name(),
                Joiner.on("\", \"").join(messageList), record.toString());
    }

    /** Builder for UploadValidationStatus. */
    public static class Builder {
        private String id;
        private List<String> messageList;
        private UploadStatus status;
        private HealthDataRecord record;

        /** 
         * @see org.sagebionetworks.bridge.sdk.models.upload.UploadValidationStatus#getId
         * @return id 
         */
        public String getId() {
            return id;
        }

        /** 
         * @see org.sagebionetworks.bridge.sdk.models.upload.UploadValidationStatus#getId
         * @param id
         * @return builder 
         */
        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        /** 
         * @see org.sagebionetworks.bridge.sdk.models.upload.UploadValidationStatus#getMessageList
         * @return messageLis 
         */
        public List<String> getMessageList() {
            return messageList;
        }

        /** 
         * @see org.sagebionetworks.bridge.sdk.models.upload.UploadValidationStatus#getMessageList
         * @param messageList
         * @return builder 
         */
        public Builder withMessageList(List<String> messageList) {
            this.messageList = messageList;
            return this;
        }

        /** 
         * @see org.sagebionetworks.bridge.sdk.models.upload.UploadValidationStatus#getMessageList
         * @param messages
         * @return builder 
         */
        public Builder withMessages(String... messages) {
            this.messageList = ImmutableList.copyOf(messages);
            return this;
        }

        /** 
         * @see org.sagebionetworks.bridge.sdk.models.upload.UploadValidationStatus#getStatus
         * @return uploadStatus 
         */
        public UploadStatus getStatus() {
            return status;
        }

        /** 
         * @see org.sagebionetworks.bridge.sdk.models.upload.UploadValidationStatus#getStatus
         * @param status
         * @return builder; 
         */
        public Builder withStatus(UploadStatus status) {
            this.status = status;
            return this;
        }

        public HealthDataRecord getRecord() { return record; }

        public Builder withRecord(HealthDataRecord record) {
            this.record = record;
            return this;
        }

        /**
         * Builds and validates an UploadValidationStatus object. id must be non-null and non-empty. messageList must
         * be non-null and must contain strings that are non-null and non-empty. status must be non-null.
         *
         * @return a validated UploadValidationStatus instance
         * @throws InvalidEntityException
         *         if called with invalid fields
         */
        public UploadValidationStatus build() throws InvalidEntityException {
            if (StringUtils.isBlank(id)) {
                throw new InvalidEntityException("id cannot be blank");
            }
            if (messageList == null) {
                throw new InvalidEntityException("messageList cannot be null");
            }
            if (status == null) {
                throw new InvalidEntityException("status cannot be null");
            }

            // loop through and validate message list
            int numMessages = messageList.size();
            for (int i = 0; i < numMessages; i++) {
                if (StringUtils.isBlank(messageList.get(i))) {
                    throw new InvalidEntityException(String.format("messageList[%d] is blank", i));
                }
            }

            // create a safe immutable copy of the message list
            List<String> messageListCopy = ImmutableList.copyOf(messageList);

            return new UploadValidationStatus(id, messageListCopy, status, record);
        }
    }
}
