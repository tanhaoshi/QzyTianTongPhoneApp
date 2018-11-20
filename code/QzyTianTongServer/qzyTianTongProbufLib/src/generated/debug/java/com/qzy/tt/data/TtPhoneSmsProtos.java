// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneSms.proto

package com.qzy.tt.data;

public final class TtPhoneSmsProtos {
  private TtPhoneSmsProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface TtPhoneSmsOrBuilder extends
      // @@protoc_insertion_point(interface_extends:phonedata.TtPhoneSms)
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
     * <code>optional string phoneNumber = 2;</code>
     */
    java.lang.String getPhoneNumber();
    /**
     * <code>optional string phoneNumber = 2;</code>
     */
    com.google.protobuf.ByteString
        getPhoneNumberBytes();

    /**
     * <pre>
     *是否是发送  否为返回
     * </pre>
     *
     * <code>optional bool isSend = 3;</code>
     */
    boolean getIsSend();

    /**
     * <code>optional bool isSendSuccess = 4;</code>
     */
    boolean getIsSendSuccess();

    /**
     * <pre>
     *对方是否成功接收短信
     * </pre>
     *
     * <code>optional bool isReceiverSuccess = 5;</code>
     */
    boolean getIsReceiverSuccess();

    /**
     * <code>optional string messageText = 6;</code>
     */
    java.lang.String getMessageText();
    /**
     * <code>optional string messageText = 6;</code>
     */
    com.google.protobuf.ByteString
        getMessageTextBytes();
  }
  /**
   * Protobuf type {@code phonedata.TtPhoneSms}
   */
  public  static final class TtPhoneSms extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:phonedata.TtPhoneSms)
      TtPhoneSmsOrBuilder {
    // Use TtPhoneSms.newBuilder() to construct.
    private TtPhoneSms(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private TtPhoneSms() {
      ip_ = "";
      phoneNumber_ = "";
      isSend_ = false;
      isSendSuccess_ = false;
      isReceiverSuccess_ = false;
      messageText_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private TtPhoneSms(
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
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              phoneNumber_ = s;
              break;
            }
            case 24: {

              isSend_ = input.readBool();
              break;
            }
            case 32: {

              isSendSuccess_ = input.readBool();
              break;
            }
            case 40: {

              isReceiverSuccess_ = input.readBool();
              break;
            }
            case 50: {
              java.lang.String s = input.readStringRequireUtf8();

              messageText_ = s;
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
      return com.qzy.tt.data.TtPhoneSmsProtos.internal_static_phonedata_TtPhoneSms_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qzy.tt.data.TtPhoneSmsProtos.internal_static_phonedata_TtPhoneSms_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms.class, com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms.Builder.class);
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

    public static final int PHONENUMBER_FIELD_NUMBER = 2;
    private volatile java.lang.Object phoneNumber_;
    /**
     * <code>optional string phoneNumber = 2;</code>
     */
    public java.lang.String getPhoneNumber() {
      java.lang.Object ref = phoneNumber_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        phoneNumber_ = s;
        return s;
      }
    }
    /**
     * <code>optional string phoneNumber = 2;</code>
     */
    public com.google.protobuf.ByteString
        getPhoneNumberBytes() {
      java.lang.Object ref = phoneNumber_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        phoneNumber_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ISSEND_FIELD_NUMBER = 3;
    private boolean isSend_;
    /**
     * <pre>
     *是否是发送  否为返回
     * </pre>
     *
     * <code>optional bool isSend = 3;</code>
     */
    public boolean getIsSend() {
      return isSend_;
    }

    public static final int ISSENDSUCCESS_FIELD_NUMBER = 4;
    private boolean isSendSuccess_;
    /**
     * <code>optional bool isSendSuccess = 4;</code>
     */
    public boolean getIsSendSuccess() {
      return isSendSuccess_;
    }

    public static final int ISRECEIVERSUCCESS_FIELD_NUMBER = 5;
    private boolean isReceiverSuccess_;
    /**
     * <pre>
     *对方是否成功接收短信
     * </pre>
     *
     * <code>optional bool isReceiverSuccess = 5;</code>
     */
    public boolean getIsReceiverSuccess() {
      return isReceiverSuccess_;
    }

    public static final int MESSAGETEXT_FIELD_NUMBER = 6;
    private volatile java.lang.Object messageText_;
    /**
     * <code>optional string messageText = 6;</code>
     */
    public java.lang.String getMessageText() {
      java.lang.Object ref = messageText_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        messageText_ = s;
        return s;
      }
    }
    /**
     * <code>optional string messageText = 6;</code>
     */
    public com.google.protobuf.ByteString
        getMessageTextBytes() {
      java.lang.Object ref = messageText_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        messageText_ = b;
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
      if (!getPhoneNumberBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, phoneNumber_);
      }
      if (isSend_ != false) {
        output.writeBool(3, isSend_);
      }
      if (isSendSuccess_ != false) {
        output.writeBool(4, isSendSuccess_);
      }
      if (isReceiverSuccess_ != false) {
        output.writeBool(5, isReceiverSuccess_);
      }
      if (!getMessageTextBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, messageText_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getIpBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, ip_);
      }
      if (!getPhoneNumberBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, phoneNumber_);
      }
      if (isSend_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(3, isSend_);
      }
      if (isSendSuccess_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(4, isSendSuccess_);
      }
      if (isReceiverSuccess_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(5, isReceiverSuccess_);
      }
      if (!getMessageTextBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, messageText_);
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
      if (!(obj instanceof com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms)) {
        return super.equals(obj);
      }
      com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms other = (com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms) obj;

      boolean result = true;
      result = result && getIp()
          .equals(other.getIp());
      result = result && getPhoneNumber()
          .equals(other.getPhoneNumber());
      result = result && (getIsSend()
          == other.getIsSend());
      result = result && (getIsSendSuccess()
          == other.getIsSendSuccess());
      result = result && (getIsReceiverSuccess()
          == other.getIsReceiverSuccess());
      result = result && getMessageText()
          .equals(other.getMessageText());
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
      hash = (37 * hash) + PHONENUMBER_FIELD_NUMBER;
      hash = (53 * hash) + getPhoneNumber().hashCode();
      hash = (37 * hash) + ISSEND_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsSend());
      hash = (37 * hash) + ISSENDSUCCESS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsSendSuccess());
      hash = (37 * hash) + ISRECEIVERSUCCESS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsReceiverSuccess());
      hash = (37 * hash) + MESSAGETEXT_FIELD_NUMBER;
      hash = (53 * hash) + getMessageText().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parseFrom(
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
    public static Builder newBuilder(com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms prototype) {
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
     * Protobuf type {@code phonedata.TtPhoneSms}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:phonedata.TtPhoneSms)
        com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSmsOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.qzy.tt.data.TtPhoneSmsProtos.internal_static_phonedata_TtPhoneSms_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.qzy.tt.data.TtPhoneSmsProtos.internal_static_phonedata_TtPhoneSms_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms.class, com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms.Builder.class);
      }

      // Construct using com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms.newBuilder()
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

        phoneNumber_ = "";

        isSend_ = false;

        isSendSuccess_ = false;

        isReceiverSuccess_ = false;

        messageText_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.qzy.tt.data.TtPhoneSmsProtos.internal_static_phonedata_TtPhoneSms_descriptor;
      }

      public com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms getDefaultInstanceForType() {
        return com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms.getDefaultInstance();
      }

      public com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms build() {
        com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms buildPartial() {
        com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms result = new com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms(this);
        result.ip_ = ip_;
        result.phoneNumber_ = phoneNumber_;
        result.isSend_ = isSend_;
        result.isSendSuccess_ = isSendSuccess_;
        result.isReceiverSuccess_ = isReceiverSuccess_;
        result.messageText_ = messageText_;
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
        if (other instanceof com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms) {
          return mergeFrom((com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms other) {
        if (other == com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms.getDefaultInstance()) return this;
        if (!other.getIp().isEmpty()) {
          ip_ = other.ip_;
          onChanged();
        }
        if (!other.getPhoneNumber().isEmpty()) {
          phoneNumber_ = other.phoneNumber_;
          onChanged();
        }
        if (other.getIsSend() != false) {
          setIsSend(other.getIsSend());
        }
        if (other.getIsSendSuccess() != false) {
          setIsSendSuccess(other.getIsSendSuccess());
        }
        if (other.getIsReceiverSuccess() != false) {
          setIsReceiverSuccess(other.getIsReceiverSuccess());
        }
        if (!other.getMessageText().isEmpty()) {
          messageText_ = other.messageText_;
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
        com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms) e.getUnfinishedMessage();
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

      private java.lang.Object phoneNumber_ = "";
      /**
       * <code>optional string phoneNumber = 2;</code>
       */
      public java.lang.String getPhoneNumber() {
        java.lang.Object ref = phoneNumber_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          phoneNumber_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string phoneNumber = 2;</code>
       */
      public com.google.protobuf.ByteString
          getPhoneNumberBytes() {
        java.lang.Object ref = phoneNumber_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          phoneNumber_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string phoneNumber = 2;</code>
       */
      public Builder setPhoneNumber(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        phoneNumber_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string phoneNumber = 2;</code>
       */
      public Builder clearPhoneNumber() {
        
        phoneNumber_ = getDefaultInstance().getPhoneNumber();
        onChanged();
        return this;
      }
      /**
       * <code>optional string phoneNumber = 2;</code>
       */
      public Builder setPhoneNumberBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        phoneNumber_ = value;
        onChanged();
        return this;
      }

      private boolean isSend_ ;
      /**
       * <pre>
       *是否是发送  否为返回
       * </pre>
       *
       * <code>optional bool isSend = 3;</code>
       */
      public boolean getIsSend() {
        return isSend_;
      }
      /**
       * <pre>
       *是否是发送  否为返回
       * </pre>
       *
       * <code>optional bool isSend = 3;</code>
       */
      public Builder setIsSend(boolean value) {
        
        isSend_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *是否是发送  否为返回
       * </pre>
       *
       * <code>optional bool isSend = 3;</code>
       */
      public Builder clearIsSend() {
        
        isSend_ = false;
        onChanged();
        return this;
      }

      private boolean isSendSuccess_ ;
      /**
       * <code>optional bool isSendSuccess = 4;</code>
       */
      public boolean getIsSendSuccess() {
        return isSendSuccess_;
      }
      /**
       * <code>optional bool isSendSuccess = 4;</code>
       */
      public Builder setIsSendSuccess(boolean value) {
        
        isSendSuccess_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool isSendSuccess = 4;</code>
       */
      public Builder clearIsSendSuccess() {
        
        isSendSuccess_ = false;
        onChanged();
        return this;
      }

      private boolean isReceiverSuccess_ ;
      /**
       * <pre>
       *对方是否成功接收短信
       * </pre>
       *
       * <code>optional bool isReceiverSuccess = 5;</code>
       */
      public boolean getIsReceiverSuccess() {
        return isReceiverSuccess_;
      }
      /**
       * <pre>
       *对方是否成功接收短信
       * </pre>
       *
       * <code>optional bool isReceiverSuccess = 5;</code>
       */
      public Builder setIsReceiverSuccess(boolean value) {
        
        isReceiverSuccess_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *对方是否成功接收短信
       * </pre>
       *
       * <code>optional bool isReceiverSuccess = 5;</code>
       */
      public Builder clearIsReceiverSuccess() {
        
        isReceiverSuccess_ = false;
        onChanged();
        return this;
      }

      private java.lang.Object messageText_ = "";
      /**
       * <code>optional string messageText = 6;</code>
       */
      public java.lang.String getMessageText() {
        java.lang.Object ref = messageText_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          messageText_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string messageText = 6;</code>
       */
      public com.google.protobuf.ByteString
          getMessageTextBytes() {
        java.lang.Object ref = messageText_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          messageText_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string messageText = 6;</code>
       */
      public Builder setMessageText(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        messageText_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string messageText = 6;</code>
       */
      public Builder clearMessageText() {
        
        messageText_ = getDefaultInstance().getMessageText();
        onChanged();
        return this;
      }
      /**
       * <code>optional string messageText = 6;</code>
       */
      public Builder setMessageTextBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        messageText_ = value;
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


      // @@protoc_insertion_point(builder_scope:phonedata.TtPhoneSms)
    }

    // @@protoc_insertion_point(class_scope:phonedata.TtPhoneSms)
    private static final com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms();
    }

    public static com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<TtPhoneSms>
        PARSER = new com.google.protobuf.AbstractParser<TtPhoneSms>() {
      public TtPhoneSms parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new TtPhoneSms(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<TtPhoneSms> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<TtPhoneSms> getParserForType() {
      return PARSER;
    }

    public com.qzy.tt.data.TtPhoneSmsProtos.TtPhoneSms getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_phonedata_TtPhoneSms_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_phonedata_TtPhoneSms_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020TtPhoneSms.proto\022\tphonedata\"\204\001\n\nTtPhon" +
      "eSms\022\n\n\002ip\030\001 \001(\t\022\023\n\013phoneNumber\030\002 \001(\t\022\016\n" +
      "\006isSend\030\003 \001(\010\022\025\n\risSendSuccess\030\004 \001(\010\022\031\n\021" +
      "isReceiverSuccess\030\005 \001(\010\022\023\n\013messageText\030\006" +
      " \001(\tB#\n\017com.qzy.tt.dataB\020TtPhoneSmsProto" +
      "sb\006proto3"
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
    internal_static_phonedata_TtPhoneSms_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_phonedata_TtPhoneSms_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_phonedata_TtPhoneSms_descriptor,
        new java.lang.String[] { "Ip", "PhoneNumber", "IsSend", "IsSendSuccess", "IsReceiverSuccess", "MessageText", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
