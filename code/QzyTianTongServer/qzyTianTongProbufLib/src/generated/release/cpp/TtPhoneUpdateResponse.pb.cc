// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneUpdateResponse.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "TtPhoneUpdateResponse.pb.h"

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

const ::google::protobuf::Descriptor* UpdateResponse_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  UpdateResponse_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_TtPhoneUpdateResponse_2eproto() GOOGLE_ATTRIBUTE_COLD;
void protobuf_AssignDesc_TtPhoneUpdateResponse_2eproto() {
  protobuf_AddDesc_TtPhoneUpdateResponse_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "TtPhoneUpdateResponse.proto");
  GOOGLE_CHECK(file != NULL);
  UpdateResponse_descriptor_ = file->message_type(0);
  static const int UpdateResponse_offsets_[4] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(UpdateResponse, ip_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(UpdateResponse, isupdate_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(UpdateResponse, issendfilefinish_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(UpdateResponse, isupdatefinish_),
  };
  UpdateResponse_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      UpdateResponse_descriptor_,
      UpdateResponse::internal_default_instance(),
      UpdateResponse_offsets_,
      -1,
      -1,
      -1,
      sizeof(UpdateResponse),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(UpdateResponse, _internal_metadata_));
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_TtPhoneUpdateResponse_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) GOOGLE_ATTRIBUTE_COLD;
void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      UpdateResponse_descriptor_, UpdateResponse::internal_default_instance());
}

}  // namespace

void protobuf_ShutdownFile_TtPhoneUpdateResponse_2eproto() {
  UpdateResponse_default_instance_.Shutdown();
  delete UpdateResponse_reflection_;
}

void protobuf_InitDefaults_TtPhoneUpdateResponse_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::internal::GetEmptyString();
  UpdateResponse_default_instance_.DefaultConstruct();
  UpdateResponse_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_TtPhoneUpdateResponse_2eproto_once_);
void protobuf_InitDefaults_TtPhoneUpdateResponse_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_TtPhoneUpdateResponse_2eproto_once_,
                 &protobuf_InitDefaults_TtPhoneUpdateResponse_2eproto_impl);
}
void protobuf_AddDesc_TtPhoneUpdateResponse_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_TtPhoneUpdateResponse_2eproto();
  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n\033TtPhoneUpdateResponse.proto\022\tphonedata"
    "\"`\n\016UpdateResponse\022\n\n\002ip\030\001 \001(\t\022\020\n\010isUpda"
    "te\030\002 \001(\010\022\030\n\020isSendFileFinish\030\003 \001(\010\022\026\n\016is"
    "UpdateFinish\030\004 \001(\010B.\n\017com.qzy.tt.dataB\033T"
    "tPhoneUpdateResponseProtosb\006proto3", 194);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "TtPhoneUpdateResponse.proto", &protobuf_RegisterTypes);
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_TtPhoneUpdateResponse_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_TtPhoneUpdateResponse_2eproto_once_);
void protobuf_AddDesc_TtPhoneUpdateResponse_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_TtPhoneUpdateResponse_2eproto_once_,
                 &protobuf_AddDesc_TtPhoneUpdateResponse_2eproto_impl);
}
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_TtPhoneUpdateResponse_2eproto {
  StaticDescriptorInitializer_TtPhoneUpdateResponse_2eproto() {
    protobuf_AddDesc_TtPhoneUpdateResponse_2eproto();
  }
} static_descriptor_initializer_TtPhoneUpdateResponse_2eproto_;

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int UpdateResponse::kIpFieldNumber;
const int UpdateResponse::kIsUpdateFieldNumber;
const int UpdateResponse::kIsSendFileFinishFieldNumber;
const int UpdateResponse::kIsUpdateFinishFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

UpdateResponse::UpdateResponse()
  : ::google::protobuf::Message(), _internal_metadata_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_TtPhoneUpdateResponse_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:phonedata.UpdateResponse)
}

void UpdateResponse::InitAsDefaultInstance() {
}

UpdateResponse::UpdateResponse(const UpdateResponse& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:phonedata.UpdateResponse)
}

void UpdateResponse::SharedCtor() {
  ip_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  ::memset(&isupdate_, 0, reinterpret_cast<char*>(&isupdatefinish_) -
    reinterpret_cast<char*>(&isupdate_) + sizeof(isupdatefinish_));
  _cached_size_ = 0;
}

UpdateResponse::~UpdateResponse() {
  // @@protoc_insertion_point(destructor:phonedata.UpdateResponse)
  SharedDtor();
}

void UpdateResponse::SharedDtor() {
  ip_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}

