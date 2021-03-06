// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: CallPhoneBack.proto

package com.qzy.tt.data;

public final class CallPhoneBackProtos {
  private CallPhoneBackProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface CallPhoneBackOrBuilder extends
      // @@protoc_insertion_point(interface_extends:phonedata.CallPhoneBack)
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
     * <code>optional bool isCalling = 2;</code>
     */
    boolean getIsCalling();
  }
  /**
   * Protobuf type {@code phonedata.CallPhoneBack}
   */
  public  static final class CallPhoneBack extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:phonedata.CallPhoneBack)
      CallPhoneBackOrBuilder {
    // Use CallPhoneBack.newBuilder() to construct.
    private CallPhoneBack(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private CallPhoneBack() {
      ip_ = "";
      isCalling_ = false;
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private CallPhoneBack(
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

              isCalling_ = input.readBool();
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
      return CallPhoneBackProtos.internal_static_phonedata_CallPhoneBack_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return CallPhoneBackProtos.internal_static_phonedata_CallPhoneBack_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              CallPhoneBack.class, Builder.class);
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

    public static final int ISCALLING_FIELD_NUMBER = 2;
    private boolean isCalling_;
    /**
     * <code>optional bool isCalling = 2;</code>
     */
    public boolean getIsCalling() {
      return isCalling_;
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
      if (isCalling_ != false) {
        output.writeBool(2, isCalling_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getIpBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, ip_);
      }
      if (isCalling_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, isCalling_);
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
      if (!(obj instanceof CallPhoneBack)) {
        return super.equals(obj);
      }
      CallPhoneBack other = (CallPhoneBack) obj;

      boolean result = true;
      result = result && getIp()
          .equals(other.getIp());
      result = result && (getIsCalling()
          == other.getIsCalling());
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
      hash = (37 * hash) + ISCALLING_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsCalling());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static CallPhoneBack parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static CallPhoneBack parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static CallPhoneBack parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static CallPhoneBack parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static CallPhoneBack parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static CallPhoneBack parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static CallPhoneBack parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static CallPhoneBack parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static CallPhoneBack parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static CallPhoneBack parseFrom(
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
    public static Builder newBuilder(CallPhoneBack prototype) {
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
     * Protobuf type {@code phonedata.CallPhoneBack}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:phonedata.CallPhoneBack)
        CallPhoneBackOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return CallPhoneBackProtos.internal_static_phonedata_CallPhoneBack_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return CallPhoneBackProtos.internal_static_phonedata_CallPhoneBack_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                CallPhoneBack.class, Builder.class);
      }

      // Construct using com.qzy.tt.data.CallPhoneBackProtos.CallPhoneBack.newBuilder()
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

        isCalling_ = false;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return CallPhoneBackProtos.internal_static_phonedata_CallPhoneBack_descriptor;
      }

      public CallPhoneBack getDefaultInstanceForType() {
        return CallPhoneBack.getDefaultInstance();
      }

      public CallPhoneBack build() {
        CallPhoneBack result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public CallPhoneBack buildPartial() {
        CallPhoneBack result = new CallPhoneBack(this);
        result.ip_ = ip_;
        result.isCalling_ = isCalling_;
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
        if (other instanceof CallPhoneBack) {
          return mergeFrom((CallPhoneBack)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(CallPhoneBack other) {
        if (other == CallPhoneBack.getDefaultInstance()) return this;
        if (!other.getIp().isEmpty()) {
          ip_ = other.ip_;
          onChanged();
        }
        if (other.getIsCalling() != false) {
          setIsCalling(other.getIsCalling());
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
        CallPhoneBack parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (CallPhoneBack) e.getUnfinishedMessage();
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

      private boolean isCalling_ ;
      /**
       * <code>optional bool isCalling = 2;</code>
       */
      public boolean getIsCalling() {
        return isCalling_;
      }
      /**
       * <code>optional bool isCalling = 2;</code>
       */
      public Builder setIsCalling(boolean value) {
        
        isCalling_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool isCalling = 2;</code>
       */
      public Builder clearIsCalling() {
        
        isCalling_ = false;
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


      // @@protoc_insertion_point(builder_scope:phonedata.CallPhoneBack)
    }

    // @@protoc_insertion_point(class_scope:phonedata.CallPhoneBack)
    private static final CallPhoneBack DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new CallPhoneBack();
    }

    public static CallPhoneBack getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CallPhoneBack>
        PARSER = new com.google.protobuf.AbstractParser<CallPhoneBack>() {
      public CallPhoneBack parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new CallPhoneBack(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<CallPhoneBack> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<CallPhoneBack> getParserForType() {
      return PARSER;
    }

    public CallPhoneBack getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_phonedata_CallPhoneBack_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_phonedata_CallPhoneBack_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\023CallPhoneBack.proto\022\tphonedata\".\n\rCall" +
      "PhoneBack\022\n\n\002ip\030\001 \001(\t\022\021\n\tisCalling\030\002 \001(\010" +
      "B&\n\017com.qzy.tt.dataB\023CallPhoneBackProtos" +
      "b\006proto3"
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
    internal_static_phonedata_CallPhoneBack_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_phonedata_CallPhoneBack_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_phonedata_CallPhoneBack_descriptor,
        new String[] { "Ip", "IsCalling", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
