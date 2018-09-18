// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ChangePcmPlayerDb.proto

package com.qzy.tt.data;

public final class ChangePcmPlayerDbProtos {
  private ChangePcmPlayerDbProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ChangePcmPlayerDbOrBuilder extends
      // @@protoc_insertion_point(interface_extends:phonedata.ChangePcmPlayerDb)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional int32 db = 1;</code>
     */
    int getDb();
  }
  /**
   * Protobuf type {@code phonedata.ChangePcmPlayerDb}
   */
  public  static final class ChangePcmPlayerDb extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:phonedata.ChangePcmPlayerDb)
      ChangePcmPlayerDbOrBuilder {
    // Use ChangePcmPlayerDb.newBuilder() to construct.
    private ChangePcmPlayerDb(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ChangePcmPlayerDb() {
      db_ = 0;
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private ChangePcmPlayerDb(
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

              db_ = input.readInt32();
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
      return com.qzy.tt.data.ChangePcmPlayerDbProtos.internal_static_phonedata_ChangePcmPlayerDb_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qzy.tt.data.ChangePcmPlayerDbProtos.internal_static_phonedata_ChangePcmPlayerDb_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb.class, com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb.Builder.class);
    }

    public static final int DB_FIELD_NUMBER = 1;
    private int db_;
    /**
     * <code>optional int32 db = 1;</code>
     */
    public int getDb() {
      return db_;
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
      if (db_ != 0) {
        output.writeInt32(1, db_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (db_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, db_);
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
      if (!(obj instanceof com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb)) {
        return super.equals(obj);
      }
      com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb other = (com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb) obj;

      boolean result = true;
      result = result && (getDb()
          == other.getDb());
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = (37 * hash) + DB_FIELD_NUMBER;
      hash = (53 * hash) + getDb();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parseFrom(
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
    public static Builder newBuilder(com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb prototype) {
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
     * Protobuf type {@code phonedata.ChangePcmPlayerDb}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:phonedata.ChangePcmPlayerDb)
        com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDbOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.qzy.tt.data.ChangePcmPlayerDbProtos.internal_static_phonedata_ChangePcmPlayerDb_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.qzy.tt.data.ChangePcmPlayerDbProtos.internal_static_phonedata_ChangePcmPlayerDb_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb.class, com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb.Builder.class);
      }

      // Construct using com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb.newBuilder()
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
        db_ = 0;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.qzy.tt.data.ChangePcmPlayerDbProtos.internal_static_phonedata_ChangePcmPlayerDb_descriptor;
      }

      public com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb getDefaultInstanceForType() {
        return com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb.getDefaultInstance();
      }

      public com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb build() {
        com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb buildPartial() {
        com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb result = new com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb(this);
        result.db_ = db_;
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
        if (other instanceof com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb) {
          return mergeFrom((com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb other) {
        if (other == com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb.getDefaultInstance()) return this;
        if (other.getDb() != 0) {
          setDb(other.getDb());
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
        com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int db_ ;
      /**
       * <code>optional int32 db = 1;</code>
       */
      public int getDb() {
        return db_;
      }
      /**
       * <code>optional int32 db = 1;</code>
       */
      public Builder setDb(int value) {

        db_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 db = 1;</code>
       */
      public Builder clearDb() {

        db_ = 0;
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


      // @@protoc_insertion_point(builder_scope:phonedata.ChangePcmPlayerDb)
    }

    // @@protoc_insertion_point(class_scope:phonedata.ChangePcmPlayerDb)
    private static final com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb();
    }

    public static com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ChangePcmPlayerDb>
        PARSER = new com.google.protobuf.AbstractParser<ChangePcmPlayerDb>() {
      public ChangePcmPlayerDb parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new ChangePcmPlayerDb(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ChangePcmPlayerDb> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<ChangePcmPlayerDb> getParserForType() {
      return PARSER;
    }

    public com.qzy.tt.data.ChangePcmPlayerDbProtos.ChangePcmPlayerDb getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_phonedata_ChangePcmPlayerDb_descriptor;
  private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_phonedata_ChangePcmPlayerDb_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\027ChangePcmPlayerDb.proto\022\tphonedata\"\037\n\021" +
      "ChangePcmPlayerDb\022\n\n\002db\030\001 \001(\005B*\n\017com.qzy" +
      ".tt.dataB\027ChangePcmPlayerDbProtosb\006proto" +
      "3"
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
    internal_static_phonedata_ChangePcmPlayerDb_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_phonedata_ChangePcmPlayerDb_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_phonedata_ChangePcmPlayerDb_descriptor,
        new String[] { "Db", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}