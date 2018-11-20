// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneRecoverSystem.proto

#ifndef PROTOBUF_TtPhoneRecoverSystem_2eproto__INCLUDED
#define PROTOBUF_TtPhoneRecoverSystem_2eproto__INCLUDED

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
void protobuf_AddDesc_TtPhoneRecoverSystem_2eproto();
void protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto();
void protobuf_AssignDesc_TtPhoneRecoverSystem_2eproto();
void protobuf_ShutdownFile_TtPhoneRecoverSystem_2eproto();

class TtPhoneRecoverSystem;

// ===================================================================

class TtPhoneRecoverSystem : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.TtPhoneRecoverSystem) */ {
 public:
  TtPhoneRecoverSystem();
  virtual ~TtPhoneRecoverSystem();

  TtPhoneRecoverSystem(const TtPhoneRecoverSystem& from);

  inline TtPhoneRecoverSystem& operator=(const TtPhoneRecoverSystem& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const TtPhoneRecoverSystem& default_instance();

  static const TtPhoneRecoverSystem* internal_default_instance();

  void Swap(TtPhoneRecoverSystem* other);

  // implements Message ----------------------------------------------

  inline TtPhoneRecoverSystem* New() const { return New(NULL); }

  TtPhoneRecoverSystem* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const TtPhoneRecoverSystem& from);
  void MergeFrom(const TtPhoneRecoverSystem& from);
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
  void InternalSwap(TtPhoneRecoverSystem* other);
  void UnsafeMergeFrom(const TtPhoneRecoverSystem& from);
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

  // optional string ip = 1;
  void clear_ip();
  static const int kIpFieldNumber = 1;
  const ::std::string& ip() const;
  void set_ip(const ::std::string& value);
  void set_ip(const char* value);
  void set_ip(const char* value, size_t size);
  ::std::string* mutable_ip();
  ::std::string* release_ip();
  void set_allocated_ip(::std::string* ip);

  // optional bool isRecover = 2;
  void clear_isrecover();
  static const int kIsRecoverFieldNumber = 2;
  bool isrecover() const;
  void set_isrecover(bool value);

  // @@protoc_insertion_point(class_scope:phonedata.TtPhoneRecoverSystem)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::ArenaStringPtr ip_;
  bool isrecover_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto_impl();
  friend void  protobuf_AddDesc_TtPhoneRecoverSystem_2eproto_impl();
  friend void protobuf_AssignDesc_TtPhoneRecoverSystem_2eproto();
  friend void protobuf_ShutdownFile_TtPhoneRecoverSystem_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<TtPhoneRecoverSystem> TtPhoneRecoverSystem_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// TtPhoneRecoverSystem

// optional string ip = 1;
inline void TtPhoneRecoverSystem::clear_ip() {
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtPhoneRecoverSystem::ip() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneRecoverSystem.ip)
  return ip_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneRecoverSystem::set_ip(const ::std::string& value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneRecoverSystem.ip)
}
inline void TtPhoneRecoverSystem::set_ip(const char* value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtPhoneRecoverSystem.ip)
}
inline void TtPhoneRecoverSystem::set_ip(const char* value, size_t size) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtPhoneRecoverSystem.ip)
}
inline ::std::string* TtPhoneRecoverSystem::mutable_ip() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtPhoneRecoverSystem.ip)
  return ip_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtPhoneRecoverSystem::release_ip() {
  // @@protoc_insertion_point(field_release:phonedata.TtPhoneRecoverSystem.ip)
  
  return ip_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneRecoverSystem::set_allocated_ip(::std::string* ip) {
  if (ip != NULL) {
    
  } else {
    
  }
  ip_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ip);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtPhoneRecoverSystem.ip)
}

// optional bool isRecover = 2;
inline void TtPhoneRecoverSystem::clear_isrecover() {
  isrecover_ = false;
}
inline bool TtPhoneRecoverSystem::isrecover() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneRecoverSystem.isRecover)
  return isrecover_;
}
inline void TtPhoneRecoverSystem::set_isrecover(bool value) {
  
  isrecover_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneRecoverSystem.isRecover)
}

inline const TtPhoneRecoverSystem* TtPhoneRecoverSystem::internal_default_instance() {
  return &TtPhoneRecoverSystem_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_TtPhoneRecoverSystem_2eproto__INCLUDED
