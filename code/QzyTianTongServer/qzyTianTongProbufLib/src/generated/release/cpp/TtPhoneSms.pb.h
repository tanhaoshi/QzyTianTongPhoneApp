// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneSms.proto

#ifndef PROTOBUF_TtPhoneSms_2eproto__INCLUDED
#define PROTOBUF_TtPhoneSms_2eproto__INCLUDED

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
void protobuf_AddDesc_TtPhoneSms_2eproto();
void protobuf_InitDefaults_TtPhoneSms_2eproto();
void protobuf_AssignDesc_TtPhoneSms_2eproto();
void protobuf_ShutdownFile_TtPhoneSms_2eproto();

class TtPhoneSms;

// ===================================================================

class TtPhoneSms : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.TtPhoneSms) */ {
 public:
  TtPhoneSms();
  virtual ~TtPhoneSms();

  TtPhoneSms(const TtPhoneSms& from);

  inline TtPhoneSms& operator=(const TtPhoneSms& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const TtPhoneSms& default_instance();

  static const TtPhoneSms* internal_default_instance();

  void Swap(TtPhoneSms* other);

  // implements Message ----------------------------------------------

  inline TtPhoneSms* New() const { return New(NULL); }

  TtPhoneSms* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const TtPhoneSms& from);
  void MergeFrom(const TtPhoneSms& from);
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
  void InternalSwap(TtPhoneSms* other);
  void UnsafeMergeFrom(const TtPhoneSms& from);
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

  // optional bool isSend = 3;
  void clear_issend();
  static const int kIsSendFieldNumber = 3;
  bool issend() const;
  void set_issend(bool value);

  // optional bool isSendSuccess = 4;
  void clear_issendsuccess();
  static const int kIsSendSuccessFieldNumber = 4;
  bool issendsuccess() const;
  void set_issendsuccess(bool value);

  // optional bool isReceiverSuccess = 5;
  void clear_isreceiversuccess();
  static const int kIsReceiverSuccessFieldNumber = 5;
  bool isreceiversuccess() const;
  void set_isreceiversuccess(bool value);

  // optional string messageText = 6;
  void clear_messagetext();
  static const int kMessageTextFieldNumber = 6;
  const ::std::string& messagetext() const;
  void set_messagetext(const ::std::string& value);
  void set_messagetext(const char* value);
  void set_messagetext(const char* value, size_t size);
  ::std::string* mutable_messagetext();
  ::std::string* release_messagetext();
  void set_allocated_messagetext(::std::string* messagetext);

  // @@protoc_insertion_point(class_scope:phonedata.TtPhoneSms)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::ArenaStringPtr ip_;
  ::google::protobuf::internal::ArenaStringPtr phonenumber_;
  ::google::protobuf::internal::ArenaStringPtr messagetext_;
  bool issend_;
  bool issendsuccess_;
  bool isreceiversuccess_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtPhoneSms_2eproto_impl();
  friend void  protobuf_AddDesc_TtPhoneSms_2eproto_impl();
  friend void protobuf_AssignDesc_TtPhoneSms_2eproto();
  friend void protobuf_ShutdownFile_TtPhoneSms_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<TtPhoneSms> TtPhoneSms_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// TtPhoneSms

// optional string ip = 1;
inline void TtPhoneSms::clear_ip() {
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtPhoneSms::ip() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneSms.ip)
  return ip_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneSms::set_ip(const ::std::string& value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneSms.ip)
}
inline void TtPhoneSms::set_ip(const char* value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtPhoneSms.ip)
}
inline void TtPhoneSms::set_ip(const char* value, size_t size) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtPhoneSms.ip)
}
inline ::std::string* TtPhoneSms::mutable_ip() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtPhoneSms.ip)
  return ip_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtPhoneSms::release_ip() {
  // @@protoc_insertion_point(field_release:phonedata.TtPhoneSms.ip)
  
  return ip_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneSms::set_allocated_ip(::std::string* ip) {
  if (ip != NULL) {
    
  } else {
    
  }
  ip_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ip);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtPhoneSms.ip)
}

// optional string phoneNumber = 2;
inline void TtPhoneSms::clear_phonenumber() {
  phonenumber_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtPhoneSms::phonenumber() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneSms.phoneNumber)
  return phonenumber_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneSms::set_phonenumber(const ::std::string& value) {
  
  phonenumber_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneSms.phoneNumber)
}
inline void TtPhoneSms::set_phonenumber(const char* value) {
  
  phonenumber_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtPhoneSms.phoneNumber)
}
inline void TtPhoneSms::set_phonenumber(const char* value, size_t size) {
  
  phonenumber_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtPhoneSms.phoneNumber)
}
inline ::std::string* TtPhoneSms::mutable_phonenumber() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtPhoneSms.phoneNumber)
  return phonenumber_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtPhoneSms::release_phonenumber() {
  // @@protoc_insertion_point(field_release:phonedata.TtPhoneSms.phoneNumber)
  
  return phonenumber_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneSms::set_allocated_phonenumber(::std::string* phonenumber) {
  if (phonenumber != NULL) {
    
  } else {
    
  }
  phonenumber_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), phonenumber);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtPhoneSms.phoneNumber)
}

// optional bool isSend = 3;
inline void TtPhoneSms::clear_issend() {
  issend_ = false;
}
inline bool TtPhoneSms::issend() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneSms.isSend)
  return issend_;
}
inline void TtPhoneSms::set_issend(bool value) {
  
  issend_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneSms.isSend)
}

// optional bool isSendSuccess = 4;
inline void TtPhoneSms::clear_issendsuccess() {
  issendsuccess_ = false;
}
inline bool TtPhoneSms::issendsuccess() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneSms.isSendSuccess)
  return issendsuccess_;
}
inline void TtPhoneSms::set_issendsuccess(bool value) {
  
  issendsuccess_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneSms.isSendSuccess)
}

// optional bool isReceiverSuccess = 5;
inline void TtPhoneSms::clear_isreceiversuccess() {
  isreceiversuccess_ = false;
}
inline bool TtPhoneSms::isreceiversuccess() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneSms.isReceiverSuccess)
  return isreceiversuccess_;
}
inline void TtPhoneSms::set_isreceiversuccess(bool value) {
  
  isreceiversuccess_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneSms.isReceiverSuccess)
}

// optional string messageText = 6;
inline void TtPhoneSms::clear_messagetext() {
  messagetext_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtPhoneSms::messagetext() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneSms.messageText)
  return messagetext_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneSms::set_messagetext(const ::std::string& value) {
  
  messagetext_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneSms.messageText)
}
inline void TtPhoneSms::set_messagetext(const char* value) {
  
  messagetext_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtPhoneSms.messageText)
}
inline void TtPhoneSms::set_messagetext(const char* value, size_t size) {
  
  messagetext_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtPhoneSms.messageText)
}
inline ::std::string* TtPhoneSms::mutable_messagetext() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtPhoneSms.messageText)
  return messagetext_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtPhoneSms::release_messagetext() {
  // @@protoc_insertion_point(field_release:phonedata.TtPhoneSms.messageText)
  
  return messagetext_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtPhoneSms::set_allocated_messagetext(::std::string* messagetext) {
  if (messagetext != NULL) {
    
  } else {
    
  }
  messagetext_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), messagetext);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtPhoneSms.messageText)
}

inline const TtPhoneSms* TtPhoneSms::internal_default_instance() {
  return &TtPhoneSms_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_TtPhoneSms_2eproto__INCLUDED
