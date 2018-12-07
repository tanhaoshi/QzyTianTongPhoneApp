// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtCallRecord.proto

#ifndef PROTOBUF_TtCallRecord_2eproto__INCLUDED
#define PROTOBUF_TtCallRecord_2eproto__INCLUDED

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
void protobuf_AddDesc_TtCallRecord_2eproto();
void protobuf_InitDefaults_TtCallRecord_2eproto();
void protobuf_AssignDesc_TtCallRecord_2eproto();
void protobuf_ShutdownFile_TtCallRecord_2eproto();

class TtCallRecordProto;
class TtCallRecordProto_CallRecord;

// ===================================================================

class TtCallRecordProto_CallRecord : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.TtCallRecordProto.CallRecord) */ {
 public:
  TtCallRecordProto_CallRecord();
  virtual ~TtCallRecordProto_CallRecord();

  TtCallRecordProto_CallRecord(const TtCallRecordProto_CallRecord& from);

  inline TtCallRecordProto_CallRecord& operator=(const TtCallRecordProto_CallRecord& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const TtCallRecordProto_CallRecord& default_instance();

  static const TtCallRecordProto_CallRecord* internal_default_instance();

  void Swap(TtCallRecordProto_CallRecord* other);

  // implements Message ----------------------------------------------

  inline TtCallRecordProto_CallRecord* New() const { return New(NULL); }

  TtCallRecordProto_CallRecord* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const TtCallRecordProto_CallRecord& from);
  void MergeFrom(const TtCallRecordProto_CallRecord& from);
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
  void InternalSwap(TtCallRecordProto_CallRecord* other);
  void UnsafeMergeFrom(const TtCallRecordProto_CallRecord& from);
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

  // optional int64 id = 1;
  void clear_id();
  static const int kIdFieldNumber = 1;
  ::google::protobuf::int64 id() const;
  void set_id(::google::protobuf::int64 value);

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

  // optional string name = 3;
  void clear_name();
  static const int kNameFieldNumber = 3;
  const ::std::string& name() const;
  void set_name(const ::std::string& value);
  void set_name(const char* value);
  void set_name(const char* value, size_t size);
  ::std::string* mutable_name();
  ::std::string* release_name();
  void set_allocated_name(::std::string* name);

  // optional string address = 4;
  void clear_address();
  static const int kAddressFieldNumber = 4;
  const ::std::string& address() const;
  void set_address(const ::std::string& value);
  void set_address(const char* value);
  void set_address(const char* value, size_t size);
  ::std::string* mutable_address();
  ::std::string* release_address();
  void set_allocated_address(::std::string* address);

  // optional int32 type = 5;
  void clear_type();
  static const int kTypeFieldNumber = 5;
  ::google::protobuf::int32 type() const;
  void set_type(::google::protobuf::int32 value);

  // optional string date = 6;
  void clear_date();
  static const int kDateFieldNumber = 6;
  const ::std::string& date() const;
  void set_date(const ::std::string& value);
  void set_date(const char* value);
  void set_date(const char* value, size_t size);
  ::std::string* mutable_date();
  ::std::string* release_date();
  void set_allocated_date(::std::string* date);

  // optional int64 duration = 7;
  void clear_duration();
  static const int kDurationFieldNumber = 7;
  ::google::protobuf::int64 duration() const;
  void set_duration(::google::protobuf::int64 value);

  // @@protoc_insertion_point(class_scope:phonedata.TtCallRecordProto.CallRecord)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::ArenaStringPtr phonenumber_;
  ::google::protobuf::internal::ArenaStringPtr name_;
  ::google::protobuf::internal::ArenaStringPtr address_;
  ::google::protobuf::internal::ArenaStringPtr date_;
  ::google::protobuf::int64 id_;
  ::google::protobuf::int64 duration_;
  ::google::protobuf::int32 type_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtCallRecord_2eproto_impl();
  friend void  protobuf_AddDesc_TtCallRecord_2eproto_impl();
  friend void protobuf_AssignDesc_TtCallRecord_2eproto();
  friend void protobuf_ShutdownFile_TtCallRecord_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<TtCallRecordProto_CallRecord> TtCallRecordProto_CallRecord_default_instance_;

// -------------------------------------------------------------------

class TtCallRecordProto : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.TtCallRecordProto) */ {
 public:
  TtCallRecordProto();
  virtual ~TtCallRecordProto();

