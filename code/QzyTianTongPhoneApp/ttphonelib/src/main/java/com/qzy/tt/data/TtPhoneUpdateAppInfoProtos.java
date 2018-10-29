// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneUpdateAppInfo.proto

package com.qzy.tt.data;

public final class TtPhoneUpdateAppInfoProtos {
  private TtPhoneUpdateAppInfoProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface UpdateAppInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:phonedata.UpdateAppInfo)
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
     * <pre>
     *手机app版本号
     * </pre>
     *
     * <code>optional string phoneAppVersion = 2;</code>
     */
    String getPhoneAppVersion();
    /**
     * <pre>
     *手机app版本号
     * </pre>
     *
     * <code>optional string phoneAppVersion = 2;</code>
     */
    com.google.protobuf.ByteString
        getPhoneAppVersionBytes();

    /**
     * <pre>
     *服务端app版本号
     * </pre>
     *
     * <code>optional string serverAppVersion = 3;</code>
     */
    String getServerAppVersion();
    /**
     * <pre>
     *服务端app版本号
     * </pre>
     *
     * <code>optional string serverAppVersion = 3;</code>
     */
    com.google.protobuf.ByteString
        getServerAppVersionBytes();

    /**
     * <pre>
     *升级文件的md5
     * </pre>
     *
     * <code>optional string tiantong_update_md = 4;</code>
     */
    String getTiantongUpdateMd();
    /**
     * <pre>
     *升级文件的md5
     * </pre>
     *
     * <code>optional string tiantong_update_md = 4;</code>
     */
    com.google.protobuf.ByteString
        getTiantongUpdateMdBytes();
  }
  /**
   * Protobuf type {@code phonedata.UpdateAppInfo}
   */
  public  static final class UpdateAppInfo extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:phonedata.UpdateAppInfo)
      UpdateAppInfoOrBuilder {
    // Use UpdateAppInfo.newBuilder() to construct.
    private UpdateAppInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private UpdateAppInfo() {
      ip_ = "";
      phoneAppVersion_ = "";
      serverAppVersion_ = "";
      tiantongUpdateMd_ = "";
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private UpdateAppInfo(
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
            case 18: {
              String s = input.readStringRequireUtf8();

              phoneAppVersion_ = s;
              break;
            }
            case 26: {
              String s = input.readStringRequireUtf8();

              serverAppVersion_ = s;
              break;
            }
            case 34: {
              String s = input.readStringRequireUtf8();

              tiantongUpdateMd_ = s;
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
      return com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.internal_static_phonedata_UpdateAppInfo_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.internal_static_phonedata_UpdateAppInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo.class, com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo.Builder.class);
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

    public static final int PHONEAPPVERSION_FIELD_NUMBER = 2;
    private volatile Object phoneAppVersion_;
    /**
     * <pre>
     *手机app版本号
     * </pre>
     *
     * <code>optional string phoneAppVersion = 2;</code>
     */
    public String getPhoneAppVersion() {
      Object ref = phoneAppVersion_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        phoneAppVersion_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *手机app版本号
     * </pre>
     *
     * <code>optional string phoneAppVersion = 2;</code>
     */
    public com.google.protobuf.ByteString
        getPhoneAppVersionBytes() {
      Object ref = phoneAppVersion_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        phoneAppVersion_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SERVERAPPVERSION_FIELD_NUMBER = 3;
    private volatile Object serverAppVersion_;
    /**
     * <pre>
     *服务端app版本号
     * </pre>
     *
     * <code>optional string serverAppVersion = 3;</code>
     */
    public String getServerAppVersion() {
      Object ref = serverAppVersion_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        serverAppVersion_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *服务端app版本号
     * </pre>
     *
     * <code>optional string serverAppVersion = 3;</code>
     */
    public com.google.protobuf.ByteString
        getServerAppVersionBytes() {
      Object ref = serverAppVersion_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        serverAppVersion_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TIANTONG_UPDATE_MD_FIELD_NUMBER = 4;
    private volatile Object tiantongUpdateMd_;
    /**
     * <pre>
     *升级文件的md5
     * </pre>
     *
     * <code>optional string tiantong_update_md = 4;</code>
     */
    public String getTiantongUpdateMd() {
      Object ref = tiantongUpdateMd_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        tiantongUpdateMd_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *升级文件的md5
     * </pre>
     *
     * <code>optional string tiantong_update_md = 4;</code>
     */
    public com.google.protobuf.ByteString
        getTiantongUpdateMdBytes() {
      Object ref = tiantongUpdateMd_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        tiantongUpdateMd_ = b;
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
      if (!getPhoneAppVersionBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, phoneAppVersion_);
      }
      if (!getServerAppVersionBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, serverAppVersion_);
      }
      if (!getTiantongUpdateMdBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, tiantongUpdateMd_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getIpBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, ip_);
      }
      if (!getPhoneAppVersionBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, phoneAppVersion_);
      }
      if (!getServerAppVersionBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, serverAppVersion_);
      }
      if (!getTiantongUpdateMdBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, tiantongUpdateMd_);
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
      if (!(obj instanceof com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo)) {
        return super.equals(obj);
      }
      com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo other = (com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo) obj;

      boolean result = true;
      result = result && getIp()
          .equals(other.getIp());
      result = result && getPhoneAppVersion()
          .equals(other.getPhoneAppVersion());
      result = result && getServerAppVersion()
          .equals(other.getServerAppVersion());
      result = result && getTiantongUpdateMd()
          .equals(other.getTiantongUpdateMd());
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
      hash = (37 * hash) + PHONEAPPVERSION_FIELD_NUMBER;
      hash = (53 * hash) + getPhoneAppVersion().hashCode();
      hash = (37 * hash) + SERVERAPPVERSION_FIELD_NUMBER;
      hash = (53 * hash) + getServerAppVersion().hashCode();
      hash = (37 * hash) + TIANTONG_UPDATE_MD_FIELD_NUMBER;
      hash = (53 * hash) + getTiantongUpdateMd().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parseFrom(
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
    public static Builder newBuilder(com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo prototype) {
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
     * Protobuf type {@code phonedata.UpdateAppInfo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:phonedata.UpdateAppInfo)
        com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.internal_static_phonedata_UpdateAppInfo_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.internal_static_phonedata_UpdateAppInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo.class, com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo.Builder.class);
      }

      // Construct using com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo.newBuilder()
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

        phoneAppVersion_ = "";

        serverAppVersion_ = "";

        tiantongUpdateMd_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.internal_static_phonedata_UpdateAppInfo_descriptor;
      }

      public com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo getDefaultInstanceForType() {
        return com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo.getDefaultInstance();
      }

      public com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo build() {
        com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo buildPartial() {
        com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo result = new com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo(this);
        result.ip_ = ip_;
        result.phoneAppVersion_ = phoneAppVersion_;
        result.serverAppVersion_ = serverAppVersion_;
        result.tiantongUpdateMd_ = tiantongUpdateMd_;
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
        if (other instanceof com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo) {
          return mergeFrom((com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo other) {
        if (other == com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo.getDefaultInstance()) return this;
        if (!other.getIp().isEmpty()) {
          ip_ = other.ip_;
          onChanged();
        }
        if (!other.getPhoneAppVersion().isEmpty()) {
          phoneAppVersion_ = other.phoneAppVersion_;
          onChanged();
        }
        if (!other.getServerAppVersion().isEmpty()) {
          serverAppVersion_ = other.serverAppVersion_;
          onChanged();
        }
        if (!other.getTiantongUpdateMd().isEmpty()) {
          tiantongUpdateMd_ = other.tiantongUpdateMd_;
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
        com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo) e.getUnfinishedMessage();
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

      private Object phoneAppVersion_ = "";
      /**
       * <pre>
       *手机app版本号
       * </pre>
       *
       * <code>optional string phoneAppVersion = 2;</code>
       */
      public String getPhoneAppVersion() {
        Object ref = phoneAppVersion_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          phoneAppVersion_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *手机app版本号
       * </pre>
       *
       * <code>optional string phoneAppVersion = 2;</code>
       */
      public com.google.protobuf.ByteString
          getPhoneAppVersionBytes() {
        Object ref = phoneAppVersion_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          phoneAppVersion_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *手机app版本号
       * </pre>
       *
       * <code>optional string phoneAppVersion = 2;</code>
       */
      public Builder setPhoneAppVersion(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }

        phoneAppVersion_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *手机app版本号
       * </pre>
       *
       * <code>optional string phoneAppVersion = 2;</code>
       */
      public Builder clearPhoneAppVersion() {

        phoneAppVersion_ = getDefaultInstance().getPhoneAppVersion();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *手机app版本号
       * </pre>
       *
       * <code>optional string phoneAppVersion = 2;</code>
       */
      public Builder setPhoneAppVersionBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

        phoneAppVersion_ = value;
        onChanged();
        return this;
      }

      private Object serverAppVersion_ = "";
      /**
       * <pre>
       *服务端app版本号
       * </pre>
       *
       * <code>optional string serverAppVersion = 3;</code>
       */
      public String getServerAppVersion() {
        Object ref = serverAppVersion_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          serverAppVersion_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *服务端app版本号
       * </pre>
       *
       * <code>optional string serverAppVersion = 3;</code>
       */
      public com.google.protobuf.ByteString
          getServerAppVersionBytes() {
        Object ref = serverAppVersion_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          serverAppVersion_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *服务端app版本号
       * </pre>
       *
       * <code>optional string serverAppVersion = 3;</code>
       */
      public Builder setServerAppVersion(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }

        serverAppVersion_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *服务端app版本号
       * </pre>
       *
       * <code>optional string serverAppVersion = 3;</code>
       */
      public Builder clearServerAppVersion() {

        serverAppVersion_ = getDefaultInstance().getServerAppVersion();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *服务端app版本号
       * </pre>
       *
       * <code>optional string serverAppVersion = 3;</code>
       */
      public Builder setServerAppVersionBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

        serverAppVersion_ = value;
        onChanged();
        return this;
      }

      private Object tiantongUpdateMd_ = "";
      /**
       * <pre>
       *升级文件的md5
       * </pre>
       *
       * <code>optional string tiantong_update_md = 4;</code>
       */
      public String getTiantongUpdateMd() {
        Object ref = tiantongUpdateMd_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          tiantongUpdateMd_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *升级文件的md5
       * </pre>
       *
       * <code>optional string tiantong_update_md = 4;</code>
       */
      public com.google.protobuf.ByteString
          getTiantongUpdateMdBytes() {
        Object ref = tiantongUpdateMd_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          tiantongUpdateMd_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *升级文件的md5
       * </pre>
       *
       * <code>optional string tiantong_update_md = 4;</code>
       */
      public Builder setTiantongUpdateMd(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }

        tiantongUpdateMd_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *升级文件的md5
       * </pre>
       *
       * <code>optional string tiantong_update_md = 4;</code>
       */
      public Builder clearTiantongUpdateMd() {

        tiantongUpdateMd_ = getDefaultInstance().getTiantongUpdateMd();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *升级文件的md5
       * </pre>
       *
       * <code>optional string tiantong_update_md = 4;</code>
       */
      public Builder setTiantongUpdateMdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

        tiantongUpdateMd_ = value;
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


      // @@protoc_insertion_point(builder_scope:phonedata.UpdateAppInfo)
    }

    // @@protoc_insertion_point(class_scope:phonedata.UpdateAppInfo)
    private static final com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo();
    }

    public static com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<UpdateAppInfo>
        PARSER = new com.google.protobuf.AbstractParser<UpdateAppInfo>() {
      public UpdateAppInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new UpdateAppInfo(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<UpdateAppInfo> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<UpdateAppInfo> getParserForType() {
      return PARSER;
    }

    public com.qzy.tt.data.TtPhoneUpdateAppInfoProtos.UpdateAppInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_phonedata_UpdateAppInfo_descriptor;
  private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_phonedata_UpdateAppInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\032TtPhoneUpdateAppInfo.proto\022\tphonedata\"" +
      "j\n\rUpdateAppInfo\022\n\n\002ip\030\001 \001(\t\022\027\n\017phoneApp" +
      "Version\030\002 \001(\t\022\030\n\020serverAppVersion\030\003 \001(\t\022" +
      "\032\n\022tiantong_update_md\030\004 \001(\tB-\n\017com.qzy.t" +
      "t.dataB\032TtPhoneUpdateAppInfoProtosb\006prot" +
      "o3"
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
    internal_static_phonedata_UpdateAppInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_phonedata_UpdateAppInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_phonedata_UpdateAppInfo_descriptor,
        new String[] { "Ip", "PhoneAppVersion", "ServerAppVersion", "TiantongUpdateMd", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}