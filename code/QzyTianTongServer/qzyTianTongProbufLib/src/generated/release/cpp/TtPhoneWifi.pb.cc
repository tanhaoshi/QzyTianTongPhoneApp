// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneWifi.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "TtPhoneWifi.pb.h"

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/port.h>
#include <google/protobuf/stubs/once.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/reflection_ops.h>
#include <google/protobuf/wire_format.h>
// @@protoc_insertion_point(includes)

namespace phonedata {

namespace {

const ::google::protobuf::Descriptor* TtWifi_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  TtWifi_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_TtPhoneWifi_2eproto() GOOGLE_ATTRIBUTE_COLD;
void protobuf_AssignDesc_TtPhoneWifi_2eproto() {
  protobuf_AddDesc_TtPhoneWifi_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "TtPhoneWifi.proto");
  GOOGLE_CHECK(file != NULL);
  TtWifi_descriptor_ = file->message_type(0);
  static const int TtWifi_offsets_[4] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtWifi, ip_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtWifi, ssid_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtWifi, passwd_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtWifi, isset_),
  };
  TtWifi_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      TtWifi_descriptor_,
      TtWifi::internal_default_instance(),
      TtWifi_offsets_,
      -1,
      -1,
      -1,
      sizeof(TtWifi),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtWifi, _internal_metadata_));
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_TtPhoneWifi_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) GOOGLE_ATTRIBUTE_COLD;
void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      TtWifi_descriptor_, TtWifi::internal_default_instance());
}

}  // namespace

void protobuf_ShutdownFile_TtPhoneWifi_2eproto() {
  TtWifi_default_instance_.Shutdown();
  delete TtWifi_reflection_;
}

void protobuf_InitDefaults_TtPhoneWifi_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::internal::GetEmptyString();
  TtWifi_default_instance_.DefaultConstruct();
  TtWifi_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_TtPhoneWifi_2eproto_once_);
void protobuf_InitDefaults_TtPhoneWifi_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_TtPhoneWifi_2eproto_once_,
                 &protobuf_InitDefaults_TtPhoneWifi_2eproto_impl);
}
void protobuf_AddDesc_TtPhoneWifi_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_TtPhoneWifi_2eproto();
  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n\021TtPhoneWifi.proto\022\tphonedata\"A\n\006TtWifi"
    "\022\n\n\002ip\030\001 \001(\t\022\014\n\004ssid\030\002 \001(\t\022\016\n\006passwd\030\003 \001"
    "(\t\022\r\n\005isSet\030\004 \001(\010B$\n\017com.qzy.tt.dataB\021Tt"
    "PhoneWifiProtosb\006proto3", 143);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "TtPhoneWifi.proto", &protobuf_RegisterTypes);
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_TtPhoneWifi_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_TtPhoneWifi_2eproto_once_);
void protobuf_AddDesc_TtPhoneWifi_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_TtPhoneWifi_2eproto_once_,
                 &protobuf_AddDesc_TtPhoneWifi_2eproto_impl);
}
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_TtPhoneWifi_2eproto {
  StaticDescriptorInitializer_TtPhoneWifi_2eproto() {
    protobuf_AddDesc_TtPhoneWifi_2eproto();
  }
} static_descriptor_initializer_TtPhoneWifi_2eproto_;

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int TtWifi::kIpFieldNumber;
const int TtWifi::kSsidFieldNumber;
const int TtWifi::kPasswdFieldNumber;
const int TtWifi::kIsSetFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

TtWifi::TtWifi()
  : ::google::protobuf::Message(), _internal_metadata_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_TtPhoneWifi_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:phonedata.TtWifi)
}

void TtWifi::InitAsDefaultInstance() {
}

TtWifi::TtWifi(const TtWifi& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:phonedata.TtWifi)
}

void TtWifi::SharedCtor() {
  ip_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  ssid_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  passwd_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  isset_ = false;
  _cached_size_ = 0;
}

TtWifi::~TtWifi() {
  // @@protoc_insertion_point(destructor:phonedata.TtWifi)
  SharedDtor();
}

void TtWifi::SharedDtor() {
  ip_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  ssid_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  passwd_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

void TtWifi::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* TtWifi::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return TtWifi_descriptor_;
}

