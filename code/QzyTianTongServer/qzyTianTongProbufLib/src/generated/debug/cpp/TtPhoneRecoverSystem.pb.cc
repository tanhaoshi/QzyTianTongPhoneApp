// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneRecoverSystem.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "TtPhoneRecoverSystem.pb.h"

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

const ::google::protobuf::Descriptor* TtPhoneRecoverSystem_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  TtPhoneRecoverSystem_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_TtPhoneRecoverSystem_2eproto() GOOGLE_ATTRIBUTE_COLD;
void protobuf_AssignDesc_TtPhoneRecoverSystem_2eproto() {
  protobuf_AddDesc_TtPhoneRecoverSystem_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "TtPhoneRecoverSystem.proto");
  GOOGLE_CHECK(file != NULL);
  TtPhoneRecoverSystem_descriptor_ = file->message_type(0);
  static const int TtPhoneRecoverSystem_offsets_[2] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtPhoneRecoverSystem, ip_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtPhoneRecoverSystem, isrecover_),
  };
  TtPhoneRecoverSystem_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      TtPhoneRecoverSystem_descriptor_,
      TtPhoneRecoverSystem::internal_default_instance(),
      TtPhoneRecoverSystem_offsets_,
      -1,
      -1,
      -1,
      sizeof(TtPhoneRecoverSystem),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtPhoneRecoverSystem, _internal_metadata_));
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_TtPhoneRecoverSystem_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) GOOGLE_ATTRIBUTE_COLD;
void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      TtPhoneRecoverSystem_descriptor_, TtPhoneRecoverSystem::internal_default_instance());
}

}  // namespace

void protobuf_ShutdownFile_TtPhoneRecoverSystem_2eproto() {
  TtPhoneRecoverSystem_default_instance_.Shutdown();
  delete TtPhoneRecoverSystem_reflection_;
}

void protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::internal::GetEmptyString();
  TtPhoneRecoverSystem_default_instance_.DefaultConstruct();
  TtPhoneRecoverSystem_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto_once_);
void protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto_once_,
                 &protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto_impl);
}
void protobuf_AddDesc_TtPhoneRecoverSystem_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto();
  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n\032TtPhoneRecoverSystem.proto\022\tphonedata\""
    "5\n\024TtPhoneRecoverSystem\022\n\n\002ip\030\001 \001(\t\022\021\n\ti"
    "sRecover\030\002 \001(\010B-\n\017com.qzy.tt.dataB\032TtPho"
    "neRecoverSystemProtosb\006proto3", 149);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "TtPhoneRecoverSystem.proto", &protobuf_RegisterTypes);
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_TtPhoneRecoverSystem_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_TtPhoneRecoverSystem_2eproto_once_);
void protobuf_AddDesc_TtPhoneRecoverSystem_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_TtPhoneRecoverSystem_2eproto_once_,
                 &protobuf_AddDesc_TtPhoneRecoverSystem_2eproto_impl);
}
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_TtPhoneRecoverSystem_2eproto {
  StaticDescriptorInitializer_TtPhoneRecoverSystem_2eproto() {
    protobuf_AddDesc_TtPhoneRecoverSystem_2eproto();
  }
} static_descriptor_initializer_TtPhoneRecoverSystem_2eproto_;

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int TtPhoneRecoverSystem::kIpFieldNumber;
const int TtPhoneRecoverSystem::kIsRecoverFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

TtPhoneRecoverSystem::TtPhoneRecoverSystem()
  : ::google::protobuf::Message(), _internal_metadata_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:phonedata.TtPhoneRecoverSystem)
}

void TtPhoneRecoverSystem::InitAsDefaultInstance() {
}

TtPhoneRecoverSystem::TtPhoneRecoverSystem(const TtPhoneRecoverSystem& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:phonedata.TtPhoneRecoverSystem)
}

void TtPhoneRecoverSystem::SharedCtor() {
  ip_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  isrecover_ = false;
  _cached_size_ = 0;
}

TtPhoneRecoverSystem::~TtPhoneRecoverSystem() {
  // @@protoc_insertion_point(destructor:phonedata.TtPhoneRecoverSystem)
  SharedDtor();
}

void TtPhoneRecoverSystem::SharedDtor() {
  ip_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

void TtPhoneRecoverSystem::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* TtPhoneRecoverSystem::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return TtPhoneRecoverSystem_descriptor_;
}

const TtPhoneRecoverSystem& TtPhoneRecoverSystem::default_instance() {
  protobuf_InitDefaults_TtPhoneRecoverSystem_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<TtPhoneRecoverSystem> TtPhoneRecoverSystem_default_instance_;

TtPhoneRecoverSystem* TtPhoneRecoverSystem::New(::google::protobuf::Arena* arena) const {
  TtPhoneRecoverSystem* n = new TtPhoneRecoverSystem;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void TtPhoneRecoverSystem::Clear() {
// @@protoc_insertion_point(message_clear_start:phonedata.TtPhoneRecoverSystem)
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  isrecover_ = false;
}

bool TtPhoneRecoverSystem::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:phonedata.TtPhoneRecoverSystem)
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
            "phonedata.TtPhoneRecoverSystem.ip"));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(16)) goto parse_isRecover;
        break;
      }

      // optional bool isRecover = 2;
      case 2: {
        if (tag == 16) {
         parse_isRecover:

          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &isrecover_)));
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
  // @@protoc_insertion_point(parse_success:phonedata.TtPhoneRecoverSystem)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:phonedata.TtPhoneRecoverSystem)
  return false;
