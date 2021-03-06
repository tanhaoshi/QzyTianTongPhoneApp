// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtBeiDouStatus.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "TtBeiDouStatus.pb.h"

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

const ::google::protobuf::Descriptor* TtBeiDouStatus_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  TtBeiDouStatus_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_TtBeiDouStatus_2eproto() GOOGLE_ATTRIBUTE_COLD;
void protobuf_AssignDesc_TtBeiDouStatus_2eproto() {
  protobuf_AddDesc_TtBeiDouStatus_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "TtBeiDouStatus.proto");
  GOOGLE_CHECK(file != NULL);
  TtBeiDouStatus_descriptor_ = file->message_type(0);
  static const int TtBeiDouStatus_offsets_[1] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtBeiDouStatus, isbeidoustatus_),
  };
  TtBeiDouStatus_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      TtBeiDouStatus_descriptor_,
      TtBeiDouStatus::internal_default_instance(),
      TtBeiDouStatus_offsets_,
      -1,
      -1,
      -1,
      sizeof(TtBeiDouStatus),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtBeiDouStatus, _internal_metadata_));
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_TtBeiDouStatus_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) GOOGLE_ATTRIBUTE_COLD;
void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      TtBeiDouStatus_descriptor_, TtBeiDouStatus::internal_default_instance());
}

}  // namespace

void protobuf_ShutdownFile_TtBeiDouStatus_2eproto() {
  TtBeiDouStatus_default_instance_.Shutdown();
  delete TtBeiDouStatus_reflection_;
}

void protobuf_InitDefaults_TtBeiDouStatus_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  TtBeiDouStatus_default_instance_.DefaultConstruct();
  TtBeiDouStatus_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_TtBeiDouStatus_2eproto_once_);
void protobuf_InitDefaults_TtBeiDouStatus_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_TtBeiDouStatus_2eproto_once_,
                 &protobuf_InitDefaults_TtBeiDouStatus_2eproto_impl);
}
void protobuf_AddDesc_TtBeiDouStatus_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_TtBeiDouStatus_2eproto();
  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n\024TtBeiDouStatus.proto\022\tphonedata\"(\n\016TtB"
    "eiDouStatus\022\026\n\016isBeiDouStatus\030\001 \001(\010B\"\n\017c"
    "om.qzy.tt.dataB\017TtBeiDouStatussb\006proto3", 119);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "TtBeiDouStatus.proto", &protobuf_RegisterTypes);
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_TtBeiDouStatus_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_TtBeiDouStatus_2eproto_once_);
void protobuf_AddDesc_TtBeiDouStatus_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_TtBeiDouStatus_2eproto_once_,
                 &protobuf_AddDesc_TtBeiDouStatus_2eproto_impl);
}
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_TtBeiDouStatus_2eproto {
  StaticDescriptorInitializer_TtBeiDouStatus_2eproto() {
    protobuf_AddDesc_TtBeiDouStatus_2eproto();
  }
} static_descriptor_initializer_TtBeiDouStatus_2eproto_;

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int TtBeiDouStatus::kIsBeiDouStatusFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

TtBeiDouStatus::TtBeiDouStatus()
  : ::google::protobuf::Message(), _internal_metadata_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_TtBeiDouStatus_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:phonedata.TtBeiDouStatus)
}

void TtBeiDouStatus::InitAsDefaultInstance() {
}

TtBeiDouStatus::TtBeiDouStatus(const TtBeiDouStatus& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:phonedata.TtBeiDouStatus)
}

void TtBeiDouStatus::SharedCtor() {
  isbeidoustatus_ = false;
  _cached_size_ = 0;
}

TtBeiDouStatus::~TtBeiDouStatus() {
  // @@protoc_insertion_point(destructor:phonedata.TtBeiDouStatus)
  SharedDtor();
}

void TtBeiDouStatus::SharedDtor() {
}

void TtBeiDouStatus::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* TtBeiDouStatus::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return TtBeiDouStatus_descriptor_;
}

