// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: protofiles/ApplicationProcessNamingInfoMessage.proto

package rina.encoding.impl.googleprotobuf.apnaminginfo;

public final class ApplicationProcessNamingInfoMessage {
  private ApplicationProcessNamingInfoMessage() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class applicationProcessNamingInfo_t extends
      com.google.protobuf.GeneratedMessage {
    // Use applicationProcessNamingInfo_t.newBuilder() to construct.
    private applicationProcessNamingInfo_t() {
      initFields();
    }
    private applicationProcessNamingInfo_t(boolean noInit) {}
    
    private static final applicationProcessNamingInfo_t defaultInstance;
    public static applicationProcessNamingInfo_t getDefaultInstance() {
      return defaultInstance;
    }
    
    public applicationProcessNamingInfo_t getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.internal_static_rina_messages_applicationProcessNamingInfo_t_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.internal_static_rina_messages_applicationProcessNamingInfo_t_fieldAccessorTable;
    }
    
    // required string applicationProcessName = 1;
    public static final int APPLICATIONPROCESSNAME_FIELD_NUMBER = 1;
    private boolean hasApplicationProcessName;
    private java.lang.String applicationProcessName_ = "";
    public boolean hasApplicationProcessName() { return hasApplicationProcessName; }
    public java.lang.String getApplicationProcessName() { return applicationProcessName_; }
    
    // optional string applicationProcessInstance = 2;
    public static final int APPLICATIONPROCESSINSTANCE_FIELD_NUMBER = 2;
    private boolean hasApplicationProcessInstance;
    private java.lang.String applicationProcessInstance_ = "";
    public boolean hasApplicationProcessInstance() { return hasApplicationProcessInstance; }
    public java.lang.String getApplicationProcessInstance() { return applicationProcessInstance_; }
    
    // optional string applicationEntityName = 3;
    public static final int APPLICATIONENTITYNAME_FIELD_NUMBER = 3;
    private boolean hasApplicationEntityName;
    private java.lang.String applicationEntityName_ = "";
    public boolean hasApplicationEntityName() { return hasApplicationEntityName; }
    public java.lang.String getApplicationEntityName() { return applicationEntityName_; }
    