const TtWifi& TtWifi::default_instance() {
  protobuf_InitDefaults_TtPhoneWifi_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<TtWifi> TtWifi_default_instance_;

TtWifi* TtWifi::New(::google::protobuf::Arena* arena) const {
  TtWifi* n = new TtWifi;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void TtWifi::Clear() {
// @@protoc_insertion_point(message_clear_start:phonedata.TtWifi)
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  ssid_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  passwd_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  isset_ = false;
}

bool TtWifi::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:phonedata.TtWifi)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // optional string ip = 1;
      case 1: {
        if (tag == 10) {
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_ip()));
          DO_(::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
            this->ip().data(), this->ip().length(),
            ::google::protobuf::internal::WireFormatLite::PARSE,
            "phonedata.TtWifi.ip"));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(18)) goto parse_ssid;
        break;
      }

      // optional string ssid = 2;
      case 2: {
        if (tag == 18) {
         parse_ssid:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_ssid()));
          DO_(::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
            this->ssid().data(), this->ssid().length(),
            ::google::protobuf::internal::WireFormatLite::PARSE,
            "phonedata.TtWifi.ssid"));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(26)) goto parse_passwd;
        break;
      }

      // optional string passwd = 3;
      case 3: {
        if (tag == 26) {
         parse_passwd:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_passwd()));
          DO_(::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
            this->passwd().data(), this->passwd().length(),
            ::google::protobuf::internal::WireFormatLite::PARSE,
            "phonedata.TtWifi.passwd"));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(32)) goto parse_isSet;
        break;
      }

      // optional bool isSet = 4;
      case 4: {
        if (tag == 32) {
         parse_isSet:

          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &isset_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectAtEnd()) goto success;
        break;
      }

      default: {
      handle_unusual:
        if (tag == 0 ||
            ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          goto success;
        }
        DO_(::google::protobuf::internal::WireFormatLite::SkipField(input, tag));
        break;
      }
    }
  }
success:
  // @@protoc_insertion_point(parse_success:phonedata.TtWifi)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:phonedata.TtWifi)
  return false;
#undef DO_
}

void TtWifi::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:phonedata.TtWifi)
  // optional string ip = 1;
  if (this->ip().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->ip().data(), this->ip().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.TtWifi.ip");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      1, this->ip(), output);
  }

  // optional string ssid = 2;
  if (this->ssid().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->ssid().data(), this->ssid().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.TtWifi.ssid");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      2, this->ssid(), output);
  }

  // optional string passwd = 3;
  if (this->passwd().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->passwd().data(), this->passwd().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.TtWifi.passwd");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      3, this->passwd(), output);
  }

  // optional bool isSet = 4;
  if (this->isset() != 0) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(4, this->isset(), output);
  }

  // @@protoc_insertion_point(serialize_end:phonedata.TtWifi)
}

::google::protobuf::uint8* TtWifi::InternalSerializeWithCachedSizesToArray(
    bool deterministic, ::google::protobuf::uint8* target) const {
  (void)deterministic; // Unused
  // @@protoc_insertion_point(serialize_to_array_start:phonedata.TtWifi)
  // optional string ip = 1;
  if (this->ip().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->ip().data(), this->ip().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.TtWifi.ip");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        1, this->ip(), target);
  }

  // optional string ssid = 2;
  if (this->ssid().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->ssid().data(), this->ssid().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.TtWifi.ssid");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        2, this->ssid(), target);
  }

  // optional string passwd = 3;
  if (this->passwd().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->passwd().data(), this->passwd().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.TtWifi.passwd");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        3, this->passwd(), target);
  }

  // optional bool isSet = 4;
  if (this->isset() != 0) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(4, this->isset(), target);
  }

  // @@protoc_insertion_point(serialize_to_array_end:phonedata.TtWifi)
  return target;
}

size_t TtWifi::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:phonedata.TtWifi)
  size_t total_size = 0;

  // optional string ip = 1;
  if (this->ip().size() > 0) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->ip());
  }

  // optional string ssid = 2;
  if (this->ssid().size() > 0) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->ssid());
  }

  // optional string passwd = 3;
  if (this->passwd().size() > 0) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->passwd());
  }

  // optional bool isSet = 4;
  if (this->isset() != 0) {
    total_size += 1 + 1;
  }

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void TtWifi::MergeFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_merge_from_start:phonedata.TtWifi)
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  const TtWifi* source =
      ::google::protobuf::internal::DynamicCastToGenerated<const TtWifi>(
          &from);
  if (source == NULL) {
  // @@protoc_insertion_point(generalized_merge_from_cast_fail:phonedata.TtWifi)
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
  // @@protoc_insertion_point(generalized_merge_from_cast_success:phonedata.TtWifi)
    UnsafeMergeFrom(*source);
  }
}