  TtCallRecordProto(const TtCallRecordProto& from);

  inline TtCallRecordProto& operator=(const TtCallRecordProto& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const TtCallRecordProto& default_instance();

  static const TtCallRecordProto* internal_default_instance();

  void Swap(TtCallRecordProto* other);

  // implements Message ----------------------------------------------

  inline TtCallRecordProto* New() const { return New(NULL); }

  TtCallRecordProto* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const TtCallRecordProto& from);
  void MergeFrom(const TtCallRecordProto& from);
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
  void InternalSwap(TtCallRecordProto* other);
  void UnsafeMergeFrom(const TtCallRecordProto& from);
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

  typedef TtCallRecordProto_CallRecord CallRecord;

  // accessors -------------------------------------------------------

  // optional bool request = 1;
  void clear_request();
  static const int kRequestFieldNumber = 1;
  bool request() const;
  void set_request(bool value);

  // optional bool response = 2;
  void clear_response();
  static const int kResponseFieldNumber = 2;
  bool response() const;
  void set_response(bool value);

  // repeated .phonedata.TtCallRecordProto.CallRecord callRecord = 3;
  int callrecord_size() const;
  void clear_callrecord();
  static const int kCallRecordFieldNumber = 3;
  const ::phonedata::TtCallRecordProto_CallRecord& callrecord(int index) const;
  ::phonedata::TtCallRecordProto_CallRecord* mutable_callrecord(int index);
  ::phonedata::TtCallRecordProto_CallRecord* add_callrecord();
  ::google::protobuf::RepeatedPtrField< ::phonedata::TtCallRecordProto_CallRecord >*
      mutable_callrecord();
  const ::google::protobuf::RepeatedPtrField< ::phonedata::TtCallRecordProto_CallRecord >&
      callrecord() const;

  // @@protoc_insertion_point(class_scope:phonedata.TtCallRecordProto)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::RepeatedPtrField< ::phonedata::TtCallRecordProto_CallRecord > callrecord_;
  bool request_;
  bool response_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtCallRecord_2eproto_impl();
  friend void  protobuf_AddDesc_TtCallRecord_2eproto_impl();
  friend void protobuf_AssignDesc_TtCallRecord_2eproto();
  friend void protobuf_ShutdownFile_TtCallRecord_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<TtCallRecordProto> TtCallRecordProto_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// TtCallRecordProto_CallRecord

// optional int64 id = 1;
inline void TtCallRecordProto_CallRecord::clear_id() {
  id_ = GOOGLE_LONGLONG(0);
}
inline ::google::protobuf::int64 TtCallRecordProto_CallRecord::id() const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.CallRecord.id)
  return id_;
}
inline void TtCallRecordProto_CallRecord::set_id(::google::protobuf::int64 value) {
  
  id_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtCallRecordProto.CallRecord.id)
}

