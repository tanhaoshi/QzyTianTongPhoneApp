// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtTime.proto

package com.qzy.tt.data;

public final class TtTimeProtos {
  private TtTimeProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface TtTimeOrBuilder extends
      // @@protoc_insertion_point(interface_extends:phonedata.TtTime)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional string ip = 1;</code>
     */
    String getIp();
    /**
     * <code>optional string ip = 1;</code>
     */
    com.google.protobuf.ByteString
        getIpBytes();

    /**
     * <code>optional bool isSync = 2;</code>
     */
    boolean getIsSync();

    /**
     * <pre>
     * YYYY-mm-dd hh:mm:ss
     * </pre>
     *
     * <code>optional string date_time = 3;</code>
     */
    String getDateTime();
    /**
     * <pre>
     * YYYY-mm-dd hh:mm:ss
     * </pre>
     *
     * <code>optional string date_time = 3;</code>
     */
    com.google.protobuf.ByteString
        getDateTimeBytes();

    /**
     * <pre>
     * YYYY-mm-dd hh:mm:ss
     * </pre>
     *
     * <code>optional string date_time_server = 4;</code>
     */
    String getDateTimeServer();
    /**
     * <pre>
     * YYYY-mm-dd hh:mm:ss
     * </pre>
     *
     * <code>optional string date_time_server = 4;</code>
     */
    com.google.protobuf.ByteString
        getDateTimeServerBytes();
  }
  /**
   * Protobuf type {@code phonedata.TtTime}
   */
  public  static final class TtTime extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:phonedata.TtTime)
      TtTimeOrBuilder {
    // Use TtTime.newBuilder() to construct.
    private TtTime(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private TtTime() {
      ip_ = "";
      isSync_ = false;
      dateTime_ = "";
      dateTimeServer_ = "";
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private TtTime(
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
              String s = input.readStringRequireUtf8();

              ip_ = s;
              break;
            }
            case 16: {

              isSync_ = input.readBool();
              break;
            }
            case 26: {
              String s = input.readStringRequireUtf8();

              dateTime_ = s;
              break;
            }
            case 34: {
              String s = input.readStringRequireUtf8();

              dateTimeServer_ = s;
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
      return TtTimeProtos.internal_static_phonedata_TtTime_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return TtTimeProtos.internal_static_phonedata_TtTime_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              TtTime.class, Builder.class);
    }

    public static final int IP_FIELD_NUMBER = 1;
    private volatile Object ip_;
    /**
     * <code>optional string ip = 1;</code>
     */
    public String getIp() {
      Object ref = ip_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        ip_ = s;
        return s;
      }
    }
    /**
     * <code>optional string ip = 1;</code>
     */
    public com.google.protobuf.ByteString
        getIpBytes() {
      Object ref = ip_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        ip_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ISSYNC_FIELD_NUMBER = 2;
    private boolean isSync_;
    /**
     * <code>optional bool isSync = 2;</code>
     */
    public boolean getIsSync() {
      return isSync_;
    }

    public static final int DATE_TIME_FIELD_NUMBER = 3;
    private volatile Object dateTime_;
    /**
     * <pre>
     * YYYY-mm-dd hh:mm:ss
     * </pre>
     *
     * <code>optional string date_time = 3;</code>
     */
    public String getDateTime() {
      Object ref = dateTime_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        dateTime_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * YYYY-mm-dd hh:mm:ss
     * </pre>
     *
     * <code>optional string date_time = 3;</code>
     */
    public com.google.protobuf.ByteString
        getDateTimeBytes() {
      Object ref = dateTime_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        dateTime_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DATE_TIME_SERVER_FIELD_NUMBER = 4;
    private volatile Object dateTimeServer_;
    /**
     * <pre>
     * YYYY-mm-dd hh:mm:ss
     * </pre>
     *
     * <code>optional string date_time_server = 4;</code>
     */
    public String getDateTimeServer() {
      Object ref = dateTimeServer_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        dateTimeServer_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * YYYY-mm-dd hh:mm:ss
     * </pre>
     *
     * <code>optional string date_time_server = 4;</code>
     */
    public com.google.protobuf.ByteString
        getDateTimeServerBytes() {
      Object ref = dateTimeServer_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        dateTimeServer_ = b;
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
      if (isSync_ != false) {
        output.writeBool(2, isSync_);
      }
      if (!getDateTimeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, dateTime_);
      }
      if (!getDateTimeServerBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, dateTimeServer_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getIpBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, ip_);
      }
      if (isSync_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, isSync_);
      }
      if (!getDateTimeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, dateTime_);
      }
      if (!getDateTimeServerBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, dateTimeServer_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof TtTime)) {
        return super.equals(obj);
      }
      TtTime other = (TtTime) obj;

      boolean result = true;
      result = result && getIp()
          .equals(other.getIp());
      result = result && (getIsSync()
          == other.getIsSync());
      result = result && getDateTime()
          .equals(other.getDateTime());
      result = result && getDateTimeServer()
          .equals(other.getDateTimeServer());
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = (37 * hash) + IP_FIELD_NUMBER;
      hash = (53 * hash) + getIp().hashCode();
      hash = (37 * hash) + ISSYNC_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsSync());
      hash = (37 * hash) + DATE_TIME_FIELD_NUMBER;
      hash = (53 * hash) + getDateTime().hashCode();
      hash = (37 * hash) + DATE_TIME_SERVER_FIELD_NUMBER;
      hash = (53 * hash) + getDateTimeServer().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static TtTime parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TtTime parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TtTime parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TtTime parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TtTime parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static TtTime parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static TtTime parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static TtTime parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static TtTime parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static TtTime parseFrom(
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
    public static Builder newBuilder(TtTime prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code phonedata.TtTime}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:phonedata.TtTime)
        TtTimeOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return TtTimeProtos.internal_static_phonedata_TtTime_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return TtTimeProtos.internal_static_phonedata_TtTime_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                TtTime.class, Builder.class);
      }

      // Construct using com.qzy.tt.data.TtTimeProtos.TtTime.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
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

        isSync_ = false;

        dateTime_ = "";

        dateTimeServer_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return TtTimeProtos.internal_static_phonedata_TtTime_descriptor;
      }

      public TtTime getDefaultInstanceForType() {
        return TtTime.getDefaultInstance();
      }

      public TtTime build() {
        TtTime result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public TtTime buildPartial() {
        TtTime result = new TtTime(this);
        result.ip_ = ip_;
        result.isSync_ = isSync_;
        result.dateTime_ = dateTime_;
        result.dateTimeServer_ = dateTimeServer_;
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
        if (other instanceof TtTime) {
          return mergeFrom((TtTime)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(TtTime other) {
        if (other == TtTime.getDefaultInstance()) return this;
        if (!other.getIp().isEmpty()) {
          ip_ = other.ip_;
          onChanged();
        }
        if (other.getIsSync() != false) {
          setIsSync(other.getIsSync());
        }
        if (!other.getDateTime().isEmpty()) {
          dateTime_ = other.dateTime_;
          onChanged();
        }
        if (!other.getDateTimeServer().isEmpty()) {
          dateTimeServer_ = other.dateTimeServer_;
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
        TtTime parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (TtTime) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private Object ip_ = "";
      /**
       * <code>optional string ip = 1;</code>
       */
      public String getIp() {
        Object ref = ip_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          ip_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>optional string ip = 1;</code>
       */
      public com.google.protobuf.ByteString
          getIpBytes() {
        Object ref = ip_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
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
          String value) {
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

      private boolean isSync_ ;
      /**
       * <code>optional bool isSync = 2;</code>
       */
      public boolean getIsSync() {
        return isSync_;
      }
      /**
       * <code>optional bool isSync = 2;</code>
       */
      public Builder setIsSync(boolean value) {
        
        isSync_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool isSync = 2;</code>
       */
      public Builder clearIsSync() {
        
        isSync_ = false;
        onChanged();
        return this;
      }

      private Object dateTime_ = "";
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time = 3;</code>
       */
      public String getDateTime() {
        Object ref = dateTime_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          dateTime_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time = 3;</code>
       */
      public com.google.protobuf.ByteString
          getDateTimeBytes() {
        Object ref = dateTime_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          dateTime_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time = 3;</code>
       */
      public Builder setDateTime(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        dateTime_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time = 3;</code>
       */
      public Builder clearDateTime() {
        
        dateTime_ = getDefaultInstance().getDateTime();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time = 3;</code>
       */
      public Builder setDateTimeBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        dateTime_ = value;
        onChanged();
        return this;
      }

      private Object dateTimeServer_ = "";
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time_server = 4;</code>
       */
      public String getDateTimeServer() {
        Object ref = dateTimeServer_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          dateTimeServer_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time_server = 4;</code>
       */
      public com.google.protobuf.ByteString
          getDateTimeServerBytes() {
        Object ref = dateTimeServer_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          dateTimeServer_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time_server = 4;</code>
       */
      public Builder setDateTimeServer(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        dateTimeServer_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time_server = 4;</code>
       */
      public Builder clearDateTimeServer() {
        
        dateTimeServer_ = getDefaultInstance().getDateTimeServer();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * YYYY-mm-dd hh:mm:ss
       * </pre>
       *
       * <code>optional string date_time_server = 4;</code>
       */
      public Builder setDateTimeServerBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        dateTimeServer_ = value;
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


      // @@protoc_insertion_point(builder_scope:phonedata.TtTime)
    }

    // @@protoc_insertion_point(class_scope:phonedata.TtTime)
    private static final TtTime DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new TtTime();
    }

    public static TtTime getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<TtTime>
        PARSER = new com.google.protobuf.AbstractParser<TtTime>() {
      public TtTime parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new TtTime(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<TtTime> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<TtTime> getParserForType() {
      return PARSER;
    }

    public TtTime getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_phonedata_TtTime_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_phonedata_TtTime_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\014TtTime.proto\022\tphonedata\"Q\n\006TtTime\022\n\n\002i" +
      "p\030\001 \001(\t\022\016\n\006isSync\030\002 \001(\010\022\021\n\tdate_time\030\003 \001" +
      "(\t\022\030\n\020date_time_server\030\004 \001(\tB\037\n\017com.qzy." +
      "tt.dataB\014TtTimeProtosb\006proto3"
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
    internal_static_phonedata_TtTime_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_phonedata_TtTime_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_phonedata_TtTime_descriptor,
        new String[] { "Ip", "IsSync", "DateTime", "DateTimeServer", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
