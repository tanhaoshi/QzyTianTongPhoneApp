// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneGetServerVersion.proto

#ifndef PROTOBUF_TtPhoneGetServerVersion_2eproto__INCLUDED
#define PROTOBUF_TtPhoneGetServerVersion_2eproto__INCLUDED

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
void protobuf_AddDesc_TtPhoneGetServerVersion_2eproto();
void protobuf_InitDefaults_TtPhoneGetServerVersion_2eproto();
void protobuf_AssignDesc_TtPhoneGetServerVersion_2eproto();
void protobuf_ShutdownFile_TtPhoneGetServerVersion_2eproto();

class TtPhoneGetServerVersion;

// ===================================================================

class TtPhoneGetServerVersion : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.TtPhoneGetServerVersion) */ {
 public:
  TtPhoneGetServerVersion();
  virtual ~TtPhoneGetServerVersion();

  TtPhoneGetServerVersion(const TtPhoneGetServerVersion& from);

  inline TtPhoneGetServerVersion& operator=(const TtPhoneGetServerVersion& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const TtPhoneGetServerVersion& default_instance();

  static const TtPhoneGetServerVersion* internal_default_instance();

  void Swap(TtPhoneGetServerVersion* other);

  // implements Message ----------------------------------------------

  inline TtPhoneGetServerVersion* New() const { return New(NULL); }

  TtPhoneGetServerVersion* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const TtPhoneGetServerVersion& from);
  void MergeFrom(const TtPhoneGetServerVersion& from);
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
  void InternalSwap(TtPhoneGetServerVersion* other);
  void UnsafeMergeFrom(const TtPhoneGetServerVersion& from);
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

  // optional bool isRequest = 2;
  void clear_isrequest();
  static const int kIsRequestFieldNumber = 2;
  bool isrequest() const;
  void set_isrequest(bool value);

  // optional string serverApkVersionName = 3;
  void clear_serverapkversionname();
  static const int kServerApkVersionNameFieldNumber = 3;
  const ::std::string& serverapkversionname() const;
  void set_serverapkversionname(const ::std::string& value);
  void set_serverapkversionname(const char* value);
  void set_serverapkversionname(const char* value, size_t size);
  ::std::string* mutable_serverapkversionname();
  ::std::string* release_serverapkversionname();
  void set_allocated_serverapkversionname(::std::string* serverapkversionname);

  // optional string serverSieralNo = 4;
  void clear_serversieralno();
  static const int kServerSieralNoFieldNumber = 4;
  const ::std::string& serversieralno() const;
  void set_serversieralno(const ::std::string& value);
  void set_serversieralno(const char* value);
  void set_serversieralno(const char* value, size_t size);
  ::std::string* mutable_serversieralno();
  ::std::string* release_serversieralno();
  void set_allocated_serversieralno(::std::string* serversieralno);

  // @@protoc_insertion_point(class_scope:phonedata.TtPhoneGetServerVersion)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::ArenaStringPtr ip_;
  ::google::protobuf::internal::ArenaStringPtr serverapkversionname_;
  ::google::protobuf::internal::ArenaStringPtr serversieralno_;
  bool isrequest_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtPhoneGetServerVersion_2eproto_impl();
  friend void  protobuf_AddDesc_TtPhoneGetServerVersion_2eproto_impl();
  friend void protobuf_AssignDesc_TtPhoneGetServerVersion_2eproto();
  friend void protobuf_ShutdownFile_TtPhoneGetServerVersion_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<TtPhoneGetServerVersion> TtPhoneGetServerVersion_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// TtPhoneGetServerVersion

// optional string ip = 1;
inline void TtPhoneGetServerVersion::clear_ip() {
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtPhoneGetServerVersion::ip() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneGetServerVersion.ip)
  return ip_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneGetServerVersion::set_ip(const ::std::string& value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneGetServerVersion.ip)
}
inline void TtPhoneGetServerVersion::set_ip(const char* value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtPhoneGetServerVersion.ip)
}
inline void TtPhoneGetServerVersion::set_ip(const char* value, size_t size) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtPhoneGetServerVersion.ip)
}
inline ::std::string* TtPhoneGetServerVersion::mutable_ip() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtPhoneGetServerVersion.ip)
  return ip_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtPhoneGetServerVersion::release_ip() {
  // @@protoc_insertion_point(field_release:phonedata.TtPhoneGetServerVersion.ip)
  
  return ip_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneGetServerVersion::set_allocated_ip(::std::string* ip) {
  if (ip != NULL) {
    
  } else {
    
  }
  ip_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ip);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtPhoneGetServerVersion.ip)
}

