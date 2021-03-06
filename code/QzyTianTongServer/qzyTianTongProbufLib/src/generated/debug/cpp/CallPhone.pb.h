// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: CallPhone.proto

#ifndef PROTOBUF_CallPhone_2eproto__INCLUDED
#define PROTOBUF_CallPhone_2eproto__INCLUDED

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
#include <google/protobuf/generated_enum_reflection.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

namespace phonedata {

// Internal implementation detail -- do not call these.
void protobuf_AddDesc_CallPhone_2eproto();
void protobuf_InitDefaults_CallPhone_2eproto();
void protobuf_AssignDesc_CallPhone_2eproto();
void protobuf_ShutdownFile_CallPhone_2eproto();

class CallPhone;

enum CallPhone_PhoneCommand {
  CallPhone_PhoneCommand_NONE = 0,
  CallPhone_PhoneCommand_CALL = 1,
  CallPhone_PhoneCommand_HUANGUP = 2,
  CallPhone_PhoneCommand_ACCEPTCALL = 3,
  CallPhone_PhoneCommand_CallPhone_PhoneCommand_INT_MIN_SENTINEL_DO_NOT_USE_ = ::google::protobuf::kint32min,
  CallPhone_PhoneCommand_CallPhone_PhoneCommand_INT_MAX_SENTINEL_DO_NOT_USE_ = ::google::protobuf::kint32max
};
bool CallPhone_PhoneCommand_IsValid(int value);
const CallPhone_PhoneCommand CallPhone_PhoneCommand_PhoneCommand_MIN = CallPhone_PhoneCommand_NONE;
const CallPhone_PhoneCommand CallPhone_PhoneCommand_PhoneCommand_MAX = CallPhone_PhoneCommand_ACCEPTCALL;
const int CallPhone_PhoneCommand_PhoneCommand_ARRAYSIZE = CallPhone_PhoneCommand_PhoneCommand_MAX + 1;

const ::google::protobuf::EnumDescriptor* CallPhone_PhoneCommand_descriptor();
inline const ::std::string& CallPhone_PhoneCommand_Name(CallPhone_PhoneCommand value) {
  return ::google::protobuf::internal::NameOfEnum(
    CallPhone_PhoneCommand_descriptor(), value);
}
inline bool CallPhone_PhoneCommand_Parse(
    const ::std::string& name, CallPhone_PhoneCommand* value) {
  return ::google::protobuf::internal::ParseNamedEnum<CallPhone_PhoneCommand>(
    CallPhone_PhoneCommand_descriptor(), name, value);
}
// ===================================================================

class CallPhone : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.CallPhone) */ {
 public:
  CallPhone();
  virtual ~CallPhone();

  CallPhone(const CallPhone& from);

  inline CallPhone& operator=(const CallPhone& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const CallPhone& default_instance();

  static const CallPhone* internal_default_instance();

  void Swap(CallPhone* other);

  // implements Message ----------------------------------------------

  inline CallPhone* New() const { return New(NULL); }

  CallPhone* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const CallPhone& from);
  void MergeFrom(const CallPhone& from);
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
  void InternalSwap(CallPhone* other);
  void UnsafeMergeFrom(const CallPhone& from);
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

  typedef CallPhone_PhoneCommand PhoneCommand;
  static const PhoneCommand NONE =
    CallPhone_PhoneCommand_NONE;
  static const PhoneCommand CALL =
    CallPhone_PhoneCommand_CALL;
  static const PhoneCommand HUANGUP =
    CallPhone_PhoneCommand_HUANGUP;
  static const PhoneCommand ACCEPTCALL =
    CallPhone_PhoneCommand_ACCEPTCALL;
  static inline bool PhoneCommand_IsValid(int value) {
    return CallPhone_PhoneCommand_IsValid(value);
  }
  static const PhoneCommand PhoneCommand_MIN =
    CallPhone_PhoneCommand_PhoneCommand_MIN;
  static const PhoneCommand PhoneCommand_MAX =
    CallPhone_PhoneCommand_PhoneCommand_MAX;
  static const int PhoneCommand_ARRAYSIZE =
    CallPhone_PhoneCommand_PhoneCommand_ARRAYSIZE;
  static inline const ::google::protobuf::EnumDescriptor*
  PhoneCommand_descriptor() {
    return CallPhone_PhoneCommand_descriptor();
  }
  static inline const ::std::string& PhoneCommand_Name(PhoneCommand value) {
    return CallPhone_PhoneCommand_Name(value);
  }
  static inline bool PhoneCommand_Parse(const ::std::string& name,
      PhoneCommand* value) {
    return CallPhone_PhoneCommand_Parse(name, value);
  }

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

