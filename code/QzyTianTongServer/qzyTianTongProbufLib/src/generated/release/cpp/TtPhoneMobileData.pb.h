// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneMobileData.proto

#ifndef PROTOBUF_TtPhoneMobileData_2eproto__INCLUDED
#define PROTOBUF_TtPhoneMobileData_2eproto__INCLUDED

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
void protobuf_AddDesc_TtPhoneMobileData_2eproto();
void protobuf_InitDefaults_TtPhoneMobileData_2eproto();
void protobuf_AssignDesc_TtPhoneMobileData_2eproto();
void protobuf_ShutdownFile_TtPhoneMobileData_2eproto();

class TtPhoneMobileData;

// ===================================================================

class TtPhoneMobileData : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.TtPhoneMobileData) */ {
 public:
  TtPhoneMobileData();
  virtual ~TtPhoneMobileData();

  TtPhoneMobileData(const TtPhoneMobileData& from);

  inline TtPhoneMobileData& operator=(const TtPhoneMobileData& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const TtPhoneMobileData& default_instance();

  static const TtPhoneMobileData* internal_default_instance();

  void Swap(TtPhoneMobileData* other);

  // implements Message ----------------------------------------------

  inline TtPhoneMobileData* New() const { return New(NULL); }

  TtPhoneMobileData* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const TtPhoneMobileData& from);
  void MergeFrom(const TtPhoneMobileData& from);
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
  void InternalSwap(TtPhoneMobileData* other);
  void UnsafeMergeFrom(const TtPhoneMobileData& from);
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

  // optional bool isEnableData = 1;
  void clear_isenabledata();
  static const int kIsEnableDataFieldNumber = 1;
  bool isenabledata() const;
  void set_isenabledata(bool value);

  // optional bool responseStatus = 2;
  void clear_responsestatus();
  static const int kResponseStatusFieldNumber = 2;
  bool responsestatus() const;
  void set_responsestatus(bool value);

  // @@protoc_insertion_point(class_scope:phonedata.TtPhoneMobileData)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool isenabledata_;
  bool responsestatus_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtPhoneMobileData_2eproto_impl();
  friend void  protobuf_AddDesc_TtPhoneMobileData_2eproto_impl();
  friend void protobuf_AssignDesc_TtPhoneMobileData_2eproto();
  friend void protobuf_ShutdownFile_TtPhoneMobileData_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<TtPhoneMobileData> TtPhoneMobileData_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// TtPhoneMobileData

// optional bool isEnableData = 1;
inline void TtPhoneMobileData::clear_isenabledata() {
  isenabledata_ = false;
}
inline bool TtPhoneMobileData::isenabledata() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneMobileData.isEnableData)
  return isenabledata_;
}
inline void TtPhoneMobileData::set_isenabledata(bool value) {
  
  isenabledata_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneMobileData.isEnableData)
}

// optional bool responseStatus = 2;
inline void TtPhoneMobileData::clear_responsestatus() {
  responsestatus_ = false;
}
inline bool TtPhoneMobileData::responsestatus() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneMobileData.responseStatus)
  return responsestatus_;
}
inline void TtPhoneMobileData::set_responsestatus(bool value) {
  
  responsestatus_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneMobileData.responseStatus)
}

inline const TtPhoneMobileData* TtPhoneMobileData::internal_default_instance() {
  return &TtPhoneMobileData_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_TtPhoneMobileData_2eproto__INCLUDED
