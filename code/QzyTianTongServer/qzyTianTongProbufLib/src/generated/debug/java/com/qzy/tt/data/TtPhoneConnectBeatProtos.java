// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneConnctBeat.proto

package com.qzy.tt.data;

public final class TtPhoneConnectBeatProtos {
  private TtPhoneConnectBeatProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface TtPhoneConnectBeatOrBuilder extends
      // @@protoc_insertion_point(interface_extends:phonedata.TtPhoneConnectBeat)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional bool isConnect = 1;</code>
     */
    boolean getIsConnect();

    /**
     * <code>optional bool request = 2;</code>
     */
    boolean getRequest();

    /**
     * <code>optional bool response = 3;</code>
     */
    boolean getResponse();
  }
  /**
   * Protobuf type {@code phonedata.TtPhoneConnectBeat}
   */
  public  static final class TtPhoneConnectBeat extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:phonedata.TtPhoneConnectBeat)
      TtPhoneConnectBeatOrBuilder {
    // Use TtPhoneConnectBeat.newBuilder() to construct.
    private TtPhoneConnectBeat(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private TtPhoneConnectBeat() {
      isConnect_ = false;
      request_ = false;
      response_ = false;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private TtPhoneConnectBeat(
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

              isConnect_ = input.readBool();
              break;
            }
            case 16: {

              request_ = input.readBool();
              break;
            }
            case 24: {

              response_ = input.readBool();
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
      return com.qzy.tt.data.TtPhoneConnectBeatProtos.internal_static_phonedata_TtPhoneConnectBeat_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qzy.tt.data.TtPhoneConnectBeatProtos.internal_static_phonedata_TtPhoneConnectBeat_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat.class, com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat.Builder.class);
    }

    public static final int ISCONNECT_FIELD_NUMBER = 1;
    private boolean isConnect_;
    /**
     * <code>optional bool isConnect = 1;</code>
     */
    public boolean getIsConnect() {
      return isConnect_;
    }

    public static final int REQUEST_FIELD_NUMBER = 2;
    private boolean request_;
    /**
     * <code>optional bool request = 2;</code>
     */
    public boolean getRequest() {
      return request_;
    }

    public static final int RESPONSE_FIELD_NUMBER = 3;
    private boolean response_;
    /**
     * <code>optional bool response = 3;</code>
     */
    public boolean getResponse() {
      return response_;
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
      if (isConnect_ != false) {
        output.writeBool(1, isConnect_);
      }
      if (request_ != false) {
        output.writeBool(2, request_);
      }
      if (response_ != false) {
        output.writeBool(3, response_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (isConnect_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(1, isConnect_);
      }
      if (request_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, request_);
      }
      if (response_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(3, response_);
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
      if (!(obj instanceof com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat)) {
        return super.equals(obj);
      }
      com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat other = (com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat) obj;

      boolean result = true;
      result = result && (getIsConnect()
          == other.getIsConnect());
      result = result && (getRequest()
          == other.getRequest());
      result = result && (getResponse()
          == other.getResponse());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = (37 * hash) + ISCONNECT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsConnect());
      hash = (37 * hash) + REQUEST_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getRequest());
      hash = (37 * hash) + RESPONSE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getResponse());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parseFrom(
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
    public static Builder newBuilder(com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat prototype) {
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
     * Protobuf type {@code phonedata.TtPhoneConnectBeat}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:phonedata.TtPhoneConnectBeat)
        com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeatOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.qzy.tt.data.TtPhoneConnectBeatProtos.internal_static_phonedata_TtPhoneConnectBeat_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.qzy.tt.data.TtPhoneConnectBeatProtos.internal_static_phonedata_TtPhoneConnectBeat_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat.class, com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat.Builder.class);
      }

      // Construct using com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat.newBuilder()
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
        isConnect_ = false;

        request_ = false;

        response_ = false;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.qzy.tt.data.TtPhoneConnectBeatProtos.internal_static_phonedata_TtPhoneConnectBeat_descriptor;
      }

      public com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat getDefaultInstanceForType() {
        return com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat.getDefaultInstance();
      }

      public com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat build() {
        com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat buildPartial() {
        com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat result = new com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat(this);
        result.isConnect_ = isConnect_;
        result.request_ = request_;
        result.response_ = response_;
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
        if (other instanceof com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat) {
          return mergeFrom((com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat other) {
        if (other == com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat.getDefaultInstance()) return this;
        if (other.getIsConnect() != false) {
          setIsConnect(other.getIsConnect());
        }
        if (other.getRequest() != false) {
          setRequest(other.getRequest());
        }
        if (other.getResponse() != false) {
          setResponse(other.getResponse());
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
        com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private boolean isConnect_ ;
      /**
       * <code>optional bool isConnect = 1;</code>
       */
      public boolean getIsConnect() {
        return isConnect_;
      }
      /**
       * <code>optional bool isConnect = 1;</code>
       */
      public Builder setIsConnect(boolean value) {
        
        isConnect_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool isConnect = 1;</code>
       */
      public Builder clearIsConnect() {
        
        isConnect_ = false;
        onChanged();
        return this;
      }

      private boolean request_ ;
      /**
       * <code>optional bool request = 2;</code>
       */
      public boolean getRequest() {
        return request_;
      }
      /**
       * <code>optional bool request = 2;</code>
       */
      public Builder setRequest(boolean value) {
        
        request_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool request = 2;</code>
       */
      public Builder clearRequest() {
        
        request_ = false;
        onChanged();
        return this;
      }

      private boolean response_ ;
      /**
       * <code>optional bool response = 3;</code>
       */
      public boolean getResponse() {
        return response_;
      }
      /**
       * <code>optional bool response = 3;</code>
       */
      public Builder setResponse(boolean value) {
        
        response_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool response = 3;</code>
       */
      public Builder clearResponse() {
        
        response_ = false;
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


      // @@protoc_insertion_point(builder_scope:phonedata.TtPhoneConnectBeat)
    }

    // @@protoc_insertion_point(class_scope:phonedata.TtPhoneConnectBeat)
    private static final com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat();
    }

    public static com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<TtPhoneConnectBeat>
        PARSER = new com.google.protobuf.AbstractParser<TtPhoneConnectBeat>() {
      public TtPhoneConnectBeat parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new TtPhoneConnectBeat(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<TtPhoneConnectBeat> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<TtPhoneConnectBeat> getParserForType() {
      return PARSER;
    }

    public com.qzy.tt.data.TtPhoneConnectBeatProtos.TtPhoneConnectBeat getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_phonedata_TtPhoneConnectBeat_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_phonedata_TtPhoneConnectBeat_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\027TtPhoneConnctBeat.proto\022\tphonedata\"J\n\022" +
      "TtPhoneConnectBeat\022\021\n\tisConnect\030\001 \001(\010\022\017\n" +
      "\007request\030\002 \001(\010\022\020\n\010response\030\003 \001(\010B+\n\017com." +
      "qzy.tt.dataB\030TtPhoneConnectBeatProtosb\006p" +
      "roto3"
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
    internal_static_phonedata_TtPhoneConnectBeat_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_phonedata_TtPhoneConnectBeat_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_phonedata_TtPhoneConnectBeat_descriptor,
        new java.lang.String[] { "IsConnect", "Request", "Response", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}