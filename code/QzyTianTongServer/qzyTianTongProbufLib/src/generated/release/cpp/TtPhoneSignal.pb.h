// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneSignal.proto

#ifndef PROTOBUF_TtPhoneSignal_2eproto__INCLUDED
#define PROTOBUF_TtPhoneSignal_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3001000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3001000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

namespace phonedata {

// Internal implementation detail -- do not call these.
void protobuf_AddDesc_TtPhoneSignal_2eproto();
void protobuf_InitDefaults_TtPhoneSignal_2eproto();
void protobuf_AssignDesc_TtPhoneSignal_2eproto();
void protobuf_ShutdownFile_TtPhoneSignal_2eproto();

class PhoneSignalStrength;

// ===================================================================

class PhoneSignalStrength : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.PhoneSignalStrength) */ {
 public:
  PhoneSignalStrength();
  virtual ~PhoneSignalStrength();

  PhoneSignalStrength(const PhoneSignalStrength& from);

  inline PhoneSignalStrength& operator=(const PhoneSignalStrength& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const PhoneSignalStrength& default_instance();

  static const PhoneSignalStrength* internal_default_instance();

  void Swap(PhoneSignalStrength* other);

  // implements Message ----------------------------------------------

  inline PhoneSignalStrength* New() const { return New(NULL); }

  PhoneSignalStrength* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const PhoneSignalStrength& from);
  void MergeFrom(const PhoneSignalStrength& from);
  void Clear();
  bool IsInitialized() const;

  size_t ByteSizeLong() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const {
    return InternalSerializeWithCachedSizesToArray(false, output);
  }
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(PhoneSignalStrength* other);
  void UnsafeMergeFrom(const PhoneSignalStrength& from);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // optional int32 signalStrength = 1;
  void clear_signalstrength();
  static const int kSignalStrengthFieldNumber = 1;
  ::google::protobuf::int32 signalstrength() const;
  void set_signalstrength(::google::protobuf::int32 value);

  // optional int32 signalDbm = 2;
  void clear_signaldbm();
  static const int kSignalDbmFieldNumber = 2;
  ::google::protobuf::int32 signaldbm() const;
  void set_signaldbm(::google::protobuf::int32 value);

  // @@protoc_insertion_point(class_scope:phonedata.PhoneSignalStrength)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::int32 signalstrength_;
  ::google::protobuf::int32 signaldbm_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtPhoneSignal_2eproto_impl();
  friend void  protobuf_AddDesc_TtPhoneSignal_2eproto_impl();
  friend void protobuf_AssignDesc_TtPhoneSignal_2eproto();
  friend void protobuf_ShutdownFile_TtPhoneSignal_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<PhoneSignalStrength> PhoneSignalStrength_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// PhoneSignalStrength

// optional int32 signalStrength = 1;
inline void PhoneSignalStrength::clear_signalstrength() {
  signalstrength_ = 0;
}
inline ::google::protobuf::int32 PhoneSignalStrength::signalstrength() const {
  // @@protoc_insertion_point(field_get:phonedata.PhoneSignalStrength.signalStrength)
  return signalstrength_;
}
inline void PhoneSignalStrength::set_signalstrength(::google::protobuf::int32 value) {
  
  signalstrength_ = value;
  // @@protoc_insertion_point(field_set:phonedata.PhoneSignalStrength.signalStrength)
}

// optional int32 signalDbm = 2;
inline void PhoneSignalStrength::clear_signaldbm() {
  signaldbm_ = 0;
}
inline ::google::protobuf::int32 PhoneSignalStrength::signaldbm() const {
  // @@protoc_insertion_point(field_get:phonedata.PhoneSignalStrength.signalDbm)
  return signaldbm_;
}
inline void PhoneSignalStrength::set_signaldbm(::google::protobuf::int32 value) {
  
  signaldbm_ = value;
  // @@protoc_insertion_point(field_set:phonedata.PhoneSignalStrength.signalDbm)
}

inline const PhoneSignalStrength* PhoneSignalStrength::internal_default_instance() {
  return &PhoneSignalStrength_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_TtPhoneSignal_2eproto__INCLUDED