  // optional string phoneNumber = 2;
  void clear_phonenumber();
  static const int kPhoneNumberFieldNumber = 2;
  const ::std::string& phonenumber() const;
  void set_phonenumber(const ::std::string& value);
  void set_phonenumber(const char* value);
  void set_phonenumber(const char* value, size_t size);
  ::std::string* mutable_phonenumber();
  ::std::string* release_phonenumber();
  void set_allocated_phonenumber(::std::string* phonenumber);

  // optional .phonedata.CallPhone.PhoneCommand phonecommand = 3;
  void clear_phonecommand();
  static const int kPhonecommandFieldNumber = 3;
  ::phonedata::CallPhone_PhoneCommand phonecommand() const;
  void set_phonecommand(::phonedata::CallPhone_PhoneCommand value);

  // @@protoc_insertion_point(class_scope:phonedata.CallPhone)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::ArenaStringPtr ip_;
  ::google::protobuf::internal::ArenaStringPtr phonenumber_;
  int phonecommand_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_CallPhone_2eproto_impl();
  friend void  protobuf_AddDesc_CallPhone_2eproto_impl();
  friend void protobuf_AssignDesc_CallPhone_2eproto();
  friend void protobuf_ShutdownFile_CallPhone_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<CallPhone> CallPhone_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// CallPhone

// optional string ip = 1;
inline void CallPhone::clear_ip() {
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& CallPhone::ip() const {
  // @@protoc_insertion_point(field_get:phonedata.CallPhone.ip)
  return ip_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void CallPhone::set_ip(const ::std::string& value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.CallPhone.ip)
}
inline void CallPhone::set_ip(const char* value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.CallPhone.ip)
}
inline void CallPhone::set_ip(const char* value, size_t size) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.CallPhone.ip)
}
inline ::std::string* CallPhone::mutable_ip() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.CallPhone.ip)
  return ip_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* CallPhone::release_ip() {
  // @@protoc_insertion_point(field_release:phonedata.CallPhone.ip)
  
  return ip_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void CallPhone::set_allocated_ip(::std::string* ip) {
  if (ip != NULL) {
    
  } else {
    
  }
  ip_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ip);
  // @@protoc_insertion_point(field_set_allocated:phonedata.CallPhone.ip)
}

// optional string phoneNumber = 2;
inline void CallPhone::clear_phonenumber() {
  phonenumber_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& CallPhone::phonenumber() const {
  // @@protoc_insertion_point(field_get:phonedata.CallPhone.phoneNumber)
  return phonenumber_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void CallPhone::set_phonenumber(const ::std::string& value) {
  
  phonenumber_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.CallPhone.phoneNumber)
}
inline void CallPhone::set_phonenumber(const char* value) {
  
  phonenumber_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.CallPhone.phoneNumber)
}
inline void CallPhone::set_phonenumber(const char* value, size_t size) {
  
  phonenumber_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.CallPhone.phoneNumber)
}
inline ::std::string* CallPhone::mutable_phonenumber() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.CallPhone.phoneNumber)
  return phonenumber_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* CallPhone::release_phonenumber() {
  // @@protoc_insertion_point(field_release:phonedata.CallPhone.phoneNumber)
  
  return phonenumber_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void CallPhone::set_allocated_phonenumber(::std::string* phonenumber) {
  if (phonenumber != NULL) {
    
  } else {
    
  }
  phonenumber_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), phonenumber);
  // @@protoc_insertion_point(field_set_allocated:phonedata.CallPhone.phoneNumber)
}

// optional .phonedata.CallPhone.PhoneCommand phonecommand = 3;
inline void CallPhone::clear_phonecommand() {
  phonecommand_ = 0;
}
inline ::phonedata::CallPhone_PhoneCommand CallPhone::phonecommand() const {
  // @@protoc_insertion_point(field_get:phonedata.CallPhone.phonecommand)
  return static_cast< ::phonedata::CallPhone_PhoneCommand >(phonecommand_);
}
inline void CallPhone::set_phonecommand(::phonedata::CallPhone_PhoneCommand value) {
  
  phonecommand_ = value;
  // @@protoc_insertion_point(field_set:phonedata.CallPhone.phonecommand)
}

inline const CallPhone* CallPhone::internal_default_instance() {
  return &CallPhone_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

#ifndef SWIG
namespace google {
namespace protobuf {

template <> struct is_proto_enum< ::phonedata::CallPhone_PhoneCommand> : ::google::protobuf::internal::true_type {};
template <>
inline const EnumDescriptor* GetEnumDescriptor< ::phonedata::CallPhone_PhoneCommand>() {
  return ::phonedata::CallPhone_PhoneCommand_descriptor();
}

}  // namespace protobuf
}  // namespace google
#endif  // SWIG

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_CallPhone_2eproto__INCLUDED