    // optional string applicationEntityInstance = 4;
    public static final int APPLICATIONENTITYINSTANCE_FIELD_NUMBER = 4;
    private boolean hasApplicationEntityInstance;
    private java.lang.String applicationEntityInstance_ = "";
    public boolean hasApplicationEntityInstance() { return hasApplicationEntityInstance; }
    public java.lang.String getApplicationEntityInstance() { return applicationEntityInstance_; }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      if (!hasApplicationProcessName) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasApplicationProcessName()) {
        output.writeString(1, getApplicationProcessName());
      }
      if (hasApplicationProcessInstance()) {
        output.writeString(2, getApplicationProcessInstance());
      }
      if (hasApplicationEntityName()) {
        output.writeString(3, getApplicationEntityName());
      }
      if (hasApplicationEntityInstance()) {
        output.writeString(4, getApplicationEntityInstance());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasApplicationProcessName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getApplicationProcessName());
      }
      if (hasApplicationProcessInstance()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getApplicationProcessInstance());
      }
      if (hasApplicationEntityName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(3, getApplicationEntityName());
      }
      if (hasApplicationEntityInstance()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(4, getApplicationEntityInstance());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t result;
      
      // Construct using rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t();
        return builder;
      }
      
      protected rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t.getDescriptor();
      }
      
      public rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t getDefaultInstanceForType() {
        return rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t) {
          return mergeFrom((rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t other) {
        if (other == rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t.getDefaultInstance()) return this;
        if (other.hasApplicationProcessName()) {
          setApplicationProcessName(other.getApplicationProcessName());
        }
        if (other.hasApplicationProcessInstance()) {
          setApplicationProcessInstance(other.getApplicationProcessInstance());
        }
        if (other.hasApplicationEntityName()) {
          setApplicationEntityName(other.getApplicationEntityName());
        }
        if (other.hasApplicationEntityInstance()) {
          setApplicationEntityInstance(other.getApplicationEntityInstance());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              setApplicationProcessName(input.readString());
              break;
            }
            case 18: {
              setApplicationProcessInstance(input.readString());
              break;
            }
            case 26: {
              setApplicationEntityName(input.readString());
              break;
            }
            case 34: {
              setApplicationEntityInstance(input.readString());
              break;
            }
          }
        }
      }
      
      
      // required string applicationProcessName = 1;
      public boolean hasApplicationProcessName() {
        return result.hasApplicationProcessName();
      }
      public java.lang.String getApplicationProcessName() {
        return result.getApplicationProcessName();
      }
      public Builder setApplicationProcessName(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasApplicationProcessName = true;
        result.applicationProcessName_ = value;
        return this;
      }
      public Builder clearApplicationProcessName() {
        result.hasApplicationProcessName = false;
        result.applicationProcessName_ = getDefaultInstance().getApplicationProcessName();
        return this;
      }
      
      // optional string applicationProcessInstance = 2;
      public boolean hasApplicationProcessInstance() {
        return result.hasApplicationProcessInstance();
      }
      public java.lang.String getApplicationProcessInstance() {
        return result.getApplicationProcessInstance();
      }
      public Builder setApplicationProcessInstance(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasApplicationProcessInstance = true;
        result.applicationProcessInstance_ = value;
        return this;
      }
      public Builder clearApplicationProcessInstance() {
        result.hasApplicationProcessInstance = false;
        result.applicationProcessInstance_ = getDefaultInstance().getApplicationProcessInstance();
        return this;
      }
      
      // optional string applicationEntityName = 3;
      public boolean hasApplicationEntityName() {
        return result.hasApplicationEntityName();
      }
      public java.lang.String getApplicationEntityName() {
        return result.getApplicationEntityName();
      }
      public Builder setApplicationEntityName(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasApplicationEntityName = true;
        result.applicationEntityName_ = value;
        return this;
      }
      public Builder clearApplicationEntityName() {
        result.hasApplicationEntityName = false;
        result.applicationEntityName_ = getDefaultInstance().getApplicationEntityName();
        return this;
      }
      
      // optional string applicationEntityInstance = 4;
      public boolean hasApplicationEntityInstance() {
        return result.hasApplicationEntityInstance();
      }
      public java.lang.String getApplicationEntityInstance() {
        return result.getApplicationEntityInstance();
      }
      public Builder setApplicationEntityInstance(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasApplicationEntityInstance = true;
        result.applicationEntityInstance_ = value;
        return this;
      }
      public Builder clearApplicationEntityInstance() {
        result.hasApplicationEntityInstance = false;
        result.applicationEntityInstance_ = getDefaultInstance().getApplicationEntityInstance();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:rina.messages.applicationProcessNamingInfo_t)
    }
    
    static {
      defaultInstance = new applicationProcessNamingInfo_t(true);
      rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:rina.messages.applicationProcessNamingInfo_t)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_rina_messages_applicationProcessNamingInfo_t_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_rina_messages_applicationProcessNamingInfo_t_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n4protofiles/ApplicationProcessNamingInf" +
      "oMessage.proto\022\rrina.messages\"\246\001\n\036applic" +
      "ationProcessNamingInfo_t\022\036\n\026applicationP" +
      "rocessName\030\001 \002(\t\022\"\n\032applicationProcessIn" +
      "stance\030\002 \001(\t\022\035\n\025applicationEntityName\030\003 " +
      "\001(\t\022!\n\031applicationEntityInstance\030\004 \001(\tB0" +
      "\n.rina.encoding.impl.googleprotobuf.apna" +
      "minginfo"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_rina_messages_applicationProcessNamingInfo_t_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_rina_messages_applicationProcessNamingInfo_t_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_rina_messages_applicationProcessNamingInfo_t_descriptor,
              new java.lang.String[] { "ApplicationProcessName", "ApplicationProcessInstance", "ApplicationEntityName", "ApplicationEntityInstance", },
              rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t.class,
              rina.encoding.impl.googleprotobuf.apnaminginfo.ApplicationProcessNamingInfoMessage.applicationProcessNamingInfo_t.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  public static void internalForceInit() {}
  
  // @@protoc_insertion_point(outer_class_scope)
}