void UpdateResponse::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* UpdateResponse::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return UpdateResponse_descriptor_;
}

const UpdateResponse& UpdateResponse::default_instance() {
  protobuf_InitDefaults_TtPhoneUpdateResponse_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<UpdateResponse> UpdateResponse_default_instance_;

UpdateResponse* UpdateResponse::New(::google::protobuf::Arena* arena) const {
  UpdateResponse* n = new UpdateResponse;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void UpdateResponse::Clear() {
// @@protoc_insertion_point(message_clear_start:phonedata.UpdateResponse)
#if defined(__clang__)
#define ZR_HELPER_(f) \
  _Pragma("clang diagnostic push") \
  _Pragma("clang diagnostic ignored \"-Winvalid-offsetof\"") \
  __builtin_offsetof(UpdateResponse, f) \
  _Pragma("clang diagnostic pop")
#else
#define ZR_HELPER_(f) reinterpret_cast<char*>(\
  &reinterpret_cast<UpdateResponse*>(16)->f)
#endif

#define ZR_(first, last) do {\
  ::memset(&(first), 0,\
           ZR_HELPER_(last) - ZR_HELPER_(first) + sizeof(last));\
} while (0)

  ZR_(isupdate_, isupdatefinish_);
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());

#undef ZR_HELPER_
#undef ZR_

}

bool UpdateResponse::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:phonedata.UpdateResponse)
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
            "phonedata.UpdateResponse.ip"));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(16)) goto parse_isUpdate;
        break;
      }

      // optional bool isUpdate = 2;
      case 2: {
        if (tag == 16) {
         parse_isUpdate:

          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &isupdate_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(24)) goto parse_isSendFileFinish;
        break;
      }

      // optional bool isSendFileFinish = 3;
      case 3: {
        if (tag == 24) {
         parse_isSendFileFinish:

          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &issendfilefinish_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(32)) goto parse_isUpdateFinish;
        break;
      }

      // optional bool isUpdateFinish = 4;
      case 4: {
        if (tag == 32) {
         parse_isUpdateFinish:

          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &isupdatefinish_)));
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
  // @@protoc_insertion_point(parse_success:phonedata.UpdateResponse)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:phonedata.UpdateResponse)
  return false;
#undef DO_
}

void UpdateResponse::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:phonedata.UpdateResponse)
  // optional string ip = 1;
  if (this->ip().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->ip().data(), this->ip().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.UpdateResponse.ip");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      1, this->ip(), output);
  }

  // optional bool isUpdate = 2;
  if (this->isupdate() != 0) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(2, this->isupdate(), output);
  }

  // optional bool isSendFileFinish = 3;
  if (this->issendfilefinish() != 0) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(3, this->issendfilefinish(), output);
  }

  // optional bool isUpdateFinish = 4;
  if (this->isupdatefinish() != 0) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(4, this->isupdatefinish(), output);
  }

  // @@protoc_insertion_point(serialize_end:phonedata.UpdateResponse)
}

::google::protobuf::uint8* UpdateResponse::InternalSerializeWithCachedSizesToArray(
    bool deterministic, ::google::protobuf::uint8* target) const {
  (void)deterministic; // Unused
  // @@protoc_insertion_point(serialize_to_array_start:phonedata.UpdateResponse)
  // optional string ip = 1;
  if (this->ip().size() > 0) {
    ::google::protobuf::internal::WireFormatLite::VerifyUtf8String(
      this->ip().data(), this->ip().length(),
      ::google::protobuf::internal::WireFormatLite::SERIALIZE,
      "phonedata.UpdateResponse.ip");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        1, this->ip(), target);
  }

  // optional bool isUpdate = 2;
  if (this->isupdate() != 0) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(2, this->isupdate(), target);
  }

  // optional bool isSendFileFinish = 3;
  if (this->issendfilefinish() != 0) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(3, this->issendfilefinish(), target);
  }

  // optional bool isUpdateFinish = 4;
  if (this->isupdatefinish() != 0) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(4, this->isupdatefinish(), target);
  }

  // @@protoc_insertion_point(serialize_to_array_end:phonedata.UpdateResponse)
  return target;
}