void TtWifi::MergeFrom(const TtWifi& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:phonedata.TtWifi)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void TtWifi::UnsafeMergeFrom(const TtWifi& from) {
  GOOGLE_DCHECK(&from != this);
  if (from.ip().size() > 0) {

    ip_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.ip_);
  }
  if (from.ssid().size() > 0) {

    ssid_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.ssid_);
  }
  if (from.passwd().size() > 0) {

    passwd_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.passwd_);
  }
  if (from.isset() != 0) {
    set_isset(from.isset());
  }
}

void TtWifi::CopyFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_copy_from_start:phonedata.TtWifi)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void TtWifi::CopyFrom(const TtWifi& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:phonedata.TtWifi)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool TtWifi::IsInitialized() const {

  return true;
}

void TtWifi::Swap(TtWifi* other) {
  if (other == this) return;
  InternalSwap(other);
}
void TtWifi::InternalSwap(TtWifi* other) {
  ip_.Swap(&other->ip_);
  ssid_.Swap(&other->ssid_);
  passwd_.Swap(&other->passwd_);
  std::swap(isset_, other->isset_);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata TtWifi::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = TtWifi_descriptor_;
  metadata.reflection = TtWifi_reflection_;
  return metadata;
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// TtWifi

// optional string ip = 1;
void TtWifi::clear_ip() {
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
const ::std::string& TtWifi::ip() const {
  // @@protoc_insertion_point(field_get:phonedata.TtWifi.ip)
  return ip_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void TtWifi::set_ip(const ::std::string& value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtWifi.ip)
}
void TtWifi::set_ip(const char* value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtWifi.ip)
}
void TtWifi::set_ip(const char* value, size_t size) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtWifi.ip)
}
::std::string* TtWifi::mutable_ip() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtWifi.ip)
  return ip_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
::std::string* TtWifi::release_ip() {
  // @@protoc_insertion_point(field_release:phonedata.TtWifi.ip)
  
  return ip_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void TtWifi::set_allocated_ip(::std::string* ip) {
  if (ip != NULL) {
    
  } else {
    
  }
  ip_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ip);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtWifi.ip)
}

// optional string ssid = 2;
void TtWifi::clear_ssid() {
  ssid_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
const ::std::string& TtWifi::ssid() const {
  // @@protoc_insertion_point(field_get:phonedata.TtWifi.ssid)
  return ssid_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void TtWifi::set_ssid(const ::std::string& value) {
  
  ssid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtWifi.ssid)
}
void TtWifi::set_ssid(const char* value) {
  
  ssid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtWifi.ssid)
}
void TtWifi::set_ssid(const char* value, size_t size) {
  
  ssid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtWifi.ssid)
}
::std::string* TtWifi::mutable_ssid() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtWifi.ssid)
  return ssid_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
::std::string* TtWifi::release_ssid() {
  // @@protoc_insertion_point(field_release:phonedata.TtWifi.ssid)
  
  return ssid_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void TtWifi::set_allocated_ssid(::std::string* ssid) {
  if (ssid != NULL) {
    
  } else {
    
  }
  ssid_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ssid);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtWifi.ssid)
}

// optional string passwd = 3;
void TtWifi::clear_passwd() {
  passwd_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
const ::std::string& TtWifi::passwd() const {
  // @@protoc_insertion_point(field_get:phonedata.TtWifi.passwd)
  return passwd_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void TtWifi::set_passwd(const ::std::string& value) {
  
  passwd_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtWifi.passwd)
}
void TtWifi::set_passwd(const char* value) {
  
  passwd_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtWifi.passwd)
}
void TtWifi::set_passwd(const char* value, size_t size) {
  
  passwd_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtWifi.passwd)
}
::std::string* TtWifi::mutable_passwd() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtWifi.passwd)
  return passwd_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
::std::string* TtWifi::release_passwd() {
  // @@protoc_insertion_point(field_release:phonedata.TtWifi.passwd)
  
  return passwd_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void TtWifi::set_allocated_passwd(::std::string* passwd) {
  if (passwd != NULL) {
    
  } else {
    
  }
  passwd_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), passwd);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtWifi.passwd)
}

// optional bool isSet = 4;
void TtWifi::clear_isset() {
  isset_ = false;
}
bool TtWifi::isset() const {
  // @@protoc_insertion_point(field_get:phonedata.TtWifi.isSet)
  return isset_;
}
void TtWifi::set_isset(bool value) {
  
  isset_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtWifi.isSet)
}

inline const TtWifi* TtWifi::internal_default_instance() {
  return &TtWifi_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)