// optional string phoneNumber = 2;
inline void TtCallRecordProto_CallRecord::clear_phonenumber() {
  phonenumber_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtCallRecordProto_CallRecord::phonenumber() const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.CallRecord.phoneNumber)
  return phonenumber_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtCallRecordProto_CallRecord::set_phonenumber(const ::std::string& value) {
  
  phonenumber_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtCallRecordProto.CallRecord.phoneNumber)
}
inline void TtCallRecordProto_CallRecord::set_phonenumber(const char* value) {
  
  phonenumber_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtCallRecordProto.CallRecord.phoneNumber)
}
inline void TtCallRecordProto_CallRecord::set_phonenumber(const char* value, size_t size) {
  
  phonenumber_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtCallRecordProto.CallRecord.phoneNumber)
}
inline ::std::string* TtCallRecordProto_CallRecord::mutable_phonenumber() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtCallRecordProto.CallRecord.phoneNumber)
  return phonenumber_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtCallRecordProto_CallRecord::release_phonenumber() {
  // @@protoc_insertion_point(field_release:phonedata.TtCallRecordProto.CallRecord.phoneNumber)
  
  return phonenumber_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtCallRecordProto_CallRecord::set_allocated_phonenumber(::std::string* phonenumber) {
  if (phonenumber != NULL) {
    
  } else {
    
  }
  phonenumber_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), phonenumber);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtCallRecordProto.CallRecord.phoneNumber)
}

// optional string name = 3;
inline void TtCallRecordProto_CallRecord::clear_name() {
  name_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtCallRecordProto_CallRecord::name() const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.CallRecord.name)
  return name_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtCallRecordProto_CallRecord::set_name(const ::std::string& value) {
  
  name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtCallRecordProto.CallRecord.name)
}
inline void TtCallRecordProto_CallRecord::set_name(const char* value) {
  
  name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtCallRecordProto.CallRecord.name)
}
inline void TtCallRecordProto_CallRecord::set_name(const char* value, size_t size) {
  
  name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtCallRecordProto.CallRecord.name)
}
inline ::std::string* TtCallRecordProto_CallRecord::mutable_name() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtCallRecordProto.CallRecord.name)
  return name_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtCallRecordProto_CallRecord::release_name() {
  // @@protoc_insertion_point(field_release:phonedata.TtCallRecordProto.CallRecord.name)
  
  return name_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtCallRecordProto_CallRecord::set_allocated_name(::std::string* name) {
  if (name != NULL) {
    
  } else {
    
  }
  name_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), name);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtCallRecordProto.CallRecord.name)
}

// optional string address = 4;
inline void TtCallRecordProto_CallRecord::clear_address() {
  address_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtCallRecordProto_CallRecord::address() const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.CallRecord.address)
  return address_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtCallRecordProto_CallRecord::set_address(const ::std::string& value) {
  
  address_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtCallRecordProto.CallRecord.address)
}
inline void TtCallRecordProto_CallRecord::set_address(const char* value) {
  
  address_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtCallRecordProto.CallRecord.address)
}
inline void TtCallRecordProto_CallRecord::set_address(const char* value, size_t size) {
  
  address_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtCallRecordProto.CallRecord.address)
}
inline ::std::string* TtCallRecordProto_CallRecord::mutable_address() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtCallRecordProto.CallRecord.address)
  return address_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtCallRecordProto_CallRecord::release_address() {
  // @@protoc_insertion_point(field_release:phonedata.TtCallRecordProto.CallRecord.address)
  
  return address_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtCallRecordProto_CallRecord::set_allocated_address(::std::string* address) {
  if (address != NULL) {
    
  } else {
    
  }
  address_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), address);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtCallRecordProto.CallRecord.address)
}

// optional int32 type = 5;
inline void TtCallRecordProto_CallRecord::clear_type() {
  type_ = 0;
}
inline ::google::protobuf::int32 TtCallRecordProto_CallRecord::type() const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.CallRecord.type)
  return type_;
}
inline void TtCallRecordProto_CallRecord::set_type(::google::protobuf::int32 value) {
  
  type_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtCallRecordProto.CallRecord.type)
}