// optional bool isRequest = 2;
inline void TtPhoneGetServerVersion::clear_isrequest() {
  isrequest_ = false;
}
inline bool TtPhoneGetServerVersion::isrequest() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneGetServerVersion.isRequest)
  return isrequest_;
}
inline void TtPhoneGetServerVersion::set_isrequest(bool value) {
  
  isrequest_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneGetServerVersion.isRequest)
}

// optional string serverApkVersionName = 3;
inline void TtPhoneGetServerVersion::clear_serverapkversionname() {
  serverapkversionname_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtPhoneGetServerVersion::serverapkversionname() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneGetServerVersion.serverApkVersionName)
  return serverapkversionname_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneGetServerVersion::set_serverapkversionname(const ::std::string& value) {
  
  serverapkversionname_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneGetServerVersion.serverApkVersionName)
}
inline void TtPhoneGetServerVersion::set_serverapkversionname(const char* value) {
  
  serverapkversionname_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtPhoneGetServerVersion.serverApkVersionName)
}
inline void TtPhoneGetServerVersion::set_serverapkversionname(const char* value, size_t size) {
  
  serverapkversionname_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtPhoneGetServerVersion.serverApkVersionName)
}
inline ::std::string* TtPhoneGetServerVersion::mutable_serverapkversionname() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtPhoneGetServerVersion.serverApkVersionName)
  return serverapkversionname_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtPhoneGetServerVersion::release_serverapkversionname() {
  // @@protoc_insertion_point(field_release:phonedata.TtPhoneGetServerVersion.serverApkVersionName)
  
  return serverapkversionname_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneGetServerVersion::set_allocated_serverapkversionname(::std::string* serverapkversionname) {
  if (serverapkversionname != NULL) {
    
  } else {
    
  }
  serverapkversionname_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), serverapkversionname);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtPhoneGetServerVersion.serverApkVersionName)
}

// optional string serverSieralNo = 4;
inline void TtPhoneGetServerVersion::clear_serversieralno() {
  serversieralno_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtPhoneGetServerVersion::serversieralno() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneGetServerVersion.serverSieralNo)
  return serversieralno_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneGetServerVersion::set_serversieralno(const ::std::string& value) {
  
  serversieralno_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneGetServerVersion.serverSieralNo)
}
inline void TtPhoneGetServerVersion::set_serversieralno(const char* value) {
  
  serversieralno_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtPhoneGetServerVersion.serverSieralNo)
}
inline void TtPhoneGetServerVersion::set_serversieralno(const char* value, size_t size) {
  
  serversieralno_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtPhoneGetServerVersion.serverSieralNo)
}
inline ::std::string* TtPhoneGetServerVersion::mutable_serversieralno() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtPhoneGetServerVersion.serverSieralNo)
  return serversieralno_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtPhoneGetServerVersion::release_serversieralno() {
  // @@protoc_insertion_point(field_release:phonedata.TtPhoneGetServerVersion.serverSieralNo)
  
  return serversieralno_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneGetServerVersion::set_allocated_serversieralno(::std::string* serversieralno) {
  if (serversieralno != NULL) {
    
  } else {
    
  }
  serversieralno_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), serversieralno);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtPhoneGetServerVersion.serverSieralNo)
}

inline const TtPhoneGetServerVersion* TtPhoneGetServerVersion::internal_default_instance() {
  return &TtPhoneGetServerVersion_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_TtPhoneGetServerVersion_2eproto__INCLUDED
