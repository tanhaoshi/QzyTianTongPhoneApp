// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneMobileData.proto

package com.qzy.tt.data;

public final class TtPhoneMobileDataProtos {
  private TtPhoneMobileDataProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface TtPhoneMobileDataOrBuilder extends
      // @@protoc_insertion_point(interface_extends:phonedata.TtPhoneMobileData)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     *应用端请求服务端打开开关状态
     * </pre>
     *
     * <code>optional bool isEnableData = 1;</code>
     */
    boolean getIsEnableData();

    /**
     * <pre>
     *服务端打开数据流量是否成功
     * </pre>
     *
     * <code>optional bool responseStatus = 2;</code>
     */
    boolean getResponseStatus();
  }
  /**
   * Protobuf type {@code phonedata.TtPhoneMobileData}
   */
  public  static final class TtPhoneMobileData extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:phonedata.TtPhoneMobileData)
      TtPhoneMobileDataOrBuilder {
    // Use TtPhoneMobileData.newBuilder() to construct.
    private TtPhoneMobileData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private TtPhoneMobileData() {
      isEnableData_ = false;
      responseStatus_ = false;
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private TtPhoneMobileData(
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
            case 8: {

              isEnableData_ = input.readBool();
              break;
            }
            case 16: {

              responseStatus_ = input.readBool();
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
      return com.qzy.tt.data.TtPhoneMobileDataProtos.internal_static_phonedata_TtPhoneMobileData_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qzy.tt.data.TtPhoneMobileDataProtos.internal_static_phonedata_TtPhoneMobileData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData.class, com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData.Builder.class);
    }

    public static final int ISENABLEDATA_FIELD_NUMBER = 1;
    private boolean isEnableData_;
    /**
     * <pre>
     *应用端请求服务端打开开关状态
     * </pre>
     *
     * <code>optional bool isEnableData = 1;</code>
     */
    public boolean getIsEnableData() {
      return isEnableData_;
    }

    public static final int RESPONSESTATUS_FIELD_NUMBER = 2;
    private boolean responseStatus_;
    /**
     * <pre>
     *服务端打开数据流量是否成功
     * </pre>
     *
     * <code>optional bool responseStatus = 2;</code>
     */
    public boolean getResponseStatus() {
      return responseStatus_;
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
      if (isEnableData_ != false) {
        output.writeBool(1, isEnableData_);
      }
      if (responseStatus_ != false) {
        output.writeBool(2, responseStatus_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (isEnableData_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(1, isEnableData_);
      }
      if (responseStatus_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, responseStatus_);
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
      if (!(obj instanceof com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData)) {
        return super.equals(obj);
      }
      com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData other = (com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData) obj;

      boolean result = true;
      result = result && (getIsEnableData()
          == other.getIsEnableData());
      result = result && (getResponseStatus()
          == other.getResponseStatus());
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = (37 * hash) + ISENABLEDATA_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsEnableData());
      hash = (37 * hash) + RESPONSESTATUS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getResponseStatus());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parseFrom(
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
    public static Builder newBuilder(com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData prototype) {
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
     * Protobuf type {@code phonedata.TtPhoneMobileData}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:phonedata.TtPhoneMobileData)
        com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileDataOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.qzy.tt.data.TtPhoneMobileDataProtos.internal_static_phonedata_TtPhoneMobileData_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.qzy.tt.data.TtPhoneMobileDataProtos.internal_static_phonedata_TtPhoneMobileData_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData.class, com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData.Builder.class);
      }

      // Construct using com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData.newBuilder()
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
        isEnableData_ = false;

        responseStatus_ = false;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.qzy.tt.data.TtPhoneMobileDataProtos.internal_static_phonedata_TtPhoneMobileData_descriptor;
      }

      public com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData getDefaultInstanceForType() {
        return com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData.getDefaultInstance();
      }

      public com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData build() {
        com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData buildPartial() {
        com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData result = new com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData(this);
        result.isEnableData_ = isEnableData_;
        result.responseStatus_ = responseStatus_;
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
        if (other instanceof com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData) {
          return mergeFrom((com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData other) {
        if (other == com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData.getDefaultInstance()) return this;
        if (other.getIsEnableData() != false) {
          setIsEnableData(other.getIsEnableData());
        }
        if (other.getResponseStatus() != false) {
          setResponseStatus(other.getResponseStatus());
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
        com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private boolean isEnableData_ ;
      /**
       * <pre>
       *应用端请求服务端打开开关状态
       * </pre>
       *
       * <code>optional bool isEnableData = 1;</code>
       */
      public boolean getIsEnableData() {
        return isEnableData_;
      }
      /**
       * <pre>
       *应用端请求服务端打开开关状态
       * </pre>
       *
       * <code>optional bool isEnableData = 1;</code>
       */
      public Builder setIsEnableData(boolean value) {

        isEnableData_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *应用端请求服务端打开开关状态
       * </pre>
       *
       * <code>optional bool isEnableData = 1;</code>
       */
      public Builder clearIsEnableData() {

        isEnableData_ = false;
        onChanged();
        return this;
      }

      private boolean responseStatus_ ;
      /**
       * <pre>
       *服务端打开数据流量是否成功
       * </pre>
       *
       * <code>optional bool responseStatus = 2;</code>
       */
      public boolean getResponseStatus() {
        return responseStatus_;
      }
      /**
       * <pre>
       *服务端打开数据流量是否成功
       * </pre>
       *
       * <code>optional bool responseStatus = 2;</code>
       */
      public Builder setResponseStatus(boolean value) {

        responseStatus_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *服务端打开数据流量是否成功
       * </pre>
       *
       * <code>optional bool responseStatus = 2;</code>
       */
      public Builder clearResponseStatus() {

        responseStatus_ = false;
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


      // @@protoc_insertion_point(builder_scope:phonedata.TtPhoneMobileData)
    }

    // @@protoc_insertion_point(class_scope:phonedata.TtPhoneMobileData)
    private static final com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData();
    }

    public static com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<TtPhoneMobileData>
        PARSER = new com.google.protobuf.AbstractParser<TtPhoneMobileData>() {
      public TtPhoneMobileData parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new TtPhoneMobileData(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<TtPhoneMobileData> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<TtPhoneMobileData> getParserForType() {
      return PARSER;
    }

    public com.qzy.tt.data.TtPhoneMobileDataProtos.TtPhoneMobileData getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_phonedata_TtPhoneMobileData_descriptor;
  private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_phonedata_TtPhoneMobileData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\027TtPhoneMobileData.proto\022\tphonedata\"A\n\021" +
      "TtPhoneMobileData\022\024\n\014isEnableData\030\001 \001(\010\022" +
      "\026\n\016responseStatus\030\002 \001(\010B*\n\017com.qzy.tt.da" +
      "taB\027TtPhoneMobileDataProtosb\006proto3"
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
    internal_static_phonedata_TtPhoneMobileData_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_phonedata_TtPhoneMobileData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_phonedata_TtPhoneMobileData_descriptor,
        new String[] { "IsEnableData", "ResponseStatus", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