const TtBeiDouStatus& TtBeiDouStatus::default_instance() {
  protobuf_InitDefaults_TtBeiDouStatus_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<TtBeiDouStatus> TtBeiDouStatus_default_instance_;

TtBeiDouStatus* TtBeiDouStatus::New(::google::protobuf::Arena* arena) const {
  TtBeiDouStatus* n = new TtBeiDouStatus;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void TtBeiDouStatus::Clear() {
// @@protoc_insertion_point(message_clear_start:phonedata.TtBeiDouStatus)
  isbeidoustatus_ = false;
}

bool TtBeiDouStatus::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:phonedata.TtBeiDouStatus)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // optional bool isBeiDouStatus = 1;
      case 1: {
        if (tag == 8) {

          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &isbeidoustatus_)));
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
  // @@protoc_insertion_point(parse_success:phonedata.TtBeiDouStatus)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:phonedata.TtBeiDouStatus)
  return false;
#undef DO_
}

void TtBeiDouStatus::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:phonedata.TtBeiDouStatus)
  // optional bool isBeiDouStatus = 1;
  if (this->isbeidoustatus() != 0) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(1, this->isbeidoustatus(), output);
  }

  // @@protoc_insertion_point(serialize_end:phonedata.TtBeiDouStatus)
}

::google::protobuf::uint8* TtBeiDouStatus::InternalSerializeWithCachedSizesToArray(
    bool deterministic, ::google::protobuf::uint8* target) const {
  (void)deterministic; // Unused
  // @@protoc_insertion_point(serialize_to_array_start:phonedata.TtBeiDouStatus)
  // optional bool isBeiDouStatus = 1;
  if (this->isbeidoustatus() != 0) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(1, this->isbeidoustatus(), target);
  }

  // @@protoc_insertion_point(serialize_to_array_end:phonedata.TtBeiDouStatus)
  return target;
}

size_t TtBeiDouStatus::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:phonedata.TtBeiDouStatus)
  size_t total_size = 0;

  // optional bool isBeiDouStatus = 1;
  if (this->isbeidoustatus() != 0) {
    total_size += 1 + 1;
  }

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void TtBeiDouStatus::MergeFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_merge_from_start:phonedata.TtBeiDouStatus)
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  const TtBeiDouStatus* source =
      ::google::protobuf::internal::DynamicCastToGenerated<const TtBeiDouStatus>(
          &from);
  if (source == NULL) {
  // @@protoc_insertion_point(generalized_merge_from_cast_fail:phonedata.TtBeiDouStatus)
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
  // @@protoc_insertion_point(generalized_merge_from_cast_success:phonedata.TtBeiDouStatus)
    UnsafeMergeFrom(*source);
  }
}

void TtBeiDouStatus::MergeFrom(const TtBeiDouStatus& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:phonedata.TtBeiDouStatus)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void TtBeiDouStatus::UnsafeMergeFrom(const TtBeiDouStatus& from) {
  GOOGLE_DCHECK(&from != this);
  if (from.isbeidoustatus() != 0) {
    set_isbeidoustatus(from.isbeidoustatus());
  }
}

void TtBeiDouStatus::CopyFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_copy_from_start:phonedata.TtBeiDouStatus)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void TtBeiDouStatus::CopyFrom(const TtBeiDouStatus& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:phonedata.TtBeiDouStatus)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool TtBeiDouStatus::IsInitialized() const {

  return true;
}

void TtBeiDouStatus::Swap(TtBeiDouStatus* other) {
  if (other == this) return;
  InternalSwap(other);
}
void TtBeiDouStatus::InternalSwap(TtBeiDouStatus* other) {
  std::swap(isbeidoustatus_, other->isbeidoustatus_);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata TtBeiDouStatus::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = TtBeiDouStatus_descriptor_;
  metadata.reflection = TtBeiDouStatus_reflection_;
  return metadata;
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// TtBeiDouStatus

// optional bool isBeiDouStatus = 1;
void TtBeiDouStatus::clear_isbeidoustatus() {
  isbeidoustatus_ = false;
}
bool TtBeiDouStatus::isbeidoustatus() const {
  // @@protoc_insertion_point(field_get:phonedata.TtBeiDouStatus.isBeiDouStatus)
  return isbeidoustatus_;
}
void TtBeiDouStatus::set_isbeidoustatus(bool value) {
  
  isbeidoustatus_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtBeiDouStatus.isBeiDouStatus)
}

inline const TtBeiDouStatus* TtBeiDouStatus::internal_default_instance() {
  return &TtBeiDouStatus_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)