size_t UpdateResponse::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:phonedata.UpdateResponse)
  size_t total_size = 0;

  // optional string ip = 1;
  if (this->ip().size() > 0) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->ip());
  }

  // optional bool isUpdate = 2;
  if (this->isupdate() != 0) {
    total_size += 1 + 1;
  }

  // optional bool isSendFileFinish = 3;
  if (this->issendfilefinish() != 0) {
    total_size += 1 + 1;
  }

  // optional bool isUpdateFinish = 4;
  if (this->isupdatefinish() != 0) {
    total_size += 1 + 1;
  }

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void UpdateResponse::MergeFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_merge_from_start:phonedata.UpdateResponse)
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  const UpdateResponse* source =
      ::google::protobuf::internal::DynamicCastToGenerated<const UpdateResponse>(
          &from);
  if (source == NULL) {
  // @@protoc_insertion_point(generalized_merge_from_cast_fail:phonedata.UpdateResponse)
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
  // @@protoc_insertion_point(generalized_merge_from_cast_success:phonedata.UpdateResponse)
    UnsafeMergeFrom(*source);
  }
}

void UpdateResponse::MergeFrom(const UpdateResponse& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:phonedata.UpdateResponse)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void UpdateResponse::UnsafeMergeFrom(const UpdateResponse& from) {
  GOOGLE_DCHECK(&from != this);
  if (from.ip().size() > 0) {

    ip_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.ip_);
  }
  if (from.isupdate() != 0) {
    set_isupdate(from.isupdate());
  }
  if (from.issendfilefinish() != 0) {
    set_issendfilefinish(from.issendfilefinish());
  }
  if (from.isupdatefinish() != 0) {
    set_isupdatefinish(from.isupdatefinish());
  }
}

void UpdateResponse::CopyFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_copy_from_start:phonedata.UpdateResponse)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void UpdateResponse::CopyFrom(const UpdateResponse& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:phonedata.UpdateResponse)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool UpdateResponse::IsInitialized() const {

  return true;
}

void UpdateResponse::Swap(UpdateResponse* other) {
  if (other == this) return;
  InternalSwap(other);
}
void UpdateResponse::InternalSwap(UpdateResponse* other) {
  ip_.Swap(&other->ip_);
  std::swap(isupdate_, other->isupdate_);
  std::swap(issendfilefinish_, other->issendfilefinish_);
  std::swap(isupdatefinish_, other->isupdatefinish_);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata UpdateResponse::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = UpdateResponse_descriptor_;
  metadata.reflection = UpdateResponse_reflection_;
  return metadata;
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// UpdateResponse

// optional string ip = 1;
void UpdateResponse::clear_ip() {
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
const ::std::string& UpdateResponse::ip() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateResponse.ip)
  return ip_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void UpdateResponse::set_ip(const ::std::string& value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.UpdateResponse.ip)
}
void UpdateResponse::set_ip(const char* value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.UpdateResponse.ip)
}
void UpdateResponse::set_ip(const char* value, size_t size) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.UpdateResponse.ip)
}
::std::string* UpdateResponse::mutable_ip() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.UpdateResponse.ip)
  return ip_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
::std::string* UpdateResponse::release_ip() {
  // @@protoc_insertion_point(field_release:phonedata.UpdateResponse.ip)
  
  return ip_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
void UpdateResponse::set_allocated_ip(::std::string* ip) {
  if (ip != NULL) {
    
  } else {
    
  }
  ip_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ip);
  // @@protoc_insertion_point(field_set_allocated:phonedata.UpdateResponse.ip)
}

// optional bool isUpdate = 2;
void UpdateResponse::clear_isupdate() {
  isupdate_ = false;
}
bool UpdateResponse::isupdate() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateResponse.isUpdate)
  return isupdate_;
}
void UpdateResponse::set_isupdate(bool value) {
  
  isupdate_ = value;
  // @@protoc_insertion_point(field_set:phonedata.UpdateResponse.isUpdate)
}

// optional bool isSendFileFinish = 3;
void UpdateResponse::clear_issendfilefinish() {
  issendfilefinish_ = false;
}
bool UpdateResponse::issendfilefinish() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateResponse.isSendFileFinish)
  return issendfilefinish_;
}
void UpdateResponse::set_issendfilefinish(bool value) {
  
  issendfilefinish_ = value;
  // @@protoc_insertion_point(field_set:phonedata.UpdateResponse.isSendFileFinish)
}

// optional bool isUpdateFinish = 4;
void UpdateResponse::clear_isupdatefinish() {
  isupdatefinish_ = false;
}
bool UpdateResponse::isupdatefinish() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateResponse.isUpdateFinish)
  return isupdatefinish_;
}
void UpdateResponse::set_isupdatefinish(bool value) {
  
  isupdatefinish_ = value;
  // @@protoc_insertion_point(field_set:phonedata.UpdateResponse.isUpdateFinish)
}

inline const UpdateResponse* UpdateResponse::internal_default_instance() {
  return &UpdateResponse_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)
