// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneAudioData.proto

package com.qzy.tt.data;

public final class TtPhoneAudioDataProtos {
  private TtPhoneAudioDataProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PhoneAudioDataOrBuilder extends
      // @@protoc_insertion_point(interface_extends:phonedata.PhoneAudioData)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional bytes audiodata = 1;</code>
     */
    com.google.protobuf.ByteString getAudiodata();
  }
  /**
   * Protobuf type {@code phonedata.PhoneAudioData}
   */
  public  static final class PhoneAudioData extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:phonedata.PhoneAudioData)
      PhoneAudioDataOrBuilder {
    // Use PhoneAudioData.newBuilder() to construct.
    private PhoneAudioData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PhoneAudioData() {
      audiodata_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private PhoneAudioData(
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

              audiodata_ = input.readBytes();
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
      return com.qzy.tt.data.TtPhoneAudioDataProtos.internal_static_phonedata_PhoneAudioData_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qzy.tt.data.TtPhoneAudioDataProtos.internal_static_phonedata_PhoneAudioData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData.class, com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData.Builder.class);
    }

    public static final int AUDIODATA_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString audiodata_;
    /**
     * <code>optional bytes audiodata = 1;</code>
     */
    public com.google.protobuf.ByteString getAudiodata() {
      return audiodata_;
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
      if (!audiodata_.isEmpty()) {
        output.writeBytes(1, audiodata_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!audiodata_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, audiodata_);
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
      if (!(obj instanceof com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData)) {
        return super.equals(obj);
      }
      com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData other = (com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData) obj;

      boolean result = true;
      result = result && getAudiodata()
          .equals(other.getAudiodata());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = (37 * hash) + AUDIODATA_FIELD_NUMBER;
      hash = (53 * hash) + getAudiodata().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parseFrom(
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
    public static Builder newBuilder(com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData prototype) {
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
     * Protobuf type {@code phonedata.PhoneAudioData}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:phonedata.PhoneAudioData)
        com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioDataOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.qzy.tt.data.TtPhoneAudioDataProtos.internal_static_phonedata_PhoneAudioData_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.qzy.tt.data.TtPhoneAudioDataProtos.internal_static_phonedata_PhoneAudioData_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData.class, com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData.Builder.class);
      }

      // Construct using com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData.newBuilder()
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
        audiodata_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.qzy.tt.data.TtPhoneAudioDataProtos.internal_static_phonedata_PhoneAudioData_descriptor;
      }

      public com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData getDefaultInstanceForType() {
        return com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData.getDefaultInstance();
      }

      public com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData build() {
        com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData buildPartial() {
        com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData result = new com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData(this);
        result.audiodata_ = audiodata_;
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
        if (other instanceof com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData) {
          return mergeFrom((com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData other) {
        if (other == com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData.getDefaultInstance()) return this;
        if (other.getAudiodata() != com.google.protobuf.ByteString.EMPTY) {
          setAudiodata(other.getAudiodata());
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
        com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private com.google.protobuf.ByteString audiodata_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes audiodata = 1;</code>
       */
      public com.google.protobuf.ByteString getAudiodata() {
        return audiodata_;
      }
      /**
       * <code>optional bytes audiodata = 1;</code>
       */
      public Builder setAudiodata(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        audiodata_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes audiodata = 1;</code>
       */
      public Builder clearAudiodata() {
        
        audiodata_ = getDefaultInstance().getAudiodata();
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


      // @@protoc_insertion_point(builder_scope:phonedata.PhoneAudioData)
    }

    // @@protoc_insertion_point(class_scope:phonedata.PhoneAudioData)
    private static final com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData();
    }

    public static com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PhoneAudioData>
        PARSER = new com.google.protobuf.AbstractParser<PhoneAudioData>() {
      public PhoneAudioData parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new PhoneAudioData(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PhoneAudioData> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PhoneAudioData> getParserForType() {
      return PARSER;
    }

    public com.qzy.tt.data.TtPhoneAudioDataProtos.PhoneAudioData getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_phonedata_PhoneAudioData_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_phonedata_PhoneAudioData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026TtPhoneAudioData.proto\022\tphonedata\"#\n\016P" +
      "honeAudioData\022\021\n\taudiodata\030\001 \001(\014B)\n\017com." +
      "qzy.tt.dataB\026TtPhoneAudioDataProtosb\006pro" +
      "to3"
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
    internal_static_phonedata_PhoneAudioData_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_phonedata_PhoneAudioData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_phonedata_PhoneAudioData_descriptor,
        new java.lang.String[] { "Audiodata", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