// optional string date = 6;
inline void TtCallRecordProto_CallRecord::clear_date() {
  date_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& TtCallRecordProto_CallRecord::date() const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.CallRecord.date)
  return date_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtCallRecordProto_CallRecord::set_date(const ::std::string& value) {
  
  date_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtCallRecordProto.CallRecord.date)
}
inline void TtCallRecordProto_CallRecord::set_date(const char* value) {
  
  date_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtCallRecordProto.CallRecord.date)
}
inline void TtCallRecordProto_CallRecord::set_date(const char* value, size_t size) {
  
  date_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtCallRecordProto.CallRecord.date)
}
inline ::std::string* TtCallRecordProto_CallRecord::mutable_date() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtCallRecordProto.CallRecord.date)
  return date_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* TtCallRecordProto_CallRecord::release_date() {
  // @@protoc_insertion_point(field_release:phonedata.TtCallRecordProto.CallRecord.date)
  
  return date_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void TtCallRecordProto_CallRecord::set_allocated_date(::std::string* date) {
  if (date != NULL) {
    
  } else {
    
  }
  date_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), date);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtCallRecordProto.CallRecord.date)
}

// optional int64 duration = 7;
inline void TtCallRecordProto_CallRecord::clear_duration() {
  duration_ = GOOGLE_LONGLONG(0);
}
inline ::google::protobuf::int64 TtCallRecordProto_CallRecord::duration() const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.CallRecord.duration)
  return duration_;
}
inline void TtCallRecordProto_CallRecord::set_duration(::google::protobuf::int64 value) {
  
  duration_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtCallRecordProto.CallRecord.duration)
}

inline const TtCallRecordProto_CallRecord* TtCallRecordProto_CallRecord::internal_default_instance() {
  return &TtCallRecordProto_CallRecord_default_instance_.get();
}
// -------------------------------------------------------------------

// TtCallRecordProto

// optional bool request = 1;
inline void TtCallRecordProto::clear_request() {
  request_ = false;
}
inline bool TtCallRecordProto::request() const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.request)
  return request_;
}
inline void TtCallRecordProto::set_request(bool value) {
  
  request_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtCallRecordProto.request)
}

// optional bool response = 2;
inline void TtCallRecordProto::clear_response() {
  response_ = false;
}
inline bool TtCallRecordProto::response() const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.response)
  return response_;
}
inline void TtCallRecordProto::set_response(bool value) {
  
  response_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtCallRecordProto.response)
}

// repeated .phonedata.TtCallRecordProto.CallRecord callRecord = 3;
inline int TtCallRecordProto::callrecord_size() const {
  return callrecord_.size();
}
inline void TtCallRecordProto::clear_callrecord() {
  callrecord_.Clear();
}
inline const ::phonedata::TtCallRecordProto_CallRecord& TtCallRecordProto::callrecord(int index) const {
  // @@protoc_insertion_point(field_get:phonedata.TtCallRecordProto.callRecord)
  return callrecord_.Get(index);
}
inline ::phonedata::TtCallRecordProto_CallRecord* TtCallRecordProto::mutable_callrecord(int index) {
  // @@protoc_insertion_point(field_mutable:phonedata.TtCallRecordProto.callRecord)
  return callrecord_.Mutable(index);
}
inline ::phonedata::TtCallRecordProto_CallRecord* TtCallRecordProto::add_callrecord() {
  // @@protoc_insertion_point(field_add:phonedata.TtCallRecordProto.callRecord)
  return callrecord_.Add();
}
inline ::google::protobuf::RepeatedPtrField< ::phonedata::TtCallRecordProto_CallRecord >*
TtCallRecordProto::mutable_callrecord() {
  // @@protoc_insertion_point(field_mutable_list:phonedata.TtCallRecordProto.callRecord)
  return &callrecord_;
}
inline const ::google::protobuf::RepeatedPtrField< ::phonedata::TtCallRecordProto_CallRecord >&
TtCallRecordProto::callrecord() const {
  // @@protoc_insertion_point(field_list:phonedata.TtCallRecordProto.callRecord)
  return callrecord_;
}

inline const TtCallRecordProto* TtCallRecordProto::internal_default_instance() {
  return &TtCallRecordProto_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS
// -------------------------------------------------------------------


// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_TtCallRecord_2eproto__INCLUDED
