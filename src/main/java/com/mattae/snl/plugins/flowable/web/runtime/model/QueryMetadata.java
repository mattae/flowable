package com.mattae.snl.plugins.flowable.web.runtime.model;

import org.flowable.common.engine.api.FlowableIllegalArgumentException;

public class QueryMetadata {
    protected String name;

    protected String operation;

    protected Object value;

    protected String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public QueryMetadataOperation getMetadataOperation() {
        if (this.operation == null)
            return null;
        return QueryMetadataOperation.forFriendlyName(this.operation);
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public enum QueryMetadataOperation {
        EXISTS("exists"),
        NOT_EXISTS("notExists"),
        EQUALS("equals"),
        NOT_EQUALS("notEquals"),
        EQUALS_IGNORE_CASE("equalsIgnoreCase"),
        NOT_EQUALS_IGNORE_CASE("notEqualsIgnoreCase"),
        LIKE("like"),
        LIKE_IGNORE_CASE("likeIgnoreCase"),
        GREATER_THAN("greaterThan"),
        GREATER_THAN_OR_EQUALS("greaterThanOrEquals"),
        LESS_THAN("lessThan"),
        LESS_THAN_OR_EQUALS("lessThanOrEquals");

        private final String friendlyName;

        QueryMetadataOperation(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        public static QueryMetadataOperation forFriendlyName(String friendlyName) {
            for (QueryMetadataOperation type : values()) {
                if (type.friendlyName.equals(friendlyName))
                    return type;
            }
            throw new FlowableIllegalArgumentException("Unsupported metadata query operation: " + friendlyName);
        }

        public String getFriendlyName() {
            return this.friendlyName;
        }
    }
}
