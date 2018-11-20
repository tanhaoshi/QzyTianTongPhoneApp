// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneGetServerVersion.proto

package com.qzy.tt.data;

public final class TtPhoneGetServerVersionProtos {
  private TtPhoneGetServerVersionProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface TtPhoneGetServerVersionOrBuilder extends
      // @@protoc_insertion_point(interface_extends:phonedata.TtPhoneGetServerVersion)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional string ip = 1;</code>
     */
    java.lang.String getIp();
    /**
     * <code>optional string ip = 1;</code>
     */
    com.google.protobuf.ByteString
        getIpBytes();

    /**
     * <code>optional bool isRequest = 2;</code>
     */
    boolean getIsRequest();

    /**
     * <pre>
     *服务版本号
     * </pre>
     *
     * <code>optional string serverApkVersionName = 3;</code>
     */
    java.lang.String getServerApkVersionName();
    /**
     * <pre>
     *服务版本号
     * </pre>
     *
     * <code>optional string serverApkVersionName = 3;</code>
     */
    com.google.protobuf.ByteString
        getServerApkVersionNameBytes();

    /**
     * <pre>
     * 串号
     * </pre>
     *
     * <code>optional string serverSieralNo = 4;</code>
     */
    java.lang.String getServerSieralNo();
    /**
     * <pre>
     * 串号
     * </pre>
     *
     * <code>optional string serverSieralNo = 4;</code>
     */
    com.google.protobuf.ByteString
        getServerSieralNoBytes();
  }
  /**
   * Protobuf type {@code phonedata.TtPhoneGetServerVersion}
   */
  public  static final class TtPhoneGetServerVersion extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:phonedata.TtPhoneGetServerVersion)
      TtPhoneGetServerVersionOrBuilder {
    // Use TtPhoneGetServerVersion.newBuilder() to construct.
    private TtPhoneGetServerVersion(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private TtPhoneGetServerVersion() {
      ip_ = "";
      isRequest_ = false;
      serverApkVersionName_ = "";
      serverSieralNo_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private TtPhoneGetServerVersion(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              ip_ = s;
              break;
            }
            case 16: {

              isRequest_ = input.readBool();
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              serverApkVersionName_ = s;
              break;
            }
            case 34: {
              java.lang.String s = input.readStringRequireUtf8();

              serverSieralNo_ = s;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.qzy.tt.data.TtPhoneGetServerVersionProtos.internal_static_phonedata_TtPhoneGetServerVersion_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qzy.tt.data.TtPhoneGetServerVersionProtos.internal_static_phonedata_TtPhoneGetServerVersion_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.class, com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.Builder.class);
    }

    public static final int IP_FIELD_NUMBER = 1;
    private volatile java.lang.Object ip_;
    /**
     * <code>optional string ip = 1;</code>
     */
    public java.lang.String getIp() {
      java.lang.Object ref = ip_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        ip_ = s;
        return s;
      }
    }
    /**
     * <code>optional string ip = 1;</code>
     */
    public com.google.protobuf.ByteString
        getIpBytes() {
      java.lang.Object ref = ip_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        ip_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ISREQUEST_FIELD_NUMBER = 2;
    private boolean isRequest_;
    /**
     * <code>optional bool isRequest = 2;</code>
     */
    public boolean getIsRequest() {
      return isRequest_;
    }

    public static final int SERVERAPKVERSIONNAME_FIELD_NUMBER = 3;
    private volatile java.lang.Object serverApkVersionName_;
    /**
     * <pre>
     *服务版本号
     * </pre>
     *
     * <code>optional string serverApkVersionName = 3;</code>
     */
    public java.lang.String getServerApkVersionName() {
      java.lang.Object ref = serverApkVersionName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        serverApkVersionName_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *服务版本号
     * </pre>
     *
     * <code>optional string serverApkVersionName = 3;</code>
     */
    public com.google.protobuf.ByteString
        getServerApkVersionNameBytes() {
      java.lang.Object ref = serverApkVersionName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serverApkVersionName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SERVERSIERALNO_FIELD_NUMBER = 4;
    private volatile java.lang.Object serverSieralNo_;
    /**
     * <pre>
     * 串号
     * </pre>
     *
     * <code>optional string serverSieralNo = 4;</code>
     */
    public java.lang.String getServerSieralNo() {
      java.lang.Object ref = serverSieralNo_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        serverSieralNo_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * 串号
     * </pre>
     *
     * <code>optional string serverSieralNo = 4;</code>
     */
    public com.google.protobuf.ByteString
        getServerSieralNoBytes() {
      java.lang.Object ref = serverSieralNo_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serverSieralNo_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getIpBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, ip_);
      }
      if (isRequest_ != false) {
        output.writeBool(2, isRequest_);
      }
      if (!getServerApkVersionNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, serverApkVersionName_);
      }
      if (!getServerSieralNoBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, serverSieralNo_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getIpBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, ip_);
      }
      if (isRequest_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, isRequest_);
      }
      if (!getServerApkVersionNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, serverApkVersionName_);
      }
      if (!getServerSieralNoBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, serverSieralNo_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion)) {
        return super.equals(obj);
      }
      com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion other = (com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion) obj;

      boolean result = true;
      result = result && getIp()
          .equals(other.getIp());
      result = result && (getIsRequest()
          == other.getIsRequest());
      result = result && getServerApkVersionName()
          .equals(other.getServerApkVersionName());
      result = result && getServerSieralNo()
          .equals(other.getServerSieralNo());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = (37 * hash) + IP_FIELD_NUMBER;
      hash = (53 * hash) + getIp().hashCode();
      hash = (37 * hash) + ISREQUEST_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsRequest());
      hash = (37 * hash) + SERVERAPKVERSIONNAME_FIELD_NUMBER;
      hash = (53 * hash) + getServerApkVersionName().hashCode();
      hash = (37 * hash) + SERVERSIERALNO_FIELD_NUMBER;
      hash = (53 * hash) + getServerSieralNo().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code phonedata.TtPhoneGetServerVersion}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:phonedata.TtPhoneGetServerVersion)
        com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersionOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.qzy.tt.data.TtPhoneGetServerVersionProtos.internal_static_phonedata_TtPhoneGetServerVersion_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.qzy.tt.data.TtPhoneGetServerVersionProtos.internal_static_phonedata_TtPhoneGetServerVersion_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.class, com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.Builder.class);
      }

      // Construct using com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        ip_ = "";

        isRequest_ = false;

        serverApkVersionName_ = "";

        serverSieralNo_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.qzy.tt.data.TtPhoneGetServerVersionProtos.internal_static_phonedata_TtPhoneGetServerVersion_descriptor;
      }

      public com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion getDefaultInstanceForType() {
        return com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.getDefaultInstance();
      }

      public com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion build() {
        com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion buildPartial() {
        com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion result = new com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion(this);
        result.ip_ = ip_;
        result.isRequest_ = isRequest_;
        result.serverApkVersionName_ = serverApkVersionName_;
        result.serverSieralNo_ = serverSieralNo_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion) {
          return mergeFrom((com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion other) {
        if (other == com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.getDefaultInstance()) return this;
        if (!other.getIp().isEmpty()) {
          ip_ = other.ip_;
          onChanged();
        }
        if (other.getIsRequest() != false) {
          setIsRequest(other.getIsRequest());
        }
        if (!other.getServerApkVersionName().isEmpty()) {
          serverApkVersionName_ = other.serverApkVersionName_;
          onChanged();
        }
        if (!other.getServerSieralNo().isEmpty()) {
          serverSieralNo_ = other.serverSieralNo_;
          onChanged();
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object ip_ = "";
      /**
       * <code>optional string ip = 1;</code>
       */
      public java.lang.String getIp() {
        java.lang.Object ref = ip_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          ip_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string ip = 1;</code>
       */
      public com.google.protobuf.ByteString
          getIpBytes() {
        java.lang.Object ref = ip_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          ip_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string ip = 1;</code>
       */
      public Builder setIp(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        ip_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string ip = 1;</code>
       */
      public Builder clearIp() {
        
        ip_ = getDefaultInstance().getIp();
        onChanged();
        return this;
      }
      /**
       * <code>optional string ip = 1;</code>
       */
      public Builder setIpBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        ip_ = value;
        onChanged();
        return this;
      }

      private boolean isRequest_ ;
      /**
       * <code>optional bool isRequest = 2;</code>
       */
      public boolean getIsRequest() {
        return isRequest_;
      }
      /**
       * <code>optional bool isRequest = 2;</code>
       */
      public Builder setIsRequest(boolean value) {
        
        isRequest_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool isRequest = 2;</code>
       */
      public Builder clearIsRequest() {
        
        isRequest_ = false;
        onChanged();
        return this;
      }

      private java.lang.Object serverApkVersionName_ = "";
      /**
       * <pre>
       *服务版本号
       * </pre>
       *
       * <code>optional string serverApkVersionName = 3;</code>
       */
      public java.lang.String getServerApkVersionName() {
        java.lang.Object ref = serverApkVersionName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          serverApkVersionName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       *服务版本号
       * </pre>
       *
       * <code>optional string serverApkVersionName = 3;</code>
       */
      public com.google.protobuf.ByteString
          getServerApkVersionNameBytes() {
        java.lang.Object ref = serverApkVersionName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          serverApkVersionName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *服务版本号
       * </pre>
       *
       * <code>optional string serverApkVersionName = 3;</code>
       */
      public Builder setServerApkVersionName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        serverApkVersionName_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *服务版本号
       * </pre>
       *
       * <code>optional string serverApkVersionName = 3;</code>
       */
      public Builder clearServerApkVersionName() {
        
        serverApkVersionName_ = getDefaultInstance().getServerApkVersionName();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *服务版本号
       * </pre>
       *
       * <code>optional string serverApkVersionName = 3;</code>
       */
      public Builder setServerApkVersionNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        serverApkVersionName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object serverSieralNo_ = "";
      /**
       * <pre>
       * 串号
       * </pre>
       *
       * <code>optional string serverSieralNo = 4;</code>
       */
      public java.lang.String getServerSieralNo() {
        java.lang.Object ref = serverSieralNo_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          serverSieralNo_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * 串号
       * </pre>
       *
       * <code>optional string serverSieralNo = 4;</code>
       */
      public com.google.protobuf.ByteString
          getServerSieralNoBytes() {
        java.lang.Object ref = serverSieralNo_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          serverSieralNo_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * 串号
       * </pre>
       *
       * <code>optional string serverSieralNo = 4;</code>
       */
      public Builder setServerSieralNo(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        serverSieralNo_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 串号
       * </pre>
       *
       * <code>optional string serverSieralNo = 4;</code>
       */
      public Builder clearServerSieralNo() {
        
        serverSieralNo_ = getDefaultInstance().getServerSieralNo();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 串号
       * </pre>
       *
       * <code>optional string serverSieralNo = 4;</code>
       */
      public Builder setServerSieralNoBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        serverSieralNo_ = value;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:phonedata.TtPhoneGetServerVersion)
    }

    // @@protoc_insertion_point(class_scope:phonedata.TtPhoneGetServerVersion)
    private static final com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion();
    }

    public static com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<TtPhoneGetServerVersion>
        PARSER = new com.google.protobuf.AbstractParser<TtPhoneGetServerVersion>() {
      public TtPhoneGetServerVersion parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new TtPhoneGetServerVersion(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<TtPhoneGetServerVersion> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<TtPhoneGetServerVersion> getParserForType() {
      return PARSER;
    }

    public com.qzy.tt.data.TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_phonedata_TtPhoneGetServerVersion_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_phonedata_TtPhoneGetServerVersion_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\035TtPhoneGetServerVersion.proto\022\tphoneda" +
      "ta\"n\n\027TtPhoneGetServerVersion\022\n\n\002ip\030\001 \001(" +
      "\t\022\021\n\tisRequest\030\002 \001(\010\022\034\n\024serverApkVersion" +
      "Name\030\003 \001(\t\022\026\n\016serverSieralNo\030\004 \001(\tB0\n\017co" +
      "m.qzy.tt.dataB\035TtPhoneGetServerVersionPr" +
      "otosb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_phonedata_TtPhoneGetServerVersion_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_phonedata_TtPhoneGetServerVersion_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_phonedata_TtPhoneGetServerVersion_descriptor,
        new java.lang.String[] { "Ip", "IsRequest", "ServerApkVersionName", "ServerSieralNo", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