#undef DO_
}

void TtPhoneRecoverSystem::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:phonedata.TtPhoneRecoverSystem)
  // optional string ip = 1;
  if (this->ip().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->ip().data(), this->ip().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.TtPhoneRecoverSystem.ip");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      1, this->ip(), output);
  }

  // optional bool isRecover = 2;
  if (this->isrecover() != 0) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(2, this->isrecover(), output);
  }

  // @@protoc_insertion_point(serialize_end:phonedata.TtPhoneRecoverSystem)
}

::google::protobuf::uint8* TtPhoneRecoverSystem::InternalSerializeWithCachedSizesToArray(
    bool deterministic, ::google::protobuf::uint8* target) const {
  (void)deterministic; // Unused
  // @@protoc_insertion_point(serialize_to_array_start:phonedata.TtPhoneRecoverSystem)
  // optional string ip = 1;
  if (this->ip().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->ip().data(), this->ip().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.TtPhoneRecoverSystem.ip");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        1, this->ip(), target);
  }

  // optional bool isRecover = 2;
  if (this->isrecover() != 0) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(2, this->isrecover(), target);
  }

  // @@protoc_insertion_point(serialize_to_array_end:phonedata.TtPhoneRecoverSystem)
  return target;
}

size_t TtPhoneRecoverSystem::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:phonedata.TtPhoneRecoverSystem)
  size_t total_size = 0;

  // optional string ip = 1;
  if (this->ip().size() > 0) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->ip());
  }

  // optional bool isRecover = 2;
  if (this->isrecover() != 0) {
    total_size += 1 + 1;
  }

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void TtPhoneRecoverSystem::MergeFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_merge_from_start:phonedata.TtPhoneRecoverSystem)
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  const TtPhoneRecoverSystem* source =
      ::google::protobuf::internal::DynamicCastToGenerated<const TtPhoneRecoverSystem>(
          &from);
  if (source == NULL) {
  // @@protoc_insertion_point(generalized_merge_from_cast_fail:phonedata.TtPhoneRecoverSystem)
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
  // @@protoc_insertion_point(generalized_merge_from_cast_success:phonedata.TtPhoneRecoverSystem)
    UnsafeMergeFrom(*source);
  }
}

void TtPhoneRecoverSystem::MergeFrom(const TtPhoneRecoverSystem& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:phonedata.TtPhoneRecoverSystem)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void TtPhoneRecoverSystem::UnsafeMergeFrom(const TtPhoneRecoverSystem& from) {
  GOOGLE_DCHECK(&from != this);
  if (from.ip().size() > 0) {

    ip_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.ip_);
  }
  if (from.isrecover() != 0) {
    set_isrecover(from.isrecover());
  }
}

void TtPhoneRecoverSystem::CopyFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_copy_from_start:phonedata.TtPhoneRecoverSystem)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void TtPhoneRecoverSystem::CopyFrom(const TtPhoneRecoverSystem& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:phonedata.TtPhoneRecoverSystem)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool TtPhoneRecoverSystem::IsInitialized() const {

  return true;
}

void TtPhoneRecoverSystem::Swap(TtPhoneRecoverSystem* other) {
  if (other == this) return;
  InternalSwap(other);
}
void TtPhoneRecoverSystem::InternalSwap(TtPhoneRecoverSystem* other) {
  ip_.Swap(&other->ip_);
  std::swap(isrecover_, other->isrecover_);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata TtPhoneRecoverSystem::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = TtPhoneRecoverSystem_descriptor_;
  metadata.reflection = TtPhoneRecoverSystem_reflection_;
  return metadata;
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// TtPhoneRecoverSystem

// optional string ip = 1;
void TtPhoneRecoverSystem::clear_ip() {
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
const ::std::string& TtPhoneRecoverSystem::ip() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneRecoverSystem.ip)
  return ip_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void TtPhoneRecoverSystem::set_ip(const ::std::string& value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneRecoverSystem.ip)
}
void TtPhoneRecoverSystem::set_ip(const char* value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.TtPhoneRecoverSystem.ip)
}
void TtPhoneRecoverSystem::set_ip(const char* value, size_t size) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.TtPhoneRecoverSystem.ip)
}
::std::string* TtPhoneRecoverSystem::mutable_ip() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.TtPhoneRecoverSystem.ip)
  return ip_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
::std::string* TtPhoneRecoverSystem::release_ip() {
  // @@protoc_insertion_point(field_release:phonedata.TtPhoneRecoverSystem.ip)
  
  return ip_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void TtPhoneRecoverSystem::set_allocated_ip(::std::string* ip) {
  if (ip != NULL) {
    
  } else {
    
  }
  ip_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ip);
  // @@protoc_insertion_point(field_set_allocated:phonedata.TtPhoneRecoverSystem.ip)
}

// optional bool isRecover = 2;
void TtPhoneRecoverSystem::clear_isrecover() {
  isrecover_ = false;
}
bool TtPhoneRecoverSystem::isrecover() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneRecoverSystem.isRecover)
  return isrecover_;
}
void TtPhoneRecoverSystem::set_isrecover(bool value) {
  
  isrecover_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneRecoverSystem.isRecover)
}

inline const TtPhoneRecoverSystem* TtPhoneRecoverSystem::internal_default_instance() {
  return &TtPhoneRecoverSystem_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)
